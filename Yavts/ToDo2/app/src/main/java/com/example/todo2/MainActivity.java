package com.example.todo2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {

    private ArrayList<ToDoItem> List;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    public DataBaseHandler dataBaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
/*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/


        dataBaseHandler = new DataBaseHandler(this);
        List = dataBaseHandler.getToDoItemList();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpDialog popUpDialog = new PopUpDialog(List, dataBaseHandler, MainActivity.this);
                popUpDialog.show(getSupportFragmentManager(), "Dialog");

            }
        });

        adapter = new RecyclerAdapter(List, dataBaseHandler, MainActivity.this);
        recyclerView = findViewById(R.id.rvToDo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }


    public void resetView() {
        int lastItem = adapter.getItemCount();
        adapter.notifyItemChanged(lastItem);
    }

    public void resetViewOnDelete(int position) {
        dataBaseHandler.deleteData(List.get(position));
        adapter.notifyItemRemoved(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_not_done) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putStringArrayList("list", mWordList);
    }

    public void DoneOnClick(MenuItem item) {
        List = dataBaseHandler.getToDoItemList();

        for (ToDoItem name : new ArrayList<ToDoItem>(List)) {
            // Do something
            if (name.getStatus() != 1) {
                List.remove(name);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void NotDoneOnClick(MenuItem item) {
        List = dataBaseHandler.getToDoItemList();

        for (ToDoItem name : new ArrayList<ToDoItem>(List)) {

            // Do something
            if (name.getStatus() != 0) {
                List.remove(name);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void allOnClick(MenuItem item) {
        List = dataBaseHandler.getToDoItemList();
        adapter.notifyDataSetChanged();
    }




   /* @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}