package com.example.ex31_httpex1;

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

public class HttpGetActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    TextView tvHtml;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get);

        tvHtml = findViewById(R.id.tvHtml1);
        webView = findViewById(R.id.webView1);
    }

    public void onBtnGet(View v){
        String sUrl = getString(R.string.server_addr) + "/JSPInServer/login.jsp";

        GetAction myGetAct = new GetAction();
        myGetAct.execute(sUrl);
    }

    public void onBtnFinish(View v){ finish(); }

    class GetAction extends AsyncTask<String, Integer, String> {

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... value) {
            String sOutput = "";

            // Get으로 부르기
            StringBuilder output = new StringBuilder();
            try{
                URL url = new URL(value[0].toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(TAG, "sUrl:" + value[0].toString());

                if(conn != null){
                    //Log.d(TAG, "asdasdasd");

                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    int resCode = conn.getResponseCode();
                    //Log.d(TAG, "qwerty : " + resCode);
                    if(resCode == HttpURLConnection.HTTP_OK){
                        //Log.d(TAG,"aaa");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;

                        while(true){
                            line = reader.readLine();
                            if(line == null){
                                break;
                            }
                            output.append(line+"\n");
                            Log.d(TAG,"bbbbb : " + output.toString());
                        }
                        reader.close();
                        conn.disconnect();

                        Log.d(TAG, output.toString());

                        sOutput = output.toString();
                        Log.d(TAG,"cccc : " + sOutput);
                    } else {

                    }
                } else {
                    //Log.d(TAG,"poipoipoi");
                }
            } catch (Exception ex){
                //ex.printStackTrace();
            }

            //Log.d(TAG, "aaaaa : " + sOutput);
            return sOutput;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(String result) {
            //Log.d(TAG, "post execute" + result);

            tvHtml.setText(result);
            webView.loadData(result, "text/html; charset=UTF-8",null);
        }
    }
}
