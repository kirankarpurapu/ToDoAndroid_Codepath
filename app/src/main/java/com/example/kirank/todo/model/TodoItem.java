package com.example.kirank.todo.model;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kirank on 8/6/17.
 */

/**
 * This is a class that represents a todo Item.
 */
public class TodoItem {

    private String todoTask;
    private Priority priority = Priority.DEFAULT;
    private Calendar dueDate = null;
    private String notes;
    private boolean isCompleted;


    public String getNotes() {
        return this.notes;
    }

    public void setNotes(@NonNull final String notes) {
        this.notes = notes;
    }


    public TodoItem(@NonNull final String todoTask) {
        this.todoTask =todoTask;
    }

    public TodoItem(@NonNull final String todoTask, @NonNull final Calendar date) {
        this.todoTask = todoTask;
        this.dueDate = date;
    }

    public String getTodoTask() {
        return this.todoTask;
    }

    public void setTodoTask(@NonNull final  String todoTask) {
        this.todoTask = todoTask;
    }

    public Calendar getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(@NonNull final  Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setPriority(@NonNull final  Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(final boolean completed) {
        isCompleted = completed;
    }
}
