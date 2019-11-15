package com.example.project_4th;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    String fImgName = "";
    Date startDate;
    Date endDate;
    String period = "";

    FestivalItemAdapter adapter;

    static FestivalItem item;

    //Locations_National locations_national;
    private static final int MULTIPLE_PERMISSIONS = 101;

    FirebaseFirestore db;
    private StorageReference mStorageRef;

    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FestivalItemAdapter(this);
        //locations_national = new Locations_National();

        FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();

        checkPermissions();

        //getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,locations_national).commit();

        dataQuery();

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FestivalItem item = (FestivalItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "selected : " + item.getfName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void dataQuery() {
        // 서버에서 일단 가져운 후에도 서버에서 데이터가 변경되면
        // 폰의 화면에서도 실시간으로 자동 갱신 된다.
        //int LIMIT = 30;
        db.collection("P4_festival")
                //.orderBy("name", Query.Direction.DESCENDING)
                //.limit(LIMIT)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d(TAG, "Listen failed.", e);
                            return;
                        }

                        String sMax = "0";
                        //StringBuilder sb = new StringBuilder();
                        //sb.append("");

                        for(QueryDocumentSnapshot doc : value){
                            if(doc.get("fName") != null){
                                startDate = doc.getTimestamp("fStartDate").toDate();
                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd kk:mm");
                                String sDate = sdf1.format(startDate);

                                endDate = doc.getTimestamp("fEndDate").toDate();
                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd kk:mm");
                                String eDate = sdf1.format(endDate);
                                period = sDate + " ~ " + eDate;

                                item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                Log.d(TAG,doc.getString("fName") + " / " + doc.getString("fLocation") + " / " + period + " / " + doc.getString("fImageName"));
                                adapter.addItem(item);
                                adapter.notifyDataSetChanged();
                                //view.setfPeriod();
                            }
                        }
                        //tvContents.setText(sb.toString());
                    }
                });
    }

    private boolean checkPermissions(){
        int result;
        List<String> permissionList  = new ArrayList<>();

        for(String pm : permissions){
            result = ContextCompat.checkSelfPermission(this, pm);
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(pm);
            }
        }

        if(!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0 ; i < permissions.length ; i++){
                        if(permissions[i].equals(this.permissions[0])){
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                                showNoPermissionToastAndFinish();
                            }
                        } else if(permissions[i].equals(this.permissions[1])){
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                                showNoPermissionToastAndFinish();
                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish(){
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void btnSeoulClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), Location_selected.class);
        intent.putExtra("location", "서울");
        startActivity(intent);
        finish();
    }
}
