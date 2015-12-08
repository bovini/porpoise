package com.example.genet42.porpoise;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Process;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 音声を再生するための非同期タスク．
 * モノラル，8bitのみ対応．
 */
public class AsyncAudioPlayTask extends StoppableAsyncTask {
    /**
     * 音声データ受信タスク
     */
    private AsyncReceiveTask receiveTask;

    /**
     * 音声を録音するときの書き込み先
     */
    private OutputStream out = null;

    /**
     * 再生時のサンプリングレート
     */
    private int sampleRateInHz;

    /**
     * バッファ時間
     */
    private int timeBufferingInMillis;

    /**
     * 音声再生タスクを作成する．
     *
     * @param receiveTask 音声データ受信タスク
     * @param sampleRateInHz 再生時のサンプリングレート
     * @param timeBufferingInMillis バッファ時間
     */
    public AsyncAudioPlayTask(AsyncReceiveTask receiveTask, int sampleRateInHz, int timeBufferingInMillis) {
        this.receiveTask = receiveTask;
        this.sampleRateInHz = sampleRateInHz;
        this.timeBufferingInMillis = timeBufferingInMillis;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // プロセスの優先度を上げる
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
        // 最小バッファサイズ
        int minBufferSize = AudioTrack.getMinBufferSize(
                sampleRateInHz,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT
        );
        Log.i("AsyncAudioPlayTask", "buffer size: " + minBufferSize);
        // 再生器を生成
        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRateInHz,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT,
                minBufferSize * 16,
                AudioTrack.MODE_STREAM
        );
        // 再生開始
        audioTrack.play();
        while (isActive()) {
            // バッファ中
            try {
                Thread.sleep(timeBufferingInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 受信バッファから音声データを読み込み
            byte[] data = receiveTask.getData();
            if (data.length > 0) {
                // 再生バッファに書き込み
                audioTrack.write(data, 0, data.length);
                // 録音
                if (isRecording()) {
                    try {
                        out.write(data);
                    } catch (IOException e) {
                        // なんかマズかったら録音停止
                        stopRecoding();
                    }
                }
            }
        }
        audioTrack.stop();
        return null;
    }

    /**
     * ストリームを指定して録音を開始する
     *
     * @param out 音声データを書き込むストリーム
     */
    public void startRecoding(OutputStream out) {
        this.out = out;
    }

    /**
     * 録音を終了する
     */
    public void stopRecoding() {
        if (out != null) {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                /* do nothing */
            }
        }
        out = null;
    }

    /**
     * 録音の状態を返す
     *
     * @return 録音中なら true
     */
    private boolean isRecording() {
        return out != null;
    }
}
