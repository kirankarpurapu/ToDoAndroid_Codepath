package com.example.kirank.todo.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by kirank on 8/12/17.
 */

@Database(name = ToDoDatabase.NAME, version = ToDoDatabase.VERSION)
public class ToDoDatabase {

    public static final String NAME = "ToDo";
    public static final int VERSION = 1;
}
