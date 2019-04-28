package com.example.asynctask_test;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import javax.xml.transform.Result;

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

        HashMap<String, Integer> hashMap = new HashMap<>();

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            hashMap.put("key", 123);
            return null;
        }

        @Override
        protected void onPreExecute() {
            TextView textView = findViewById(R.id.info);
            textView.setText("DONE");
            super.onPreExecute();
        }
    }


    class Job2Task extends AsyncTask<Integer, Void, Void> {
//Background 執行
        @Override
        protected Void doInBackground(Integer... voids) {
            try {
                Thread.sleep(voids[0] * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
//mainthread 上執行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView textView = findViewById(R.id.info);
            textView.setText("Begin");
        }
        //mainthread 上執行
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView textView = findViewById(R.id.info);
            textView.setText("End");
            textView.setTextColor(Color.BLUE);
        }
    }
    class Job3Task extends AsyncTask<Integer, Integer, String> {
        TextView textView = findViewById(R.id.info);

        @Override
        protected String doInBackground(Integer... voids) {
            for (int i = 0; i < voids[0]; i++) {
                publishProgress(i); //call onProgressUpdate 產生倒數的效果
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return (voids[0] + " already passed");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            textView.setText("DONE");
            textView.setText(aVoid);
        }
    }
}
