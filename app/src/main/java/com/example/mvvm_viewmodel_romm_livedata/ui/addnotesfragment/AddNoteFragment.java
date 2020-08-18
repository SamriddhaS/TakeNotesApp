package com.example.mvvm_viewmodel_romm_livedata.ui.addnotesfragment;


import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_viewmodel_romm_livedata.R;
import com.example.mvvm_viewmodel_romm_livedata.data.db.EntityTableNotes;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNoteFragment extends Fragment {

    private EditText addNoteTitle, addNoteBody;
    private TextView title , addNoteDateTime;
    private NumberPicker addNotePriority;
    private Toolbar toolbar;
    private AddNotesViewModel addNotesViewModel;
    private RelativeLayout relativeLayout ;
    private NavController navController;
    public static final String KEY_NOTE_ID= "com.example.mvvm_viewmodel_romm_livedata.Fragments_NoteId";
    public static final String KEY_NOTE_TITLE= "com.example.mvvm_viewmodel_romm_livedata.Fragments_NoteTitle";
    public static final String KEY_NOTE_BODY= "com.example.mvvm_viewmodel_romm_livedata.Fragments_NoteBody";
    public static final String KEY_NOTE_PRIORITY= "com.example.mvvm_viewmodel_romm_livedata.Fragments_NotePriority";
    public static final String KEY_NOTE_DATETIME= "com.example.mvvm_viewmodel_romm_livedata.Fragments_NoteDateTime";



    public AddNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addNoteTitle = (EditText) view.findViewById(R.id.addnotetitle);
        addNoteBody = (EditText) view.findViewById(R.id.addnotebody);
        addNotePriority = (NumberPicker) view.findViewById(R.id.addnotepriority);
        toolbar =(Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView)view.findViewById(R.id.toolbarTitle);
        addNoteDateTime= (TextView)view.findViewById(R.id.addNoteDateTime);
        relativeLayout = (RelativeLayout)view.findViewById(R.id.addnoteRelativeLayout);
        addNotesViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get((AddNotesViewModel.class));
        navController = Navigation.findNavController(view);

        addNotePriority.setMinValue(1);
        addNotePriority.setMaxValue(50);


        if (getArguments()!=null){

            title.setText("Edit Note");
            addNoteTitle.setText(getArguments().getString(KEY_NOTE_TITLE));
            addNoteBody.setText(getArguments().getString(KEY_NOTE_BODY));
            addNotePriority.setValue(getArguments().getInt(KEY_NOTE_PRIORITY));
            relativeLayout.setVisibility(View.VISIBLE);
            addNoteDateTime.setText("Last Edited : "+getArguments().getString(KEY_NOTE_DATETIME));

        }else {

            title.setText("Add Note");
            relativeLayout.setVisibility(View.GONE);
            addNoteDateTime.setVisibility(View.GONE);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId()==R.id.save){
                    saveNote();
                }
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navController!=null){
                    navController.navigate(R.id.action_addNoteFragment_to_homeFragment);
                }

            }
        });

    }



    private void saveNote() {

        String noteTitle = addNoteTitle.getText().toString();
        String noteBody = addNoteBody.getText().toString();
        int notePriority = addNotePriority.getValue();
        int noteId = -1 ;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  HH:mm", Locale.getDefault());
        String noteDateTime = sdf.format(new Date());

        if (getArguments()!=null){
           noteId = getArguments().getInt(KEY_NOTE_ID);
        }

        if (noteTitle.trim().isEmpty()){
            addNoteTitle.setError("Please Give A Note Title");
            addNoteTitle.requestFocus();
            return;
        }
        if (noteBody.trim().isEmpty()){
            addNoteBody.setError("Please Write A Note Description");
            addNoteBody.requestFocus();
            return;
        }

        if (noteId!=-1){

            EntityTableNotes note = new EntityTableNotes(noteTitle,noteBody,notePriority,noteDateTime);
            note.setNoteId(noteId);
            addNotesViewModel.updateNote(note);
            Toast.makeText(getContext(), "Note Edited", Toast.LENGTH_SHORT).show();
        }else{

            addNotesViewModel.insertNote(new EntityTableNotes(noteTitle,noteBody,notePriority,noteDateTime));
            Toast.makeText(getContext(), "New Note Saved", Toast.LENGTH_SHORT).show();
        }


        if (navController!=null){
            navController.navigate(R.id.action_addNoteFragment_to_homeFragment);
        }

        //hide typing pad from screen
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(),0);



    }

}
