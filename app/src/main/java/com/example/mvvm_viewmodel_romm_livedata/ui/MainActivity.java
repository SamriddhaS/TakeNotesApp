package com.example.mvvm_viewmodel_romm_livedata.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mvvm_viewmodel_romm_livedata.R;
import com.example.mvvm_viewmodel_romm_livedata.ui.homefragment.HomeFragment;
import com.example.mvvm_viewmodel_romm_livedata.utils.RecyclerItemTouchListner;


public class MainActivity extends AppCompatActivity {

    public static RecyclerItemTouchListner mListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private String saveSomething(){
        return "This is a experimental commit";
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
