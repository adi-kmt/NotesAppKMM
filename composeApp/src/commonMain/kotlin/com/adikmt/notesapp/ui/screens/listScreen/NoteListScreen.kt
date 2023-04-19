package com.adikmt.notesapp.ui.screens.listScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.adikmt.notesapp.ui.krouter.rememberViewModel

@Composable
fun NoteListScreen(
    onAddOrItemClicked: (NoteDataModel?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: NoteListViewModel =
        rememberViewModel(NoteListViewModel::class) {
            NoteListViewModel()
        }

    val state by viewModel.states.collectAsState()

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
                    text = state.searchText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    onTextChange = viewModel::searchTextChanged,
                    isSearchActive = state.isSearchActive,
                    onSearchClick = viewModel::toggleSearchFocus,
                    onCloseClick = viewModel::toggleSearchFocus,
                )
                this@Column.AnimatedVisibility(
                    visible = !state.isSearchActive,
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
            LazyVerticalGrid(
                columns = GridCells.Adaptive(128.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp),
            ) {
                items(state.notes) { note ->
                    NoteListItemComponent(
                        noteDataModel = note,
                        onNoteClick = { onAddOrItemClicked.invoke(note) },
                        onNoteDeleted = { viewModel.deleteNote(note.id) },
                    )
                }
            }
        }
    }
}
