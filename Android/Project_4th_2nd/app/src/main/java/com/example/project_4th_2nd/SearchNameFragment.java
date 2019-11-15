package com.example.project_4th_2nd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SearchNameFragment extends Fragment {
    private static String TAG = "lecture";
    ImageView content_search_img;
    Context context;

    LinearLayout festlist;
    ListView lv_search;
    Date startDate;
    Date endDate;
    Date nowDate;
    String period = "";
    String username;
    String id;
    String docNum;

    FestivalItemAdapter adapter;


    static FestivalItem item;

    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search_name, container, false);
        context = rootView.getContext();
        username = getArguments().getString("username");
        id = getArguments().getString("id");
        docNum = getArguments().getString("docNum");

        FirebaseApp.initializeApp(context);

        db = FirebaseFirestore.getInstance();
        adapter = new FestivalItemAdapter(context);
        final EditText et_search = rootView.findViewById(R.id.et_search);
        final Button btn_search = rootView.findViewById(R.id.btn_search);
        content_search_img = rootView.findViewById(R.id.search_imgView);
        lv_search = rootView.findViewById(R.id.content_list);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataQuery(et_search.getText().toString());
                listViewUpdate();
            }
        });

        return rootView;
    }

    public void dataQuery(final String search) {
        adapter.resetItem();
        // 서버에서 일단 가져운 후에도 서버에서 데이터가 변경되면
        // 폰의 화면에서도 실시간으로 자동 갱신 된다.
        //int LIMIT = 30;
        db.collection("P4_festival")
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
                                if(doc.get("fName").toString().contains(search) == true){
                                    lvOn();
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

                                        item = new FestivalItem(doc.getString("fName"), doc.getString("fLocation"), period, doc.getString("fImageName"));
                                        adapter.addItem(item);
                                        adapter.notifyDataSetChanged();

                                    } catch (Exception ex){
                                        ex.printStackTrace();
                                    }
                                }
                                //view.setfPeriod();
                            }
                        }
                        if(adapter.hasItem() == false){
                            lvOff();
                        }

                        //tvContents.setText(sb.toString());
                    }
                });
    }

    public void listViewUpdate() {

        lv_search.setAdapter(adapter);
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                FestivalItem item = (FestivalItem) adapter.getItem(position);
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

    public void lvOn() {
        content_search_img.setVisibility(View.GONE);
        lv_search.setVisibility(View.VISIBLE);
    }

    public void lvOff() {
        content_search_img.setImageResource(R.drawable.search_not_find_img);
        content_search_img.setVisibility(View.VISIBLE);
        lv_search.setVisibility(View.GONE);
    }
}
