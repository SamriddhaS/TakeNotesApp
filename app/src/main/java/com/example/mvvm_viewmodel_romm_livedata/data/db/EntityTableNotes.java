package com.example.mvvm_viewmodel_romm_livedata.Room;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_notes_table") // room library uses this annotation to convert this class into a sqlite database table
public class EntityTableNotes {

    @PrimaryKey(autoGenerate = true)
    private int noteId ;

    private String noteTitle ;

    private String noteBody ;

    private int notePriority;

    private String noteDateTime;


    public EntityTableNotes(String noteTitle, String noteBody, int notePriority,String noteDateTime) {
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.notePriority = notePriority;
        this.noteDateTime = noteDateTime ;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId; // we don't set id inside constructer because noteId will be auto generated. Instead we use a setter method for setting value in noteId.
    }

    public String getNoteDateTime() {
        return noteDateTime;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public int getNotePriority() {
        return notePriority;
    }
}
