package com.example.kabdelbaki.nfcstatelogger;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.SimpleDateFormat;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class NfcStateHandler extends BroadcastReceiver {

    private IntentFilter filter;
    private BroadcastReceiver nfcLBroadcastListener;
    private NfcAdapter nfcAdapter;
    private static final String TAG = "NfcState";
    private ArrayList<String> nfcStateChangeMessages;


    /**
     * NfcHandler Constructor, contains setters for the instance variables
     */
    public NfcStateHandler(Context context) {
        this.filter = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
        NfcManager nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        this.nfcAdapter = nfcManager.getDefaultAdapter();
        this.nfcLBroadcastListener = broadcastReceiverListener();
        this.nfcStateChangeMessages = new ArrayList<>();

    }

    /*
    Ignore this method, it is a a way to hack the parent class into thinking that method onReceive is being implemented.
    The receiver object passed back to the activity through the broadcastReceiverListener method
     */
    @Override
    public void onReceive(Context context, Intent intent) {


    }

    /**
     * Method to create a broadcast receiver which listens to NFC changes and logs appropriately
     *
     * @return
     */
    public BroadcastReceiver broadcastReceiverListener() {


        BroadcastReceiver nfcListener = new BroadcastReceiver() {

            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();


                if (action.equals(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)) {
                    final int state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE,
                            NfcAdapter.STATE_OFF);
                    switch (state) {
                        case NfcAdapter.STATE_OFF:
                            nfcStateChangeMessages.add(timeStampFormatter() + " " + MainActivity.storeNfcStateChange("NFC is off"));
                            Log.v(TAG, "NFC is off");
                            break;
                        case NfcAdapter.STATE_TURNING_OFF:

                            nfcStateChangeMessages.add(timeStampFormatter() + " " +MainActivity.storeNfcStateChange("NFC is turning off"));
                            Log.v(TAG, "NFC is turning off");
                            break;
                        case NfcAdapter.STATE_ON:

                            nfcStateChangeMessages.add(timeStampFormatter() + " " +MainActivity.storeNfcStateChange("NFC is on"));
                            Log.v(TAG, "NFC is on");
                            break;
                        case NfcAdapter.STATE_TURNING_ON:

                            nfcStateChangeMessages.add(timeStampFormatter() + " " +MainActivity.storeNfcStateChange("NFC is turning on"));
                            Log.v(TAG, "NFC is turning on");
                            break;
                    }
                } else if (nfcAdapter == null) {
                    Log.v(TAG, "This device may not have NFC capabilities");

                }
            }
        };
        return nfcListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String timeStampFormatter() {

        String timeStamp = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy").format(new Date());


        return timeStamp + " ";

    }


    public ArrayList<String> getNfcStateChangeMessages() {
        return this.nfcStateChangeMessages;

    }


    public BroadcastReceiver getNfcBroadcastListener() {
        return this.nfcLBroadcastListener;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public IntentFilter getFilter() {
        return this.filter;
    }
}
