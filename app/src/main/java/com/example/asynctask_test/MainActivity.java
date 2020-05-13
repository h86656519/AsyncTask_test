package com.example.asynctask_test;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity implements TimeListiner {
    private String TAG = this.getClass().getSimpleName();
    private Button start, stop;
    private ProgressBar pb;
    private TextView info_tv;
    private MyAsyncTask task1;
    private int second = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        pb = findViewById(R.id.pb);
        info_tv = findViewById(R.id.info);

    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.go1:
                new Job1Task().execute();
                break;

            case R.id.go2:
//                new Job2Task().executeOnExecutor(Executors.newCachedThreadPool(), 3);
//                new Job2Task().executeOnExecutor(Executors.newCachedThreadPool(), 3);

//                new Job2Task().execute(3);
//                new Job2Task().execute(3);

                new Job2Task().execute(3);
                new Job2Task().execute(3);
                new Job2Task().executeOnExecutor(Executors.newCachedThreadPool(), 3); //將秒數3傳入
                break;

            case R.id.go3:
                new Job3Task().execute(6);
                break;

            case R.id.start:
                Log.i(TAG, "second: " + second);
                task1 = new MyAsyncTask(this);
//                task1.doInBackground("132","456"); //或是從這邊傳入doInBackground 也可以，但就會在execute()時呼叫第2次doInBackground，一般不會這樣寫
                task1.setPauseTime(second);
                task1.execute(); //
//                task1.execute("132","456"); //正常來說要從這顛做呼叫
                break;

            case R.id.stop:
                task1.cancel(true);
                break;

            case R.id.pause:
                task1.cancel(true);
//                second = task1.getPauseTime();
                break;
        }
    }

//    public static void setPauseTime(int sec) {
//        second = sec;
//    }

    @Override
    protected void onPause() {
        super.onPause();
//        second = task1.getPauseTime();
        task1.cancel(true);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("second", second);
    }

    @Override
    public void updateSecond(int second) {
        this.second = second;
        pb.setProgress(second);
        if (second == 10){
            info_tv.setText("完成");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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
            if (isCancelled()) {
                return null;
            }

            return null;
        }

        //MainThread 上執行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView textView = findViewById(R.id.info);
            textView.setText("Begin");
        }

        //MainThread 上執行
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("tagggg", "onPostExecute");
            TextView textView = findViewById(R.id.info);
            textView.setText("End");
            textView.setTextColor(Color.BLUE);
        }
    }

    class Job3Task extends AsyncTask<Integer, Integer, String> {
        TextView textView = findViewById(R.id.info);

        //MainThread 上執行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Background 執行
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
            return (voids[0] + " already passed"); //如果return null 則之後的 onPostExecute 就不會執行
        }

        //用來顯示目前的進度
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

//    class MyAsyncTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String result = "完成";
//            for (int i = pauseTime; i <= 10  ; i++) {
//                    try {
//                        Log.i(TAG, "doInBackground: " + i);
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    publishProgress(i);
//                }
//            return result;
//        }
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            start.setText(s);
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            Log.i(TAG, "onProgressUpdate: " + values[0]);
//            pb.setProgress(values[0]);
//             second = values[0];
//        }
//    }
}
