package com.example.mvvm_viewmodel_romm_livedata.Room;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {EntityTableNotes.class},version = 1)
public abstract class MyNotesDatabase extends RoomDatabase {

    private static MyNotesDatabase mInstance ;

    public abstract MyNotesDao getMyNotesDao();

    public static synchronized MyNotesDatabase getMyNotesDatabaseInstance(Context context){

        if (mInstance==null){

            mInstance = Room.databaseBuilder(context.getApplicationContext(),MyNotesDatabase.class,"mynotes_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(databaseCallBack)
                    .build();

        }
        return mInstance ;

    }

    private static MyNotesDatabase.Callback databaseCallBack = new MyNotesDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDBAsynTask(mInstance).execute();
        }
    };

    private static class populateDBAsynTask extends AsyncTask<Void,Void,Void>{

        private MyNotesDao notesDao ;

        public populateDBAsynTask(MyNotesDatabase db) {
            this.notesDao = db.getMyNotesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            notesDao.insert(new EntityTableNotes("My Note 1","This is a private note",1,"10-04-2020 09:16"));
            notesDao.insert(new EntityTableNotes("My Note 2","This is a private note",2,"10-04-2020 09:16"));
            notesDao.insert(new EntityTableNotes("My Note 3","This is a private note",3,"10-04-2020 09:16"));
            return null;
        }
    }
}
