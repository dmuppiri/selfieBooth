package com.orbitdental.selfiebooth;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.orbitdental.selfiebooth.cameraAPI.Camera2BasicFragment;
import java.io.File;


public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Initializing AWSMobileClient*/
        AWSMobileClient.getInstance().initialize(this).execute();
        Util.setTransferUtility(this);
        setContentView(R.layout.activity_main);
        if (null == savedInstanceState){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }

    }
    public void onResume() {
        super.onResume();

    }

}
