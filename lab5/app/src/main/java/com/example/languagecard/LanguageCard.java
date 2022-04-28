package com.example.languagecard;

import android.app.Application;

public class LanguageCard extends Application {

        private DataBaseHandler dataBaseHandler;

        @Override
        public void onCreate() {
            super.onCreate();
            dataBaseHandler = new DataBaseHandler(this);

        }

        public DataBaseHandler getDataBaseHandler() {
            return dataBaseHandler;

        }
}
