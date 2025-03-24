package com.example.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes() //Fetches all notes

    fun saveNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    fun getNoteById(id: Long?): Flow<Note?> {
        return if (id == null) flowOf(null) else noteDao.getNoteById(id) //Retrieves a specific note using its ID
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }
}

