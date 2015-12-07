package com.example.genet42.porpoise;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDPデータグラムを受信する非同期タスク
 */
public class AsyncUDPReceiveTask extends AsyncReceiveTask {
    private static final int SIZE_BUFFER = 65536;
    private static final int SIZE_DATA_RECEIVE = 16;
    private final ByteArrayRingBuffer buffer = new ByteArrayRingBuffer(SIZE_BUFFER);
    private int port;

    public AsyncUDPReceiveTask(int port) {
        this.port = port;
    }

    @Override
    protected Void doInBackground(Void... params) {
        byte[] dataReceived = new byte[SIZE_DATA_RECEIVE];
        DatagramPacket packet = new DatagramPacket(dataReceived, dataReceived.length);
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
            while (isActive()) {
                socket.receive(packet);
                buffer.offer(packet.getData());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
        return null;
    }

    @Override
    public byte[] getData() {
        return buffer.pollAll();
    }
}
