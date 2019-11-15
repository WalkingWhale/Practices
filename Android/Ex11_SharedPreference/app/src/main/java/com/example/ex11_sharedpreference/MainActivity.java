package com.example.ex11_sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    SharedPreferences.Editor editor;
    EditText tvId;
    EditText tvPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor = pref.edit();

        tvId = findViewById(R.id.etID);
        tvPwd = findViewById(R.id.etPwd);

        // --------------------------------------------------------------------------

        String id = pref.getString("id", "");
        String pwd = pref.getString("pwd", "");

        Log.d(TAG, "id : " + id);

        tvId.setText(id);
        tvPwd.setText(pwd);
    }

    public void btnLoginClicked(View v){
        String sid = tvId.getText().toString();
        String spwd = tvPwd.getText().toString();

        editor.putString("id", sid);
        editor.putString("pwd", spwd);
        editor.commit();
    }
}
