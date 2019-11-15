package com.example.project_4th_2nd;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyReviewAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<MyReviewItem> items = new ArrayList<>();

    FirebaseFirestore db;
    float review_rate = 0;

    public MyReviewAdapter(Context context) {
        this.context = context;
    }

    public void addItem(MyReviewItem item){
        //Log.d(TAG, "add item 실행 id : " + item.getUserId());
        items.add(item);
        //Log.d(TAG, "cccc + " + items.size());
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void resetItem() { items.clear(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = FirebaseFirestore.getInstance();
        MyReviewItemView view = null;

        final MyReviewItem item = items.get(position);

        if(convertView == null){
            view = new MyReviewItemView(context);
        } else {
            view = (MyReviewItemView) convertView;
        }

        view.setfName(item.getfName());
        view.setUsername(item.getUserId());
        view.setDate(item.getDate());
        view.setReview(item.getReview());
        view.setRating(item.getRating());

        Button btn_review_mod = view.findViewById(R.id.btn_review_mod);
        btn_review_mod.setFocusable(false);
        btn_review_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod_review(item.getId(), item.getReview(), item.getfName(), item.getRating());
            }
        });

        Button btn_review_del = view.findViewById(R.id.btn_review_del);
        btn_review_del.setFocusable(false);
        btn_review_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_review(item.getId());
            }
        });

        return view;
    }

    public void mod_review(String reviewNum, String content, String fName, float myRate){
        Log.d(TAG, "수정 버튼 클릭 : " + reviewNum);
        final String freviewNum = reviewNum;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.review_modify);
        dialog.setTitle("리뷰 수정");
        dialog.setCancelable(false);

        final EditText review_mod_con = dialog.findViewById(R.id.rv_mod_content);
        review_mod_con.setText(content);

        final TextView mod_rv_textCount = dialog.findViewById(R.id.mod_rv_textCount);
        mod_rv_textCount.setText(String.valueOf(content.length()));

        review_mod_con.addTextChangedListener(new TextWatcher() {
            final int Max = 100;
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
                    alert.setMessage("리뷰는 100자를 초과할 수 없습니다.");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alert.show();


                    review_mod_con.setText(strcur);
                    review_mod_con.setSelection(before);
                } else{
                    mod_rv_textCount.setText(String.valueOf(s.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final TextView myrv_fName = dialog.findViewById(R.id.mod_myrv_fName);
        myrv_fName.setText(fName);

        final RatingBar mod_my_rate = dialog.findViewById(R.id.mod_my_rate);
        mod_my_rate.setRating(myRate);
        mod_my_rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setRate(rating);
            }
        });

        final Button btn_mod_cancel = dialog.findViewById(R.id.btn_modify_canel);
        btn_mod_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "수정을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        final Button btn_mod_submit = dialog.findViewById(R.id.btn_review_modify);
        btn_mod_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(review_mod_con.length() == 0){
                    Toast.makeText(context, "리뷰 내용을 입력해 주십시오.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String review_rateS = String.valueOf(review_rate);
                Date now = new Date();
                Timestamp nowT = new Timestamp(now.getTime());

                Map<String, Object> quote = new HashMap<>();
                quote.put("review", review_mod_con.getText().toString());
                quote.put("rating", review_rateS);
                quote.put("date", nowT);

                db.collection("p4_review")
                        .document(freviewNum)
                        .update(quote)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                Toast.makeText(context, "리뷰가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Error Writing document!", e);
                            }
                        });
            }
        });

        dialog.show();
    }

    public void del_review(String reviewNum){
        Log.d(TAG, "삭제 버튼 클릭 : " + reviewNum);
        db.collection("MyFireStoreDB")
                //.document()
                .document(reviewNum)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Toast.makeText(context, "리뷰가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error deleting document!", e);
                    }
                });
    }

    public void setRate(float rate){
        this.review_rate = rate;
    }
}
