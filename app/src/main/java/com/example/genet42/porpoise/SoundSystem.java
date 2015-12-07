package com.example.genet42.porpoise;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 音声受信システム
 */
public class SoundSystem {
    /**
     * ローカルのポート番号．
     */
    private int port;

    /**
     * 音声を受信するタスク
     */
    private AsyncReceiveTask receiveTask;

    /**
     * 音声を再生するタスク
     */
    private AsyncAudioPlayTask audioPlayTask;

    /**
     * true でTCPを使用する
     */
    private boolean forceTCP;

    /**
     * ローカルのポート番号を指定して音声受信システムを作成する.
     *
     * @param port ポート番号.
     */
    public SoundSystem(int port, boolean forceTCP) {
        this.port = port;
        this.forceTCP = forceTCP;
    }

    /**
     * 聴音機から受信される音声の再生を開始する
     */
    public void startPlaying() {
        // 生成
        if (forceTCP) {
            receiveTask = new AsyncTCPReceiveTask(port);
        } else {
            receiveTask = new AsyncUDPReceiveTask(port);
        }
        audioPlayTask = new AsyncAudioPlayTask(receiveTask);
        // 開始
        receiveTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        audioPlayTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 聴音機から受信される音声の録音を開始する
     */
    public void startRecoding() {

    }

    /**
     * 聴音機から受信される音声の録音を終了し，ファイルに保存する．
     */
    public void stopRecoding() {

    }

    /**
     * 音声の受信および再生を停止する
     */
    public void stop() {
        if (audioPlayTask != null) {
            audioPlayTask.stop();
        }
        if (receiveTask != null) {
            receiveTask.stop();
        }
    }
}
