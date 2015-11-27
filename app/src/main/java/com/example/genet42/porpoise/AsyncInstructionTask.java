package com.example.genet42.porpoise;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * 指示用の非同期タスク
 */
public abstract class AsyncInstructionTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected final Void doInBackground(Void... params) {
        try {
            instruct();
        } catch (IOException e) {
            onError();
        }
        return null;
    }

    /**
     * 非同期に実行する．
     *
     * @throws IOException 入出力エラーが発生した場合
     */
    protected abstract void instruct() throws IOException;

    /**
     * Called to indicate an error.
     */
    protected abstract void onError();
}
