package com.example.project_4th;

import android.content.Intent;
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
import java.util.Date;

import javax.annotation.Nullable;

public class Location_selected extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_location_selected);
        String locationName = intent.getExtras().getString("location");
        //Toast.makeText(getApplicationContext(), "aaaa + " + locationName, Toast.LENGTH_SHORT).show();

        adapter = new FestivalItemAdapter(this);
        //locations_national = new Locations_National();

        FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();

        dataQuery(locationName);

        ListView listView = findViewById(R.id.listview_ing);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FestivalItem item = (FestivalItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "selected : " + item.getfName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void dataQuery(String locationName) {
        //Log.d(TAG, "aaaaaaaa : " + locationName);
        // 서버에서 일단 가져운 후에도 서버에서 데이터가 변경되면
        // 폰의 화면에서도 실시간으로 자동 갱신 된다.
        //int LIMIT = 30;
        db.collection("P4_festival")
                .whereEqualTo("fLocation", locationName)
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
}
