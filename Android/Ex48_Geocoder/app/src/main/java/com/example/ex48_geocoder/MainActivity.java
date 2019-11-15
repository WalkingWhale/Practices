package com.example.ex48_geocoder;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    Geocoder coder;
    TextView tvResult;
    EditText etLatitude;
    EditText etLongitude;
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coder = new Geocoder(this);
        tvResult = findViewById(R.id.result);
        etLatitude = findViewById(R.id.lattitude);
        etLongitude = findViewById(R.id.longitude);
        etAddress = findViewById(R.id.address);
    }

    public void onBtn1Clicked(View v){
        List<Address> list = null;
        String latitude = etLatitude.getText().toString();
        String longitude = etLongitude.getText().toString();

        try{
            list = coder.getFromLocation(
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    10
            );
        } catch (NumberFormatException e){
            e.printStackTrace();
        } catch (IOException ie){
            tvResult.setText("에러 : " + ie.getMessage());
            ie.printStackTrace();
        }

        if(list != null){
            tvResult.setText(list.get(0).toString());
        }
    }

    public void onBtn2Clicked(View v){
        List<Address> list = null;
        String address = etAddress.getText().toString();

        try{
            list = coder.getFromLocationName(address, 10);
        } catch (IOException ie){
            tvResult.setText("에러 : " + ie.getMessage());
            ie.printStackTrace();
        }

        if(list != null){
            tvResult.setText(list.get(0).toString());
        }
    }

}
