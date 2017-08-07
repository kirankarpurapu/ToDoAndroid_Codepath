package com.example.kirank.todo.Data;

import android.support.annotation.NonNull;

import com.example.kirank.todo.model.TodoItem;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kirank on 8/6/17.
 */

public class DataSource {

    private final ArrayList<TodoItem> todoItems;

    public DataSource() {
        this.todoItems = new ArrayList<>();
        this.todoItems.add( new TodoItem("Task1", new Date()));
        this.todoItems.add( new TodoItem("Task2", new Date()));
        this.todoItems.add( new TodoItem("Task3", new Date()));
    }

    public boolean addItem(@NonNull final TodoItem todoItem) {

        return this.todoItems.add(todoItem);
    }
    public ArrayList<TodoItem> getTodoItems() {

        return this.todoItems;
    }

    public TodoItem remove(final int position) {

        return this.todoItems.remove(position);
    }

    public TodoItem get(final int position) {

        return this.todoItems.get(position);
    }

    public void add(final int position, final TodoItem item) {

        this.todoItems.add(position, item);
    }
}
