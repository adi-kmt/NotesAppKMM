package com.adikmt.notesapp.data

import com.adikmt.notesapp.data.model.NoteDataModel
import com.adikmt.notesapp.utils.DateTimeUtil

class SearchNotes {
    fun execute(notes: List<NoteDataModel>, query: String): List<NoteDataModel> {
        if (query.isBlank())
            return notes
        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.content.trim().lowercase().contains(query.lowercase())
        }.sortedBy { DateTimeUtil.toEpochMillis(it.createdAt) }
    }
}