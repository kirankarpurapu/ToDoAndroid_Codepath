package com.example.kirank.todo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirank.todo.R;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.data.ParentTodoDataSource;
import com.example.kirank.todo.listener.ParentTodoItemClickListener;

import java.util.List;

/**
 * Created by kirank on 8/26/17.
 */

public class AllToDoParentListAdapter extends RecyclerView.Adapter<AllToDoParentListAdapter.CustomViewHolder> {

    private final List<String> parentItems = ParentTodoDataSource.getTodoParentItems();
    private final LayoutInflater layoutInflater;
    private final ParentTodoItemClickListener parentTodoItemClickListener;

    public AllToDoParentListAdapter(Context context, ParentTodoItemClickListener parentTodoItemClickListener) {

        super();
        this.layoutInflater = LayoutInflater.from(context);
        this.parentTodoItemClickListener = parentTodoItemClickListener;
    }


    @Override
    public AllToDoParentListAdapter.CustomViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View view = layoutInflater.inflate(R.layout.parent_todo_item, parent, false);
        return new AllToDoParentListAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AllToDoParentListAdapter.CustomViewHolder holder, final int position) {

        String item = parentItems.get(position);
        holder.parentToDoName.setText(item);
    }

    @Override
    public int getItemCount() {
        return this.parentItems.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView parentToDoName;

        public CustomViewHolder(final View itemView) {
            super(itemView);

            this.parentToDoName = (TextView) itemView.findViewById(R.id.parent_todo_item_tv);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            final int position = getAdapterPosition();
            Log.d(Constants.ALL_TODO_PARENT_LIST_ADAPTER, "Clicked on " + parentToDoName.getText().toString() + "at position " + position);
            parentTodoItemClickListener.clicked(position);

        }
    }
}
