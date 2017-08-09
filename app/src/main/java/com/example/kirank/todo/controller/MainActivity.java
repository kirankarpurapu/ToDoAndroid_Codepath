package com.example.kirank.todo.controller;

import android.content.Context;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirank.todo.Data.DataSource;
import com.example.kirank.todo.R;
import com.example.kirank.todo.adapter.ToDoMainAdapter;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.listener.TodoItemClickListener;
import com.example.kirank.todo.model.TodoItem;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private ToDoMainAdapter toDoMainAdapter;
    private EditText newTodo;
    private ImageView newTodoInfo;
    private ImageView newTodoButton;
    private Paint paint = new Paint();
    private final DataSource dataSource = new DataSource();

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView todoListRecyclerView = (RecyclerView) findViewById(R.id.mainTodoRecyclerView);
        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_layout);
        this.newTodo = (EditText) findViewById(R.id.new_todo);
        this.newTodoInfo = (ImageView) findViewById(R.id.new_item_info_id);
        this.newTodoButton = (ImageView) findViewById(R.id.new_todo_button);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

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


        this.newTodo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (newTodo.getRight() - newTodo.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        String newTodoString = newTodo.getText().toString();
                        if(newTodoString == null || newTodoString.length() == 0) {

                        }
                        else {
                            TodoItem newTodoItem = new TodoItem(newTodoString);
                            dataSource.addItem(newTodoItem);
                            toDoMainAdapter.notifyDataSetChanged();
                            todoListRecyclerView.scrollToPosition(dataSource.getSize() - 1);
                            newTodo.getText().clear();
                            newTodo.clearFocus();
                            newTodo.setCursorVisible(false);
                            imm.hideSoftInputFromWindow(newTodo.getWindowToken(), 0);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        todoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.toDoMainAdapter = new ToDoMainAdapter(dataSource.getTodoItems(), this, new TodoItemClickListener() {
            @Override
            public void clicked(final int position) {
                Snackbar.make(coordinatorLayout, "Clicked on item at position " + position, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void checked(final int position, final int time) {
                Snackbar.make(coordinatorLayout, "checked on item at position " + position, Snackbar.LENGTH_SHORT).show();
            }
        });

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(todoListRecyclerView);
        todoListRecyclerView.setAdapter(toDoMainAdapter);


        this.newTodoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Snackbar.make(coordinatorLayout, "typed " + newTodo.getText().toString(), Snackbar.LENGTH_SHORT).show();
            }
        });

        this.newTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                newTodo.requestFocus();
                imm.showSoftInput(newTodo, InputMethodManager.SHOW_IMPLICIT);
            }
        });

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
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
