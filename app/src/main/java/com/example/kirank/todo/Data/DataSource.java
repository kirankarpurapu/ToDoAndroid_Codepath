package com.example.kirank.todo.Data;

import android.support.annotation.NonNull;

import com.example.kirank.todo.model.TodoItem;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kirank on 8/6/17.
 */

public class DataSource {

    private static final ArrayList<TodoItem> todoItems = new ArrayList<>();

    static {
        todoItems.add( new TodoItem("Task1"));
        todoItems.add( new TodoItem("Task2"));
        todoItems.add( new TodoItem("Task3"));
        todoItems.add( new TodoItem("Task4"));
    }

    public boolean addItem(@NonNull final TodoItem todoItem) {

        return todoItems.add(todoItem);
    }
    public ArrayList<TodoItem> getTodoItems() {

        return todoItems;
    }

    public TodoItem remove(final int position) {

        return todoItems.remove(position);
    }

    public TodoItem get(final int position) {

        return todoItems.get(position);
    }

    public void add(final int position, final TodoItem item) {

        todoItems.add(position, item);
    }

    public int getSize() {
        return todoItems.size();
    }
}
