package com.example.mvvm_viewmodel_romm_livedata.Adaptar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mvvm_viewmodel_romm_livedata.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView noteTitle,noteBody,notePriority,noteDateTime ;
    public RelativeLayout backgroundView ;
    public CoordinatorLayout foregroundView ;
    public LinearLayout checkBoxLayout;
    CheckBox checkBox ;
    CardView cardView ;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        noteTitle = (TextView)itemView.findViewById(R.id.noteTitle);
        noteBody = (TextView)itemView.findViewById(R.id.noteBody);
        notePriority = (TextView) itemView.findViewById(R.id.notePriority);
        noteDateTime = (TextView) itemView.findViewById(R.id.noteDateTime);
        foregroundView = (CoordinatorLayout) itemView.findViewById(R.id.viewForeground);
        backgroundView = (RelativeLayout)itemView.findViewById(R.id.viewBackground);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        checkBoxLayout = (LinearLayout)itemView.findViewById(R.id.checkboxLayout);


    }

}
