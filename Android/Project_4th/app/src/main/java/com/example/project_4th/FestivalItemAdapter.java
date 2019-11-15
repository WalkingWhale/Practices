package com.example.project_4th;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nullable;

public class FestivalItemAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<FestivalItem> items = new ArrayList<>();
    Bitmap bitmapImage;

    FestivalItem item;

    FirebaseFirestore db;
    ImageView festImg;
    private StorageReference mStorageRef;

    public FestivalItemAdapter(Context context) {
        this.context = context;
    }

    public void addItem(FestivalItem item){
        items.add(item);
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

    public View getView(int position, View convertView, ViewGroup parent) {

        db = FirebaseFirestore.getInstance();
        FestivalItemView view = null;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        item = items.get(position);

        if(convertView == null){
            view = new FestivalItemView(context, item.getfImgName());
        } else {
            view = (FestivalItemView) convertView;
        }

        //dataQuery(view);

        view.setfName(item.getfName());
        //Log.d(TAG, "aaa - " + item.getfName());
        view.setfPeriod(item.getfPeriod());
        //Log.d(TAG, "bbb - " + item.getfPeriod());
        view.setfLocation(item.getfLocation());
        //Log.d(TAG, "cc - " + item.getfLocation());
        //view.setFestImg();
        //downloadImage(fImgName);
        //printImageList();
        //view.setFestImg(downloadImage(item.getfImgName()));
        //Log.d(TAG, Integer.toString(downloadImage(item.getfImgName()).describeContents()));
        return view;
    }



    /*public Bitmap downloadImage(String imgName) {

        String folderName = "images";
        //String imageName = String.format("%s.png", imgName);
        //Log.d(TAG, "aaaaaaa - " + imgName);
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
                            //imageView.setImageBitmap(bitmapImage);
                            //Toast.makeText(context.getApplicationContext(), "Success !!", Toast.LENGTH_LONG).show();
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

        return bitmapImage;
    }

    public void printImageList() {
        // 별도의 메서드가 없다.
        // 리얼타임 데이터베이스에 저장하고 불러오는 방법이 있다.
    }*/

}
