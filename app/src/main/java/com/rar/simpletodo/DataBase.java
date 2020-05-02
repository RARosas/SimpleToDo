package com.rar.simpletodo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class DataBase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "TasksDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "TasksToDo";
    private static final String TASKID = "idTask";
    private static final String TASKTITLE = "titleTask";
    private static final String TASKDESC = "descTask";
    private static final String TASKSTATUS = "statusTask";

    private static final String SENTENCE =
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    TASKID + " INTEGER PRIMARY KEY NOT NULL, " +
                    TASKTITLE + " VARCHAR(45) NOT NULL, " +
                    TASKDESC + " VARCHAR(45), " +
                    TASKSTATUS + " INTEGER)";

    private SQLiteDatabase tasksDatabase;

    DataBase(Context context, SQLiteDatabase db)    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.tasksDatabase = db;
        tasksDatabase.execSQL(SENTENCE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SENTENCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ArrayList<Task> readTasks()    {
        tasksDatabase.execSQL(SENTENCE);
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = tasksDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + ";", null);
        if (cursor.moveToFirst()){
            do {
                tasks.add( new Task (
                        cursor.getInt(cursor.getColumnIndex(TASKID)),
                        cursor.getString(cursor.getColumnIndex(TASKTITLE)),
                        cursor.getString(cursor.getColumnIndex(TASKDESC)),
                        cursor.getInt(cursor.getColumnIndex(TASKSTATUS))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return tasks;
    }

    void insertTask(Task task)  {
        tasksDatabase.execSQL(SENTENCE);
        ContentValues values = new ContentValues();
        values.put(TASKID, task.getId());
        values.put(TASKTITLE, task.getTitle());
        values.put(TASKDESC, task.getDescription());
        values.put(TASKSTATUS, task.getDone());
        tasksDatabase.insert(TABLE_NAME, null, values);
    }

    void deleteTask(Task task)   {
        tasksDatabase.execSQL(SENTENCE);
        tasksDatabase.delete(TABLE_NAME, TASKID + " = ?", new String[]{String.valueOf(task.getId())});
    }

    void updateTask(Task task)   {
        tasksDatabase.execSQL(SENTENCE);
        ContentValues values = new ContentValues();
        values.put(TASKID, task.getId());
        values.put(TASKTITLE, task.getTitle());
        values.put(TASKDESC, task.getDescription());
        values.put(TASKSTATUS, task.getDone());
        tasksDatabase.update(TABLE_NAME, values, TASKID + " = ?", new String[]{String.valueOf(task.getId())});
    }

    void databaseClose() {
        tasksDatabase.close();
    }
}
