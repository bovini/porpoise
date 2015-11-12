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
    private static final int ID_NULL = -1;
    private static final String ADDR_DEFAULT = "192.168.64.208";
    private static final int PORT_DEFAULT = 30704;
    private int idTouching = ID_NULL;
    private ControlSystem controlSystem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        try {
            controlSystem = new ControlSystem(InetAddress.getByName(ADDR_DEFAULT), PORT_DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
            this.finish();
        }
        Button forward = (Button) findViewById(R.id.forward);
        forward.setOnTouchListener(new View.OnTouchListener() {
            // ボタンがタッチされた時のハンドラ
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (idTouching == ID_NULL) {
                        idTouching = R.id.forward;
                        // 前進ボタンに指がタッチした時の処理を記述
                        Log.v("OnTouch", "Fwd Touch Down");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (idTouching == R.id.forward) {
                        idTouching = ID_NULL;
                        // タッチした指が離れた時の処理を記述
                        Log.v("OnTouch", "Fwd Touch Up");
                    }
                }
                return false;
            }
        });
        Button backward = (Button) findViewById(R.id.backward);
        backward.setOnTouchListener(new View.OnTouchListener() {
            // ボタンがタッチされた時のハンドラ
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (idTouching == ID_NULL) {
                        idTouching = R.id.backward;

                        // 指がタッチした時の処理を記述

                        Log.v("OnTouch", "Bwd Touch Down");
                    }

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (idTouching == R.id.backward) {
                        idTouching = ID_NULL;
                        // タッチした指が離れた時の処理を記述
                        Log.v("OnTouch", "Bwd Touch Up");
                    }

                }
                return false;
            }
        });
        Button rotationLeft = (Button) findViewById(R.id.rotationLeft);
        rotationLeft.setOnTouchListener(new View.OnTouchListener() {
            // ボタンがタッチされた時のハンドラ
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (idTouching == ID_NULL) {
                        idTouching = R.id.rotationLeft;
                        // 指がタッチした時の処理を記述

                        Log.v("OnTouch", "RLft Touch Down");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (idTouching == R.id.rotationLeft) {
                        idTouching = ID_NULL;
                        // タッチした指が離れた時の処理を記述
                        Log.v("OnTouch", "RLft Touch Up");
                    }

                }
                return false;
            }
        });
        Button rotationRight = (Button) findViewById(R.id.rotationRight);
        rotationRight.setOnTouchListener(new View.OnTouchListener() {
            // ボタンがタッチされた時のハンドラ
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (idTouching == ID_NULL) {
                        idTouching = R.id.rotationRight;
                        // 指がタッチした時の処理を記述

                        Log.v("OnTouch", "RRit Touch Down");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (idTouching == R.id.rotationRight) {
                        idTouching = ID_NULL;
                        // タッチした指が離れた時の処理を記述
                        Log.v("OnTouch", "RRit Touch Up");
                    }

                }
                return false;
            }
        });
        Button led = (Button) findViewById(R.id.led);
        led.setOnTouchListener(new View.OnTouchListener() {
            // ボタンがタッチされた時のハンドラ
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (idTouching == ID_NULL) {
                        idTouching = R.id.led;
                        // 指がタッチした時の処理を記述

                        Log.v("OnTouch", "LED Touch Down");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (idTouching == R.id.led) {
                        idTouching = ID_NULL;
                        // タッチした指が離れた時の処理を記述
                        Log.v("OnTouch", "LED Touch Up");
                    }

                }
                return false;
            }
        });

    }


}
