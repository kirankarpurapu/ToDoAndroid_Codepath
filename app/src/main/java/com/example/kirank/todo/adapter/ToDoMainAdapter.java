package com.example.kirank.todo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kirank.todo.R;
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
        private ImageView infoOfTodoItem;
        private CheckBox stateOfTodoItem;

        public CustomViewHolder(final View itemView) {
            super(itemView);

            this.nameOfTodoItem = (TextView) itemView.findViewById(R.id.todo_item);
            this.infoOfTodoItem = (ImageView) itemView.findViewById(R.id.info_id);
            this.stateOfTodoItem = (CheckBox) itemView.findViewById(R.id.checkBox);

            this.nameOfTodoItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(stateOfTodoItem.isChecked()) {
                        stateOfTodoItem.setChecked(false);
                    }
                    else {
                        stateOfTodoItem.setChecked(true);
                    }
                }
            });

            this.stateOfTodoItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    if(isChecked) {
                        nameOfTodoItem.setPaintFlags(nameOfTodoItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        removeAfterMilliSeconds(getAdapterPosition(), 2000);
                    }
                    else {
                        nameOfTodoItem.setPaintFlags(0);
                    }
                }
            });
            this.infoOfTodoItem.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {
            final int position = getAdapterPosition();
            todoItemClickListener.clicked(position);
        }
    }

    private void removeAfterMilliSeconds(final int position, int time) {
        todoItemClickListener.checked(position, time);
    }
}
