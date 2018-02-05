package com.orbitdental.selfiebooth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.orbitdental.selfiebooth.cameraAPI.Camera2BasicFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dpk on 1/28/18.
 */

public class ShareActivity extends Activity{
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private FrameLayout imageFrame;
    private EditText mobileNumber;
    private EditText emailId;
    private Button sendShare;
    private Button goBack;
    private static final String FILE_NAME ="FNAME";
    private File mFile;
    private Bitmap bitmap;
    private static  String TAG = "ShareActivity";
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_share);
        imageView = (ImageView) findViewById(R.id.imageView);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        mobileNumber = (EditText) findViewById(R.id.number);
        emailId  = (EditText)findViewById(R.id.mail);
        sendShare = (Button) findViewById(R.id.button);
        goBack = (Button) findViewById(R.id.button2);
        imageFrame = (FrameLayout) findViewById(R.id.imageFrame);
        if(getIntent().hasExtra(FILE_NAME)){
            mFile = new File(getIntent().getStringExtra(FILE_NAME));
        }
        if(mFile.exists()) {
            Bitmap myImg = BitmapFactory.decodeFile(mFile.getPath());
            imageView.setImageBitmap(myImg);
        }
        sendShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFile.exists()){
                    new S3Upload().execute(mFile);
                }
                uploadData();
                finish();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
    public static void saveFrameLayout(FrameLayout frameLayout, final String path) {
        frameLayout.setDrawingCacheEnabled(true);
        frameLayout.buildDrawingCache();
        final Bitmap cache = frameLayout.getDrawingCache();
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(path);
                    cache.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        };
        thread1.start();
        frameLayout.destroyDrawingCache();
    }

    @Override
    public void onStart(){
        super.onStart();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    bitmap = viewToBitmap(imageFrame);
                    try {
                        FileOutputStream output = new FileOutputStream(mFile.getPath());
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    Log.d(TAG, e.getMessage());
                }
                // After sleep finished blocking, create a Runnable to run on the UI Thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                });

            }

        };
        thread.start();

    }

    public void uploadData(){

        final UserUploadsDO userData = new UserUploadsDO();
        userData.setFileid(mFile.getName());
        String number = "+1" + mobileNumber.getText().toString();
        userData.setMobile(Double.parseDouble(number));
        userData.setEmail(emailId.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Util.getDynamoDBMapper().save(userData);
                Log.d(TAG, "ITEM SAVED");
                System.out.println("ITEM SAVED");
                // Item saved
            }
        }).start();

    }


}
