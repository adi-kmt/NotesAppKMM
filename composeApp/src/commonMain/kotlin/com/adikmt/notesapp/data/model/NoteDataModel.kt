package com.adikmt.notesapp.data.model

import com.adikmt.notesapp.ui.theme.BabyBlueHex
import com.adikmt.notesapp.ui.theme.LightGreenHex
import com.adikmt.notesapp.ui.theme.RedOrangeHex
import com.adikmt.notesapp.ui.theme.RedPinkHex
import com.adikmt.notesapp.ui.theme.VioletHex
import kotlinx.datetime.LocalDateTime

data class NoteDataModel(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val createdAt: LocalDateTime,
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, BabyBlueHex, VioletHex, LightGreenHex)

        fun generateRandomColor() = colors.random()
    }
}
