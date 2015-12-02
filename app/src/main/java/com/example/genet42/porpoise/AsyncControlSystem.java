package com.example.genet42.porpoise;

import android.os.AsyncTask;
import android.os.Handler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * 非同期版 制御指示システム
 * Created by genet42 on 2015/11/12.
 */
public class AsyncControlSystem {
    /**
     * WiPortのIPアドレス
     */
    private InetAddress address;

    /**
     * WiPortのポート番号
     */
    private int port;

    /**
     * プロトコルの選択用(true: TCP, false: UDP)
     */
    private boolean forceTCP;

    /**
     * 制御指示システム
     */
    private ControlSystem controlSystem;

    /**
     * ハンドラ
     */
    private final Handler handler = new Handler();

    /**
     * The specified timeout, in milliseconds.
     * The timeout must be > 0. A timeout of zero is interpreted as an infinite timeout.
     */
    private int timeout = 0;

    /**
     * Interface definition for a callback to be invoked when the instruction has completed
     * or there has been an error during an operation.
     */
    public interface InstructionListener {
        /**
         * Called when the instruction has completed.
         */
        void onCompletion();

        /**
         * Called to indicate an error.
         */
        void onError();
    }

    /**
     * Register a callback to be invoked when the instruction has completed
     * or when an error has happened during an operation.
     *
     * @param listener the callback that will be run
     */
    public void setInstructionListener(InstructionListener listener)
    {
        instructionListener = listener;
    }

    private InstructionListener instructionListener;

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     */
    public AsyncControlSystem(InetAddress address, int port) {
        this(address, port, true);
    }

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @param forceTCP true ならTCPを強制する
     */
    public AsyncControlSystem(final InetAddress address, final int port, final boolean forceTCP) {
        this.address = address;
        this.port = port;
        this.forceTCP = forceTCP;
    }

    /**
     * 制御指示システムを作成する
     *
     * @return ちゃんと生成されていたら true を返す．
     */
    private boolean initialize() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (forceTCP) {
                    controlSystem = new TCPControlSystem(address, port);
                } else {
                    controlSystem = new UDPControlSystem(address, port);
                }
            }
        };
        executeWithListener(task);
        return controlSystem != null;
    }

    /**
     * Enable/disable the timeout of a connection with the specified timeout, in milliseconds.
     * The option must be enabled prior to entering the async operation to have effect.
     * The timeout must be > 0. A timeout of zero is interpreted as an infinite timeout.
     *
     * @param timeout the specified timeout, in milliseconds.
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 聴音機の前進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructForward() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructForward();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機の後進の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructBackward() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructBackward();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機の右回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationRight() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructRotationRight();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機の左回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     */
    public void instructRotationLeft() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructRotationLeft();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     */
    public void instructLEDOn() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructLEDOn();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     */
    public void instructLEDOff() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructLEDOff();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     */
    public void instructTestLEDOn() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructTestLEDOn();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     */
    public void instructTestLEDOff() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.instructTestLEDOff();
            }
        };
        executeWithListener(task);
    }

    /**
     * 聴音機のLEDを除いた全ての動作の停止を指示する．
     *
     */
    public void stopOperation() {
        AsyncInstructionTask task = new AsyncInstructionTask() {
            @Override
            protected void instruct() throws IOException {
                if (controlSystem == null) {
                    if (!initialize()) {
                        return;
                    }
                }
                controlSystem.stopOperation();
            }
        };
        executeWithListener(task);
    }

    /**
     * 非同期タスクにタイムアウトを設定して実行する．
     *
     * @param task 実行するタスク
     */
    private void executeWithListener(final AsyncInstructionTask task) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean isSuccess = timeout > 0 ? task.get(timeout, TimeUnit.MILLISECONDS) : task.get();
                    if (isSuccess) {
                        instructionListener.onCompletion();
                    } else {
                        instructionListener.onError();
                    }
                } catch (Exception e) {
                    instructionListener.onError();
                }
            }
        });
    }
}
