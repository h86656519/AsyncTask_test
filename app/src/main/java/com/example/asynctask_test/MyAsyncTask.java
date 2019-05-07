package com.example.asynctask_test;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {
    private int pauseTime = 1;
    private int second = 1;
    private TimeListiner timeListiner;

    public MyAsyncTask(TimeListiner tl) {
        this.timeListiner = tl;
    }

    public void setPauseTime(int second) {
        pauseTime = second;
    }
//
//    public int getPauseTime() {
//        pauseTime = second;
//        return pauseTime;
//    }

    @Override
    protected String doInBackground(String... params) {
        String result = "完成";
        for (int i = pauseTime; i <= 10; i++) {
            try {
                Log.i("TAG", "doInBackground: " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String s) {
     //   start_btn.setText(s);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i("TAG", "onProgressUpdate: " + values[0]);
//        progressBar.setProgress(values[0]);
        second = values[0];
        timeListiner.updateSecond(second);
    }
}
