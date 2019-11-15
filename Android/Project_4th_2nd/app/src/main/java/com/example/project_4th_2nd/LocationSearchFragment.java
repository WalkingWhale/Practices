package com.example.project_4th_2nd;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationSearchFragment extends Fragment {
    private static String TAG = "lecture";
    private Context context;

    LinearLayout festlist;

    Date startDate;
    Date endDate;
    Date nowDate;
    String period = "";

    FestivalItemAdapter location_ingAdapter;
    FestivalItemAdapter location_upcomingAdapter;

    ListView listViewIng;
    ListView listViewUpcoming;
    String username;
    String id;
    String docNum;

    static FestivalItem item;

    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_location_search, container, false);
        context = container.getContext();
        id = getArguments().getString("id");
        username = getArguments().getString("username");
        docNum = getArguments().getString("docNum");

        festlist = rootView.findViewById(R.id.layout_festlist_location);

        nowDate = new Date();

        location_ingAdapter = new FestivalItemAdapter(context);
        location_upcomingAdapter = new FestivalItemAdapter(context);
        //locations_national = new Locations_National();

        listViewIng = rootView.findViewById(R.id.lv_ing_location);
        listViewUpcoming = rootView.findViewById(R.id.lv_upcoming_location);

        FirebaseApp.initializeApp(context);

        db = FirebaseFirestore.getInstance();

        final Button btn_seoul = rootView.findViewById(R.id.btn_seoul);       // 서울 선택
        btn_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_seoul.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Incheon = rootView.findViewById(R.id.btn_Incheon);   // 인천 선택
        btn_Incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Incheon.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Daejeon = rootView.findViewById(R.id.btn_Daejeon);   // 대전 선택
        btn_Daejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Daejeon.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Daegu = rootView.findViewById(R.id.btn_Daegu);       // 대구 선택
        btn_Daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Daegu.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Busan = rootView.findViewById(R.id.btn_Busan);       // 부산 선택
        btn_Busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Busan.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Gwangju = rootView.findViewById(R.id.btn_Gwangju);   // 광주 선택
        btn_Gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Gwangju.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Ulsan = rootView.findViewById(R.id.btn_Ulsan);       // 울산 선택
        btn_Ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Ulsan.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Gyeonggi = rootView.findViewById(R.id.btn_Gyeonggi); // 경기 선택
        btn_Gyeonggi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Gyeonggi.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Gangone = rootView.findViewById(R.id.btn_Gangone);   // 강원 선택
        btn_Gangone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Gangone.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Chungcheong_North = rootView.findViewById(R.id.btn_Chungcheong_North);   // 충북 선택
        btn_Chungcheong_North.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Chungcheong_North.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Chungcheong_South = rootView.findViewById(R.id.btn_Chungcheong_South);   // 충남 선택
        btn_Chungcheong_South.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Chungcheong_South.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Gyeongsang_North = rootView.findViewById(R.id.btn_Gyeongsang_North);     // 경북 선택
        btn_Gyeongsang_North.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Gyeongsang_North.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Gyeongsang_South = rootView.findViewById(R.id.btn_Gyeongsang_South);     // 경남 선택
        btn_Gyeongsang_South.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Gyeongsang_South.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Jeonlla_North = rootView.findViewById(R.id.btn_Jeonlla_North);     // 전북 선택
        btn_Jeonlla_North.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Jeonlla_North.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Jeonlla_Sorth = rootView.findViewById(R.id.btn_Jeonlla_Sorth);     // 전남 선택
        btn_Jeonlla_Sorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Jeonlla_Sorth.getText().toString());
                listViewUpdate();
            }
        });

        final Button btn_Jeju = rootView.findViewById(R.id.btn_Jeju);     // 제주 선택
        btn_Jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                festlist.setVisibility(View.VISIBLE);
                dataQuery(btn_Jeju.getText().toString());
                listViewUpdate();
            }
        });

        return rootView;
    }

    public void dataQuery(String location) {
        location_ingAdapter.resetItem();
        location_upcomingAdapter.resetItem();
        // 서버에서 일단 가져운 후에도 서버에서 데이터가 변경되면
        // 폰의 화면에서도 실시간으로 자동 갱신 된다.
        //int LIMIT = 30;
        db.collection("P4_festival")
                .whereEqualTo("fLocation", location)
                //.orderBy("name", Query.Direction.DESCENDING)
                //.limit(LIMIT)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot value, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d(TAG, "Listen failed.", e);
                            return;
                        }

                        for(QueryDocumentSnapshot doc : value){

                            if(doc.get("fName") != null){
                                //startDate = doc.getTimestamp("fStartDate").toDate();
                                //endDate = doc.getTimestamp("fEndDate").toDate();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
                                try{
                                    startDate = sdf.parse(doc.getString("fStartDate"));
                                    //String sDate = sdf.format(startDate);

                                    endDate = sdf.parse(doc.getString("fEndDate"));
                                    //String eDate = sdf.format(endDate);
                                    //period = sDate + " ~ " + eDate;

                                    period = doc.getString("fStartDate") + " ~ " + doc.getString("fEndDate");

                                    if(startDate.before(nowDate) && endDate.after(nowDate)){
                                        item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                        //Log.d(TAG,doc.getString("fName") + " / " + doc.getString("fLocation") + " / " + period + " / " + doc.getString("fImageName"));
                                        location_ingAdapter.addItem(item);
                                        location_ingAdapter.notifyDataSetChanged();
                                    } else {
                                        item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                        //Log.d(TAG,doc.getString("fName") + " / " + doc.getString("fLocation") + " / " + period + " / " + doc.getString("fImageName"));
                                        location_upcomingAdapter.addItem(item);
                                        location_upcomingAdapter.notifyDataSetChanged();
                                    }
                                } catch (Exception ex){
                                    ex.printStackTrace();
                                }


                                //view.setfPeriod();
                            }
                        }
                        //tvContents.setText(sb.toString());
                    }
                });
    }

    public void listViewUpdate() {
        listViewIng.setAdapter(location_ingAdapter);
        listViewIng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FestivalItem item = (FestivalItem) location_ingAdapter.getItem(position);
                //Toast.makeText(context, "selected : " + item.getfName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Festinfo.class);
                intent.putExtra("fName", item.getfName());
                intent.putExtra("id", id);
                intent.putExtra("username", username);
                intent.putExtra("docNum",docNum);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        listViewUpcoming.setAdapter(location_upcomingAdapter);
        listViewUpcoming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FestivalItem item = (FestivalItem) location_upcomingAdapter.getItem(position);
                //Toast.makeText(context, "selected : " + item.getfName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Festinfo.class);
                intent.putExtra("fName", item.getfName());
                intent.putExtra("id", id);
                intent.putExtra("username", username);
                intent.putExtra("docNum",docNum);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
