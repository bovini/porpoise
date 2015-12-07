package com.example.genet42.porpoise;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDPデータグラムを受信する非同期タスク
 */
public class AsyncUDPReceiveTask extends AsyncTask<Void, Void, Void> {
    private static final int SIZE_BUFFER = 65536;
    private static final int SIZE_DATA_RECEIVE = 16;
    private final ByteArrayRingBuffer buffer = new ByteArrayRingBuffer(SIZE_BUFFER);
    private boolean isActive = true;
    private int port;

    public AsyncUDPReceiveTask(int port) {
        this.port = port;
    }

    @Override
    protected Void doInBackground(Void... params) {
        byte[] dataReceived = new byte[SIZE_DATA_RECEIVE];
        DatagramPacket dp = new DatagramPacket(dataReceived, dataReceived.length);
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(port);
            while (isActive) {
                ds.receive(dp);
                for (int i = 0; i < dp.getLength(); i++) {
                    buffer.offer(dataReceived[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
        return null;
    }

    public void stop() {
        isActive = false;
    }

    public byte[] getData() {
        return buffer.pollAll();
    }
}
