package com.example.kirank.todo.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.example.kirank.todo.R;
import com.example.kirank.todo.adapter.AllToDoParentListAdapter;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.listener.ParentTodoItemClickListener;
import com.example.kirank.todo.view.NewParentToDoAlertFragment;

public class ParentTodoItemActivity extends AppCompatActivity {

    private RecyclerView parentToDoItemRecyclerView;
    private AllToDoParentListAdapter adapter;
    private FloatingActionButton newParentListButton;
    private CoordinatorLayout parentListCoordinatorLayout;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private NewParentToDoAlertFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_list);

        initViews();
        setViewsListeners();
        createAdapter();
        prepareRecyclerView();

    }

    private void initViews() {

        this.parentToDoItemRecyclerView = (RecyclerView) findViewById(R.id.parent_todo_item_recycler_view);
        this.newParentListButton = (FloatingActionButton) findViewById(R.id.new_parent_fab);
        this.parentListCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent_list_coordinator_layout);
    }

    private void setViewsListeners() {

        this.newParentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                Snackbar.make(parentListCoordinatorLayout, "Adding a new Parent", Snackbar.LENGTH_SHORT).show();

                fragment = new NewParentToDoAlertFragment();
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                fragment.show(fragmentManager, "New Parent Todo List");
                fragmentManager.executePendingTransactions();
                fragment.getDialog().getWindow().setLayout((3 * width) / 4, (1 * height) / 4);
            }
        });
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

    public void clickedOnCancelOnDialog() {
        fragment.dismiss();

    }

    public void clickedOnSaveOnDialog(String newListName) {

        Toast.makeText(getApplicationContext(), "new List " + newListName, Toast.LENGTH_SHORT).show();
        fragment.dismiss();

    }
}

