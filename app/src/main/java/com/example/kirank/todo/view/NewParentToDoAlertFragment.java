package com.example.kirank.todo.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kirank.todo.R;
import com.example.kirank.todo.controller.ParentTodoItemActivity;
import com.example.kirank.todo.listener.DialogListenerInterface;

/**
 * Created by kirank on 8/27/17.
 */

public class NewParentToDoAlertFragment extends DialogFragment {

    private Button save, cancel;
    private EditText name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_parent_list_dialog_fragment, container,
                false);
        initViews(rootView);
        setListeners();
        getDialog().setTitle("New ToDo List");
        return rootView;
    }

    private void initViews(final View rootView) {
        this.save = (Button) rootView.findViewById(R.id.new_list_add);
        this.cancel = (Button) rootView.findViewById(R.id.new_list_cancel);
        this.name = (EditText) rootView.findViewById(R.id.new_parent_list_et);
    }

    private void setListeners() {

        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((ParentTodoItemActivity) getActivity()).clickedOnCancelOnDialog();

            }
        });

        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ((ParentTodoItemActivity) getActivity()).clickedOnSaveOnDialog(name.getText().toString());

            }
        });
    }
}
