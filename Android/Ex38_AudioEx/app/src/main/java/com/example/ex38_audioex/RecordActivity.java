package com.example.ex38_audioex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    private static final String TAG = "lecture";
    private static String RECORDED_FILE;

    String[] permissions= new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    MediaPlayer player;
    MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "recorded.mp3");
        RECORDED_FILE = file.getAbsolutePath();

        if (!checkPermissions()) {
            Toast.makeText(getApplicationContext(),
                    "권한 설정을 해주셔야 앱이 정상 동작합니다.",
                    Toast.LENGTH_LONG).show();
        }

    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    1 );
            return false;
        }
        return true;
    }


    public void onBtn1Clicked(View v){
        if(recorder != null){
            recorder.stop();
            recorder.release();
            recorder = null;
        }

        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(RECORDED_FILE);

        try{
            Toast.makeText(getApplicationContext(), "녹음을 시작합니다.", Toast.LENGTH_LONG).show();

            recorder.prepare();
            recorder.start();
        } catch (Exception e){
            Log.d(TAG, "Exception : " + e);
        }
    }

    public void onBtn2Clicked(View v){
        if(recorder == null){
           return;
        }
        recorder.stop();
        recorder.release();
        recorder = null;

        Toast.makeText(getApplicationContext(), "녹음이 중지되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void onBtn3Clicked(View v){
        if(player != null){
            player.stop();
            player.release();
            player = null;
        }
        Toast.makeText(getApplicationContext(), "녹음된 파일을 재생합니다.", Toast.LENGTH_LONG).show();

        try{
            player = new MediaPlayer();

            player.setDataSource(RECORDED_FILE);
            player.prepare();
            player.start();
        } catch (Exception e){
            Log.d(TAG, "Exception : " + e);
        }

    }

    public void onBtn4Clicked(View v){
        if(player == null){
            return;
        }
        player.stop();
        player.release();
        player = null;

        Toast.makeText(getApplicationContext(), "재생이 중지되었습니다.", Toast.LENGTH_LONG).show();
    }
}
