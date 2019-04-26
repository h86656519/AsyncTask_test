package com.example.asynctask_test;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.go1:
                new Job1Task().execute();
                break;

            case R.id.go2:
                new Job2Task().execute(3); //將秒數3傳入
                break;

            case R.id.go3:
                new Job3Task().execute(6);
                break;

        }
    }

    class Job1Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            TextView textView = findViewById(R.id.info);
            textView.setText("DONE");
            super.onPreExecute();
        }
    }


    class Job2Task extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... voids) {
            try {
                Thread.sleep(voids[0] * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            TextView textView = findViewById(R.id.info);
            textView.setText("DONE");
            super.onPreExecute();
        }
    }
    class Job3Task extends AsyncTask<Integer, Void, Void> {
        TextView textView = findViewById(R.id.info);

        @Override
        protected Void doInBackground(Integer... voids) {
            for (int i = 0; i < voids[0]; i++) {
                publishProgress();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

            textView.setText("DONE");
            super.onPreExecute();
        }
    }
}
