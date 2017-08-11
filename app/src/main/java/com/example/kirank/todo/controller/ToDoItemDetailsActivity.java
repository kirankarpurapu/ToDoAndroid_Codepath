package com.example.kirank.todo.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.example.kirank.todo.R;

import java.util.Calendar;

/**
 * Created by kirank on 8/11/17.
 */

public class ToDoItemDetailsActivity extends AppCompatActivity{

    private Switch remindOnADaySwitch;
    private TextView toDoSelectedDay;
    private DatePicker toDoDay;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_item_details);

        bindViews();
        applyListeners();


    }

    private void bindViews() {

        this.remindOnADaySwitch = (Switch) findViewById(R.id.day_switch);
        this.toDoDay = (DatePicker) findViewById(R.id.datePicker);
        this.toDoSelectedDay = (TextView) findViewById(R.id.todo_selected_day);
        Calendar today = Calendar.getInstance();

        this.toDoDay.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(final DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String updatedDateSting = monthOfYear + "/ " + dayOfMonth + "/ " + year;
                toDoSelectedDay.setText(updatedDateSting);
            }
        });
    }

    private void applyListeners() {

        this.remindOnADaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if(isChecked) {
                    toDoDay.setVisibility(View.VISIBLE);
                    toDoSelectedDay.setVisibility(View.VISIBLE);
                }
                else {
                    toDoDay.setVisibility(View.GONE);
                    toDoSelectedDay.setText("Select a day");
                    toDoSelectedDay.setVisibility(View.GONE);
                }
            }
        });

    }

    //TODO: https://stackoverflow.com/questions/17919933/action-bar-does-not-show

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
