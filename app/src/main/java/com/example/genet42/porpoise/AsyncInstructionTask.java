package com.example.genet42.porpoise;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * 指示用の非同期タスク
 */
public abstract class AsyncInstructionTask extends AsyncTask<Void, Void, Void> {
    /**
     * 何かあったときに Activity#finish() するためのアクティビティ
     */
    private Activity activity;

    /**
     * 指示用の非同期タスクを作成する
     *
     * @param activity 何かあったときに Activity#finish() するためのアクティビティ
     */
    public AsyncInstructionTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected final Void doInBackground(Void... params) {
        try {
            instruct();
        } catch (IOException e) {
            e.printStackTrace();
            activity.finish();
        }
        return null;
    }

    /**
     * 非同期に実行する．
     *
     * @throws IOException 入出力エラーが発生した場合
     */
    protected abstract void instruct() throws IOException;
}
