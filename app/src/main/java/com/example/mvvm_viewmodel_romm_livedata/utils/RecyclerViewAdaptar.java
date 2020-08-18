package com.example.mvvm_viewmodel_romm_livedata.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mvvm_viewmodel_romm_livedata.R;
import com.example.mvvm_viewmodel_romm_livedata.data.db.EntityTableNotes;
import com.example.mvvm_viewmodel_romm_livedata.ui.homefragment.HomeFragment;

public class RecyclerViewAdaptar extends ListAdapter<EntityTableNotes, RecyclerViewHolder> {

    private RecyclerItemTouchListner mListner;
    public static final int ITEM_TYPE_LIST = 0;
    public static final int ITEM_TYPE_GRID = 1;
    private int mViewType = 0 ;


    public RecyclerViewAdaptar() {
        super(diffCallBack);
    }

    private static final DiffUtil.ItemCallback<EntityTableNotes> diffCallBack = new DiffUtil.ItemCallback<EntityTableNotes>() {
        @Override
        public boolean areItemsTheSame(@NonNull EntityTableNotes oldItem, @NonNull EntityTableNotes newItem) {
            return oldItem.getNoteId() == newItem.getNoteId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull EntityTableNotes oldItem, @NonNull EntityTableNotes newItem) {
            return oldItem.getNoteTitle().equals(newItem.getNoteTitle()) &&
                    oldItem.getNoteBody().equals(newItem.getNoteBody()) &&
                    oldItem.getNotePriority() == newItem.getNotePriority();
        }
    };


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (mViewType == ITEM_TYPE_LIST){
            return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitem, parent, false));

        }else{

            return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singleitemgrid, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {


        holder.noteTitle.setText(getItem(position).getNoteTitle());
        holder.noteBody.setText(getItem(position).getNoteBody());
        holder.noteDateTime.setText(getItem(position).getNoteDateTime());
        holder.notePriority.setText(String.valueOf(getItem(position).getNotePriority()));

        if (!HomeFragment.isInActionMode) {
            holder.checkBox.setVisibility(View.GONE);
            holder.checkBoxLayout.setVisibility(View.GONE);
        } else {

            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBoxLayout.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

        if (HomeFragment.isInActionMode && HomeFragment.selectAllFlag) {

            if (!holder.checkBox.isChecked())
                holder.checkBox.setChecked(true);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListner != null && position != RecyclerView.NO_POSITION)
                    mListner.onItemClick(getItem(position));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (mListner != null) {
                    mListner.onLongClick();
                    return true;
                }
                return false;
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListner != null)
                    if (holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(true);
                        mListner.onClickCheckBox(true, position);
                    }else{
                        holder.checkBox.setChecked(false);
                        mListner.onClickCheckBox(false,position);
                    }

            }
        });

        holder.checkBoxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListner != null)
                    if (!holder.checkBox.isChecked()){
                        holder.checkBox.setChecked(true);
                        mListner.onClickCheckBox(true, position);
                    }else{
                        holder.checkBox.setChecked(false);
                        mListner.onClickCheckBox(false,position);
                    }
            }
        });


    }

    public void setViewType(int viewType) {
        mViewType = viewType;
        notifyDataSetChanged();
    }

    public EntityTableNotes getNoteAt(int position) {
        return getItem(position);
    }

    public void setItemLongClick(RecyclerItemTouchListner listner) {
        mListner = listner;
    }

}
