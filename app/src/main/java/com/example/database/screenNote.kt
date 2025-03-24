import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.database.Note
import com.example.database.NoteViewModel

@Composable
fun NoteInputScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    noteId: Long? = null
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val note by noteViewModel.getNoteById(noteId)
        .collectAsState(initial = null)// // Fetch and observe the note by ID



    // re-fill title and content if editing an existing note
    LaunchedEffect(note) {
        note?.let {
            title = it.title
            content = it.content
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (noteId == null) "Create Note" else "Edit Note",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    val newNote = Note(id = noteId ?: 0, title = title, content = content)
                    if (noteId == null) {
                        noteViewModel.saveNote(newNote)  // Create new note
                    } else {
                        noteViewModel.updateNote(newNote) // Update existing note
                    }
                    navController.navigate("NoteListScreen")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            //Display query individual notes by ID
            Text(
                text = "Query for individual notes with specific ID $noteId",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "Note: ${note?.title ?: "New Note"}") //show the specific note by ID
        }
    }
}
