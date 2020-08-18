package com.example.mvvm_viewmodel_romm_livedata.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvvm_viewmodel_romm_livedata.Adaptar.RecyclerViewAdaptar;
import com.example.mvvm_viewmodel_romm_livedata.MainActivity;
import com.example.mvvm_viewmodel_romm_livedata.R;
import com.example.mvvm_viewmodel_romm_livedata.Adaptar.RecyclerItemTouch;
import com.example.mvvm_viewmodel_romm_livedata.RecyclerItemTouchListner;
import com.example.mvvm_viewmodel_romm_livedata.Room.EntityTableNotes;
import com.example.mvvm_viewmodel_romm_livedata.SharedPrefManager;
import com.example.mvvm_viewmodel_romm_livedata.ViewModel.MyNotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, RecyclerItemTouchListner {

    private NavController navController;
    private FloatingActionButton addNote;
    private RecyclerView recyclerView;
    private MyNotesViewModel notesViewModel;
    private RecyclerViewAdaptar recyclerViewAdaptar;
    private CoordinatorLayout cooLayout;
    private androidx.appcompat.widget.Toolbar toolbar;
    private TextView countSelectedTv;
    public static boolean isInActionMode = false;
    public static boolean selectAllFlag = false;
    private List<EntityTableNotes> selectionList = new ArrayList<>();
    private int selectionCounter = 0;
    private List<EntityTableNotes> restoreData = new ArrayList<>();
    private SharedPrefManager sharedPrefManager;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        addNote = view.findViewById(R.id.addNoteBtn);
        recyclerView = view.findViewById(R.id.recyclerView);
        notesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MyNotesViewModel.class);
        recyclerViewAdaptar = new RecyclerViewAdaptar();
        MainActivity.initListner(this);
        cooLayout = view.findViewById(R.id.fragmentHome);
        toolbar = view.findViewById(R.id.contaxualToolbar);
        countSelectedTv = view.findViewById(R.id.countSelectedTv);
        sharedPrefManager = new SharedPrefManager(getContext());
        recyclerViewAdaptar.setItemLongClick(this);


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setRecyclerViewLayout(sharedPrefManager.getLayoutSetting(SharedPrefManager.LAYOUT_INFO_KEY));

        countSelectedTv.setText("All Notes");

        notesViewModel.getAlldata().observe(this, new Observer<List<EntityTableNotes>>() {
            @Override
            public void onChanged(List<EntityTableNotes> entityTableNotes) {

                recyclerViewAdaptar.submitList(entityTableNotes);
            }
        });

        addNote.setOnClickListener(this);

        ItemTouchHelper.SimpleCallback touchHelper = new RecyclerItemTouch(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(touchHelper).attachToRecyclerView(recyclerView);


    }

    private void setRecyclerViewLayout(boolean viewType) {

        if (viewType) {

            recyclerViewAdaptar.setViewType(RecyclerViewAdaptar.ITEM_TYPE_GRID);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recyclerViewAdaptar);

        } else {

            recyclerViewAdaptar.setViewType(RecyclerViewAdaptar.ITEM_TYPE_LIST);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recyclerViewAdaptar);
        }

    }

    public void prepareSelectionList(boolean addItem, int position) {


        if (addItem) {

            selectionList.add(recyclerViewAdaptar.getNoteAt(position));
            selectionCounter++;
            updateCounterText(selectionCounter);
        } else {

            selectionList.remove(recyclerViewAdaptar.getNoteAt(position));
            selectionCounter--;
            updateCounterText(selectionCounter);
        }
    }

    private void updateCounterText(int selectionCounter) {

        if (selectionCounter == 0) {

            countSelectedTv.setText("0 Item Selected");

        } else if (selectionCounter == 1) {

            countSelectedTv.setText("1 Item Selected");

        } else {

            countSelectedTv.setText(selectionCounter + " Items Selected");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addNoteBtn:
                if (navController != null)
                    navController.navigate(R.id.action_homeFragment2_to_addNoteFragment);

                break;
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder != null) {

            final EntityTableNotes deleteData = notesViewModel.getAlldata().getValue().get(viewHolder.getAdapterPosition());

            notesViewModel.deleteNote(recyclerViewAdaptar.getNoteAt(viewHolder.getAdapterPosition()));
            Snackbar snackbar = Snackbar.make(cooLayout, "Note Deleted", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notesViewModel.insertNote(deleteData);
                }
            });

            snackbar.setTextColor(Color.GREEN);
            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
        }
    }

    @Override
    public boolean onLongClick() {

        if (!isInActionMode) {
            startActionMode();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(EntityTableNotes note) {

        if (navController != null) {

            if (isInActionMode)
                stopActionMode();


            Bundle bundle = new Bundle();
            bundle.putInt(AddNoteFragment.KEY_NOTE_ID, note.getNoteId());
            bundle.putString(AddNoteFragment.KEY_NOTE_TITLE, note.getNoteTitle());
            bundle.putString(AddNoteFragment.KEY_NOTE_BODY, note.getNoteBody());
            bundle.putInt(AddNoteFragment.KEY_NOTE_PRIORITY, note.getNotePriority());
            bundle.putString(AddNoteFragment.KEY_NOTE_DATETIME, note.getNoteDateTime());

            navController.navigate(R.id.action_homeFragment2_to_addNoteFragment, bundle);
        }

    }


    @Override
    public void onClickCheckBox(boolean addItem, int position) {

        prepareSelectionList(addItem, position);
    }

    @Override
    public void onBackButtonPressed() {

        if (isInActionMode) {
            stopActionMode();
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
        if (sharedPrefManager.getLayoutSetting(SharedPrefManager.LAYOUT_INFO_KEY)) {
            menu.findItem(R.id.gridViewItem).setChecked(true);
        } else {
            menu.findItem(R.id.listViewItem).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.deleteAll: {

                final List<EntityTableNotes> deleteData = notesViewModel.getAlldata().getValue();

                notesViewModel.deleteAllNote();

                Snackbar snackbar = Snackbar.make(cooLayout, "All Notes Deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("Restore All", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (EntityTableNotes data : deleteData) {
                            notesViewModel.insertNote(data);
                        }
                    }
                });

                snackbar.setTextColor(Color.GREEN);
                snackbar.setActionTextColor(Color.GREEN);
                snackbar.show();
                return true;

            }
            case R.id.deleteSelected: {


                if (selectionList.isEmpty()) {

                    Toast.makeText(getContext(), "Please Select An Item To Delete", Toast.LENGTH_SHORT).show();
                    return false;

                }

                restoreData.addAll(selectionList);

                for (EntityTableNotes data : selectionList) {
                    notesViewModel.deleteNote(data);
                    //restoreData.add(data);
                }

                Snackbar snackbar = Snackbar.make(cooLayout, selectionCounter + " Items Deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (EntityTableNotes table : restoreData) {
                            notesViewModel.insertNote(table);
                        }
                        restoreData.clear();
                    }
                });

                snackbar.setTextColor(Color.GREEN);
                snackbar.setActionTextColor(Color.GREEN);
                snackbar.show();

                stopActionMode();
                return true;

            }
            case R.id.selectAll: {

                if (selectAllFlag) {
                    selectAllFlag = false;
                } else {
                    selectAllFlag = true;
                }

                if (selectAllFlag && !selectionList.isEmpty()) {
                    selectionList.clear();
                }
                if (selectAllFlag) {
                    selectionList.addAll(notesViewModel.getAlldata().getValue());
                    selectionCounter = selectionList.size();
                    countSelectedTv.setText(selectionCounter + " Items Selected");
                    recyclerViewAdaptar.notifyDataSetChanged();
                    return true;
                } else {

                    selectionCounter = 0;
                    countSelectedTv.setText(selectionCounter + " Item Selected");
                    selectionList.clear();
                    recyclerViewAdaptar.notifyDataSetChanged();
                    return true;
                }

            }
            case android.R.id.home: {

                stopActionMode();
                return true;
            }
            case R.id.listViewItem:

                item.setChecked(true);
                sharedPrefManager.setLayoutSetting(SharedPrefManager.LAYOUT_INFO_KEY, false);
                setRecyclerViewLayout(sharedPrefManager.getLayoutSetting(SharedPrefManager.LAYOUT_INFO_KEY));
                return true;

            case R.id.gridViewItem:


                item.setChecked(true);
                sharedPrefManager.setLayoutSetting(SharedPrefManager.LAYOUT_INFO_KEY, true);
                setRecyclerViewLayout(sharedPrefManager.getLayoutSetting(SharedPrefManager.LAYOUT_INFO_KEY));
                return true;
            default:
                return false;

        }
    }

    public void stopActionMode() {

        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.home_menu);
        countSelectedTv.setText("0 Item selected");
        countSelectedTv.setText("All Notes");
        isInActionMode = false;
        selectAllFlag = false;
        selectionCounter = 0;
        selectionList.clear();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        recyclerViewAdaptar.notifyDataSetChanged();
    }

    public void startActionMode() {

        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.action_mode_menu);
        countSelectedTv.setVisibility(View.VISIBLE);
        countSelectedTv.setText("0 Item Selected");
        isInActionMode = true;
        recyclerViewAdaptar.notifyDataSetChanged();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


}


