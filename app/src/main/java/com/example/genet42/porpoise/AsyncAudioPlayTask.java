package com.example.genet42.porpoise;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 音声を再生するための非同期タスク
 */
public class AsyncAudioPlayTask extends StoppableAsyncTask {
    public static final int SAMPLE_RATE_IN_HZ = 12000;
    public static final int TIME_BUFFERING_IN_MS = 5000;
    private AsyncReceiveTask receiveTask;

    public AsyncAudioPlayTask(AsyncReceiveTask receiveTask) {
        this.receiveTask = receiveTask;
    }

    @Override
    protected Void doInBackground(Void... params) {
        int minBufferSize = AudioTrack.getMinBufferSize(
                SAMPLE_RATE_IN_HZ,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT
        );
        Log.i("AsyncAudioPlayTask", "buffer size: " + minBufferSize);
        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLE_RATE_IN_HZ,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT,
                minBufferSize * 16,
                AudioTrack.MODE_STREAM
        );
        audioTrack.play();
        while (isActive()) {
            try {
                Thread.sleep(TIME_BUFFERING_IN_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] data = receiveTask.getData();
            if (data.length > 0) {
                audioTrack.write(data, 0, data.length);
            }
        }
        return null;
    }
}
