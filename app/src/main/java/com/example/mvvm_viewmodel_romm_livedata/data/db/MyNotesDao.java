package com.example.mvvm_viewmodel_romm_livedata.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyNotesDao {

    @Insert
    void insert(EntityTableNotes notes);

    @Update
    void update(EntityTableNotes notes);

    @Delete
    void delete(EntityTableNotes notes);

    @Query("DELETE FROM my_notes_table")
    void deleteAll();

    @Query("SELECT * FROM my_notes_table ORDER BY notePriority DESC")
    LiveData<List<EntityTableNotes>> getAllNotes();


}
