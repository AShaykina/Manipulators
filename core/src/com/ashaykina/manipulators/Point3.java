package com.ashaykina.manipulators;

public class Point3 {
    private short fi1;
    private short fi2;
    private short fi3;

    public Point3(short fi1, short fi2, short fi3) {
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
    }

    public short getFi1() {
        return fi1;
    }

    public short getFi2() {
        return fi2;
    }

    public short getFi3() {
        return fi3;
    }

    @Override
    public String toString() {
        return "Point3{" +
                "fi1=" + fi1 +
                ", fi2=" + fi2 +
                ", fi3=" + fi3 +
                '}';
    }
}