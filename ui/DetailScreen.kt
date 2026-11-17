package com.example.skyjournal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable

//one observation
fun DetailScreen(
    viewModel: ObservationViewModel,
    id: Long,
    onEdit: () -> Unit,
    onDeleted: () -> Unit
) {
    val flow = remember(id) { viewModel.observation(id) }
    val obs by flow.collectAsState(initial = null) //gets pipe for one id
    var confirmDelete by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(obs?.title ?: "") }) }
    ) { padding ->
        val o = obs ?: return@Scaffold

        Column(
            Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(formatDate(o.timestamp), style = MaterialTheme.typography.labelLarge)
            Text(o.notes.ifBlank { "No notes" })
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onEdit) { Text("Edit") }
                OutlinedButton(onClick = { confirmDelete = true }) { Text("Delete") }
            }
        }

        if (confirmDelete) {
            AlertDialog(
                onDismissRequest = { confirmDelete = false },
                title = { Text("Delete observation?") },
                confirmButton = {
                    TextButton(onClick = {
                        confirmDelete = false
                        viewModel.delete(o)
                        onDeleted()
                    }) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { confirmDelete = false }) { Text("Cancel") }
                }
            )
        }
    }
}