package com.example.kirank.todo.data;

import android.support.annotation.NonNull;

import com.example.kirank.todo.database.ToDo;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirank on 8/6/17.
 */

public class DataSource {

    private static List<ToDo> toDoItems;

    static {
        toDoItems = new ArrayList<>();
        toDoItems.addAll(SQLite.select().from(ToDo.class).queryList());
    }

    public static void updateDataSource() {
        toDoItems.clear();
        toDoItems.addAll(SQLite.select().from(ToDo.class).queryList());
    }

    public static List<ToDo> getTodoItems() {
        updateDataSource();
        return toDoItems;
    }

    public static ToDo get(final int position) {
        return toDoItems.get(position);
    }
}
