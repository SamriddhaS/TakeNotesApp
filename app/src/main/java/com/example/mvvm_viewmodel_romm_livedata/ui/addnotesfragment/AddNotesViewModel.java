package com.example.mvvm_viewmodel_romm_livedata.ui.addnotesfragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.mvvm_viewmodel_romm_livedata.data.db.EntityTableNotes;
import com.example.mvvm_viewmodel_romm_livedata.data.repository.MyNotesRepo;

import java.util.List;

public class AddNotesViewModel extends AndroidViewModel {

    private MyNotesRepo notesRepo ;

    public AddNotesViewModel(@NonNull Application application) {
        super(application);

        notesRepo = new MyNotesRepo(application);
    }

    public void insertNote(EntityTableNotes notes){

        notesRepo.insert(notes);

    }

    public void updateNote(EntityTableNotes notes){

        notesRepo.update(notes);
    }

}
