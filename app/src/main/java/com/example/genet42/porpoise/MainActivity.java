package com.example.genet42.porpoise;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
     * The specified timeout, in milliseconds.
     * The timeout must be > 0. A timeout of zero is interpreted as an infinite timeout.
     */
    private static final int TIMEOUT = 500;

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
        AsyncControlSystem.InstructionListener listener = new AsyncControlSystem.InstructionListener() {
            @Override
            public void onCompletion() {
                setTitle("Porpoise");
                Log.i("Instruction", "Completed");
            }

            @Override
            public void onError() {
                setTitle("Porpoise [Connection Error]");
                Toast.makeText(getApplicationContext(), "Instruction(s) failed", Toast.LENGTH_SHORT).show();
                Log.i("Instruction", "Failed");
            }
        };
        asyncControlSystem = new AsyncControlSystem(host, PORT_DEFAULT, true);
        asyncControlSystem.setInstructionListener(listener);
        asyncControlSystem.setTimeout(TIMEOUT);

        new UDPClient(65432).start();

        Button forward = (Button) findViewById(R.id.forward);
        forward.setOnTouchListener(new BehavioralButtonListener(R.id.forward) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructForward();
                Log.i("OnTouch", "Fwd Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.i("OnTouch", "Fwd Up");
            }
        });

        Button backward = (Button) findViewById(R.id.backward);
        backward.setOnTouchListener(new BehavioralButtonListener(R.id.backward) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructBackward();
                Log.i("OnTouch", "Bwd Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.i("OnTouch", "Bwd Up");
            }
        });

        Button rotationLeft = (Button) findViewById(R.id.rotationLeft);
        rotationLeft.setOnTouchListener(new BehavioralButtonListener(R.id.rotationLeft) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructRotationLeft();
                Log.i("OnTouch", "RLft Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.i("OnTouch", "RLft Up");
            }
        });

        Button rotationRight = (Button) findViewById(R.id.rotationRight);
        rotationRight.setOnTouchListener(new BehavioralButtonListener(R.id.rotationRight) {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructRotationRight();
                Log.i("OnTouch", "RRit Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.stopOperation();
                Log.i("OnTouch", "RRit Up");
            }
        });

        Button led = (Button) findViewById(R.id.led);
        led.setOnTouchListener(new ButtonListener() {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructLEDOn();
                Log.i("OnTouch", "LED Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.instructLEDOff();
                Log.i("OnTouch", "LED Up");
            }
        });

        Button testLED = (Button) findViewById(R.id.testLED);
        testLED.setOnTouchListener(new ButtonListener() {
            @Override
            protected void onTouchDown() {
                asyncControlSystem.instructTestLEDOn();
                Log.i("OnTouch", "TestLED Down");
            }

            @Override
            protected void onTouchUp() {
                asyncControlSystem.instructTestLEDOff();
                Log.i("OnTouch", "TestLED Up");
            }
        });
    }
}
