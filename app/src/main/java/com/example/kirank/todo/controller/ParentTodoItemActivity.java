package com.example.kirank.todo.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.kirank.todo.R;
import com.example.kirank.todo.adapter.AllToDoParentListAdapter;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.listener.ParentTodoItemClickListener;

public class ParentTodoItemActivity extends AppCompatActivity {

    private RecyclerView parentToDoItemRecyclerView;
    private AllToDoParentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_list);

        initViews();
        createAdapter();
        prepareRecyclerView();

    }

    private void initViews() {

        this.parentToDoItemRecyclerView = (RecyclerView) findViewById(R.id.parent_todo_item_recycler_view);
    }

    private void createAdapter() {

        this.adapter = new AllToDoParentListAdapter(this, new ParentTodoItemClickListener() {
            @Override
            public void clicked(final int position) {
                Intent openChildToDoItems = new Intent(ParentTodoItemActivity.this, ChildToDoItemsActivity.class);
                openChildToDoItems.putExtra(Constants.SELECTED_PARENT_ITEM, position);
                startActivity(openChildToDoItems);

            }
        });
    }

    private void prepareRecyclerView() {

        this.parentToDoItemRecyclerView.setAdapter(this.adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.parentToDoItemRecyclerView.setLayoutManager(layoutManager);

    }
}
