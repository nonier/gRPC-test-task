package org.example.dto;

public class ServerValue {

    private long value;

    public ServerValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
