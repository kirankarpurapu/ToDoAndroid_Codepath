package com.example.kirank.todo.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kirank.todo.R;
import com.example.kirank.todo.adapter.ToDoMainAdapter;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.data.DataSource;
import com.example.kirank.todo.database.ToDo;
import com.example.kirank.todo.listener.TodoItemClickListener;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private ToDoMainAdapter toDoMainAdapter;
    private EditText newTodo;
    private ImageView newTodoInfo;
    private ImageView newTodoButton;
    private Paint paint = new Paint();
    private RecyclerView todoListRecyclerView;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        bindViews();
        addListeners();
        prepareAdapter();

//        toDoMainAdapter.hideCompleted();
        todoListRecyclerView.setAdapter(toDoMainAdapter);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());

        itemTouchHelper.attachToRecyclerView(todoListRecyclerView);
    }

    private void bindViews() {

        this.todoListRecyclerView = (RecyclerView) findViewById(R.id.mainTodoRecyclerView);
        this.todoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_layout);
        this.newTodo = (EditText) findViewById(R.id.new_todo);
        this.newTodoInfo = (ImageView) findViewById(R.id.new_item_info_id);
        this.newTodoButton = (ImageView) findViewById(R.id.new_todo_button);
    }

    private void addListeners() {

        this.newTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                newTodo.requestFocus();
                imm.showSoftInput(newTodo, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        this.newTodoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(Constants.MAIN_ACTIVITY, "clicked on info at new todo place");


                ToDo toDo = new ToDo();
                toDo.setToDoText(newTodo.getText().toString());
                toDo.save();
                DataSource.updateDataSource();


                newTodo.getText().clear();
                newTodo.clearFocus();
                newTodo.setCursorVisible(false);
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(newTodo.getWindowToken(), 0);

                Intent intent = new Intent(MainActivity.this, ToDoItemDetailsActivity.class);
                intent.putExtra(Constants.SELECTED_ITEM_INDEX, DataSource.getTodoItems().size() - 1);
                startActivity(intent);
            }
        });

        this.newTodo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (newTodo.getRight() - newTodo.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        String newTodoString = newTodo.getText().toString();
                        if (newTodoString.length() != 0) {

                            ToDo toDo = new ToDo();
                            toDo.setToDoText(newTodoString);
                            toDo.setToDoCompleted(false);
                            toDo.save();

                            DataSource.updateDataSource();
                            toDoMainAdapter.notifyDataSetChanged();
                            todoListRecyclerView.scrollToPosition(DataSource.getTodoItems().size() - 1);

                            newTodo.getText().clear();
                            newTodo.clearFocus();
                            newTodo.setCursorVisible(false);

                            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(newTodo.getWindowToken(), 0);
                        }

                        return true;
                    }
                }
                return false;
            }
        });
        this.newTodo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

                String newTodoItem = s.toString();
                if (newTodoItem.length() > 0) {
                    newTodoInfo.setVisibility(View.VISIBLE);
                } else if (newTodoItem.length() == 0) {
                    newTodoInfo.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });
    }

    private void prepareAdapter() {

        this.toDoMainAdapter = new ToDoMainAdapter(this, new TodoItemClickListener() {
            @Override
            public void clicked(final int position) {

                Log.d(Constants.MAIN_ACTIVITY, "clicked on info of " + position);
                Intent intent = new Intent(MainActivity.this, ToDoItemDetailsActivity.class);
                intent.putExtra(Constants.SELECTED_ITEM_INDEX, position);
                startActivity(intent);
            }

            @Override
            public void checked(final int position, boolean isChecked) {
                Log.d(Constants.MAIN_ACTIVITY, "checked on item at " + position);

                ToDo item = DataSource.get(position);
                if(isChecked)
                    item.setToDoCompleted(true);
                else
                    item.setToDoCompleted(false);

                item.save();
                DataSource.updateDataSource();

                //commenting this line because I dont want the item to disappear immediately
                // I want it to disappear next time the app launches
//                toDoMainAdapter.notifyDataSetChanged();

            }
        });
    }

    private ItemTouchHelper.Callback createHelperCallback() {

        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                Snackbar.make(coordinatorLayout, "moving todo items coming soon !!", Snackbar.LENGTH_SHORT).show();
//                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                deleteItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;

                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    paint.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, paint);

                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(icon, null, icon_dest, paint);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
    }

    private void deleteItem(int position) {

        Log.d(Constants.MAIN_ACTIVITY, "delete Item at " + position);
        ToDo item = DataSource.get(position);
        item.delete();
        DataSource.updateDataSource();
        toDoMainAdapter.notifyDataSetChanged();
    }

//    private void moveItem(int fromPosition, int toPosition) {
//        Log.d(Constants.MAIN_ACTIVITY, "move Item from " + fromPosition + " to " + toPosition);
//
//        TodoItem item = dataSource.get(fromPosition);
//        dataSource.remove(fromPosition);
//        dataSource.add(toPosition, item);
//        this.toDoMainAdapter.notifyItemMoved(fromPosition, toPosition);
//    }


    @Override
    public void onResume() {

        super.onResume();
        DataSource.updateDataSource();
        toDoMainAdapter.notifyDataSetChanged();
        todoListRecyclerView.scrollToPosition(DataSource.getTodoItems().size() - 1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Snackbar.make(coordinatorLayout, "Settings coming soon !! ", Snackbar.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_show_completed) {

            Snackbar.make(coordinatorLayout, "Completed Items coming soon !! ", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
