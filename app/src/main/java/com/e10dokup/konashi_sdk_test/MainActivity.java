package com.e10dokup.konashi_sdk_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.uxxu.konashi.lib.Konashi;
import com.uxxu.konashi.lib.KonashiObserver;


public class MainActivity extends Activity {

    private static final String TAG = "konashi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findButton = (Button)findViewById(R.id.find);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Konashi.getManager().find(MainActivity.this);
            }
        });

        Konashi.initialize(getApplicationContext());
        Konashi.getManager().addObserver(mKonashiObserver);

    }

    @Override
    protected void onDestroy() {
        Konashi.close();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Konashi.getManager()!=null){
            Konashi.getManager().disconnect();
        }
    }

    private final KonashiObserver mKonashiObserver = new KonashiObserver(MainActivity.this) {
        @Override
        public void onReady(){
            Log.d(TAG, "onKonashiReady");

            Konashi.getManager().pwmMode(Konashi.LED2, Konashi.PWM_ENABLE_LED_MODE);

            Intent intent = new Intent(MainActivity.this, ControllerActivity.class);
            startActivity(intent);
        }
    };

}
