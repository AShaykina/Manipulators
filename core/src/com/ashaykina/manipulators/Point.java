package com.ashaykina.manipulators;

public class Point {
    private int fi1;
    private int fi2;
    private int fi3;
    private int fi4;
    private int fi5;
    private int fi6;

    public Point(int fi1, int fi2, int fi3) {
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
    }

    public Point(int fi1, int fi2, int fi3, int fi4, int fi5, int fi6) {
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        this.fi4 = fi4;
        this.fi5 = fi5;
        this.fi6 = fi6;
    }

    public int getFi1() {
        return fi1;
    }

    public int getFi2() {
        return fi2;
    }

    public int getFi3() {
        return fi3;
    }

    public int getFi4() {
        return fi4;
    }

    public int getFi5() {
        return fi5;
    }

    public int getFi6() {
        return fi6;
    }

    @Override
    public String toString() {
        return "{" + fi1 + " " + fi2 + " " + fi3 + " " + fi4 + " " + fi5 + " " + fi6 + "}";
    }

}