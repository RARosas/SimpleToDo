package com.rar.simpletodo;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, RecyclerAdapter.onButtonsClicks {

    private RecyclerView recyclerView;
    private ArrayList<Task> tasks;
    private RecyclerAdapter adapter;
    private DataBase dataBase;
    public static final String DATABASE_NAME = "TasksDB";
    private boolean doubleBackToExitPressedOnce = false;
    private boolean theme = true;
    private Bitmap bitmap = null;
    private AdView mAdView;

    //App start.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    //Initialization of elements.
    private void start() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        FloatingActionButton addTaskButton = findViewById(R.id.addTaskButton);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addTaskButton.setOnClickListener(this);
        //ImageButton light_night = findViewById(R.id.changeBGButton);
        //light_night.setOnClickListener(this);
        //open database and recover all data in it.
        SQLiteDatabase tasksDB = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        dataBase = new DataBase(this, tasksDB);
        tasks = dataBase.readTasks();
        adapter = new RecyclerAdapter(tasks, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    //override method of floatingActionButton
    @Override
    public void onClick(View v) {
        switch (v.getId())  {
            case R.id.addTaskButton:
                addTask();
                break;
            //case R.id.changeBGButton:
                //open media from phone and load image to background.
                //saves image path to database.
            default:
                break;
        }
    }

    //method to add task to view and Database
    public void addTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle("New Task");
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

    //method to delete task from view and database
    @Override
    public void deleteButtonClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("¿Delete Task?");
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

    //method to update task on view and database
    @Override
    public void editButtonClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Edit Task");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        View editTasDialog = LayoutInflater
                .from(MainActivity.this)
                .inflate(R.layout.newtaskdialog, null);
        builder.setView(editTasDialog);
        final EditText newtaskname = editTasDialog.findViewById(R.id.newTaskTitleView);
        final EditText newtaskdesc = editTasDialog.findViewById(R.id.newTaskDescView);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = tasks.get(position).getId();
                Task tarea = new Task (
                        id,
                        newtaskname.getText().toString(),
                        newtaskdesc.getText().toString(),
                        0
                );
                tasks.get(position).setTitle(tarea.getTitle());
                tasks.get(position).setDescription(tarea.getDescription());
                tasks.get(position).setDone(tarea.getDone());
                dataBase.updateTask(tarea);
                adapter = new RecyclerAdapter(tasks, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
        builder.create().show();
    }

    //method to update status of task on view and database
    @Override
    public void changeTaskStatus(int position) {
        if (tasks.get(position).getDone()==1)   {
            tasks.get(position).setDone(0);
        } else  {
            tasks.get(position).setDone(1);
        }
        dataBase.updateTask(tasks.get(position));
    }

    //method to check exit from app and close database
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
