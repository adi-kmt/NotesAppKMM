package com.adikmt.notesapp.ui.screens.listScreen

import com.adikmt.notesapp.data.NoteLocalDataSource
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.krouter.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NoteListViewModel : ViewModel(), KoinComponent {

    private val notes: MutableStateFlow<List<NoteDataModel>> = MutableStateFlow(emptyList())
    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private val isSearchActive: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val noteLocalDataSource = get<NoteLocalDataSource>()

    val states by lazy {
        combine(notes, searchText, isSearchActive) { notes, searchText, isSearchActive ->
            NoteListState(
                notes = notes,
                searchText = searchText,
                isSearchActive = isSearchActive,
            )
        }
            .stateIn(
                CoroutineScope(coroutineContext),
                SharingStarted.WhileSubscribed(5000),
                NoteListState(),
            )
    }

    fun getAllNotes() {
        CoroutineScope(coroutineContext).launch {
            notes.emit(noteLocalDataSource.getAllNotes())
        }
    }

    fun deleteNote(id: Long?) {
        CoroutineScope(coroutineContext).launch {
            id?.let {
                noteLocalDataSource.deleteNote(it)
                notes.emit(noteLocalDataSource.getAllNotes())
            }
        }
    }

    fun searchTextChanged(text: String) {
        CoroutineScope(coroutineContext).launch {
            searchText.value = text
            notes.emit(noteLocalDataSource.searchNotes(text))
        }
    }

    fun toggleSearchFocus() {
        this.isSearchActive.value = !isSearchActive.value
        if (!isSearchActive.value) {
            searchText.value = ""
            getAllNotes()
        }
    }
}

data class NoteListState(
    val notes: List<NoteDataModel> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false,
)
