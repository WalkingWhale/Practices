package com.example.project_4th_2nd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment extends Fragment {
    private static String TAG = "lecture";
    private Context context;

    Date startDate;
    Date endDate;
    Date nowDate;
    String period = "";

    FestivalItemAdapter ingAdapter;
    FestivalItemAdapter upcomingAdapter;

    static FestivalItem item;

    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        context = container.getContext();

        nowDate = new Date();
        final String username = getArguments().getString("username");
        final String id = getArguments().getString("id");
        final String docNum = getArguments().getString("docNum");

        ingAdapter = new FestivalItemAdapter(context);
        upcomingAdapter = new FestivalItemAdapter(context);

        FirebaseApp.initializeApp(context);

        db = FirebaseFirestore.getInstance();

        dataQuery();

        ListView listViewIng = rootView.findViewById(R.id.fest_list_ing);
        listViewIng.setAdapter(ingAdapter);
        listViewIng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FestivalItem item = (FestivalItem) ingAdapter.getItem(position);
                //Toast.makeText(context, "selected : " + item.getfName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Festinfo.class);
                intent.putExtra("fName", item.getfName());
                intent.putExtra("id", id);
                intent.putExtra("username", username);
                intent.putExtra("docNum", docNum);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ListView listViewUpcoming = rootView.findViewById(R.id.fest_list_upcoming);
        listViewUpcoming.setAdapter(upcomingAdapter);
        listViewUpcoming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FestivalItem item = (FestivalItem) upcomingAdapter.getItem(position);
                //Toast.makeText(context, "selected : " + item.getfName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Festinfo.class);
                intent.putExtra("fName", item.getfName());
                intent.putExtra("id", id);
                intent.putExtra("username", username);
                intent.putExtra("docNum", docNum);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        return rootView;
    }

    public void dataQuery() {
        // 서버에서 일단 가져운 후에도 서버에서 데이터가 변경되면
        // 폰의 화면에서도 실시간으로 자동 갱신 된다.
        //int LIMIT = 30;
        db.collection("P4_festival")
                .orderBy("fStartDate", Query.Direction.ASCENDING)
                //.limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot value, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            Log.d(TAG, "Listen failed.", e);
                            return;
                        }
                        int i = 0;
                        int j = 0;
                        for(QueryDocumentSnapshot doc : value){

                            if(doc.get("fName") != null){
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
                                try{
                                    startDate = sdf.parse(doc.getString("fStartDate"));

                                    endDate = sdf.parse(doc.getString("fEndDate"));

                                    period = doc.getString("fStartDate") + " ~ " + doc.getString("fEndDate");

                                    if(startDate.before(nowDate) && endDate.after(nowDate)){
                                        if(i > 10){
                                            continue;
                                        } else{
                                            item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                            Log.d(TAG,doc.getString("fName") + " / " + doc.getString("fLocation") + " / " + period + " / " + doc.getString("fImageName"));
                                            ingAdapter.addItem(item);
                                            ingAdapter.notifyDataSetChanged();
                                            i++;
                                        }

                                    } else {
                                        if(j > 10){
                                            continue;
                                        } else {
                                            item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                            Log.d(TAG,doc.getString("fName") + " / " + doc.getString("fLocation") + " / " + period + " / " + doc.getString("fImageName"));
                                            upcomingAdapter.addItem(item);
                                            upcomingAdapter.notifyDataSetChanged();
                                            j++;
                                        }

                                    }
                                } catch (Exception ex){
                                    ex.printStackTrace();
                                }

                            }
                        }

                    }
                });
    }
}
