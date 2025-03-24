package com.example.database

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun NoteListScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val notes by noteViewModel.allNotes.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Notes App",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        if (notes.isEmpty()) {
            // Display this message if there are no notes
            Text(
                text = "No notes available",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            // Display the list of notes if available
            LazyColumn {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("NoteScreen/${note.id}") }, // Navigate to edit

                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                Column {
                                    Text(
                                        text = note.title,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    Text(
                                        text = note.content,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            Spacer(modifier = Modifier.weight(1f))
                            Row {
                                IconButton(onClick = {
                                    if (note.id != null) {

                                        navController.navigate("NoteInputScreen/${note.id}")
                                    }
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit Note")
                                }

                                IconButton(onClick = { noteViewModel.deleteNote(note) }) { // Delete Note
                                    Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                                }

                            }
                        }
                    }
                }
            }

        }

        //button to create a new note
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("NoteInputScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create New Note")
        }
        //Query all notes
        Text(text = "Query for all notes",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )
        LazyColumn {
            items(notes) { note ->
                Text(text = "ID: ${note.id}, Title: ${note.title}, Content: ${note.content}")
            }
        }

    }
}

