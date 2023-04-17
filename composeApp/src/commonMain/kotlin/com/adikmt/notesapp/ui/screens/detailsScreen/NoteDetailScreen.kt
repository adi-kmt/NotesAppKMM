package com.adikmt.notesapp.ui.screens.detailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.components.HeadingTextFieldComponent
import com.adikmt.notesapp.ui.krouter.SavedStateHandle
import com.adikmt.notesapp.ui.krouter.rememberViewModel
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
fun NoteDetailScreen(noteId: Long?, onBack: () -> Unit) {
    val viewModel: NoteDetailViewModel =
        rememberViewModel(NoteDetailViewModel::class) { savedState: SavedStateHandle ->
            NoteDetailViewModel(savedState)
        }

    val state by viewModel.state.collectAsState()
    val isSaved by viewModel.hasNoteBeenSaved.collectAsState()
    val onBackClicked by viewModel.onBackMutableState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getNote(noteId)
    }

    LaunchedEffect(isSaved, onBackClicked) {
        if (isSaved || onBackClicked) {
            onBack.invoke()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveNote(noteId)
                },
                backgroundColor = Color.Black,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = Color.White
                )
            }
        },
        topBar = {
            TopAppBar(title = { Text("Note Detail") }, navigationIcon = {
                IconButton(
                    onClick = {
                        if (!isSaved) {
                            viewModel.saveNote(noteId)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

            })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(state.color))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            HeadingTextFieldComponent(
                text = state.title,
                hint = "Enter a Title...",
                isHintVisible = state.isTitleHintVisible,
                onValueChanged = viewModel::onTitleChanged,
                onFocusChanged = {
                    viewModel.onTitleFocusChanged(it.isFocused)
                },
                isSingleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            HeadingTextFieldComponent(
                text = state.content,
                hint = "Enter some content...",
                isHintVisible = state.isContentHintVisible,
                onValueChanged = viewModel::onContentChanged,
                onFocusChanged = {
                    viewModel.onContentFocusChanged(it.isFocused)
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }
    }
}