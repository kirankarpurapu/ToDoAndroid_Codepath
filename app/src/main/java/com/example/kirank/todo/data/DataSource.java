package com.example.kirank.todo.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.database.ToDo;
import com.example.kirank.todo.model.TodoItem;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirank on 8/6/17.
 */

public class DataSource {

    private static final ArrayList<TodoItem> todoItems = new ArrayList<>();

    private static final List<ToDo> toDoItems;

    static {
        toDoItems = SQLite.select().from(ToDo.class).queryList();

        ToDo todo1 = new ToDo();
        todo1.setId(1);
        todo1.setToDoText("Task1");
        todo1.save();

        ToDo todo2 = new ToDo();
        todo2.setId(2);
        todo2.setToDoText("Task2");
        todo2.save();


        todoItems.add( new TodoItem("Task1"));
        todoItems.add( new TodoItem("Task2"));
        todoItems.add( new TodoItem("Task3"));
        todoItems.add( new TodoItem("Task4"));

    }

    public void addItem(@NonNull final TodoItem todoItem) {

        todoItems.add(todoItem);

        ToDo todo = new ToDo();

        todo.setId(Constants.ID ++);
        todo.setToDoText(todoItem.getTodoTask());
        todo.setToDoNotes(todoItem.getNotes());
        todo.setToDoDate(todoItem.getDueDate());
        todo.setToDoPriority(todoItem.getPriority());
        todo.setToDoCompleted(todoItem.isCompleted());

        todo.save();
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
