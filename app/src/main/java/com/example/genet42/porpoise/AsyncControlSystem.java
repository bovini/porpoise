package com.example.genet42.porpoise;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 非同期版 制御指示システム
 * Created by genet42 on 2015/11/12.
 */
public class AsyncControlSystem {

    /**
     * 制御指示システム
     */
    private ControlSystem controlSystem;

    /**
     * 何かあったときに Activity#finish() するためのアクティビティ
     */
    private Activity activity;

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     */
    public AsyncControlSystem(final InetAddress address, final int port, final Activity activity) {
        this.activity = activity;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem = new ControlSystem(address, port);
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 聴音機の前進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructForward() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem.instructForward();
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 聴音機の後進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructBackward() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem.instructBackward();
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 聴音機の右回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationRight() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem.instructRotationRight();
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 聴音機の左回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationLeft() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem.instructRotationLeft();
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     */
    public void instructLEDOn() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem.instructLEDOn();
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     */
    public void instructLEDOff() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem.instructLEDOff();
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 聴音機のLEDを除いた全ての動作の停止を指示する．
     *
     */
    public void stopOperation() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    controlSystem.stopOperation();
                } catch (IOException e) {
                    e.printStackTrace();
                    activity.finish();
                }
                return null;
            }
        }.execute();
    }

}
