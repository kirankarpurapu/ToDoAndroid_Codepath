package com.example.kirank.todo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kirank.todo.R;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.data.DataSource;
import com.example.kirank.todo.database.ToDo;
import com.example.kirank.todo.listener.TodoItemClickListener;
import com.example.kirank.todo.model.Priority;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kirank on 8/6/17.
 */

public class ToDoMainListAdapter extends RecyclerView.Adapter<ToDoMainListAdapter.CustomViewHolder>{

    private final List<ToDo> todoItems = DataSource.getTodoItems();
    private final LayoutInflater layoutInflater;
    private final TodoItemClickListener todoItemClickListener;
    private boolean showCompleted = true;

    public ToDoMainListAdapter(Context context, TodoItemClickListener todoItemClickListener) {

        super();
        this.layoutInflater = LayoutInflater.from(context);
        this.todoItemClickListener = todoItemClickListener;
    }


    public void showCompleted() {
        this.showCompleted = true;
    }
    public void hideCompleted() {
        this.showCompleted = false;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View view = layoutInflater.inflate(R.layout.child_todo_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        ToDo item = todoItems.get(position);
        holder.nameOfTodoItem.setText(item.getToDoText());

        if(item.isToDoCompleted()) {
            holder.stateOfTodoItem.setChecked(true);
        }
        else {
            holder.stateOfTodoItem.setChecked(false);
        }

        if(item.getToDoDate() != null) {
            Calendar calendar = item.getToDoDate();
            holder.dueDateOfTodoItem.setText(calendar.get(Calendar.MONTH) + "/ " + calendar.get(Calendar.DAY_OF_MONTH)
                    + "/ " + calendar.get(Calendar.YEAR));
            holder.dueDateOfTodoItem.setVisibility(View.VISIBLE);
        }
        else {
            holder.dueDateOfTodoItem.setVisibility(View.GONE);
        }

        Priority priority = item.getToDoPriority();

        if (priority == Priority.DEFAULT) {
            holder.priorityOfTodoItem.setVisibility(View.GONE);

        } else if (priority == Priority.LOW) {
            holder.priorityOfTodoItem.setVisibility(View.VISIBLE);
            holder.priorityOfTodoItem.setImageResource(R.drawable.ic_filter_1_black_24dp);

        } else if (priority == Priority.MEDIUM) {
            holder.priorityOfTodoItem.setVisibility(View.VISIBLE);
            holder.priorityOfTodoItem.setImageResource(R.drawable.ic_filter_2_black_24dp);

        } else if (priority == Priority.HIGH) {
            holder.priorityOfTodoItem.setVisibility(View.VISIBLE);
            holder.priorityOfTodoItem.setImageResource(R.drawable.ic_filter_3_black_24dp);

        }

        if(!showCompleted) {
            if(item.isToDoCompleted()) {

                final LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.height = 0;
                holder.itemView.setLayoutParams(params);
//                holder.itemView.setVisibility(View.GONE);
            }
        }
        else {

        }
    }

    @Override
    public int getItemCount() {
        return this.todoItems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        private TextView nameOfTodoItem;
        private TextView dueDateOfTodoItem;
        private ImageView infoOfTodoItem;
        private ImageView priorityOfTodoItem;
        private CheckBox stateOfTodoItem;

        public CustomViewHolder(final View itemView) {
            super(itemView);

            this.nameOfTodoItem = (TextView) itemView.findViewById(R.id.todo_item);
            this.dueDateOfTodoItem = (TextView) itemView.findViewById(R.id.todo_item_due_date);
            this.infoOfTodoItem = (ImageView) itemView.findViewById(R.id.info_id);
            this.priorityOfTodoItem = (ImageView) itemView.findViewById(R.id.todo_priority);
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
                    }
                    else {
                        nameOfTodoItem.setPaintFlags(0);
                    }

                    todoItemClickListener.checked(getAdapterPosition(), isChecked);
                }
            });
            this.infoOfTodoItem.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {
            final int position = getAdapterPosition();
            Log.d(Constants.TODO_ADAPTER, "clicked on info of:" + position);
            todoItemClickListener.clicked(position);
        }
    }
}
