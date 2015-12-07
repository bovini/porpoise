package com.example.genet42.porpoise;

import android.os.AsyncTask;

public class UDPClient {
    private AsyncUDPReceiveTask udpReceiveTask;
    private AsyncAudioPlayTask audioPlayTask;

    public UDPClient(int port) {
        udpReceiveTask = new AsyncUDPReceiveTask(port);
        audioPlayTask = new AsyncAudioPlayTask(udpReceiveTask);
    }

    public void start() {
        // UDP受信タスク開始
        udpReceiveTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        // 音声送信タスク開始
        audioPlayTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void stop() {
        audioPlayTask.stop();
        udpReceiveTask.stop();
    }
}