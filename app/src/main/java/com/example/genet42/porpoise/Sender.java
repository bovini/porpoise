package com.example.genet42.porpoise;

import java.io.IOException;

/**
 *
 */
public interface Sender {
    /**
     * 指定されたバイト配列の b.length バイトを送信する．
     *
     * @param b データ
     */
    void send(byte[] b) throws IOException;
}
