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

        System.out.println("Experimenting out with 'experimentalBranch'. And making a commit in child branch.");
        System.out.println("Experimenting out with 'experimentalBranch'. And making a commit in child branch.");
        System.out.println("Experimenting out with 'experimentalBranch'. And making a commit in child branch.");
        System.out.println("Experimenting out with 'experimentalBranch'. And making a commit in child branch.");
        System.out.println("Experimenting out with 'experimentalBranch'. And making a commit in child branch.");
        System.out.println("Experimenting out with 'experimentalBranch'. And making a commit in child branch.");
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
