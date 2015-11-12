package com.example.genet42.porpoise;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * WiPortのCPに対するSet current statesのためのデータ生成器
 */
public class WiPortCommand {
    /**
     * コマンドデータ (Set current states)
     */
    private static final byte COMMAND = 0x1b;

    /**
     * 1つのパラメータの長さ
     */
    private static final int LENGTH_PARAMETER = 4;

    /**
     * 送信データの長さ
     */
    private static final int LENGTH_DATA = 1 + LENGTH_PARAMETER * 2;

    /**
     * 返信の長さ
     */
    private static final int LENGTH_REPLY = 1 + LENGTH_PARAMETER;

    /**
     * マスク
     */
    private byte[] mask = new byte[LENGTH_PARAMETER];

    /**
     * 新しい状態
     */
    private byte[] states = new byte[LENGTH_PARAMETER];

    /**
     * 指定された番号のCPをアクティブにする．
     *
     * @param numberCP CP番号
     */
    public void setActive(int numberCP) {
        byte data = updateMask(numberCP);
        states[numberCP / Byte.SIZE] |= data;
    }

    /**
     * 指定された番号のCPを非アクティブにする．
     *
     * @param numberCP CP番号
     */
    public void setInactive(int numberCP) {
        byte data = updateMask(numberCP);
        states[numberCP / Byte.SIZE] &= ~ data;
    }

    /**
     * 指定された番号のCPに対する変更を有効にして，設定用のデータを返す．
     *
     * @param numberCP CP番号
     * @return 設定用のデータ
     */
    private byte updateMask(int numberCP) {
        byte data = (byte) (1 << (numberCP % Byte.SIZE));
        mask[numberCP / Byte.SIZE] |= data;
        return data;
    }

    /**
     * この指示を与えられたOutputStreamに書き込む．
     *
     * @param out 書き込み先
     * @throws IOException 入出力エラーが発生した場合
     */
    public void writeTo(OutputStream out) throws IOException {
        byte[] data = new byte[LENGTH_DATA];
        data[0] = COMMAND;
        for (int i = 0; i < LENGTH_PARAMETER; i++) {
            data[1 + i] = mask[i];
            data[1 + LENGTH_PARAMETER + i] = states[i];
        }
        out.write(data);
    }

    /**
     * 与えられたInputStreamから値を読み込み，
     * これ返信と見なして妥当性を判定する．
     *
     * @param in 読み込み元
     * @throws IOException 入出力エラーが発生した場合，および返信が妥当でない場合．
     */
    public void checkReply(InputStream in) throws IOException {
        // 読む
        byte[] reply = new byte[LENGTH_REPLY];
        int length_reply = in.read(reply);
        // 確認
        if (length_reply != LENGTH_REPLY) {
            throw new IOException("Invalid length of reply: " + length_reply);
        }
        if (reply[0] != COMMAND) {
            throw new IOException("Invalid Command Type of reply.");
        }
        for (int i = 1; i < reply.length; i++) {
            if (((reply[i] ^ states[i - 1]) & mask[i - 1]) != 0) {
                throw new IOException("Changes not correctly applied.");
            }
        }
    }
}
