package com.example.genet42.porpoise;

import java.io.IOException;
import java.net.InetAddress;

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
     * Interface definition of a callback to be invoked when there
     * has been an error during an operation.
     */
    private OnErrorListener onErrorListener;

    /**
     * Interface definition of a callback to be invoked when there
     * has been an error during an operation.
     */
    public interface OnErrorListener
    {
        /**
         * Called to indicate an error.
         */
        void onError();
    }

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @param listener listener the callback that will be run
     */
    public AsyncControlSystem(InetAddress address, int port, OnErrorListener listener) {
        this(address, port, listener, true);
    }

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @param listener listener the callback that will be run
     * @param forceTCP true ならTCPを強制する
     */
    public AsyncControlSystem(final InetAddress address, final int port, final OnErrorListener listener, final boolean forceTCP) {
        onErrorListener = listener;
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (forceTCP) {
                    controlSystem = new TCPControlSystem(address, port);
                } else {
                    controlSystem = new UDPControlSystem(address, port);
                }
            }
            
            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機の前進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructForward() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructForward();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機の後進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructBackward() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructBackward();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機の右回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationRight() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructRotationRight();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機の左回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationLeft() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructRotationLeft();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     */
    public void instructLEDOn() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructLEDOn();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     */
    public void instructLEDOff() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructLEDOff();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     */
    public void instructTestLEDOn() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructTestLEDOn();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     */
    public void instructTestLEDOff() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.instructTestLEDOff();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }

    /**
     * 聴音機のLEDを除いた全ての動作の停止を指示する．
     *
     */
    public void stopOperation() {
        new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                controlSystem.stopOperation();
            }

            @Override
            protected void onError() {
                onErrorListener.onError();
            }
        }.execute();
    }
}
