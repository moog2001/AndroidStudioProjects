package com.example.helloConstraint;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COUNT = "com.example.android.helloConstraint.extra.count";

    private int mCount = 0;
    private TextView mShowCount;
    private TextView mCountButton;
    private TextView mZeroButton;

    private Random rnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity", "Hello World!");


        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        mCountButton = (TextView) findViewById(R.id.button_count);
        mZeroButton = (TextView) findViewById(R.id.Zero);

    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message,Toast.LENGTH_SHORT);
        toast.show();


        Intent intent = new Intent(this, MainActivity2.class);

        String count = mShowCount.getText().toString();
        intent.putExtra(EXTRA_COUNT, count);

        startActivity(intent);

    }



    public void countUp(View view) {

        mCount++;
        updateViews();

    }

    public void zero(View view) {
        mCount = 0;
        updateViews();
    }

    public void updateViews(){
        if(mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));

        if(mCount == 0)
            mZeroButton.setTextColor(Color.GRAY);
        else
            mZeroButton.setTextColor(Color.MAGENTA);

        int color = Color.argb(255,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
        mCountButton.setTextColor(color);



    }
}