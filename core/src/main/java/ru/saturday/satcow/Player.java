package ru.saturday.satcow;

public class Player {
    String name;
    long time;

    public Player(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public void set(String name, long time) {
        this.name = name;
        this.time = time;
    }
}
