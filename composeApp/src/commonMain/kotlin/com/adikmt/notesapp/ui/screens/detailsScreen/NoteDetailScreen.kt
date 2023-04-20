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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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

    /**
     * State stored here instead of the VM as the transfer of data was causing
     * poor writing effort in the text fields especially in iOS
     */

    var stateTitle by remember {
        mutableStateOf("")
    }

    var stateContent by remember {
        mutableStateOf("")
    }

    val noteDetailState by viewModel.noteMutableStateFlow.collectAsState()

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
                    if (!isSaved) {
                        viewModel.saveNote(noteId, stateTitle, stateContent)
                    }
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
                            viewModel.saveNote(noteId, stateTitle, stateContent)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

            }, backgroundColor = Color.White)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(viewModel.getColour(noteId)))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            HeadingTextFieldComponent(
                text = if (stateTitle.checkNotEmptyOrBlank()) stateTitle else {
                    stateTitle = noteDetailState.title
                    noteDetailState.title
                },
                hint = "Enter a Title...",
                onValueChanged = {
                    stateTitle = it
                },
                isSingleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            HeadingTextFieldComponent(
                text = if (stateContent.checkNotEmptyOrBlank()) stateContent else {
                    stateContent = noteDetailState.content
                    noteDetailState.content
                },
                hint = "Enter some content...",
                onValueChanged = {
                    stateContent = it
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f),
                isSingleLine = false
            )
        }
    }
}