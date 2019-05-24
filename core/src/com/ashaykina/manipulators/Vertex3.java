package com.ashaykina.manipulators;

import java.util.ArrayList;

class Vertex3 {
    private int number; // Номер вершины
    private short fi1;
    private short fi2;
    private short fi3;
    private ArrayList<Ark3> list; // Список смежности
    private int distance; // Расстояние от начальной вершины
    private boolean mark; // Маркировка посещённой вершины
    private Vertex3 prev;

    Vertex3(int number, short fi1, short fi2, short fi3) {
        this.number = number;
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        this.list = new ArrayList<>();
    }

    void addArk(Ark3 a) {
        list.add(a);
    }

    int getNumber() {
        return number;
    }

    short getFi1() {
        return fi1;
    }

    short getFi2() {
        return fi2;
    }

    short getFi3() {
        return fi3;
    }

    ArrayList<Ark3> getList() {
        return list;
    }

    int getDistance() {
        return distance;
    }

    boolean isMark() {
        return mark;
    }

    Vertex3 getPrev() {
        return prev;
    }

    void setDistance(int distance) {
        this.distance = distance;
    }

    void setMark(boolean mark) {
        this.mark = mark;
    }

    void setPrev(Vertex3 prev) {
        this.prev = prev;
    }

}