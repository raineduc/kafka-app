package com.example.kafkainternapp;

public enum Mode {
    PRODUCE ("produce"),
    CONSUME ("consume");

    private final String name;

    private Mode(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
