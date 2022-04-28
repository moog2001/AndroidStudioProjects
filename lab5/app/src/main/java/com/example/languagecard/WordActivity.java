package com.example.languagecard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class WordActivity extends AppCompatActivity {

    DataBaseHandler dataBaseHandler;
    MainActivity mainActivity;
    EditText etEnglish;
    EditText etMongolian;
    ArrayList<Word> wordList;
    Toast toastDuplicate;
    Toast toastNotFound;

    SharedPreferences sharedPreferences;
    Button btnInsert;
    Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        sharedPreferences = getSharedPreferences(Constants.sharedPrefFile, MODE_PRIVATE);
        mainActivity = (MainActivity) getParent();
        etEnglish = findViewById(R.id.etEnglish);
        etMongolian = findViewById(R.id.etMongolian);
        dataBaseHandler = ((LanguageCard) getApplication()).getDataBaseHandler();
        wordList = dataBaseHandler.getWordItems();
        toastDuplicate = Toast.makeText(this, "Duplicate!", Toast.LENGTH_SHORT);
        toastNotFound = Toast.makeText(this, "Not Found!", Toast.LENGTH_SHORT);

        btnCancel = findViewById(R.id.btnCancel);
        btnInsert = findViewById(R.id.btnInsert);

        int type = sharedPreferences.getInt("EXEC", 0);
        if (type == Constants.EXEC_ADD) {
            btnInsert.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    insert();
                }
            });
        } else {
            btnInsert.setText("ШИНЭЧЛЭХ");
            btnInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update();
                }
            });
        }
    }

    public void insert() {
        String mongolian = etMongolian.getText().toString();
        String english = etEnglish.getText().toString();
        if (mongolian.isEmpty() || english.isEmpty()) {
            return;
        }
        wordList = dataBaseHandler.getWordItems();
        for (Word w : wordList) {
            if (w.getEnglish().equals(english)) {
                toastDuplicate.show();
                return;
            }
        }
        Word wordItem = new Word(mongolian, english, dataBaseHandler);
    }

    public void update() {

        Word wordItem;

        for(Word w : wordList){
            if(w.getEnglish().equals(etEnglish.getText().toString())){
                wordItem = w;
                wordItem.setMongolia(etMongolian.getText().toString());
                dataBaseHandler.updateData(wordItem);
            }
        }
    }

    public void cancel(View view) {
        this.finish();
    }
}