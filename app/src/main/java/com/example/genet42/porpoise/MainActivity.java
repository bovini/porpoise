package com.example.genet42.porpoise;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    /**
     * WiPortのIPアドレスの規定値
     */
    private static final String ADDR_DEFAULT = "192.168.64.208";

    /**
     * WiPortのCPの状態設定用ポート番号の規定値
     */
    private static final int PORT_DEFAULT = 30704;

    /**
     * 非同期版 制御指示システム
     */
    private AsyncControlSystem asyncControlSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InetAddress host = null;
        try {
            host = InetAddress.getByName(ADDR_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        AsyncControlSystem.OnErrorListener onErrorListener = new AsyncControlSystem.OnErrorListener() {
            @Override
            public void onError() {

            }
        };
        asyncControlSystem = new AsyncControlSystem(host, PORT_DEFAULT, onErrorListener);

        Button forward = (Button) findViewById(R.id.forward);
        forward.setOnTouchListener(new BehavioralButtonListener(R.id.forward) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructForward();
                Log.v("OnTouch", "Fwd Touch Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.v("OnTouch", "Fwd Touch Up");
            }
        });

        Button backward = (Button) findViewById(R.id.backward);
        backward.setOnTouchListener(new BehavioralButtonListener(R.id.backward) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructBackward();
                Log.v("OnTouch", "Bwd Touch Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.v("OnTouch", "Bwd Touch Up");
            }
        });

        Button rotationLeft = (Button) findViewById(R.id.rotationLeft);
        rotationLeft.setOnTouchListener(new BehavioralButtonListener(R.id.rotationLeft) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructRotationLeft();
                Log.v("OnTouch", "RLft Touch Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.v("OnTouch", "RLft Touch Up");
            }
        });

        Button rotationRight = (Button) findViewById(R.id.rotationRight);
        rotationRight.setOnTouchListener(new BehavioralButtonListener(R.id.rotationRight) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructRotationRight();
                Log.v("OnTouch", "RRit Touch Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.v("OnTouch", "RRit Touch Up");
            }
        });

        Button led = (Button) findViewById(R.id.led);
        led.setOnTouchListener(new ButtonListener() {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructLEDOn();
                Log.v("OnTouch", "LED Touch Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.instructLEDOff();
                Log.v("OnTouch", "LED Touch Up");
            }
        });

        Button testLED = (Button) findViewById(R.id.testLED);
        testLED.setOnTouchListener(new ButtonListener() {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructTestLEDOn();
                Log.v("OnTouch", "TestLED Touch Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.instructTestLEDOff();
                Log.v("OnTouch", "TestLED Touch Up");
            }
        });
    }
}
