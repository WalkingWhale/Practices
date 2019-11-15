package com.example.project_4th;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class FestivalItemView extends LinearLayout {

    FirebaseFirestore db;

    private StorageReference mStorageRef;
    Bitmap bitmapImage;

    TextView tv_festName;
    TextView tv_period;
    TextView tv_fLocation;
    ImageView festImg;

    public FestivalItemView(Context context, String festImgName){

        super(context);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fest_listview, this, true);

        tv_festName = findViewById(R.id.tv_festName);
        tv_period = findViewById(R.id.tv_period);
        tv_fLocation = findViewById(R.id.tv_fLocation);
        festImg = findViewById(R.id.festImg);
        //Log.d("lecture", "aaa123 = " + festImgName);
        printImageList();
        downloadImage(festImgName);
    }

    public void setfName(String fname){
        tv_festName.setText(fname);
    }

    public void setfPeriod(String period) {
        tv_period.setText(period);
    }

    public void setfLocation(String location){
        tv_fLocation.setText(location);
    }

    public void setFestImg(Bitmap fImg){festImg.setImageBitmap(fImg);}

    public void downloadImage(String imgName) {

        String folderName = "images";
        //String imageName = String.format("%s.png", imgName);
        //Log.d("lecture", "aaaaaaa - " + imgName);
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
                            //Log.d("lecture", "bbbbbbbbbb : " + Integer.toString(bitmapImage.describeContents()));
                            festImg.setImageBitmap(bitmapImage);
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
    }

    public void printImageList() {
        // 별도의 메서드가 없다.
        // 리얼타임 데이터베이스에 저장하고 불러오는 방법이 있다.
    }
}
