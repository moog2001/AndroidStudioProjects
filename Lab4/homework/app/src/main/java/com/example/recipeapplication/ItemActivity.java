package com.example.recipeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    private final String extra = "RECIPE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();

        Recipe item = (Recipe) intent.getSerializableExtra(extra);
        ScrollView root = findViewById(R.id.root_activity_item);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 12;
        LinearLayout innerRoot = new LinearLayout(this);
        innerRoot.setOrientation(LinearLayout.VERTICAL);
        innerRoot.setLayoutParams(params);


        TextView tvName = new TextView(this);
        tvName.setText(item.getName());
        tvName.setTextSize(30);
        tvName.setTypeface(null, Typeface.BOLD);        // for Bold only
        tvName.setLayoutParams(params);
        innerRoot.addView(tvName);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(item.getImageId());
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);
        imageView.setAdjustViewBounds(true);
        innerRoot.addView(imageView);

        TextView tvContent = new TextView(this);
        tvContent.setLayoutParams(params);
        tvContent.setText(item.getContent());
        tvContent.setTextSize(20);
        innerRoot.addView(tvContent);

        root.removeAllViews();
        root.addView(innerRoot);


    }
}