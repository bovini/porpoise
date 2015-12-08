package com.example.genet42.porpoise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    /**
     * WiPortのIPアドレスの規定値
     */
    private static final String ADDR_WIPORT = "192.168.64.208";

    /**
     * WiPortのCPの状態設定用ポート番号の規定値
     */
    private static final int PORT_WIPORT_CP = 30704;

    /**
     * 音声受信ポート番号の規定値
     */
    private static final int PORT_LOCAL_SOUND = 65432;

    /**
     * The specified timeout, in milliseconds.
     * The timeout must be > 0. A timeout of zero is interpreted as an infinite timeout.
     */
    private static final int TIMEOUT_CONNECTION = 500;

    /**
     * true だとTCPで通信する．
     */
    private static final boolean FORCE_TCP = true;

    /**
     * 受信する音声のサンプルレート (in Hz)
     */
    public static final int SAMPLE_RATE_IN_HZ = 8000;

    /**
     * 音声を再生するときのバッファ時間 (in milliseconds)
     */
    public static final int TIME_BUFFERING = 5000;

    /**
     * 非同期版 制御指示システム
     */
    private AsyncControlSystem asyncControlSystem;

    /**
     * 音声受信システム
     */
    private SoundSystem soundSystem;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InetAddress host = null;
        try {
            host = InetAddress.getByName(ADDR_WIPORT);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
        asyncControlSystem = new AsyncControlSystem(host, PORT_WIPORT_CP, FORCE_TCP);
        asyncControlSystem.setTimeout(TIMEOUT_CONNECTION);
        asyncControlSystem.setInstructionListener(new AsyncControlSystem.InstructionListener() {
            @Override
            public void onCompletion() {
                setTitle("Porpoise");
                Log.i("Instruction", "Completed");
            }

            @Override
            public void onError() {
                setTitle("Porpoise [Connection Error]");
                Toast.makeText(getApplicationContext(), "Instruction failed", Toast.LENGTH_SHORT).show();
                Log.i("Instruction", "Failed");
            }
        });

        soundSystem = new SoundSystem(PORT_LOCAL_SOUND, SAMPLE_RATE_IN_HZ, TIME_BUFFERING, FORCE_TCP);
        soundSystem.startPlaying();

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

        final Button startRecording = (Button) findViewById(R.id.startRecording);
        final Button stopRecording = (Button) findViewById(R.id.stopRecording);
        startRecording.setOnTouchListener(new ButtonListener() {
            @Override
            protected void onTouchDown() {
                soundSystem.startRecoding(getApplicationContext());
                stopRecording.setVisibility(View.VISIBLE);
                startRecording.setVisibility(View.INVISIBLE);
                Log.i("OnTouch", "Start Recording Down");
            }

            @Override
            protected void onTouchUp() {
                Log.i("OnTouch", "Start Recoding Up");
            }
        });
        stopRecording.setOnTouchListener(new ButtonListener() {
            @Override
            protected void onTouchDown() {
                soundSystem.stopRecoding();
                stopRecording.setVisibility(View.INVISIBLE);
                startRecording.setVisibility(View.VISIBLE);
                Log.i("OnTouch", "Stop Recording Down");
            }

            @Override
            protected void onTouchUp() {
                Log.i("OnTouch", "Stop Recoding Up");
            }
        });
    }
}
