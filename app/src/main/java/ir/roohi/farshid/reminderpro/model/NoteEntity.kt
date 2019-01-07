package ir.roohi.farshid.reminderpro.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

import java.util.Date

@Entity(tableName = "notes")
data class NoteEntity(
    var date: Date,
    var title: String,
    var text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}