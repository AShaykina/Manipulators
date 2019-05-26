package com.ashaykina.manipulators;

class Point3 {
    double fi1, fi2, fi3;

    Point3(double fi1, double fi2, double fi3) {
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
    }

    @Override
    public String toString() {
        return "Point3{" + "fi1=" + fi1 + ", fi2=" + fi2 + ", fi3=" + fi3 + '}';
    }
}