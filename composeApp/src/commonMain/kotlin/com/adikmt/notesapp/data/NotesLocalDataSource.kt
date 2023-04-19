package com.adikmt.notesapp.data

import com.adikmt.notesapp.NotesDB
import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.data.model.toNoteModel
import com.adikmt.notesapp.utils.DateTimeUtil

class NotesLocalDataSourceImpl(database: NotesDB) : NoteLocalDataSource {

    private val queries = database.noteQueries
    override suspend fun insertNote(note: NoteDataModel) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            color = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.createdAt),
        )
    }

    override suspend fun getNote(id: Long): NoteDataModel? {
        return queries.getNote(id = id).executeAsOneOrNull()?.toNoteModel()
    }

    override suspend fun getAllNotes(): List<NoteDataModel> {
        return queries.getAllNotes().executeAsList().map { it.toNoteModel() }
    }

    override suspend fun deleteNote(id: Long) {
        return queries.deleteNote(id = id)
    }

    override suspend fun searchNotes(title: String): List<NoteDataModel> {
        return queries.searchNotes(title = title).executeAsList().map { it.toNoteModel() }
    }
}

interface NoteLocalDataSource {

    suspend fun insertNote(note: NoteDataModel)

    suspend fun getNote(id: Long): NoteDataModel?

    suspend fun getAllNotes(): List<NoteDataModel>

    suspend fun deleteNote(id: Long)

    suspend fun searchNotes(title: String): List<NoteDataModel>
}
