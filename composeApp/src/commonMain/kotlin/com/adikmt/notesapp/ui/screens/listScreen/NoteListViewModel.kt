package com.adikmt.notesapp.ui.screens.listScreen

import com.adikmt.notesapp.data.NoteLocalDataSource
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.krouter.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NoteListViewModel : ViewModel(), KoinComponent {

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()

    private val _noteListStateFlow = MutableStateFlow(NoteListState())
    val noteListStateFlow: StateFlow<NoteListState> = _noteListStateFlow.asStateFlow()

    private val noteLocalDataSource = get<NoteLocalDataSource>()

    fun getAllNotes() {
        CoroutineScope(coroutineContext).launch {
            _noteListStateFlow.update { noteListState ->
                noteListState.copy(
                    notes = noteLocalDataSource.getAllNotes(),
                )
            }
        }
    }

    fun deleteNote(id: Long?) {
        CoroutineScope(coroutineContext).launch {
            id?.let {
                noteLocalDataSource.deleteNote(it)
                _noteListStateFlow.update { noteListState ->
                    noteListState.copy(
                        notes = noteLocalDataSource.getAllNotes(),
                    )
                }
            }
        }
    }

    fun searchTextChanged(text: String) {
        CoroutineScope(coroutineContext).launch {
            _noteListStateFlow.update { noteListState ->
                noteListState.copy(
                    notes = noteLocalDataSource.searchNotes(text),
                    searchText = text,
                )
            }
        }
    }

    fun toggleSearchFocus() {
        _isSearchActive.value = !_isSearchActive.value
        if (!_isSearchActive.value) {
            _noteListStateFlow.update { noteListState ->
                noteListState.copy(
                    searchText = "",
                )
            }
            getAllNotes()
        }
    }
}

data class NoteListState(
    val notes: List<NoteDataModel> = emptyList(),
    val searchText: String = "",
)
