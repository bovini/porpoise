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
     * @param activity 何かあったときに Activity#finish() するためのアクティビティ
     */
    public AsyncControlSystem(InetAddress address, int port, Activity activity) {
        this(address, port, activity, true);
    }

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @param activity 何かあったときに Activity#finish() するためのアクティビティ
     * @param forceTCP true ならTCPを強制する
     */
    public AsyncControlSystem(final InetAddress address, final int port, final Activity activity, final boolean forceTCP) {
        this.activity = activity;
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                if (forceTCP) {
                    controlSystem = new TCPControlSystem(address, port);
                } else {
                    controlSystem = new UDPControlSystem(address, port);
                }
            }
        }.execute();
    }

    /**
     * 聴音機の前進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructForward() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructForward();
            }
        }.execute();
    }

    /**
     * 聴音機の後進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructBackward() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructBackward();
            }
        }.execute();
    }

    /**
     * 聴音機の右回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationRight() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructRotationRight();
            }
        }.execute();
    }

    /**
     * 聴音機の左回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationLeft() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructRotationLeft();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     */
    public void instructLEDOn() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructLEDOn();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     */
    public void instructLEDOff() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructLEDOff();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     */
    public void instructTestLEDOn() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructTestLEDOn();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     */
    public void instructTestLEDOff() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructTestLEDOff();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDを除いた全ての動作の停止を指示する．
     *
     */
    public void stopOperation() {
        new AsyncInstructionTask(activity) {
            @Override
            protected void instruct() throws IOException {
                controlSystem.stopOperation();
            }
        }.execute();
    }
}
