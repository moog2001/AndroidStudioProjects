package com.example.todo2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Locale;


public class PopUpDialog extends DialogFragment {



    final String L0G_TAG = "PopUpDialog";
    DataBaseHandler DB;
    private ArrayList<ToDoItem> List;
    MainActivity Parent;

    public PopUpDialog(ArrayList<ToDoItem> list, DataBaseHandler dataBaseHandler, MainActivity parent){
        List = list;
        DB = dataBaseHandler;
        Parent = parent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the Builder class for convenient dialog construction

        EditText editText = new EditText(getContext());
        DatePicker datePicker = new DatePicker(getContext());
        LinearLayout container = new LinearLayout(getContext());
        container.addView(editText);
        container.addView(datePicker);
        container.setOrientation(LinearLayout.VERTICAL);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(L0G_TAG, "onclick!");
                        if(editText.getText() == null){
                            return;
                        }
                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth()+1;
                        int year = datePicker.getYear();

                        LocalDate date = LocalDate.of(year, month, day);
                        ToDoItem item = new ToDoItem(editText.getText().toString(), 0,date, DB );
                        List.add(item);
                        Parent.resetView();
//                        ToDoItem item = new ToDoItem
////                        List.add(editText.getText().toString());

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dismiss();
                    }
                })
                .setView(container);

        // Create the AlertDialog object and return it
        return builder.create();
    }



}