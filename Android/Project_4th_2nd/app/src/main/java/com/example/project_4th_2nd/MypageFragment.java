package com.example.project_4th_2nd;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MypageFragment extends Fragment {
    private static String TAG = "lecture";
    Context context;
    Button btn_modify;
    Button myReview;
    Button qna;
    Button logout;

    FirebaseFirestore db;
    static MyReviewItem item;
    static Boolean nick_dup = true;
    String selected_option= "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        context = container.getContext();
        final String username = getArguments().getString("username");
        final String id = getArguments().getString("id");
        final String docNum = getArguments().getString("docNum");

        btn_modify = rootView.findViewById(R.id.btn_my_modify);
        myReview = rootView.findViewById(R.id.btn_my_review);
        //qna = rootView.findViewById(R.id.btn_qna);
        logout = rootView.findViewById(R.id.btn_logout);

        db = FirebaseFirestore.getInstance();

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModifyClicked(id, username, docNum);
            }
        });

        myReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyReviewClicked(id);
            }
        });

        /*qna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQnAClicked();
            }
        });*/

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void onModifyClicked(String id, final String nickname, final String docNum) {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.mypage_dialog);
        dialog.setTitle("회원 정보 수정");
        dialog.setCancelable(false);
        final TextView modId = dialog.findViewById(R.id.mod_id);
        modId.setText(id);

        final EditText mod_pw = dialog.findViewById(R.id.mod_pw);
        final EditText mod_pw_ck = dialog.findViewById(R.id.mod_pw_ck);

        final EditText mod_nick = dialog.findViewById(R.id.mod_nickname);
        mod_nick.setText(nickname);
        final Button btn_cancle = dialog.findViewById(R.id.btn_mod_cancel);
        final Button btn_submit_mod = dialog.findViewById(R.id.btn_modify_submit);
        final Button btn_nick_dup_mod = dialog.findViewById(R.id.btn_nick_dup_mod);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_nick_dup_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mod_nick.length() == 0){
                    Toast.makeText(context, "닉네임을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mod_nick.getText().toString().equals("관리자") || mod_nick.getText().toString().equals("admin") || mod_nick.getText().toString().equals("ADMIN")){
                    Toast.makeText(context, "사용하실 수 없는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nickname.equals(mod_nick.getText().toString())){
                    return;
                }
                //Log.d(TAG, "test1 : " + nick_dup);
                nick_dup_ck(mod_nick.getText().toString());

                if(nick_dup == true){
                    return;
                }
            }
        });

        btn_submit_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "중복 결과 " + nick_dup);
                if(nick_dup == true && !(nickname.equals(mod_nick.getText().toString()))){
                    Toast.makeText(context, "닉네임 중복검사를 먼저 해주시기 바랍니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mod_pw.length() == 0){
                    Toast.makeText(context, "비밀번호를 입력해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mod_pw_ck.length() == 0){
                    Toast.makeText(context, "비밀번호를 다시한번 입력해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(mod_pw.getText().toString().equals(mod_pw_ck.getText().toString())){
                    //Log.d(TAG, "여기까지 와야됨 제발");
                    final Map<String, Object> quote = new HashMap<>();
                    quote.put("username", mod_nick.getText().toString());
                    quote.put("pw", mod_pw.getText().toString());

                    db.collection("p4_members")
                            .document(docNum)
                            .update(quote)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    Toast.makeText(context, "회원정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                    mod_nick.setText(quote.get("username").toString());
                                    mod_pw.setText("");
                                    mod_pw_ck.setText("");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Error Writing document!", e);
                                }
                            });



                } else{
                    Toast.makeText(context, "입력된 비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                    mod_pw.setText("");
                    mod_pw_ck.setText("");
                    return;
                }
            }
        });

        dialog.show();
    }

    public void onMyReviewClicked(String id){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.all_reviews);
        dialog.setTitle("회원 정보 수정");
        dialog.setCancelable(false);
        final Button btn_back = dialog.findViewById(R.id.btn_back);
        final ListView myReview_list = dialog.findViewById(R.id.all_review_list);

        final MyReviewAdapter adapter = new MyReviewAdapter(context);

        db.collection("p4_review")
                .whereEqualTo("userId", id)
                .orderBy("date", Query.Direction.DESCENDING)
                //.orderBy("name", Query.Direction.DESCENDING)
                //.limit(LIMIT)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot value, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        adapter.resetItem();
                        if(e != null){
                            Log.d(TAG, "Listen failed.", e);
                            return;
                        }


                        if(value.size() == 0){
                            Toast.makeText(context, "작성된 리뷰가 없습니다.", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        else {
                            for(QueryDocumentSnapshot doc: value){
                                String period = doc.getTimestamp("date").toDate().toString();
                                String[] array = period.split(" ");
                                String conPeroid = array[5] + "/" +  array[1] + "/" + array[2] + " " + array[3];
                                String rateS = doc.getString("rating");
                                float rateF = Float.parseFloat(rateS);

                                item = new MyReviewItem(doc.getId().toString(), doc.getString("fName"), doc.getString("userId"), doc.getString("nickname"), conPeroid, doc.getString("review"), rateF);
                                adapter.addItem(item);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        myReview_list.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // final ReviewItemAdapter allAdapter = new ReviewItemAdapter(dialog.getContext());

        dialog.show();
    }

    public void onQnAClicked() {
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.qna_dialog);
        dialog.setTitle("Q n A");
        dialog.setCancelable(false);

        final Button btn_qna_cancle = dialog.findViewById(R.id.qna_cancel);
        btn_qna_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final Button btn_qna_submit = dialog.findViewById(R.id.qna_submit);

        final EditText qna_content = dialog.findViewById(R.id.qna_content);
        final TextView qna_count = dialog.findViewById(R.id.qna_textCount);
        final EditText qna_email = dialog.findViewById(R.id.qna_email);
        final EditText qna_title = dialog.findViewById(R.id.qna_title);
        final Spinner qna_option = dialog.findViewById(R.id.qna_option);
        final ArrayAdapter option = ArrayAdapter.createFromResource(context, R.array.option, android.R.layout.simple_spinner_item);
        option.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qna_option.setAdapter(option);
        qna_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                optionSelect(option.getItem(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        qna_content.addTextChangedListener(new TextWatcher() {
            final int Max = 300;
            String strcur;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                strcur = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > Max){
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Error");
                    alert.setMessage("QnA는 300자를 초과할 수 없습니다.");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alert.show();

                    qna_content.setText(strcur);
                    qna_content.setSelection(before);
                } else{
                    qna_count.setText(String.valueOf(s.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_qna_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(qna_email.length() == 0) {
                    Toast.makeText(context, "답변받을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selected_option.equals("옵션선택")){
                    Toast.makeText(context, "옵션을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(qna_title.length() == 0){
                    Toast.makeText(context, "제목을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (qna_content.length() == 0 ){
                    Toast.makeText(context, "내용을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "서밋 시도 제목 : " + qna_title.getText().toString() +" 내용 : " + qna_content.getText().toString() + " 옵션 내용 : " + selected_option + " 이메일 : " + qna_email.getText().toString());
            }
        });


        dialog.show();
    }

    public void nick_dup_ck(String nick){

        db.collection("p4_members")
                .whereEqualTo("username", nick)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() == 0){
                                Log.d(TAG, "중복된 닉네임 없음");
                                Toast.makeText(context, "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                nick_dup_noexit();
                            }
                            else {
                                Toast.makeText(context, "중복된 닉네임이 존재합니다. 다시 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            /*for (QueryDocumentSnapshot doc : task.getResult()) {
                                Log.d(TAG, doc.getId() + " => " + doc.getData() + " => " + doc.get("id"));
                            }*/
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void nick_dup_noexit(){
        nick_dup = false;
    }

    public void optionSelect(String o){
        this.selected_option = o;
    }
}
