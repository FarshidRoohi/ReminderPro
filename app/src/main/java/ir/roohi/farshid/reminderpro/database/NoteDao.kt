package ir.roohi.farshid.reminderpro.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ir.roohi.farshid.reminderpro.model.NoteEntity
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */

@Dao
interface NoteDao {

    @get:Query("SELECT * FROM NOTES ORDER BY date DESC")
    val all: LiveData<List<NoteEntity>>

    @get:Query("SELECT COUNT(*) FROM notes")
    val count: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(noteEntity: List<NoteEntity>)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM NOTES WHERE id = :id")
    fun getNotesById(id: Int): NoteEntity

    @Query("DELETE FROM notes")
    fun deleteAll(): Int


    @Query("UPDATE NOTES SET date = :date , title = :title , text = :text WHERE id == :id")
    fun updateNote(id: Int, date: Date, title: String, text: String)

}
