package com.example.clock;

public class Alarm {
    private final String time;
    private final String label;

    public Alarm(String time, String label) {
        this.time = time;
        this.label = label;
    }

    public String getTime() {
        return time;
    }

    public String getLabel() {
        return label;
    }
}