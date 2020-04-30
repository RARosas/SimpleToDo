package com.rar.simpletodo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerAdapter.onButtonsClicks{

    private RecyclerView recyclerView;
    private FloatingActionButton addTaskButton;
    private ArrayList<Task> tasks;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    private void start() {
        addTaskButton = findViewById(R.id.addTaskButton);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(tasks, MainActivity.this);
        recyclerView.setAdapter(adapter);
        addTaskButton.setOnClickListener(this);
        tasks = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        addTask();
    }

    public void addTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle("Nueva Tarea");
        View newtaskdialog = LayoutInflater
                .from(MainActivity.this)
                .inflate(R.layout.newtaskdialog, null);
        builder.setView(newtaskdialog);
        final EditText newtaskname = newtaskdialog.findViewById(R.id.newTaskTitleView);
        final EditText newtaskdesc = newtaskdialog.findViewById(R.id.newTaskDescView);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tasks.add(new Task(
                        newtaskname.getText().toString(),
                        newtaskdesc.getText().toString()
                ));
                adapter = new RecyclerAdapter(tasks, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
        builder.create().show();
    }

    @Override
    public void deleteButtonClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("¿Borrar Tarea?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tasks.remove(position);
                adapter = new RecyclerAdapter(tasks, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
        builder.create().show();
    }

    @Override
    public void changeTaskStatus(int position) {
        tasks.get(position).setDone(
                !tasks.get(position).getDone()
        );
    }
}
