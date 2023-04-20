package com.adikmt.notesapp.ui.screens.listScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.components.NoteListItemComponent
import com.adikmt.notesapp.ui.components.SearchTextFieldComponent
import com.adikmt.notesapp.ui.components.VerticalStaggeredGrid
import com.adikmt.notesapp.ui.krouter.rememberViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    onAddOrItemClicked: (NoteDataModel?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NoteListViewModel =
        rememberViewModel(NoteListViewModel::class) {
            NoteListViewModel()
        }

    val noteListState by viewModel.noteListStateFlow.collectAsState()
    val isSearchActive by viewModel.isSearchActive.collectAsState()

    LaunchedEffect(true) {
        viewModel.getAllNotes()
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddOrItemClicked(null)
                },
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Note",
                        tint = Color.Black,
                    )
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                SearchTextFieldComponent(
                    text = noteListState.searchText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    onTextChange = viewModel::searchTextChanged,
                    isSearchActive = isSearchActive,
                    onSearchClick = viewModel::toggleSearchFocus,
                    onCloseClick = viewModel::toggleSearchFocus,
                )
                this@Column.AnimatedVisibility(
                    visible = !isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Text(
                        text = "All Notes",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            VerticalStaggeredGrid(
                modifier = Modifier.padding(8.dp),
                content = {
                    noteListState.notes.forEach { note ->
                        NoteListItemComponent(
                            noteDataModel = note,
                            onNoteClick = { onAddOrItemClicked.invoke(note) },
                            onNoteDeleted = { viewModel.deleteNote(note.id) },
                            modifier = modifier.padding(4.dp),
                        )
                    }
                },
            )
        }
    }
}
