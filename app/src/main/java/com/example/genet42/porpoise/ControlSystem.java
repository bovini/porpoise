package com.example.genet42.porpoise;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 制御指示システム
 */
public class ControlSystem {

    /**
     * WiPortのIPアドレス．
     */
    private InetAddress address;

    /**
     * WiPortのポート番号．
     */
    private int port;

    /**
     * 制御指示
     */
    private enum Instruction {
        /**
         * 前進
         */
        FORWARD(4),

        /**
         * 後進
         */
        BACKWARD(3),

        /**
         * 右回転
         */
        ROTATE_RIGHT(2),

        /**
         * 左回転
         */
        ROTATE_LEFT(1),

        /**
         * LED点灯
         */
        LED(0);

        /**
         * CP番号
         */
        private int numberCP;

        /**
         * 聴音機の移動を伴う制御指示
         */
        public static final Instruction[] BEHAVIORAL_INSTRUCTIONS = {
            FORWARD,
            BACKWARD,
            ROTATE_RIGHT,
            ROTATE_LEFT,
        };

        /**
         * 指定されたCP番号が割当てられた制御データを作成する．
         *
         * @param number CP番号
         */
        private Instruction(int number) {
            this.numberCP = number;
        }
    }

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @throws IOException 接続時にエラーが発生した場合
     */
    public ControlSystem(InetAddress address, int port) throws IOException {
        this.address = address;
        this.port = port;

        // reset
        instructLEDOff();
        stopOperation();
    }

    /**
     * 聴音機の前進の開始を指示する．LEDを除く他の動作は終了する．
     *
     * @throws IOException 接続時にエラーが発生した場合
     */
    public void instructForward() throws IOException {
        instructBehaviorally(Instruction.FORWARD);
    }

    /**
     * 聴音機の後進の開始を指示する．LEDを除く他の動作は終了する．
     *
     * @throws IOException 接続時にエラーが発生した場合
     */
    public void instructBackward() throws IOException {
        instructBehaviorally(Instruction.BACKWARD);
    }

    /**
     * 聴音機の右回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     * @throws IOException 接続時にエラーが発生した場合
     */
    public void instructRotationRight() throws IOException {
        instructBehaviorally(Instruction.ROTATE_RIGHT);
    }

    /**
     * 聴音機の左回転の開始を指示する．LEDを除く他の動作は終了する．
     *
     * @throws IOException 接続時にエラーが発生した場合
     */
    public void instructRotationLeft() throws IOException {
        instructBehaviorally(Instruction.ROTATE_LEFT);
    }

    /**
     * 聴音機のLEDの点灯を指示する．
     *
     * @throws IOException 接続時にエラーが発生した場合
     */
    public void instructLEDOn() throws IOException {
        try (Socket socket = new Socket(address, port)) {
            // 指示を作成
            WiPortCommand cmd = new WiPortCommand();
            cmd.setActive(Instruction.LED.numberCP);
            // 指示を送信
            cmd.writeTo(socket.getOutputStream());
            // 返信を確認
            cmd.checkReply(socket.getInputStream());
        }
    }

    /**
     * 聴音機のLEDの消灯を指示する．
     *
     * @throws IOException 接続時にエラーが発生した場合
     */
    public void instructLEDOff() throws IOException {
        try (Socket socket = new Socket(address, port)) {
            // 指示を作成
            WiPortCommand cmd = new WiPortCommand();
            cmd.setInactive(Instruction.LED.numberCP);
            // 指示を送信
            cmd.writeTo(socket.getOutputStream());
            // 返信を確認
            cmd.checkReply(socket.getInputStream());
        }
    }

    /**
     * 聴音機のLEDを除いた全ての動作の停止を指示する．
     *
     * @throws IOException 接続時にエラーが発生した場合
     */
    public void stopOperation() throws IOException {
        instructBehaviorally(null);
    }

    /**
     * 聴音機の移動を伴う制御指示を指示する．
     *
     * @param instruction 制御指示
     * @throws IOException 接続時にエラーが発生した場合
     */
    private void instructBehaviorally(Instruction instruction) throws IOException {
        try (Socket socket = new Socket(address, port)) {
            // 指示を作成
            WiPortCommand cmd = createBehavioralCommand(instruction);
            // 指示を送信
            cmd.writeTo(socket.getOutputStream());
            // 返信を確認
            cmd.checkReply(socket.getInputStream());
        }
    }

    /**
     * 指定された制御指示を有効化するための指示を作成する．
     * 有効化されない聴音機の移動を伴う制御指示は無効化するため，
     * 常に1つ以下の聴音機の移動を伴う制御指示が有効となる．
     * 制御指示としてnullを指定すると，全ての聴音機の移動を伴う制御指示が無効化される．
     *
     * @param instructionToActivate 有効化する制御指示．nullが許容される．
     * @return 指定された制御指示を有効化するための指示
     */
    private WiPortCommand createBehavioralCommand(Instruction instructionToActivate) {
        WiPortCommand cmd = new WiPortCommand();
        for (Instruction instruction : Instruction.BEHAVIORAL_INSTRUCTIONS) {
            if (instruction == instructionToActivate) {
                cmd.setActive(instruction.numberCP);
            } else {
                cmd.setInactive(instruction.numberCP);
            }
        }
        return cmd;
    }
}
