package ir.roohi.farshid.reminderpro.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ir.roohi.farshid.reminderpro.database.AppDatabase;
import ir.roohi.farshid.reminderpro.model.NoteEntity;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
public class NoteRepository {

    private static NoteRepository                  ourInstance;
    public         LiveData<List<NoteEntity>> notes;
    private        AppDatabase                     database;
    private        Executor                        executor = Executors.newSingleThreadExecutor();

    public static NoteRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new NoteRepository(context);
        }
        return ourInstance;
    }

    private NoteRepository(Context context) {
        this.database = AppDatabase.Companion.getInstance(context);
        this.notes = getAllNotes();
    }


    private LiveData<List<NoteEntity>> getAllNotes() {
        return this.database.noteDao().getAll();
    }

    public void deleteAllNotes() {
        executor.execute(() -> database.noteDao().deleteAll());
    }

    public NoteEntity getNoteById(int idNote) {
        return database.noteDao().getNotesById(idNote);
    }

    public void insertNote(final NoteEntity note) {
        executor.execute(() -> database.noteDao().insertNote(note));
    }

    public void updateNote(NoteEntity note) {
        executor.execute(() -> database.noteDao().updateNote(note.getId(), note.getDate(), note.getTitle(), note.getText()));
    }

    public void deleteNote(final NoteEntity noteEntity) {
        executor.execute(() -> database.noteDao().deleteNote(noteEntity));
    }
}
