package com.example.mvvm_viewmodel_romm_livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.mvvm_viewmodel_romm_livedata.Adaptar.RecyclerViewAdaptar;
import com.example.mvvm_viewmodel_romm_livedata.Fragments.HomeFragment;
import com.example.mvvm_viewmodel_romm_livedata.Room.EntityTableNotes;
import com.example.mvvm_viewmodel_romm_livedata.ViewModel.MyNotesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static RecyclerItemTouchListner mListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public static void initListner(RecyclerItemTouchListner itemTouch){

        mListner = itemTouch ;
    }


    @Override
    public void onBackPressed() {

        if(HomeFragment.isInActionMode){
            mListner.onBackButtonPressed();
        }else{
            super.onBackPressed();
        }

    }
}
