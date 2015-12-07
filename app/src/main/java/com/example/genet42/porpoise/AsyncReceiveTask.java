package com.example.genet42.porpoise;

import android.os.AsyncTask;

/**
 * 音声を受信する非同期タスク
 */
public abstract class AsyncReceiveTask extends StoppableAsyncTask {
    public abstract byte[] getData();
}
