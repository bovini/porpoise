package com.example.genet42.porpoise;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 音声受信システム
 */
public class SoundSystem {
    /**
     * ファイル名のフォーマット
     */
    private static final String FORMAT_FILENAME_REC = "rec_%s.wav";

    /**
     * 日付のパターン
     */
    private static final String PATTERN_DATETIME = "yyyyMMddHHmmssSSS";

    /**
     * 受信する音声のサンプルレート
     */
    private final int sampleRateInHz;

    /**
     * 音声を再生するときのバッファ時間
     */
    private final int timeBufferingInMillis;

    /**
     * ローカルのポート番号．
     */
    private final int port;

    /**
     * true でTCPを使用する
     */
    private final boolean forceTCP;

    /**
     * 音声を受信するタスク
     */
    private AsyncReceiveTask receiveTask = null;

    /**
     * 音声を再生するタスク
     */
    private AsyncAudioPlayTask audioPlayTask = null;

    /**
     * ローカルのポート番号，音声のサンプルレートおよびバッファ時間を指定して音声受信システムを作成する.
     *
     * @param port ポート番号.
     * @param sampleRateInHz 受信する音声のサンプルレート
     * @param timeBufferingInMillis 音声を再生するときのバッファ時間
     */
    public SoundSystem(int port, int sampleRateInHz, int timeBufferingInMillis, boolean forceTCP) {
        this.port = port;
        this.forceTCP = forceTCP;
        this.sampleRateInHz = sampleRateInHz;
        this.timeBufferingInMillis = timeBufferingInMillis;
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
        audioPlayTask = new AsyncAudioPlayTask(receiveTask, sampleRateInHz, timeBufferingInMillis);
        // 開始
        receiveTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        audioPlayTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 聴音機から受信される音声の録音を開始する
     *
     * @param context コンテキスト
     */
    public void startRecoding(Context context) {
        if (audioPlayTask == null) {
            throw new IllegalStateException("Audio not playing.");
        }
        audioPlayTask.startRecoding(getFileOutputStream(context));
    }

    /**
     * 聴音機から受信される音声の録音を終了し，ファイルに保存する．
     */
    public void stopRecoding() {
        if (audioPlayTask == null) {
            return;
        }
        audioPlayTask.stopRecoding();
        Log.i("SoundSystem", "recording stopped");
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

    private FileOutputStream getFileOutputStream(Context context) {
        String datetime = new SimpleDateFormat(PATTERN_DATETIME, Locale.JAPAN).format(new Date());
        String filename = String.format(FORMAT_FILENAME_REC, datetime);
        Log.i("SoundSystem", "recording to: " + filename);
        try {
            return context.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
