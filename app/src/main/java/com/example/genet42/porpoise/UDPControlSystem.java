package com.example.genet42.porpoise;

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
    protected void invoke(final WiPortCommand cmd, int timeout) throws IOException {
        // Sender 作成
        final DatagramSocket socketSender = new DatagramSocket();
        Sender sender = new Sender() {
            @Override
            public void send(byte[] b) throws IOException {
                DatagramPacket packet = new DatagramPacket(b, b.length, address);
                socketSender.send(packet);
            }
        };
        // Receiver 作成
        final int portLocal = socketSender.getLocalPort();
        final Receiver receiver = new Receiver() {
            @Override
            public int receive(byte[] b) throws IOException {
                DatagramSocket socketReceiver = new DatagramSocket(portLocal);
                DatagramPacket packet = new DatagramPacket(b, b.length);
                socketReceiver.receive(packet);
                socketReceiver.close();
                return packet.getLength();
            }
        };
        // 送信
        cmd.sendTo(sender);
        socketSender.close();
        // 受信
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cmd.checkReply(receiver);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
