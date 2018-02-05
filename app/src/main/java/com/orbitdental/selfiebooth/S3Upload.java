package com.orbitdental.selfiebooth;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;

/**
 * Created by dpk on 1/23/18.
 */

public class S3Upload  extends AsyncTask<File, Integer, Boolean>{
    private static String TAG = "S3UPLOAD";
    @Override
    protected Boolean doInBackground(File... files) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File mFile = files[0];
        Log.d("Upload", "path: "+ mFile.getPath() + "\n" + "filename: " + mFile.getName());
        TransferUtility transferUtility = Util.transferUtility;
        TransferObserver uploadObserver =
                transferUtility.upload("uploads/" +mFile.getName() , new File(mFile.getPath()));

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    Log.d(TAG, "UPLOAD SUCCESSFUL" + state.toString());
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("Upload", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d(TAG, "UPLOAD FAILED" + ex);
            }

        });

        // If your upload does not trigger the onStateChanged method inside your
        // TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            Log.d(TAG, "UPLOAD SUCCESSFUL");
            return true;
        }
        return false;
    }
    @Override
    protected void onPostExecute(Boolean state){
        if (state){

        }
    }
}
