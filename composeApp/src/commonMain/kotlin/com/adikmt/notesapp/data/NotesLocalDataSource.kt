package com.adikmt.notesapp.data

import com.adikmt.notesapp.NotesDB
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.data.model.toNoteModel
import com.adikmt.notesapp.utils.DateTimeUtil

class NotesLocalDataSourceImpl(private val database: NotesDB) : NoteLocalDataSource {

    private val queries = database.noteQueries
    override suspend fun insertNote(note: NoteDataModel) {
        queries.insertNote(
            note.id,
            note.title,
            note.content,
            note.colorHex,
            DateTimeUtil.toEpochMillis(note.createdAt)
        )
    }

    override suspend fun getNote(id: Long): NoteDataModel? {
        return queries.getNote(id).executeAsOneOrNull()?.toNoteModel()
    }

    override suspend fun getAllNotes(): List<NoteDataModel> {
        return queries.getAllNotes().executeAsList().map { it.toNoteModel() }
    }

    override suspend fun deleteNote(id: Long) {
        return queries.deleteNote(id)
    }
}

interface NoteLocalDataSource {

    suspend fun insertNote(note: NoteDataModel)

    suspend fun getNote(id: Long): NoteDataModel?

    suspend fun getAllNotes(): List<NoteDataModel>

    suspend fun deleteNote(id: Long)

}