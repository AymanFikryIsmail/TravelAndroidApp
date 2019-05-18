package com.travel.iti.travelapp.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.travel.iti.travelapp.R;
import com.travel.iti.travelapp.repository.local.PrefManager;

public class SplashActivity extends AppCompatActivity {

    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefManager =new PrefManager(this);

        new ProgressTask().execute();

    }

    class ProgressTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {

            for (int i = 0; i < 300; i++) {
                try {
                    Thread.sleep(10);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // code executed after task finish hide progress and change text
            openActivity();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    public void openActivity() {

        if (prefManager.isFirstTimeLaunch() ){
            Intent i = new Intent(this,WelcomeActivity.class);
            startActivity(i);
            finish();

        }
        else {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
            finish();

        }
    }

}
