package com.example.todoapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddNewTask;
import com.example.todoapp.MainActivity;
import com.example.todoapp.MainPage;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.R;
import com.example.todoapp.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<ToDoModel> mList;
    private MainPage activity;
    private DataBaseHelper myDB;
    public ToDoAdapter(DataBaseHelper myDB , MainPage activity){
        this.activity = activity;
        this.myDB = myDB;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent , false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind data to the views in each item of the RecyclerView
        final ToDoModel item = mList.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        // Set a listener for the CheckBox to handle its state change
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the status of the task in the database based on the CheckBox state
                if (isChecked){
                    myDB.updateStatus(item.getId() , 1);
                }else
                    myDB.updateStatus(item.getId() , 0);
            }
        });
    }
    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ToDoModel> mList){
        // Update the list of tasks and notify the adapter of the data change
        this.mList = mList;
        notifyDataSetChanged();
    }
    public void deletTask(int position){
        // Delete a task from the list and notify the adapter
        ToDoModel item = mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        // Edit a task by showing the AddNewTask fragment with the task data
        ToDoModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());
    }
    @Override
    public int getItemCount() {
        // Return the number of items in the RecyclerView
        return mList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the CheckBox view
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}