package com.example.ex31_httpex1;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView tvHtml2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_post);

        tvHtml2 = findViewById(R.id.tvHtml2);
    }

    public void onBtnPost(View v){
        String sUrl = getString(R.string.server_addr) + "/JSPInServer/loginOk.jsp";

        try{
            ContentValues values = new ContentValues();
            values.put("userid", "abcde");
            values.put("userpwd", "1234");

            // AsyncTask를 통해 HttpURLConnection을 수행.
            NetworkTask networkTask = new NetworkTask(sUrl, values);
            networkTask.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onBtnFinish(View v){ finish(); }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            // 해당 URL로 부터 결과물을 얻어온다.
            result = requestHttpURLConnection.request(url, values);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackGround()로 부터 리턴된 값이 onPostExecute()의 매개변수로
            // 넘어오므로 s를 출력한다.
            tvHtml2.setText(s);
        }
    }

}
