package com.example.project_4th_2nd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class JoinActivity extends AppCompatActivity {
    private  static final String TAG = "lecture";
    int nMax = 0;



    boolean id_ck_flag;
    boolean username_ck_flag;
    boolean email_ck_flag;

    EditText et_join_id;
    EditText et_join_pw;
    EditText et_join_pw_ck;
    EditText et_join_name;
    EditText et_join_username;
    EditText et_join_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        et_join_id = findViewById(R.id.et_join_id);
        et_join_pw = findViewById(R.id.et_join_pw);
        et_join_pw_ck = findViewById(R.id.et_join_pw_ck);
        et_join_name = findViewById(R.id.et_join_name);
        et_join_username = findViewById(R.id.et_join_username);
        et_join_email = findViewById(R.id.et_join_email);

        id_ck_flag = false;
        username_ck_flag = false;
        email_ck_flag = false;

        dataQuery();
    }

    public void onJoinClicked(View v){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        if(!checkEmpty()){
            return;
        }

        if(checkUnique() == false){
            return;
        }

        if(!et_join_pw.getText().toString().equals(et_join_pw_ck.getText().toString())){
            Toast.makeText(getApplicationContext(), "입력된 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            et_join_pw.setText("");
            et_join_pw_ck.setText("");
            return;
        }


        Map<String, Object> quote = new HashMap<>();
        quote.put("id", et_join_id.getText().toString());
        quote.put("pw", et_join_pw.getText().toString());
        quote.put("name", et_join_name.getText().toString());
        quote.put("username", et_join_username.getText().toString());
        quote.put("email", et_join_email.getText().toString());
        quote.put("block", false);
        quote.put("admin", false);

        String newCount = String.format("%03d", nMax +1 );
        Log.d(TAG, "bbbbbb +" + newCount);

        db.collection("p4_members")
                //.document()
                .document(newCount)
                .set(quote)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(), "회원가입을 축하드립니다!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        intent.putExtra("id", et_join_id.getText().toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error Writing document!", e);
                    }
                });


        return;
    }

    public void onCancleClicked(View v){
        super.onBackPressed();
    }

    public void onIdckClicked(View v) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        if(et_join_id.length() == 0){
            Toast.makeText(getApplicationContext(), "아이디를 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("p4_members")
                .whereEqualTo("id", et_join_id.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() == 0){
                                Log.d(TAG, "중복된 아이디 없음");
                                idck();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "중복된 아이디가 존재합니다. 다시 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                                et_join_id.setText("");
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

    public void idck() {
        Toast.makeText(getApplicationContext(), "사용하실 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
        id_ck_flag = true;
    }

    public void onUsernaemckClicked(View v) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        if(et_join_username.length() == 0){
            Toast.makeText(getApplicationContext(), "닉네임을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(et_join_username.getText().toString().equals("관리자") || et_join_username.getText().toString().equals("admin") || et_join_username.getText().toString().equals("ADMIN")){
            Toast.makeText(getApplicationContext(), "사용하실 수 없는 닉네임입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("p4_members")
                .whereEqualTo("username", et_join_username.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() == 0){
                                Log.d(TAG, "중복된 닉네임 없음");
                                usernameck();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "중복된 닉네임이 존재합니다. 다시 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                                et_join_id.setText("");
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

    public void usernameck() {
        Toast.makeText(getApplicationContext(), "사용하실 수 있는 닉네임입니다.", Toast.LENGTH_SHORT).show();
        username_ck_flag = true;
    }

    public void onEmailckClicked(View v) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        if(et_join_email.length() == 0){
            Toast.makeText(getApplicationContext(), "이메일을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("p4_members")
                .whereEqualTo("email", et_join_email.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() == 0){
                                Log.d(TAG, "중복된 이메일 없음");
                                emailck();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "중복된 이메일이 존재합니다. 다시 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                                et_join_id.setText("");
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

    public void emailck() {
        Toast.makeText(getApplicationContext(), "사용하실 수 있는 이메일입니다.", Toast.LENGTH_SHORT).show();
        email_ck_flag = true;
    }

    protected boolean checkEmpty() {
        if(et_join_id.length() == 0){
            Toast.makeText(getApplicationContext(), "아이디를 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_join_pw.length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_join_pw_ck.length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 다시한번 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_join_name.length() == 0){
            Toast.makeText(getApplicationContext(), "이름을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_join_username.length() == 0){
            Toast.makeText(getApplicationContext(), "닉네임을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(et_join_email.length() == 0){
            Toast.makeText(getApplicationContext(), "이메일을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    protected boolean checkUnique(){

        //Log.d(TAG,"aaaa + "+id_ck_flag );
        if(id_ck_flag == false){
            Toast.makeText(getApplicationContext(), "아이디 중복확인을 해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Log.d(TAG,"bbbbb + "+username_ck_flag );
        if(username_ck_flag == false){
            Toast.makeText(getApplicationContext(), "닉네임 중복확인을 해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Log.d(TAG,"cccccc + "+email_ck_flag );
        if(email_ck_flag == false){
            Toast.makeText(getApplicationContext(), "이메일 중복확인을 해 주십시오.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void dataQuery() {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();


        // 서버에서 일단 가져운 후에도 서버에서 데이터가 변경되면
        // 폰의 화면에서도 실시간으로 자동 갱신 된다.
        db.collection("p4_members")
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
                            if(doc.get("id") != null){
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
}
