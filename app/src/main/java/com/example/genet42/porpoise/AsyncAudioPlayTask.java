package com.example.genet42.porpoise;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;

/**
 * 音声を再生するための非同期タスク
 */
public class AsyncAudioPlayTask extends AsyncTask<Void, Void, Void> {
    public static final int SAMPLE_RATE_IN_HZ = 12000;
    public static final int TIME_BUFFERING_IN_MS = 5000;
    private AsyncUDPReceiveTask udpReceiveTask;
    private boolean isActive = true;

    public AsyncAudioPlayTask(AsyncUDPReceiveTask udpReceiveTask) {
        this.udpReceiveTask = udpReceiveTask;
    }

    @Override
    protected Void doInBackground(Void... params) {
        int minBufferSize = AudioTrack.getMinBufferSize(
                SAMPLE_RATE_IN_HZ,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT
        );
        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLE_RATE_IN_HZ,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT,
                minBufferSize * 5,
                AudioTrack.MODE_STREAM
        );
        audioTrack.play();
        while (isActive) {
            try {
                Thread.sleep(TIME_BUFFERING_IN_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] data = udpReceiveTask.getData();
            if (data.length > 0) {
//                        Log.i("UDPClient", "Audio: length: " + data.length);
                audioTrack.write(data, 0, data.length);
            }
        }
        return null;
    }

    public void stop() {
        isActive = false;
    }
}