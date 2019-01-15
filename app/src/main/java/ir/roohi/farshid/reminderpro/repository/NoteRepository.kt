package ir.roohi.farshid.reminderpro.repository

import androidx.lifecycle.LiveData
import android.content.Context
import ir.roohi.farshid.reminderpro.database.AppDatabase
import ir.roohi.farshid.reminderpro.model.NoteEntity
import java.util.concurrent.Executors

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteRepository private constructor(context: Context) {
    var notes: LiveData<List<NoteEntity>>
    private val database: AppDatabase? = AppDatabase.getInstance(context)
    private val executor = Executors.newSingleThreadExecutor()


    private val allNotes: LiveData<List<NoteEntity>>
        get() = this.database!!.noteDao().all

    init {
        this.notes = allNotes
    }

    fun deleteAllNotes() {
        executor.execute { database!!.noteDao().deleteAll() }
    }

    fun getNoteById(idNote: Int): NoteEntity {
        return database!!.noteDao().getNotesById(idNote)
    }

    fun insertNote(note: NoteEntity) {
        executor.execute { database!!.noteDao().insertNote(note) }
    }

    fun updateNote(note: NoteEntity) {
        executor.execute { database!!.noteDao().updateNote(note.id!!, note.date, note.title, note.text) }
    }

    fun deleteNote(noteEntity: NoteEntity) {
        executor.execute { database!!.noteDao().deleteNote(noteEntity) }
    }

    companion object {

        private var ourInstance: NoteRepository? = null

        fun getInstance(context: Context): NoteRepository {
            if (ourInstance == null) {
                ourInstance = NoteRepository(context)
            }
            return ourInstance!!
        }
    }
}
