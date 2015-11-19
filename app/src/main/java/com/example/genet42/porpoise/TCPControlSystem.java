package com.example.genet42.porpoise;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * TCP通信を用いる制御指示システム
 */
public class TCPControlSystem extends ControlSystem {

    /**
     * WiPortのIPアドレス．
     */
    private InetAddress address;

    /**
     * WiPortのポート番号．
     */
    private int port;

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @throws IOException 接続時にエラーが発生した場合
     */
    public TCPControlSystem(InetAddress address, int port) throws IOException {
        this.address = address;
        this.port = port;

        reset();
    }

    @Override
    protected void invoke(WiPortCommand cmd) throws IOException {
        final Socket socket = new Socket(address, port);
        // 指示を送信
        cmd.sendTo(new Sender() {
            @Override
            public void send(byte[] b) throws IOException {
                socket.getOutputStream().write(b);
            }
        });
        // 返信を確認
        cmd.checkReply(new Receiver() {
            @Override
            public int receive(byte[] b) throws IOException {
                return socket.getInputStream().read(b);
            }
        });
        socket.close();
    }
}
