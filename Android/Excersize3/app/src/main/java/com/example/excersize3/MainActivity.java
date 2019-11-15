package com.example.excersize3;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "lecture";

    MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtn1Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.do1);
        mp.seekTo(0);
        mp.start();

    }

    public void onBtn2Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.re2);
        mp.seekTo(0);
        mp.start();

    }

    public void onBtn3Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.mi3);
        mp.seekTo(0);
        mp.start();
    }

    public void onBtn4Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.fa4);
        mp.seekTo(0);
        mp.start();
    }

    public void onBtn5Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.sol5);
        mp.seekTo(0);
        mp.start();
    }

    public void onBtn6Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.ra6);
        mp.seekTo(0);
        mp.start();
    }

    public void onBtn7Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.si7);
        mp.seekTo(0);
        mp.start();
    }

    public void onBtn8Clicked(View v){
        if(mp != null){
            try{
                wait(1000);
            }catch (Exception e){
            }
            mp.release();
        }

        mp = MediaPlayer.create(this, R.raw.do8);
        mp.seekTo(0);
        mp.start();
    }

}
