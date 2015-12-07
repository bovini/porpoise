package com.example.genet42.porpoise;

import android.os.AsyncTask;

/**
 * Created by genet42 on 2015/12/07.
 */
public abstract class StoppableAsyncTask extends AsyncTask<Void, Void, Void> {
    private boolean isActive = true;

    protected boolean isActive() {
        return isActive;
    }

    public void stop() {
        isActive = false;
    }
}
