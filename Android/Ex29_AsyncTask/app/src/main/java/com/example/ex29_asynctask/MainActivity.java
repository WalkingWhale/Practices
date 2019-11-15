package com.example.ex29_asynctask;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "lecture";
    int mProgressStatus = 0;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
    }

    public void onBtn1Clicked(View v) {
        new CounterTask().execute(0);
    }

    class CounterTask extends AsyncTask<Integer, Integer, Integer> {

        protected void onPreExecute(Integer integer) {
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            while (mProgressStatus < 100){
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                mProgressStatus++;
                publishProgress(mProgressStatus);
            }

            return mProgressStatus;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(mProgressStatus);
        }

        protected  void onPostExecute(Integer result){
            progressBar.setProgress(mProgressStatus);
        }

    }
}
