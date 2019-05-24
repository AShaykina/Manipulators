package com.ashaykina.manipulators;

import java.util.ArrayList;

public class Vertex3 {
    private int number; // Номер вершины
    private short fi1;
    private short fi2;
    private short fi3;
    private ArrayList<Ark3> list; // Список смежности
    private int distance; // Расстояние от начальной вершины
    private boolean mark; // Маркировка посещённой вершины
    private Vertex3 prev;

    public Vertex3(int number, short fi1, short fi2, short fi3) {
        this.number = number;
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        this.list = new ArrayList<>();
    }

    public void addArk(Ark3 a) {
        list.add(a);
    }

    public int getNumber() {
        return number;
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

    public ArrayList<Ark3> getList() {
        return list;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isMark() {
        return mark;
    }

    public Vertex3 getPrev() {
        return prev;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public void setPrev(Vertex3 prev) {
        this.prev = prev;
    }

}