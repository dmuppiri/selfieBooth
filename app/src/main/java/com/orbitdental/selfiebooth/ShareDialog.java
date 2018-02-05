package com.orbitdental.selfiebooth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by dpk on 1/28/18.
 */

public class ShareDialog extends DialogFragment {
    public ShareDialog(){}
    private EditText mobile_number;
    private EditText email;
    private Button sendButton;


    public interface ShareDialogListener{
        public void onDialogSendClick(DialogFragment dialogFragment);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.share_dialog, null));
        mobile_number = (EditText) getView().findViewById(R.id.number);
        return builder.create();
    }
}
