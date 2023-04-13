package com.adikmt.notesapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.utils.DateTimeUtil

@Composable
fun NoteListItemComponent(
    noteDataModel: NoteDataModel,
    maxLines: Int = 5,
    modifier: Modifier = Modifier,
    onNoteClick: () -> Unit,
    onNoteDeleted: () -> Unit
) {
    val formattedDate = remember(noteDataModel.createdAt) {
        DateTimeUtil.formatNoteDate(noteDataModel.createdAt)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(noteDataModel.colorHex))
            .clickable { onNoteClick.invoke() }
            .padding(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = noteDataModel.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete Note",
                modifier = Modifier
                    .clickable(MutableInteractionSource(), null) {
                        onNoteDeleted()
                    }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = noteDataModel.content,
            fontWeight = FontWeight.Normal,
            maxLines = maxLines,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = formattedDate,
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.End)
        )
    }
}