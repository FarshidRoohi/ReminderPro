package ir.roohi.farshid.reminderpro.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ir.roohi.farshid.reminderpro.model.NoteEntity;
import ir.roohi.farshid.reminderpro.repository.NoteRepository;

/**
 * Created by Farshid Roohi.
 * ReminderPro | Copyrights 2018.
 */
public class NoteViewModel extends AndroidViewModel {

    public  LiveData<List<NoteEntity>> notes;
    private NoteRepository             appRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.appRepository = NoteRepository.getInstance(application.getApplicationContext());
        this.notes = this.appRepository.notes;

    }

    public void deleteAllNotes() {
        this.appRepository.deleteAllNotes();
    }

    public void add(NoteEntity entity) {
        this.appRepository.insertNote(entity);
    }
}
