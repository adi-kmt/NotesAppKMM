package com.adikmt.notesapp.ui.screens.detailsScreen

import com.adikmt.notesapp.data.NoteLocalDataSource
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.krouter.SavedStateHandle
import com.adikmt.notesapp.ui.krouter.ViewModel
import com.adikmt.notesapp.utils.DateTimeUtil
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NoteDetailViewModel(savedState: SavedStateHandle) : ViewModel(), KoinComponent {

    private val title = MutableStateFlow("")
    private val isTitleTextFocused = MutableStateFlow(false)
    private val isContentTextFocused =
        MutableStateFlow(false)
    private val content = MutableStateFlow("")
    private val color = MutableStateFlow(NoteDataModel.generateRandomColor())
    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private val noteLocalDataSource = get<NoteLocalDataSource>()

    val state by lazy {
        combine(
            title,
            isTitleTextFocused,
            content,
            isContentTextFocused,
            color
        ) { title, isTitleTextFocused, content, isContentTextFocused, color ->
            NoteDetailState(
                title = title,
                isTitleHintVisible = title.isEmpty() && !isTitleTextFocused,
                content = content,
                isContentHintVisible = content.isEmpty() && !isContentTextFocused,
                color = color
            )
        }
            .stateIn(
                CoroutineScope(coroutineContext),
                SharingStarted.WhileSubscribed(5000),
                NoteDetailState()
            )
    }

    fun onTitleChanged(text: String) {
        title.value = text
    }

    fun onTitleFocusChanged(isFocused: Boolean) {
        isTitleTextFocused.value = isFocused
    }

    fun onContentChanged(text: String) {
        content.value = text
    }

    fun onContentFocusChanged(isFocused: Boolean) {
        isContentTextFocused.value = isFocused
    }

    fun getNote(noteId: Long?) {
        CoroutineScope(coroutineContext).launch {
            noteId?.let {
                val note = noteLocalDataSource.getNote(it)

                note?.let {
                    title.value = it.title
                    content.value = it.content
                    color.value = it.colorHex
                }
            }
        }
    }

    fun saveNote(noteId: Long?) {
        CoroutineScope(coroutineContext).launch {
            noteLocalDataSource.insertNote(
                NoteDataModel(
                    id = noteId,
                    title = title.value,
                    content = content.value,
                    colorHex = color.value,
                    createdAt = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}

data class NoteDetailState(
    val title: String = "",
    val isTitleHintVisible: Boolean = false,
    val content: String = "",
    val isContentHintVisible: Boolean = false,
    val color: Long = 0xFFFFFFFF
)