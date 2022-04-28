package com.example.languagecard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(Constants.sharedPrefFile, MODE_PRIVATE);
        int type = sharedPreferences.getInt("TYPE", 2);
        radioGroup = findViewById(R.id.radio_group);

        switch (type) {
            case 0:
                radioGroup.check(R.id.rbtnMongolian);
                break;
            case 1:
                radioGroup.check(R.id.rbtnEnglish);
                break;

            case 2:
                radioGroup.check(R.id.rbtnBoth);
                break;
            default:
                break;

        }

    }

    public void save(View view) {
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();
        int buttonId = radioGroup.getCheckedRadioButtonId();


        int type = 2;
        switch (buttonId) {
            case R.id.rbtnMongolian:
                type = Constants.TYPE_ENGLISH;
                break;
            case R.id.rbtnEnglish:
                type = Constants.TYPE_MONGOLIA;
                break;
            default:
                break;

        }

        preferenceEditor.putInt("TYPE", type);
        preferenceEditor.apply();

        int test = sharedPreferences.getInt("TYPE", 2);
        finish();
    }

    public void back(View view) {
        finish();
    }
}