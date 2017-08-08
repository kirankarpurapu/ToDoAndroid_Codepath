package com.example.kirank.todo.listener;

/**
 * Created by kirank on 8/6/17.
 */

public interface TodoItemClickListener {

    void clicked(int position);
    void checked(int position, int time);
}
