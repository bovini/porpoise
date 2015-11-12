package com.example.genet42.porpoise;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 音声受信システム
 */
public class SoundSystem {

    /**
     * WiPortのIPアドレス．
     */
    private InetAddress address;

    /**
     * WiPortのポート番号．
     */
    private int port;

    /**
     * WiPortのIPアドレスとリモートアドレスを指定して制御指示システムを作成する.
     *
     * @param address IPアドレス.
     * @param port ポート番号.
     * @throws IOException 接続時にエラーが発生した場合
     */
    public SoundSystem(InetAddress address, int port) throws IOException {
        this.address = address;
        this.port = port;
    }

    /**
     * 聴音機から受信される音声のバッファリングを開始する
     */
    public void startBuffering() {

    }

    /**
     * 聴音機から受信される音声の録音を開始する
     */
    public void startRecoding() {

    }

    /**
     * 聴音機から受信される音声の録音を終了し，ファイルに保存する．
     */
    public void stopRecoding() {

    }
}
