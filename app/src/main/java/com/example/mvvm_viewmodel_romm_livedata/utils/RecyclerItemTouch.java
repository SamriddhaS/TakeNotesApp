package com.example.mvvm_viewmodel_romm_livedata.utils;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerItemTouch extends ItemTouchHelper.SimpleCallback {

    RecyclerItemTouchListner mListener;

    public RecyclerItemTouch(int dragDirs, int swipeDirs, RecyclerItemTouchListner mListener) {
        super(dragDirs, swipeDirs);
        this.mListener = mListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        if (mListener != null)
            mListener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }




    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        if (viewHolder != null) {
            View forgroundView = ((RecyclerViewHolder) viewHolder).foregroundView;
            getDefaultUIUtil().clearView(forgroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (viewHolder != null) {
            View forgroundView = ((RecyclerViewHolder) viewHolder).foregroundView;
            getDefaultUIUtil().onDraw(c, recyclerView, forgroundView, dX, dY, actionState, isCurrentlyActive);

        }


    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        if (viewHolder != null) {
            View forgroundView = ((RecyclerViewHolder) viewHolder).foregroundView;
            getDefaultUIUtil().onSelected(forgroundView);
        }

    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


        if (viewHolder != null) {
            View forgroundView = ((RecyclerViewHolder) viewHolder).foregroundView;
            getDefaultUIUtil().onDrawOver(c, recyclerView, forgroundView, dX, dY, actionState, isCurrentlyActive);
        }

    }



}
