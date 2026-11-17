package com.example.skyjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skyjournal.ui.DetailScreen
import com.example.skyjournal.ui.EditScreen
import com.example.skyjournal.ui.ListScreen
import com.example.skyjournal.ui.ObservationViewModel
import com.example.skyjournal.ui.theme.SkyJournalTheme

//navigation, creates viewmodel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyJournalTheme {
                SkyJournalNavHost()
            }
        }
    }
}

@Composable
fun SkyJournalNavHost() {
    val navController = rememberNavController()
    val viewModel: ObservationViewModel = viewModel()
    val idArg = listOf(navArgument("id") { type = NavType.LongType })

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            ListScreen(
                viewModel = viewModel,
                onOpen = { id -> navController.navigate("detail/$id") },
                onAdd = { navController.navigate("edit/0") }
            )
        }

        composable("detail/{id}", arguments = idArg) { entry ->
            val id = entry.arguments!!.getLong("id")
            DetailScreen(
                viewModel = viewModel,
                id = id,
                onEdit = { navController.navigate("edit/$id") },
                onDeleted = { navController.popBackStack() }
            )
        }

        composable("edit/{id}", arguments = idArg) { entry ->
            EditScreen(
                viewModel = viewModel,
                id = entry.arguments!!.getLong("id"),
                onDone = { navController.popBackStack() }
            )
        }
    }
}