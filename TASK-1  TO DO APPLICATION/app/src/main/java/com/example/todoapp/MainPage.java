package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class MainPage extends AppCompatActivity implements OnDialogCloseListener {

    //variables
    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;
    SharedPreferences.Editor editor;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Set the toolbar as the activity's action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        mRecyclerview = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);

        // Create an instance of the database helper
        myDB = new DataBaseHelper(MainPage.this);

        // Initialize list and adapter for the RecyclerView
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB , MainPage.this);

        // Configure RecyclerView
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);

        // Retrieve all tasks from the database and display them in reverse order
        mList = myDB.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        // Initialize shared preferences for storing login information
        sp = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sp.edit();

        // Set click listener for the fab button to add a new task
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
            }
        });

        // Attach swipe-to-delete functionality to the RecyclerView using ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerview);
    }

    // Callback method called when the dialog is closed
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        // Refresh the task list and update the adapter when the dialog is closed
        mList = myDB.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
    }

    // Create options menu in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    // Handle option menu item selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id=item.getItemId();
        if (item_id==R.id.logout) {

            // Clear the login information and navigate to the main activity
            editor.clear();
            editor.apply();
            startActivity(new Intent(MainPage.this, MainActivity.class));
            finish();
        }
        if (item_id==R.id.task) {

            // Navigate to the task page activity
            startActivity(new Intent(MainPage.this, TaskPage.class));
        }
        return true;
    }

}


