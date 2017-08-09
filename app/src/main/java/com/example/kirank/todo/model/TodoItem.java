package com.example.kirank.todo.model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by kirank on 8/6/17.
 */

/**
 * This is a class that represents a todo Item.
 */
public class TodoItem {

    private String todoTask;
    private Date dueDate;
    private String notes;

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(@NonNull final String notes) {
        this.notes = notes;
    }

    private Priority priority = Priority.DEFAULT;


    public TodoItem(@NonNull final String todoTask) {
        this.todoTask =todoTask;
    }

    public TodoItem(@NonNull final String todoTask, @NonNull final Date date) {
        this.todoTask = todoTask;
        this.dueDate = date;
    }

    public String getTodoTask() {
        return this.todoTask;
    }

    public void setTodoTask(@NonNull final  String todoTask) {
        this.todoTask = todoTask;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(@NonNull final  Date dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(@NonNull final  Priority priority) {
        this.priority = priority;
    }
}
