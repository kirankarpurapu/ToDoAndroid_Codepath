package com.example.kirank.todo.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirank.todo.Data.DataSource;
import com.example.kirank.todo.R;
import com.example.kirank.todo.constants.Constants;
import com.example.kirank.todo.model.Priority;
import com.example.kirank.todo.model.TodoItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kirank on 8/11/17.
 */

public class ToDoItemDetailsActivity extends AppCompatActivity {

    private EditText toDoEditText;
    private Switch remindOnADaySwitch;
    private Switch calendarToggleSwitch;
    private TextView toDoSelectedDayTextView;
    private DatePicker toDoDatePicker;
    private Spinner prioritySpinner;
    private EditText toDoNotes;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_item_details);

        int index = getIntent().getIntExtra(Constants.SELECTED_ITEM_INDEX, -1);
        if (index == -1) {
            Toast.makeText(getApplicationContext(), "Something critically failed, sorry. Please try again", Toast.LENGTH_SHORT).show();

        } else {
            Log.d(Constants.TODO_DETAILS_ACTIVITY, "Loading Todo item at location " + index);

            bindViews();
            initScreen(index);
            applyListeners();
        }
    }

    private void bindViews() {

        this.toDoEditText = (EditText) findViewById(R.id.todo_editText);
        this.remindOnADaySwitch = (Switch) findViewById(R.id.day_switch);
        this.toDoDatePicker = (DatePicker) findViewById(R.id.datePicker);
        this.toDoSelectedDayTextView = (TextView) findViewById(R.id.todo_selected_day);
        this.calendarToggleSwitch = (Switch) findViewById(R.id.calendar_switch);
        this.prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);
        this.toDoNotes = (EditText) findViewById(R.id.todo_notes);

        populatePrioritySpinner();
        Calendar today = Calendar.getInstance();

        this.toDoDatePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String updatedDateSting = monthOfYear + "/ " + dayOfMonth + "/ " + year;
                toDoSelectedDayTextView.setText(updatedDateSting);
            }
        });
    }

    private void initScreen(int itemLocation) {
        final DataSource dataSource = new DataSource();
        final TodoItem item = dataSource.get(itemLocation);

        this.toDoEditText.setText(item.getTodoTask());
        final Calendar calendar = item.getDueDate();
        if (calendar != null) {
            this.remindOnADaySwitch.setChecked(true);
            this.calendarToggleSwitch.setChecked(false);
            final Date date = calendar.getTime();
            this.toDoEditText.setText(date.getMonth() + "/ " + date.getDay() + "/ " + date.getYear());
        }

        final Priority itemPriority = item.getPriority();
        if (itemPriority != Priority.DEFAULT) {
            if (itemPriority == Priority.LOW)
                this.prioritySpinner.setSelection(1);
            else if (itemPriority == Priority.MEDIUM)
                this.prioritySpinner.setSelection(2);
            else if (itemPriority == Priority.HIGH)
                this.prioritySpinner.setSelection(3);
        }

        final String notes = item.getNotes();
        if (notes != null) {
            this.toDoNotes.setText(notes);
        }
    }

    private void populatePrioritySpinner() {

        List<String> priorities = new ArrayList<>();
        priorities.add(Priority.DEFAULT.getPriority());
        priorities.add(Priority.LOW.getPriority());
        priorities.add(Priority.MEDIUM.getPriority());
        priorities.add(Priority.HIGH.getPriority());

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, priorities);
        this.prioritySpinner.setAdapter(spinnerAdapter);

    }

    private void applyListeners() {

        this.remindOnADaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    calendarToggleSwitch.setVisibility(View.VISIBLE);
                    toDoSelectedDayTextView.setVisibility(View.VISIBLE);
                } else {
                    calendarToggleSwitch.setVisibility(View.GONE);
                    toDoSelectedDayTextView.setVisibility(View.GONE);
                }
            }
        });

        this.calendarToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    toDoDatePicker.setVisibility(View.VISIBLE);
                } else {
                    toDoDatePicker.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            Toast.makeText(getApplicationContext(), "Saving.....", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}