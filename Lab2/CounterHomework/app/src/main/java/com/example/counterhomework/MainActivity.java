package com.example.counterhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private TextView tvShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShowCount = findViewById(R.id.tvShowCount);


        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("countState");
            tvShowCount.setText(String.valueOf(count));
        }
    }

    public void buttonCountOnClick(View view) {
        count++;
        tvShowCount.setText(String.valueOf(count));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("countState", count);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "destoyed");

    }
}