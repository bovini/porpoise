package com.example.genet42.porpoise;

import android.view.MotionEvent;
import android.view.View;

/**
 * 聴音機の前後進と回転動作用のボタンのリスナ．
 * ボタンが同時に1つだけ押されたことにするために用いる．
 */
public abstract class BehavioralButtonListener extends ButtonListener {
    /**
     * どのボタンでもないことを表すID
     */
    private static final int ID_NULL = -1;

    /**
     * 押されているボタンのID
     */
    private static int idTouching = ID_NULL;

    /**
     * このリスナが紐付けされるボタンのID
     */
    private int idButton;

    /**
     * 指定されたボタンのIDのためのリスナを作成する．
     * @param idButton このリスナが紐付けされるボタンのID
     */
    public BehavioralButtonListener(int idButton) {
        this.idButton = idButton;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (idTouching == ID_NULL) {
                idTouching = idButton;
                // 前進ボタンに指がタッチした時の処理を記述
                onTouchDown();
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (idTouching == idButton) {
                idTouching = ID_NULL;
                // タッチした指が離れた時の処理を記述
                onTouchUp();
            }
        }
        return false;
    }
}
