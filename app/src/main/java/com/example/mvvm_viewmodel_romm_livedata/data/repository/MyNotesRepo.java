package com.example.mvvm_viewmodel_romm_livedata;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.mvvm_viewmodel_romm_livedata.Room.EntityTableNotes;
import com.example.mvvm_viewmodel_romm_livedata.Room.MyNotesDao;
import com.example.mvvm_viewmodel_romm_livedata.Room.MyNotesDatabase;

import java.util.List;

public class MyNotesRepo {

    private MyNotesDao notesDao;
    private LiveData<List<EntityTableNotes>> allNotes ;

    public MyNotesRepo(Application application){

        MyNotesDatabase myNotesDatabase = MyNotesDatabase.getMyNotesDatabaseInstance(application);
        notesDao = myNotesDatabase.getMyNotesDao() ;
        allNotes = myNotesDatabase.getMyNotesDao().getAllNotes() ;
    }

    public void insert(EntityTableNotes notes){
        new InsertAsynkTask(notesDao).execute(notes);
    }

    public void delete(EntityTableNotes notes){

        new DeleteAsynkTask(notesDao).execute(notes);
    }

    public void update(EntityTableNotes notes){
        new UpdateAsynkTask(notesDao).execute(notes);
    }

    public void deleteAll(){
        new DeleteAllAsynkTask(notesDao).execute();
    }

    public LiveData<List<EntityTableNotes>> getAllNotes() {
        return allNotes;
    }

    private static class InsertAsynkTask extends AsyncTask<EntityTableNotes,Void,Void>{

        private MyNotesDao notesDao ;

        public InsertAsynkTask(MyNotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(EntityTableNotes... entityTableNotes) {
            notesDao.insert(entityTableNotes[0]);
            return null ;

        }
    }

    private static class DeleteAsynkTask extends AsyncTask<EntityTableNotes,Void,Void>{

        private MyNotesDao notesDao ;

        public DeleteAsynkTask(MyNotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(EntityTableNotes... entityTableNotes) {
            notesDao.delete(entityTableNotes[0]);
            return null ;

        }
    }

    private static class UpdateAsynkTask extends AsyncTask<EntityTableNotes,Void,Void>{

        private MyNotesDao notesDao ;

        public UpdateAsynkTask(MyNotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(EntityTableNotes... entityTableNotes) {
            notesDao.update(entityTableNotes[0]);
            return null ;

        }
    }

    private static class DeleteAllAsynkTask extends AsyncTask<Void,Void,Void>{

        private MyNotesDao notesDao ;

        public DeleteAllAsynkTask(MyNotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.deleteAll();
            return null ;

        }
    }


}
