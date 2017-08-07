package com.example.kirank.todo.controller;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.kirank.todo.Data.DataSource;
import com.example.kirank.todo.R;
import com.example.kirank.todo.adapter.ToDoMainAdapter;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.listener.TodoItemClickListener;
import com.example.kirank.todo.model.TodoItem;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private ToDoMainAdapter toDoMainAdapter;
    private final DataSource dataSource = new DataSource();

    public MainActivity() { }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton addTodoItem = (FloatingActionButton) findViewById(R.id.fab);
        final RecyclerView todoListRecyclerView = (RecyclerView) findViewById(R.id.mainTodoRecyclerView);
        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_layout);

        todoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.toDoMainAdapter = new ToDoMainAdapter(dataSource.getTodoItems(), this, new TodoItemClickListener() {
            @Override
            public void clicked(final int position) {
                Snackbar.make(coordinatorLayout, "Clicked on item at position " + position, Snackbar.LENGTH_SHORT).show();
            }
        });

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(todoListRecyclerView);
        todoListRecyclerView.setAdapter(toDoMainAdapter);


        addTodoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding a new todo item", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder.getAdapterPosition());
            }
        };
    }

    private void deleteItem(int position) {
        Log.d(Constants.MAIN_ACTIVITY, "delete Item at " + position);
        dataSource.remove(position);
        this.toDoMainAdapter.notifyItemRemoved(position);

    }

    private void moveItem(int fromPosition, int toPosition) {
        Log.d(Constants.MAIN_ACTIVITY, "move Item from " + fromPosition + " to " + toPosition);

        TodoItem item = dataSource.get(fromPosition);
        dataSource.remove(fromPosition);
        dataSource.add(toPosition, item);
        this.toDoMainAdapter.notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
