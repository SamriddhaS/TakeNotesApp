package com.example.mvvm_viewmodel_romm_livedata.utils;


import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_viewmodel_romm_livedata.data.db.EntityTableNotes;


public interface RecyclerItemTouchListner {

    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);

    boolean onLongClick();

    void onItemClick(EntityTableNotes note);

    void onClickCheckBox(boolean addItem,int position);

    void onBackButtonPressed();
}
