package com.example.genet42.porpoise;

import java.util.Arrays;

/**
 * 配列を用いたバイトリングバッファ
 */
public class ByteArrayRingBuffer {
    private byte[] elements;
    private boolean wrappedAround = false;
    private int tail = 0;

    public ByteArrayRingBuffer(int capacity) {
        elements = new byte[capacity];
    }

    public synchronized void offer(byte e) {
        elements[tail] = e;
        tail++;
        if (tail == elements.length) {
            tail = 0;
            wrappedAround = true;
        }
    }

    public synchronized byte[] pollAll() {
        if (!wrappedAround) {
            byte[] result = Arrays.copyOf(elements, tail);
            reset();
            return result;
        }
        byte[] result = new byte[elements.length];
        System.arraycopy(elements, tail, result, 0, elements.length - tail);
        System.arraycopy(elements, 0, result, elements.length - tail, tail);
        reset();
        return result;
    }

    private void reset() {
        elements = new byte[elements.length];
        tail = 0;
        wrappedAround = false;
    }
}
