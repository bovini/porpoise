package com.example.genet42.porpoise;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * TCP通信を用いる制御指示システム
 */
public class UDPControlSystem extends ControlSystem {
    /**
     * WiPortのソケットアドレス．
     */
    private InetSocketAddress address;

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @throws IOException 接続時にエラーが発生した場合
     */
    public UDPControlSystem(InetAddress address, int port) throws IOException {
        this.address = new InetSocketAddress(address, port);
        reset();
    }

    @Override
    protected void invoke(final WiPortCommand cmd) throws IOException {
        final DatagramSocket socket = new DatagramSocket();
        Log.i("UDP", "sending...");
        // 送信
        cmd.sendTo(new Sender() {
            @Override
            public void send(byte[] b) throws IOException {
                DatagramPacket packet = new DatagramPacket(b, b.length, address);
                socket.send(packet);
            }
        });
        Log.i("UDP", "sent");
        // 受信
        Log.i("UDP", "receiving...");
        cmd.checkReply(new Receiver() {
            @Override
            public int receive(byte[] b) throws IOException {
                DatagramPacket packet = new DatagramPacket(b, b.length);
                socket.receive(packet);
                return packet.getLength();
            }
        });
        Log.i("UDP", "received");
        socket.close();
    }
}
