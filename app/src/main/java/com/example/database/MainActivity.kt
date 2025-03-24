package com.example.database

import NoteInputScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            val noteViewModel: NoteViewModel = viewModel()

            Scaffold { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "NoteListScreen",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("NoteListScreen") {
                        NoteListScreen(navController, noteViewModel)
                    }
                    composable("NoteInputScreen/{noteId}") { backStackEntry: NavBackStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
                        NoteInputScreen(navController, noteViewModel, noteId)
                    }
                    composable("NoteInputScreen") { // For creating a new note
                        NoteInputScreen(navController, noteViewModel, null)
                    }
                }
            }
        }
    }
}
