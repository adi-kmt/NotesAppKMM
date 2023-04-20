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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adikmt.notesapp.ui.components.HeadingTextFieldComponent
import com.adikmt.notesapp.ui.krouter.rememberViewModel

@Composable
fun NoteDetailScreen(
    noteId: Long?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NoteDetailViewModel =
        rememberViewModel(NoteDetailViewModel::class) { NoteDetailViewModel() }

    /**
     * State stored here instead of the VM as the transfer of data was causing
     * poor writing effort in the text fields
     */

    val noteDetailState by viewModel.noteMutableStateFlow.collectAsState()
    val isSaved by viewModel.hasNoteBeenSaved.collectAsState()
    val onBackClicked by viewModel.onBackMutableState.collectAsState()

    var stateTitle by remember {
        mutableStateOf(noteDetailState.title)
    }

    var stateContent by remember {
        mutableStateOf(noteDetailState.content)
    }

    LaunchedEffect(Unit) {
        viewModel.getNote(noteId)
    }

    LaunchedEffect(noteDetailState) {
        stateTitle = noteDetailState.title
        stateContent = noteDetailState.content
    }

    LaunchedEffect(isSaved, onBackClicked) {
        if (isSaved || onBackClicked) {
            onBack.invoke()
        }
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!isSaved) {
                        viewModel.saveNote(
                            noteId = noteId,
                            title = stateTitle,
                            content = stateContent,
                        )
                    }
                },
                backgroundColor = Color.Black,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save Note",
                    tint = Color.White,
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Note Detail") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!isSaved) {
                                viewModel.saveNote(
                                    noteId = noteId,
                                    title = stateTitle,
                                    content = stateContent,
                                )
                            }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                            )
                        },
                    )
                },
                backgroundColor = Color.White,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .background(Color(noteDetailState.color))
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            HeadingTextFieldComponent(
                hint = "Enter a Title...",
                value = stateTitle,
                onValueChanged = {
                    stateTitle = it
                },
                isSingleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            HeadingTextFieldComponent(
                value = stateContent,
                hint = "Enter some content...",
                onValueChanged = {
                    stateContent = it
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f),
                isSingleLine = false,
            )
        }
    }
}
