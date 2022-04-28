package com.example.languagecard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int wordPosition = 0;


    private DataBaseHandler dataBaseHandler;
    private ArrayList<Word> wordList;
    private SharedPreferences sharedPreferences;
    private int type;

    private TextView tvEnglish;
    private TextView tvMongolian;

    private Button btnUpdate;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnNext;
    private Button btnBefore;

    Drawable buttonColorDrawable;
    int textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            type = sharedPreferences.getInt("TYPE", 0);
        } else {
            type = 2;
        }

        setSupportActionBar(findViewById(R.id.toolbar));


        sharedPreferences = getSharedPreferences(Constants.sharedPrefFile, MODE_PRIVATE);
        dataBaseHandler = ((LanguageCard) getApplication()).getDataBaseHandler();
        wordList = dataBaseHandler.getWordItems();

        tvEnglish = findViewById(R.id.tvEnglish);
        tvMongolian = findViewById(R.id.tvMongolian);

        tvEnglish.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                turnTextVisible((TextView) view);
                return true;
            }
        });

        tvMongolian.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                turnTextVisible((TextView) view);
                return true;
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                add();
            }
        });
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                update();
            }
        });
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                delete();
            }
        });
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                next();
            }
        });
        btnBefore = findViewById(R.id.btnBefore);
        btnBefore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                before();
            }
        });


        buttonColorDrawable = btnAdd.getBackground();

        textColor = tvMongolian.getCurrentTextColor();
        updateWord();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri url = data.getData();

        try {
            BufferedReader reader;

            InputStream is = getContentResolver().openInputStream(url);

            reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line = reader.readLine();
            while (line != null) {
                String[] ugs = line.split(",");
                Word wordItem = new Word(ugs[0], ugs[1], dataBaseHandler);
                line = reader.readLine();
            }

            updateWord();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_setttings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.action_add_file) {
            Intent i;
            i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("*/*");
            int requestCodeUri = 100;
            startActivityForResult(i, requestCodeUri);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWord();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
//        preferenceEditor.putInt("TYPE", Constants.TYPE_ALL);
//        preferenceEditor.apply();
        // preference saving
    }

    private void add() {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putInt("EXEC", Constants.EXEC_ADD);
        preferenceEditor.apply();
        callWordActivity();
    }

    private void update() {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        preferenceEditor.putInt("EXEC", Constants.EXEC_UPDATE);
        preferenceEditor.apply();
        callWordActivity();
    }

    private void delete() {
        if (wordList.isEmpty()) {
            return;
        }
        Word wordItem = wordList.get(wordPosition);
        dataBaseHandler.deleteData(wordItem);

        if (wordPosition > 0) {
            wordPosition--;
        }
        updateWord();
    }

    private void callWordActivity() {
        Intent intent = new Intent(this, WordActivity.class);
        startActivity(intent);
    }

    private void next() {
        if (wordPosition >= wordList.size() - 1) {
            return;
        }

        wordPosition++;
        updateWord();
    }

    private void before() {
        if (wordPosition == 0) {
            return;
        }

        wordPosition--;
        updateWord();
    }

    private void updateWord() {

        wordList = dataBaseHandler.getWordItems();

        if (wordList.size() <= 0) {
            btnDelete.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
            btnBefore.setVisibility(View.INVISIBLE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnBefore.setVisibility(View.VISIBLE);
        }

        if (wordList.isEmpty()) {
            tvEnglish.setText("");
            tvMongolian.setText("");
            return;
        }
        Word wordItem = wordList.get(wordPosition);
        tvEnglish.setText(wordItem.getEnglish());
        tvMongolian.setText(wordItem.getMongolia());


        type = sharedPreferences.getInt("TYPE", 2);


        switch (type) {
            case 0:
                tvEnglish.setTextColor(Color.TRANSPARENT);
                tvMongolian.setTextColor(textColor);
                break;
            case 1:
                tvMongolian.setTextColor(Color.TRANSPARENT);
                tvEnglish.setTextColor(textColor);
                break;
            default:
                tvEnglish.setTextColor(textColor);
                tvMongolian.setTextColor(textColor);
                break;
        }
    }

    private void turnTextVisible(TextView textView) {
        textView.setTextColor(textColor);
    }

    public DataBaseHandler getDataBaseHandler() {
        return dataBaseHandler;
    }


}