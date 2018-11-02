package ir.roohi.farshid.reminderpro.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import ir.roohi.farshid.reminderpro.model.NoteEntity;
import ir.roohi.farshid.reminderpro.repository.NoteRepository;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
public class NoteViewModel extends AndroidViewModel {

    public  LiveData<List<NoteEntity>> notes;
    private NoteRepository             noteRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.noteRepository = NoteRepository.getInstance(application.getApplicationContext());
        this.notes = this.noteRepository.notes;

    }

    public void add(String text) {

        String title = text;
        if (text.length() > 20) {
            title = text.substring(0, 19) + "...";
        }

        this.noteRepository.insertNote(new NoteEntity(new Date(), title, text));
    }

    public void add(NoteEntity entity) {
        this.noteRepository.insertNote(entity);
    }

    public void updateNote(NoteEntity entity) {
        this.noteRepository.updateNote(entity);
    }

    public void removeNote(NoteEntity entity){
        this.noteRepository.deleteNote(entity);
    }
}
