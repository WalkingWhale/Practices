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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class LoginActivity extends AppCompatActivity {
    private  static final String TAG = "lecture";

    FirebaseFirestore db;

    String username;

    EditText et_id;
    EditText et_pw;

    static Boolean id_ck_flag;
    static Boolean pw_ck_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String id = getIntent().getStringExtra("id");
        username = "";

        //Log.d(TAG, "aaaa + " + id);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);

        db = FirebaseFirestore.getInstance();

        if(id != null){
            et_id.setText(id);
        }

    }

    public void onLoginClicked(View v){
        final String input_id = et_id.getText().toString();
        final String input_pw = et_pw.getText().toString();

        /*db.collection("p4_members")
                .whereEqualTo("id",input_id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {

                        if(e != null){
                            Log.d(TAG, "Listen failed.", e);
                            return;
                        }

                        if(value.size() == 0){
                            //Log.d(TAG, "아이디 존재 x");
                            Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            et_id.setText("");
                            et_pw.setText("");
                            return;
                        }

                        for(QueryDocumentSnapshot doc: value){
                            if(doc.getBoolean("block") == true){
                                //Log.d(TAG, "블락계정");
                                Toast.makeText(getApplicationContext(), "블락된 계정입니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(doc.get("pw").toString().equals(input_pw)) {
                                //Log.d(TAG, "bbbb  id : " + doc.get("id") + " /  id check : " + input_id);
                                //Log.d(TAG, "ccccc  pw : " + doc.get("pw") + " /  pw check : " + input_pw);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("validMem", "yes");
                                intent.putExtra("docNum",doc.getId().toString());
                                intent.putExtra("id", et_id.getText().toString());
                                intent.putExtra("username", doc.get("username").toString());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "환영합니다 " + et_id.getText().toString() + "회원님", Toast.LENGTH_SHORT).show();

                            } else{
                                //Log.d(TAG, "비밀번호 x");
                                Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                    }
                });*/
        db.collection("p4_members")
                .whereEqualTo("id",input_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size() == 0){
                                //Log.d(TAG, "아이디 존재 x");
                                Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                et_id.setText("");
                                et_pw.setText("");
                                return;

                            }
                            else {
                                for(QueryDocumentSnapshot doc: task.getResult()){
                                    if(doc.getBoolean("block") == true){
                                        //Log.d(TAG, "블락계정");
                                        Toast.makeText(getApplicationContext(), "블락된 계정입니다", Toast.LENGTH_SHORT).show();
                                        et_pw.setText("");
                                        return;
                                    }
                                    if(doc.get("pw").toString().equals(input_pw)) {
                                        //Log.d(TAG, "bbbb  id : " + doc.get("id") + " /  id check : " + input_id);
                                        //Log.d(TAG, "ccccc  pw : " + doc.get("pw") + " /  pw check : " + input_pw);

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("validMem", "yes");
                                        intent.putExtra("docNum",doc.getId().toString());
                                        intent.putExtra("id", et_id.getText().toString());
                                        intent.putExtra("username", doc.get("username").toString());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "환영합니다 " + et_id.getText().toString() + "회원님", Toast.LENGTH_SHORT).show();

                                    } else{
                                        //Log.d(TAG, "비밀번호 x");
                                        Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
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


    public void onJoinClicked(View v){
        Intent intent = new Intent(LoginActivity.this , JoinActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
