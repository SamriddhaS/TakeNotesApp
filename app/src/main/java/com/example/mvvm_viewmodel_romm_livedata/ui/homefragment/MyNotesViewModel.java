package com.example.mvvm_viewmodel_romm_livedata.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvm_viewmodel_romm_livedata.MyNotesRepo;
import com.example.mvvm_viewmodel_romm_livedata.Room.EntityTableNotes;

import java.util.List;

public class MyNotesViewModel extends AndroidViewModel {

    private MyNotesRepo notesRepo ;
    private LiveData<List<EntityTableNotes>> alldata ;

    public MyNotesViewModel(@NonNull Application application) {
        super(application);

        notesRepo = new MyNotesRepo(application);
        alldata = notesRepo.getAllNotes();
    }

    public void insertNote(EntityTableNotes notes){

        notesRepo.insert(notes);

    }

    public void deleteNote(EntityTableNotes notes){

        notesRepo.delete(notes);

    }

    public void updateNote(EntityTableNotes notes){

        notesRepo.update(notes);
    }

    public void deleteAllNote(){

        notesRepo.deleteAll();

    }

    public LiveData<List<EntityTableNotes>> getAlldata() {
        return alldata;
    }
}
