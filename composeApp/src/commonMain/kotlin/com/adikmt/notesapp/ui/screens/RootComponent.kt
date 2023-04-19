package com.adikmt.notesapp.ui.screens

import androidx.compose.runtime.Composable
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.ui.krouter.RoutedContent
import com.adikmt.notesapp.ui.krouter.Router
import com.adikmt.notesapp.ui.krouter.rememberRouter
import com.adikmt.notesapp.ui.screens.detailsScreen.NoteDetailScreen
import com.adikmt.notesapp.ui.screens.listScreen.NoteListScreen
import com.adikmt.notesapp.ui.theme.MyApplicationTheme
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Composable
fun RootComponent() {
    val router: Router<RootStateModel> =
        rememberRouter(RootStateModel::class, listOf(RootStateModel.NoteList))

    MyApplicationTheme {
        RoutedContent(
            router = router,
            animation = stackAnimation(animator = slide()),
            content = { screen ->
                when (screen) {
                    is RootStateModel.NoteDetails -> NoteDetailScreen(
                        noteId = screen.noteId,
                        onBack = { router.pop() },
                    )

                    RootStateModel.NoteList -> NoteListScreen(
                        onAddOrItemClicked = { noteDataModel: NoteDataModel? ->
                            router.push(RootStateModel.NoteDetails(noteDataModel?.id))
                        },
                    )
                }
            },
        )
    }
}

@Parcelize
sealed class RootStateModel : Parcelable {
    object NoteList : RootStateModel()
    data class NoteDetails(val noteId: Long?) : RootStateModel()
}
