package com.example.genet42.porpoise;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配列を用いたバイトリングバッファ
 */
public class ByteArrayRingBuffer {
    private byte[] elements;
    private int capacity;
    private boolean wrappedAround = false;
    private int tail = 0;

    public ByteArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        elements = new byte[capacity];
    }

    public synchronized void offer(byte[] elements) {
        offer(elements, 0, capacity);
    }

    public synchronized void offer(byte[] elements, int toIndex) {
        offer(elements, 0, toIndex);
    }

    public synchronized void offer(byte[] elements, int fromIndex, int toIndex) {
        int length = toIndex - fromIndex;
        Log.i("ByteArrayRingBuffer", "offered length: " + length);
        Log.i("ByteArrayRingBuffer", "stored: " + (wrappedAround ? capacity + " (wrapped)" : tail) + " (" + 100 * (wrappedAround ? capacity : tail) / capacity + " %)");
        // length <= capacity を保証
        if (length > capacity) {
            offer(elements, toIndex - capacity, toIndex);
            return;
        }
        int capacityTail = capacity - tail;
        Log.i("ByteArrayRingBuffer", String.format("arraycopy: [%d, %d) -> [%d, %d)", fromIndex, fromIndex + Math.min(toIndex, capacityTail), tail, tail + Math.min(toIndex, capacityTail)));
        System.arraycopy(elements, fromIndex, this.elements, tail, Math.min(toIndex, capacityTail));
        tail += length;
        if (length > capacityTail) {
            System.arraycopy(elements, fromIndex + capacityTail, this.elements, 0, length - capacityTail);
            tail = length - capacityTail;
            wrappedAround = true;
        }
    }

    public synchronized byte[] pollAll() {
        Log.i("ByteArrayRingBuffer", "polled length: " + (wrappedAround ? capacity + " (wrapped)" : tail));
        if (!wrappedAround) {
            byte[] result = Arrays.copyOf(elements, tail);
            reset();
            return result;
        }
        byte[] result = new byte[capacity];
        System.arraycopy(elements, tail, result, 0, capacity - tail);
        System.arraycopy(elements, 0, result, capacity - tail, tail);
        reset();
        return result;
    }

    private void reset() {
        elements = new byte[capacity];
        tail = 0;
        wrappedAround = false;
    }
}
