package com.example.recipeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {


    LinkedList<Recipe> dataRecipe = new LinkedList<>();
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        dataRecipe.add(new Recipe("test", "test", "test", R.drawable.test));
        dataRecipe.add(new Recipe("test1", "test1", "test1", R.drawable.test1));

        recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new RecyclerAdapter(this, dataRecipe);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
}