package com.example.skyjournal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first

// id == 0 -> new observation, otherwise -> edit existing
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    viewModel: ObservationViewModel,
    id: Long,
    onDone: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var notes by rememberSaveable { mutableStateOf("") }
    var loaded by rememberSaveable { mutableStateOf(id == 0L) }

    LaunchedEffect(id) {
        if (!loaded) {
            viewModel.observation(id).first()?.let {
                title = it.title
                notes = it.notes
            }
            loaded = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (id == 0L) "New observation" else "Edit observation") })
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).padding(16.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    viewModel.save(id, title.trim(), notes.trim())
                    onDone()
                },
                enabled = loaded && title.isNotBlank()
            ) { Text("Save") }
        }
    }
}