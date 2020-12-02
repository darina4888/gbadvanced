package ru.geekbrains;

public class Cat implements Actions{

    private int limitTrack;
    private int limitHeight;

    public Cat(int _limitTrack,int _limitHeight) {
        this.limitTrack = _limitTrack;
        this.limitHeight = _limitHeight;
    }

    @Override
    public boolean run(int limit) {
        System.out.println("Кот бежит");
        return limit >= this.limitTrack;
    }

    @Override
    public boolean jump(int limit) {
        System.out.println("Кот прыгает");
        return limit >= this.limitHeight;
    }

    public int getLimitTrack() {
        return limitTrack;
    }

    public void setLimitTrack(int limitTrack) {
        this.limitTrack = limitTrack;
    }

    public int getLimitHeight() {
        return limitHeight;
    }

    public void setLimitHeight(int limitHeight) {
        this.limitHeight = limitHeight;
    }
}