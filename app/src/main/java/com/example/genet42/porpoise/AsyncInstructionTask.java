package com.example.genet42.porpoise;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * 指示用の非同期タスク
 */
public abstract class AsyncInstructionTask extends AsyncTask<Void, Void, Boolean> {

    /**
     * Interface definition for a callback to be invoked when the instruction has completed.
     */
    public interface OnCompletionListener
    {
        /**
         * Called when the instruction has completed.
         */
        void onCompletion();
    }

    /**
     * Register a callback to be invoked when the instruction has completed.
     *
     * @param listener the callback that will be run
     */
    public void setOnCompletionListener(OnCompletionListener listener)
    {
        onCompletionListener = listener;
    }

    private OnCompletionListener onCompletionListener;

    /**
     * Interface definition of a callback to be invoked when there
     * has been an error during an operation.
     */
    public interface OnErrorListener
    {
        /**
         * Called to indicate an error.
         */
        void onError();
    }

    /**
     * Register a callback to be invoked when an error has happened
     * during an operation.
     *
     * @param listener the callback that will be run
     */
    public void setOnErrorListener(OnErrorListener listener)
    {
        onErrorListener = listener;
    }

    private OnErrorListener onErrorListener;

    @Override
    protected final Boolean doInBackground(Void... params) {
        try {
            instruct();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 非同期に実行する．
     *
     * @throws IOException 入出力エラーが発生した場合
     */
    protected abstract void instruct() throws IOException;

    @Override
    protected void onPostExecute(Boolean succeeded) {
        if (succeeded) {
            if (onCompletionListener != null) {
                onCompletionListener.onCompletion();
            }
        } else {
            if (onErrorListener != null) {
                onErrorListener.onError();
            }
        }
    }
}
