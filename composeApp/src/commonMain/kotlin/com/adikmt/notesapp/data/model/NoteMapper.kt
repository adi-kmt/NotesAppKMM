package com.adikmt.notesapp.data.model


import com.adikmt.notesapp.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NoteEntity.toNoteModel(): NoteDataModel = NoteDataModel(
    id = this.Id,
    title = this.Title,
    content = this.Content,
    colorHex = this.ColorHex,
    createdAt = Instant.fromEpochMilliseconds(this.CreatedAt).toLocalDateTime(TimeZone.UTC)
)