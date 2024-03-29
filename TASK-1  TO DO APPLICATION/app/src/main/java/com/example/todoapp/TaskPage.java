package com.example.todoapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class TaskPage extends AppCompatActivity {

    // ArrayList to store the notes
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu with the add_note_menu.xml file
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item selection
        if(item.getItemId() == R.id.add_note){
            // Start the NoteEditorActivity to add a new note
            Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listView = (ListView) findViewById(R.id.listView);

        // Retrieve the notes from SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.todoapp", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        // If no notes exist, add an example note
        if(set == null){
            notes.add("Example Note");
        }else{
            notes = new ArrayList(set);
        }

        // Set up the ArrayAdapter for the ListView
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);

        // Handle item click on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start the NoteEditorActivity to edit the selected note
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class);
                intent.putExtra("noteId",position);
                startActivity(intent);
            }
        });

        // Handle long item click on the ListView
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final int itemToDelete = position;
                // Display a confirmation dialog to delete the note
                new AlertDialog.Builder(TaskPage.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int position) {
                                        // Remove the note from the list and update the adapter
                                        notes.remove(itemToDelete);
                                        arrayAdapter.notifyDataSetChanged();
                                        // Update the notes in SharedPreferences
                                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.todoapp", Context.MODE_PRIVATE);
                                        HashSet<String> set = new HashSet(TaskPage.notes);
                                        sharedPreferences.edit().putStringSet("notes", set).apply();
                                    }

                                }
                        )
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }


}