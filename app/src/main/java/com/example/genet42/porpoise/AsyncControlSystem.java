package com.example.genet42.porpoise;

import java.io.IOException;
import java.net.InetAddress;

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
     * Interface definition for a callback to be invoked when the instruction has completed
     * or there has been an error during an operation.
     */
    public interface InstructionListener
            extends AsyncInstructionTask.OnCompletionListener, AsyncInstructionTask.OnErrorListener {
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
        // ちゃんと生成されていたら true
        return controlSystem != null;
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
     * 非同期タスクにリスナを設定して実行する．
     *
     * @param task 実行するタスク．
     */
    private void executeWithListener(AsyncInstructionTask task) {
        task.setOnCompletionListener(instructionListener);
        task.setOnErrorListener(instructionListener);
        task.execute();
    }
}
