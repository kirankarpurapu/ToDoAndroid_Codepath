package com.example.kirank.todo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kirank.todo.R;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.listener.TodoItemClickListener;
import com.example.kirank.todo.model.TodoItem;

import java.util.ArrayList;

/**
 * Created by kirank on 8/6/17.
 */

public class ToDoMainAdapter extends RecyclerView.Adapter<ToDoMainAdapter.CustomViewHolder>{

    private final ArrayList<TodoItem> todoItems;
    private final Context activity;
    private final LayoutInflater layoutInflater;
    private final TodoItemClickListener todoItemClickListener;

    public ToDoMainAdapter(@NonNull final ArrayList<TodoItem> list, Context activity, TodoItemClickListener todoItemClickListener) {
        super();
        this.todoItems = list;
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        this.todoItemClickListener = todoItemClickListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View view = layoutInflater.inflate(R.layout.todo_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        TodoItem item = todoItems.get(position);
        holder.nameOfTodoItem.setText(item.getTodoTask());
    }

    @Override
    public int getItemCount() {
        return this.todoItems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        private TextView nameOfTodoItem;

        public CustomViewHolder(final View itemView) {
            super(itemView);
            nameOfTodoItem = (TextView) itemView.findViewById(R.id.todo_item);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {
            final int position = getAdapterPosition();
            todoItemClickListener.clicked(position);
        }
    }
}
