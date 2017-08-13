package com.example.kirank.todo.database;

import com.example.kirank.todo.model.Priority;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Calendar;

/**
 * Created by kirank on 8/12/17.
 */

@Table(database = ToDoDatabase.class)

public class ToDo extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private int id;

    @Column
    private String toDoText;

    @Column
    private Priority toDoPriority = Priority.DEFAULT;

    @Column
    private String toDoNotes;

    @Column
    private boolean isToDoCompleted = false;

    @Column
    private Calendar toDoDate;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getToDoText() {
        return toDoText;
    }

    public void setToDoText(final String toDoText) {
        this.toDoText = toDoText;
    }

    public Priority getToDoPriority() {
        return toDoPriority;
    }

    public void setToDoPriority(final Priority toDoPriority) {
        this.toDoPriority = toDoPriority;
    }

    public String getToDoNotes() {
        return toDoNotes;
    }

    public void setToDoNotes(final String toDoNotes) {
        this.toDoNotes = toDoNotes;
    }

    public boolean isToDoCompleted() {
        return isToDoCompleted;
    }

    public void setToDoCompleted(final boolean toDoCompleted) {
        isToDoCompleted = toDoCompleted;
    }

    public Calendar getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(final Calendar toDoDate) {
        this.toDoDate = toDoDate;
    }
}
