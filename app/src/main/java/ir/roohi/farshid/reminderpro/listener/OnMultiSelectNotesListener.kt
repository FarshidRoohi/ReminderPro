package ir.roohi.farshid.reminderpro.listener

import ir.roohi.farshid.reminderpro.model.NoteEntity

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 1/14/19.
 */
interface OnMultiSelectNotesListener {
    fun onMultiSelectNotes(items: ArrayList<NoteEntity>)
}