package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    //variables
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        // Set the toolbar as the activity's action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing views
        EditText editText = (EditText) findViewById(R.id.editText);

        // Retrieve the noteId from the intent
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        // Check if the noteId is valid
        if(noteId != -1){
            // Set the text of the EditText to the corresponding note text
            editText.setText(TaskPage.notes.get(noteId));
        }else{
            // Add a new empty note to the notes list and update the noteId
            TaskPage.notes.add("");
            noteId = TaskPage.notes.size()-1;
            TaskPage.arrayAdapter.notifyDataSetChanged();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the corresponding note text in the notes list
                TaskPage.notes.set(noteId,String.valueOf(s));
                TaskPage.arrayAdapter.notifyDataSetChanged();

                // Save the updated notes list to SharedPreferences
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.todoapp", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(TaskPage.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}