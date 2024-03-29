package com.example.todoapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    //widgets
    private EditText mEditText;
    private Button mSaveButton;
    private DataBaseHelper myDb;
    public static AddNewTask newInstance(){
        return new AddNewTask();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for the bottom sheet dialog
        View v = inflater.inflate(R.layout.activity_add_new_task , container , false);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the EditText and Button
        mEditText = view.findViewById(R.id.edittext);
        mSaveButton = view.findViewById(R.id.button_save);

        // Create an instance of the database helper
        myDb = new DataBaseHelper(getActivity());

        // Check if it's an update operation by checking the bundle
        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            mEditText.setText(task);

            // Disable the save button if the task description is already filled
            if (task.length() > 0 ){
                mSaveButton.setEnabled(false);
            }
        }

        // Set a TextWatcher on the EditText to enable/disable the save button based on text changes
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Enable/disable the save button and change its background color based on text presence
                if (s.toString().equals("")){
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }else{
                    mSaveButton.setEnabled(true);
                    mSaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        // Set a click listener on the save button to perform the save or update operation
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();

                if (finalIsUpdate){
                    // If it's an update operation, update the task in the database
                    myDb.updateTask(bundle.getInt("id") , text);
                }else{
                    // If it's a new task, insert it into the database
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    myDb.insertTask(item);
                }
                dismiss(); // Close the bottom sheet dialog

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}