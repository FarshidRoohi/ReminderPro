package ir.roohi.farshid.reminderpro.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ir.roohi.farshid.reminderpro.model.NoteEntity
import ir.roohi.farshid.reminderpro.repository.NoteRepository
import java.util.*

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    var notes: LiveData<List<NoteEntity>>
    private val noteRepository: NoteRepository = NoteRepository.getInstance(application.applicationContext)

    init {
        this.notes = this.noteRepository.notes
    }

    fun add(text: String,title:String) {
        this.noteRepository.insertNote(NoteEntity(Date(), title, text))
    }

    fun add(entity: NoteEntity) {
        this.noteRepository.insertNote(entity)
    }

    fun update(entity: NoteEntity) {
        this.noteRepository.updateNote(entity)
    }

    fun remove(entity: NoteEntity) {
        this.noteRepository.deleteNote(entity)
    }
}
