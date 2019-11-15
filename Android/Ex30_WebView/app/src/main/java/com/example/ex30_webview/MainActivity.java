package com.example.ex30_webview;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    private final Handler handler = new Handler();

    WebView web;
    EditText etMessage;
    CustomCircleProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.web1);
        web.clearCache(true);                                      // 캐쉬 지우기
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 캐쉬 사용하지 않기
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDefaultTextEncodingName("UTF-8");

        web.loadUrl("https://www.google.com");
        web.setWebViewClient(new myWebView());
        web.setWebChromeClient(new myWebChromeClient());
        web.setHorizontalScrollBarEnabled(false); // 세로 scroll 제거
        web.setVerticalScrollBarEnabled(false);    // 가로 scroll 제거
        web.addJavascriptInterface(new JavaScriptBridge(), "android");

        etMessage = (EditText)findViewById(R.id.etMessage);
    }

    public void btnLocalHtml(View v){
        // 로컬 HTML 부르기
        web.loadUrl("file:///android_asset/jsexam.html");
    }

    public void btnWebHtml(View v){
        // 웹 HTML 부르기
        web.loadUrl("https://www.google.com");
    }

    public void btnJSCall(View v){
        web.loadUrl("javascript:setMessage('"+etMessage.getText()+"')");
    }

    public void btnStringLoad(View v){
        // String 웹뷰로 부르기
        String summary = "<html>" +
                         "<head>" +
                         "<meta charset = \"utf-8\">" +
                         "</head>" +
                         "<body>" +
                         "Hello~ 강감찬" +
                         "<img src='https://goo.gl/LvIXUL'>" +
                         "</body>" +
                         "</html>";
        // Android 4.0 이하 버전
        // web.loadData(summary,"text/html; charset=UTF-8");
        // Android 4.1 이상 버젼
        web.loadData(summary,"text/html; charset=UTF-8", null);
    }

    private class myWebChromeClient extends WebChromeClient
    {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final android.webkit.JsResult result){
            result.confirm();

            new AlertDialog.Builder(view.getContext())
                    //.setIcon(R.drawable.icon);
                    //.setTitle(R.string.title_activity_main)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener(){
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(true)
                    .create()
                    .show();

            return true;
        };

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final android.webkit.JsResult result)
        {
            new AlertDialog.Builder(view.getContext())
                    //.setTitle(R.string.title_activity_main)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();

            return true;
        };

    }

    private class myWebView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (url.startsWith("http://")) {
                view.loadUrl(url);
                return true;
            }
            if (url.startsWith("https://")) {
                view.loadUrl(url);
                return true;
            }
            if (url.startsWith("mailto:")) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                startActivity(i);
                return true;
            }
            if (url.startsWith("sms:")) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                startActivity(i);
                return true;
            }
            if (url.startsWith("tel:")) {
                int permissionCheck = ContextCompat.checkSelfPermission(
                        MainActivity.this,
                        Manifest.permission.CALL_PHONE);
                if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(),
                            "전화걸기를 사용하시려면 먼저 권한을 승인해 주세요.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                    startActivity(i);
                }

                return true;
            }
            if (url.startsWith("geo:")) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
                return true;
            }
            return true;
        }
        /*
        * 웹페이지 로딩이 시작될 때 처리
        * */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressDialog = new CustomCircleProgressDialog(MainActivity.this);
            // 주변 클릭 터치 시 프로그램에서 사라지지 않게 하기 : false
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        /*
        * 웹페이지 로딩중 에러발생시 처리
        * */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            Toast.makeText(getApplicationContext(), "Loading Error : " + description, Toast.LENGTH_SHORT).show();

        }

        /**
         * 웹 페이지 로딩이 끝났을 때 처리
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }

    final class JavaScriptBridge {

        @JavascriptInterface
        public void test(){
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "테스트", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Parameter must be final
        @JavascriptInterface
        public void testParams(final String arg){
            handler.post(new Runnable() {
                public void run() {
                    Log.d(TAG, "setMessage("+arg+")");
                    Toast.makeText(getApplicationContext(), "테스트파라미터:"+arg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK) && web.canGoBack()){
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        web.stopLoading();
    }
}
