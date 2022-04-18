package com.example.toppingstoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<CheckBox> arrayCheckBox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox cbChoco = findViewById(R.id.cbChoco);
        arrayCheckBox.add(cbChoco);
        CheckBox cbSprinkles = findViewById(R.id.cbSprinkles);
        arrayCheckBox.add(cbSprinkles);
        CheckBox cbCrushedNuts = findViewById(R.id.cbCrushedNuts);
        arrayCheckBox.add(cbCrushedNuts);
        CheckBox cbCherries = findViewById(R.id.cbCherries);
        arrayCheckBox.add(cbCherries);
        CheckBox cbOreo = findViewById(R.id.cbOreo);
        arrayCheckBox.add(cbOreo);


    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void buttonClicked(View view) {
        String toastMessage = "Toppings: ";

        for(CheckBox checkBox : arrayCheckBox){
            if(checkBox.isChecked()){
                toastMessage = toastMessage.concat(checkBox.getText().toString()+", ");
            }
        }

        displayToast(toastMessage);
    }
}