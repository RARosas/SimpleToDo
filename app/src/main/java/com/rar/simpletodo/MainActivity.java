package com.rar.simpletodo;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, RecyclerAdapter.onButtonsClicks   {

    private RecyclerView recyclerView;
    private ArrayList<Task> tasks;
    private RecyclerAdapter adapter;
    private DataBase dataBase;
    public static final String DATABASE_NAME = "TasksDB";
    private boolean doubleBackToExitPressedOnce = false;

    //App start.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    //Initialization of elements.
    private void start() {
        FloatingActionButton addTaskButton = findViewById(R.id.addTaskButton);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addTaskButton.setOnClickListener(this);

        //open database and recover all data in it.
        SQLiteDatabase tasksDB = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        dataBase = new DataBase(this, tasksDB);
        tasks = dataBase.readTasks();
        adapter = new RecyclerAdapter(tasks, MainActivity.this);
        recyclerView.setAdapter(adapter);
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
                int id = 0;
                if (tasks.size()!=0)    {
                    id = tasks.get(tasks.size()-1).getId()+1;
                }
                Task tarea = new Task(
                        id,
                        newtaskname.getText().toString(),
                        newtaskdesc.getText().toString(),
                        0
                );
                tasks.add(tarea);
                dataBase.insertTask(tarea);
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
                dataBase.deleteTask(tasks.get(position));
                tasks.remove(position);
                adapter = new RecyclerAdapter(tasks, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
        builder.create().show();
    }

    @Override
    public void changeTaskStatus(int position) {
        if (tasks.get(position).getDone()==1)   {
            tasks.get(position).setDone(0);
        } else  {
            tasks.get(position).setDone(1);
        }
        dataBase.updateTask(tasks.get(position));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                dataBase.databaseClose();
                dataBase.close();
            }
        }, 2000);
        
    }
}
