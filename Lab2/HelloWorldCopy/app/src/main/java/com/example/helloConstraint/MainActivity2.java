package com.example.helloConstraint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        String count = intent.getStringExtra(MainActivity.EXTRA_COUNT);
        TextView textView = findViewById(R.id.textView2);
        textView.setText(count);
    }
}