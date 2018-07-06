package com.example.kabdelbaki.nfcstatelogger;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG;
    private NfcStateHandler nfcStateHandler;
    private ArrayList<String> nfcLogArrayList;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nfcStateHandler = new NfcStateHandler(this);
        registerReceiver(this.nfcStateHandler.getNfcBroadcastListener(), nfcStateHandler.getFilter());
        this.setNfcLogArrayList(nfcStateHandler);
//        this.nfcStateChangeUi(this.nfcLogArrayList);

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nfcStateChangeUi(nfcLogArrayList);
            }
        });

    }

    @Override
    protected void onResume() {

        this.nfcStateChangeUi(this.nfcLogArrayList);
        super.onResume();
    }


    public TextView nfcStateChangeUi(ArrayList<String> nfcStateChangeList) {


        TextView textView = (TextView) findViewById(R.id.logPanel);
        textView.setText("");
        for (int i = 0; i < nfcStateChangeList.size(); i++) {
            textView.append(nfcStateChangeList.get(i) + "\n");
        }
        textView.setMovementMethod(new ScrollingMovementMethod());

        return textView;
    }

    public void setNfcLogArrayList(NfcStateHandler nfcStateChangeList) {
        this.nfcLogArrayList = nfcStateChangeList.getNfcStateChangeMessages();
    }

    @Override
    public void onDestroy() {

        this.unregisterReceiver(this.nfcStateHandler);
        super.onDestroy();
    }


    public static String storeNfcStateChange(String nfcState) {
        return nfcState;
    }
}







