package com.adikmt.notesapp.ui.screens.detailsScreen

import com.adikmt.notesapp.data.NoteLocalDataSource
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.krouter.ViewModel
import com.adikmt.notesapp.utils.DateTimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NoteDetailViewModel : ViewModel(), KoinComponent {
    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved: StateFlow<Boolean> = _hasNoteBeenSaved.asStateFlow()

    val onBackMutableState = MutableStateFlow(false)

    private val _noteMutableStateFlow = MutableStateFlow(NoteDetailState())
    val noteMutableStateFlow: StateFlow<NoteDetailState> = _noteMutableStateFlow.asStateFlow()

    private val noteLocalDataSource = get<NoteLocalDataSource>()

    fun getNote(noteId: Long?) {
        CoroutineScope(coroutineContext).launch {
            if (noteId == null) {
                _noteMutableStateFlow.update {
                    it.copy(
                        color = NoteDataModel.generateRandomColor(),
                    )
                }
            } else {
                val note = noteLocalDataSource.getNote(noteId)
                note?.let { nnNote ->
                    _noteMutableStateFlow.update { noteDetail ->
                        noteDetail.copy(
                            title = nnNote.title,
                            content = nnNote.content,
                            color = nnNote.colorHex,
                        )
                    }
                }
            }
        }
    }

    fun saveNote(
        noteId: Long?,
        title: String,
        content: String,
    ) {
        CoroutineScope(coroutineContext).launch {
            if (title.checkNotEmptyOrBlank()) {
                noteLocalDataSource.insertNote(
                    NoteDataModel(
                        id = noteId,
                        title = title,
                        content = content,
                        colorHex = _noteMutableStateFlow.value.color,
                        createdAt = DateTimeUtil.now(),
                    ),
                )
                _hasNoteBeenSaved.value = true
            }
            onBackMutableState.value = true
        }
    }
}

data class NoteDetailState(
    val title: String = "",
    val content: String = "",
    val color: Long = 0xFFFFFFFF,
)

fun String.checkNotEmptyOrBlank(): Boolean {
    return isNotEmpty() && isNotBlank()
}
