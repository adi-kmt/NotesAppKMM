package com.adikmt.notesapp.ui.screens.detailsScreen

import com.adikmt.notesapp.data.NoteLocalDataSource
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.krouter.SavedStateHandle
import com.adikmt.notesapp.ui.krouter.ViewModel
import com.adikmt.notesapp.utils.DateTimeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.toInstant
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NoteDetailViewModel(savedState: SavedStateHandle) : ViewModel(), KoinComponent {
    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    val onBackMutableState = MutableStateFlow(false)
    var noteMutableStateFlow =
        MutableStateFlow(NoteDetailState())

    private val noteLocalDataSource = get<NoteLocalDataSource>()

    fun getNote(noteId: Long?) {
        CoroutineScope(coroutineContext).launch {
            noteId?.let {
                val note = noteLocalDataSource.getNote(it)

                note?.let {
                    noteMutableStateFlow.emit(
                        NoteDetailState(
                            title = it.title,
                            content = it.content,
                            color = it.colorHex
                        )
                    )
                }
            }
        }
    }

    fun saveNote(noteId: Long?, title: String, content: String) {
        CoroutineScope(coroutineContext).launch {
            if (title.checkNotEmptyOrBlank()) {
                noteId?.let {
                    noteLocalDataSource.updateNote(
                            id = noteId,
                            title = title,
                            content = content,
                            createdAt = DateTimeUtil.toEpochMillis(DateTimeUtil.now())
                    )
                } ?: noteLocalDataSource.insertNote(
                    NoteDataModel(
                        id = noteId,
                        title = title,
                        content = content,
                        colorHex = getColour(noteId),
                        createdAt = DateTimeUtil.now()
                    )
                )
                _hasNoteBeenSaved.value = true
            }
            onBackMutableState.value = true
        }
    }

    fun getColour(noteId: Long?): Long {
        return noteId?.let {
            noteMutableStateFlow.value.color
        } ?: NoteDataModel.generateRandomColor()
    }
}

data class NoteDetailState(
    val title: String = "",
    val content: String = "",
    val color: Long = 0xFFFFFFFF
)

fun String.checkNotEmptyOrBlank(): Boolean {
    return isNotEmpty() && isNotBlank()
}