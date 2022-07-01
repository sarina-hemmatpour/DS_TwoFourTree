package com.company;

public class Entry<E> {

    private E value;
    private int key;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Entry(E value, int key) {
        this.value = value;
        this.key = key;
    }

    public Entry() {
    }
}

