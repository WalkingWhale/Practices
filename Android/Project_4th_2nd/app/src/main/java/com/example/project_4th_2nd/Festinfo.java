package com.example.project_4th_2nd;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.annotation.Nullable;

public class Festinfo extends AppCompatActivity {
    private static String TAG = "lecture";

    String id;
    String username;
    String docNum;
    float review_rate;

    private StorageReference mStorageRef;
    //static ReviewItem item;
    static int nMax = 0;

    SupportMapFragment mapFragment;
    GoogleMap map;

    Date startDate;
    Date endDate;
    Date nowDate;
    String period = "";
    LatLng mapPosition;
    Bitmap bitmapImage;

    TextView fName;
    TextView fPeriod;
    TextView fLocation;
    ImageView fStatus;
    ImageView fMainImg;
    TextView fContent;
    Button btn_nav;
    RatingBar avg_rating;

    FirebaseFirestore db;
    FirebaseFirestore db_review;

    TabLayout tabLayout;
    static ReviewItemAdapter review_adapter;
    String fName_In;

    MarkerOptions myLocationMarker;

    boolean review_dup = false;
    boolean new_review = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festinfo);

        fName_In = getIntent().getStringExtra("fName");
        //Log.d(TAG, fName_In);

        id = getIntent().getStringExtra("id");
        username = getIntent().getStringExtra("username");
        docNum = getIntent().getStringExtra("docNum");
        //Log.d(TAG, "잘옴? : " + docNum);
        //Log.d(TAG, "id : " + id + ", username : " + username);

        // 초기화
        String bannerid = "ca-app-pub-9639528876696085/8388545157";
        MobileAds.initialize(getApplicationContext(), bannerid);

        // 테스트 광고 부르기
        AdView mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "b:"+errorCode);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        AdRequest adRequest = new AdRequest
                .Builder()
                .addTestDevice("FE7FFF0A25FF80FD24EC7C56BA85F603")
                .build();
        mAdView.loadAd(adRequest);


        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        db_review = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        tabLayout = findViewById(R.id.tabMenu2);
        fName = findViewById(R.id.tv_FestName_item);
        fPeriod = findViewById(R.id.tv_festPeriod);
        fContent = findViewById(R.id.tv_content);
        fStatus = findViewById(R.id.iv_status);
        fMainImg = findViewById(R.id.iv_festImg_item);
        fLocation = findViewById(R.id.tv_SpecificLoc);
        btn_nav = findViewById(R.id.btn_nav);
        avg_rating = findViewById(R.id.avg_rating);

        review_adapter = new ReviewItemAdapter(getApplicationContext());

        nowDate = new Date();

        dataQuery(fName_In);
        getCount();
        //reviewQuery(fName_In);

        ListView reviewList = findViewById(R.id.rv_listview);
        reviewList.setAdapter(review_adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                startMainActivity(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                startMainActivity(tab.getPosition());
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //Log.d(TAG, "GoogleMap is ready");

                map = googleMap;
                requestMapDraw();
            }
        });


        try{
            MapsInitializer.initialize(this);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startMainActivity(int position){
        Intent intent = new Intent(Festinfo.this, MainActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        intent.putExtra("username", username);
        intent.putExtra("docNum", docNum);
        startActivity(intent);
    }

    public void dataQuery(String fName_In) {
        db.collection("P4_festival")
                .whereEqualTo("fName", fName_In)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if(doc.get("fName") != null){
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
                                    try{
                                        startDate = sdf.parse(doc.getString("fStartDate"));

                                        endDate = sdf.parse(doc.getString("fEndDate"));

                                        period = doc.getString("fStartDate") + " ~ " + doc.getString("fEndDate");

                                        if(startDate.before(nowDate) && endDate.after(nowDate)){
                                            fName.setText(doc.getString("fName"));
                                            fPeriod.setText(period);
                                            fStatus.setImageResource(R.drawable.ing_btn);
                                            fContent.setText(doc.getString("fContent"));
                                            fLocation.setText(doc.getString("fLocation") + " " + doc.getString("fLocSpecific"));

                                            mapPosition = new LatLng(doc.getDouble("fLatitude"),doc.getDouble("fLongitude"));

                                            Log.d(TAG, "first check lat : " + mapPosition.latitude + "log : " + mapPosition.longitude);

                                            setMapPosition(mapPosition);
                                            showLocation(mapPosition);
                                            downloadImage(doc.getString("fImageName"));
                                            //item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                            //Log.d(TAG,doc.getString("fName") + " / " + doc.getString("fLocation") + " / " + period + " / " + doc.getString("fImageName"));
                                            //ingAdapter.addItem(item);
                                            //ingAdapter.notifyDataSetChanged();
                                        } else {
                                            fName.setText(doc.getString("fName"));
                                            fPeriod.setText(period);
                                            fStatus.setImageResource(R.drawable.upcoming_btn);
                                            fContent.setText(doc.getString("fContent"));
                                            fLocation.setText(doc.getString("fLocation") + " " + doc.getString("fLocSpecific"));

                                            mapPosition = new LatLng(doc.getDouble("fLatitude"),doc.getDouble("fLongitude"));

                                            setMapPosition(mapPosition);
                                            showLocation(mapPosition);
                                            downloadImage(doc.getString("fImageName"));

                                            //item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                            //Log.d(TAG,doc.getString("fName") + " / " + doc.getString("fLocation") + " / " + period + " / " + doc.getString("fImageName"));
                                            //upcomingAdapter.addItem(item);
                                            //upcomingAdapter.notifyDataSetChanged();
                                        }
                                    } catch (Exception ex){
                                        ex.printStackTrace();
                                    }

                                }
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        db_review.collection("p4_review")
                .whereEqualTo("fName", fName_In)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d(TAG, "Listen failed.", e);
                            return;
                        }

                        if(value.size() == 0){
                            Log.d(TAG, "작성된 리뷰 없음");
                            return;
                        }

                        float avg = 0;
                        int count = 0;
                        String period;
                        String rateS;
                        float rateF;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd kk:mm:ss");

                        for(QueryDocumentSnapshot doc : value){
                            period = doc.getTimestamp("date").toDate().toString();
                            String[] array = period.split(" ");
                            String conPeroid = array[5] + "/" +  array[1] + "/" + array[2] + " " + array[3];

                            //Log.d(TAG, "시간 확인용 : " + conPeroid);
                            rateS = doc.getString("rating");
                            rateF = Float.parseFloat(rateS);
                            //Log.d(TAG, "fName : " + doc.getString("fName") + ", userId : " + doc.getString("userId") + ", date : " + period + ", review : " + doc.getString("review") + ", rate : " + rateF);
                            avg += rateF;

                            ReviewItem item = new ReviewItem(doc.getString("fName"), doc.getString("userId"), doc.getString("nickname"), conPeroid, doc.getString("review"), rateF);
                            //Log.d(TAG, "bbbbbbb ~ fName : " + item.getfName() + ", userId : " + item.getUserId() + ", date : " + item.getDate() + ", review : " + item.getReview() + ", rate : " + item.getRating());
                            review_adapter.addItem(item);
                            review_adapter.notifyDataSetChanged();
                            //upDateItemList(item);
                            //review_adapter.update(item);
                            count++;
                        }
                        //Log.d(TAG, "aaa : " + avg + ", " + count);
                        setAvg_rating(avg, count);
                    }
                });


    }

    public void setMapPosition(LatLng latlng){
        Log.d(TAG, "first check lat : " + latlng.latitude + "log : " + latlng.longitude);
        this.mapPosition = latlng;
    }

    public void downloadImage(String imgName) {

        String folderName = "images";
        // Storage 이미지 다운로드 경로
        String storagePath = folderName + "/" + imgName;

        StorageReference imageRef = mStorageRef.child(storagePath);

        try {
            // Storage 에서 다운받아 저장시킬 임시파일
            final File imageFile = File.createTempFile("images", "jpg");
            imageRef.getFile(imageFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Success Case
                            bitmapImage = BitmapFactory.decodeFile(imageFile.getPath());
                            fMainImg.setImageBitmap(bitmapImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail Case
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Fail !!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestMapDraw(){
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            long minTime = 10000;
            float minDistance = 0;
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation != null){
                showLocation(lastLocation);
            }

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }

            );
        } catch(SecurityException e){
            e.printStackTrace();
        }
    }

    private void showLocation(Location location){

        if(mapPosition != null){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapPosition, 15));
            showMyLocationMarker(location);
        } else {
            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
            showMyLocationMarker(location);
        }
    }

    private void showLocation(LatLng latlng){

        if(mapPosition != null){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
            showMyLocationMarker(latlng);
        }
    }

    private void showMyLocationMarker(Location location){
        if(myLocationMarker == null){
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
            myLocationMarker.title(fName.getText().toString());
            //myLocationMarker.snippet("GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        } else{
            myLocationMarker.position(new LatLng(location.getLatitude(),location.getLongitude()));
        }
    }

    private void showMyLocationMarker(LatLng latLng){
        if(myLocationMarker == null){
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(latLng.latitude , latLng.longitude));
            myLocationMarker.title(fName.getText().toString());
            //myLocationMarker.snippet("GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        } else{
            myLocationMarker.position(new LatLng(latLng.latitude, latLng.longitude));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(map != null){
            // 권한 체크 후 사용자에 의해 취소되었다면 다시 요청
            map.setMyLocationEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(map != null){
            // 권한 체크 후 사용자에 의해 취소되었다면 다시 요청
            map.setMyLocationEnabled(true);
        }
    }

    public void onBtnNavCliecked(View v) {

        LocationManager manager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( Festinfo.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }

        Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        //Log.d(TAG, "aaabbbb 위도 : " + longitude + ", 경도 : " + latitude + ", 저장된 위도 : " + mapPosition.longitude + ", 저장된 경도 : " + mapPosition.latitude);
        String uri = "https://m.map.naver.com/route.nhn?menu=route&sname=내 위치&sx="+ longitude +"&sy=" + latitude + "&ename=" + fName.getText().toString() + "&ex=" + mapPosition.longitude +"&ey=" + mapPosition.latitude + "&pathType=0&showMap=true";
        //String uri = "https://m.map.naver.com/route.nhn?menu=route&sname=출발지&sx=126.8106458&sy=37.5776317&ename=도착지&ex=126.8767315&ey=37.4788962&pathType=0&showMap=true";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }

    public void setAvg_rating(float rate, int count){
        //Log.d(TAG, "ccccccc : " + rate + ", " + count);
        float avg_temp = rate / (float) count;
        //Log.d(TAG, "eeeeeeeeeeeeeee + " + avg_temp);
        String avgS = String.format("%.1f", avg_temp);
        float avgF = Float.parseFloat(avgS);
        //Log.d(TAG, "bbbbbb + " + avgF);
        avg_rating.setRating(avgF);
    }

    public void onbtnReviewWriteClicked(View v){
        final String nickname = this.username;
        final String id = this.id;
        final String name_fes = this.fName.getText().toString();

        dupCheck(name_fes, nickname);
        //Log.d(TAG, "eeee + " + review_dup);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.review_wirte_dialog);
        dialog.setTitle("리뷰 작성");
        dialog.setCancelable(false);
        final TextView rving_fName = dialog.findViewById(R.id.rving_fname);
        final TextView rving_username = dialog.findViewById(R.id.rving_username);
        final EditText rving_content = dialog.findViewById(R.id.rving_content);
        final TextView rving_textCount = dialog.findViewById(R.id.review_textCount);
        final RatingBar rving_rating = dialog.findViewById(R.id.rving_rating);
        rving_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setRate(rating);
            }
        });

        final Button btn_submmit = dialog.findViewById(R.id.btn_rving_submmit);
        btn_submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rving_content.length() == 0){
                    Toast.makeText(Festinfo.this, "리뷰 내용을 입력해 주십시오", Toast.LENGTH_SHORT).show();
                    return;
                }

                submmit_review(name_fes, id, nickname, rving_content.getText().toString());

                dialog.dismiss();
            }
        });

        final Button btn_cancel = dialog.findViewById(R.id.btn_rving_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rving_content.addTextChangedListener(new TextWatcher() {
            final int Max = 100;
            String strcur;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strcur = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > Max){
                    AlertDialog.Builder alert = new AlertDialog.Builder(Festinfo.this);
                    alert.setTitle("Error");
                    alert.setMessage("리뷰는 100자를 초과할 수 없습니다.");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alert.show();


                    rving_content.setText(strcur);
                    rving_content.setSelection(before);
                } else{
                    rving_textCount.setText(String.valueOf(s.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rving_fName.setText(name_fes);
        rving_username.setText(nickname);

        dialog.show();
    }

    public void onShowAllReviewClicked(View v){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.all_reviews);
        dialog.setTitle("모든 리뷰 보기");
        dialog.setCancelable(false);
        final ReviewItemAdapter allAdapter = new ReviewItemAdapter(dialog.getContext());


        final Button btn_back = dialog.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        db.collection("p4_review")
                .whereEqualTo("fName", fName_In)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        String period;
                        String rateS;
                        float rateF;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd kk:mm:ss");

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                period = doc.getTimestamp("date").toDate().toString();
                                String[] array = period.split(" ");
                                String conPeroid = array[5] + "/" +  array[1] + "/" + array[2] + " " + array[3];
                                rateS = doc.getString("rating");
                                rateF = Float.parseFloat(rateS);
                                //Log.d(TAG, "fName : " + doc.getString("fName") + ", userId : " + doc.getString("userId") + ", date : " + period + ", review : " + doc.getString("review") + ", rate : " + rateF);

                                ReviewItem item = new ReviewItem(doc.getString("fName"), doc.getString("userId"), doc.getString("nickname"), conPeroid, doc.getString("review"), rateF);
                                //Log.d(TAG, doc.getId() + " => " + doc.getData() + " => " + doc.get("id"));
                                allAdapter.addItem(item);
                                allAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        final ListView all_review_list = dialog.findViewById(R.id.all_review_list);
        all_review_list.setAdapter(allAdapter);

        dialog.show();
    }

    public void dupCheck(final String fesName, final String nickname){
        db.collection("p4_review")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if(doc.get("fName").toString().equals(fesName) && doc.get("userId").toString().equals(nickname)){
                                    //Toast.makeText(getApplicationContext(), "리뷰는 한번만 작성할 수 있습니다.", Toast.LENGTH_SHORT).show();
                                    isDup();
                                }
                                //Log.d(TAG, doc.getId() + " => " + doc.getData() + " => " + doc.get("id"));
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void isDup(){
        this.review_dup = true;
    }

    public void submmit_review(final String fesName, final String id, final String nickname, final String comment){
        //dupCheck(fesName, nickname);
        //Log.d(TAG, "cccccc + " + review_dup);

        if(review_dup == true){
            Toast.makeText(getApplicationContext(), "리뷰는 한번만 작성할 수 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        Date now = new Date();
        Timestamp nowT = new Timestamp(now.getTime());

        final String sNow = sdf.format(now);
        String review_rateS = String.valueOf(review_rate);

        Map<String, Object> quote = new HashMap<>();
        quote.put("fName", fesName);
        quote.put("userId", id);
        quote.put("nickname", nickname);
        quote.put("rating", review_rateS);
        quote.put("date", nowT);
        quote.put("review", comment);

        String newCount = String.format("%03d", nMax +1 );
        //String newCount = "2";
        //Log.d(TAG, "bbbbbb + " + newCount);

        db.collection("p4_review")
                //.document()
                .document(newCount)
                .set(quote)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(), "리뷰를 작성했습니다", Toast.LENGTH_SHORT).show();
                        final String f_sNow = sNow;
                        final String f_comment = comment;
                        updateNewReivew(fesName, id, nickname, sNow, comment, review_rate);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error Writing document!", e);
                    }
                });


        updateNewReivew(fesName, id, nickname, sNow, comment, review_rate);
        return;

    }

    public void setRate(float rate){
        this.review_rate = rate;
    }

    public void getCount(){
        db_review.collection("p4_review")
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

                        for(QueryDocumentSnapshot doc : value){
                            if(doc.get("fName") != null){
                                sMax = doc.getId();
                            }
                        }
                        setMax(Integer.parseInt(sMax));
                    }
                });

    }

    public void setMax(int max) {
        //Log.d(TAG, "set맥스 실행 qwerty : " + max);
        nMax = max;
    }
    public void updateNewReivew(String fesName,String id, String nickname,String sNow,String comment,float review_rate) {

        review_adapter.resetItem();
        ReviewItem item = new ReviewItem(fesName, id, nickname, sNow, comment, review_rate);
        review_adapter.addItem(item);
        review_adapter.notifyDataSetChanged();
    }
}
