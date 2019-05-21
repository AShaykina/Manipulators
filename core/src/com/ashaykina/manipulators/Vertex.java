package com.ashaykina.manipulators;

import java.util.ArrayList;

public class Vertex {
    private int number; // Номер вершины
    private int fi1; // Координата x вершины
    private int fi2; // Координата y вершины
    private int fi3;
    private int fi4;
    private int fi5;
    private int fi6;
    private ArrayList<Ark> list; // Список смежности
    private double distance; // Расстояние от начальной вершины
    private boolean mark; // Маркировка посещённой вершины
    private Vertex prev;

    public Vertex(int number, int fi1, int fi2, int fi3){
        this.number = number;
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        this.list = new ArrayList<>();
    }

    public Vertex(int number, int fi1, int fi2, int fi3, int fi4, int fi5, int fi6) {
        this.number = number;
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        this.fi4 = fi4;
        this.fi5 = fi5;
        this.fi6 = fi6;
        this.list = new ArrayList<>();
    }

    public void addArk(Ark a){
        list.add(a);
    }

    public int getNumber() {
        return number;
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

    public ArrayList<Ark> getList() {
        return list;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isMark() {
        return mark;
    }

    public Vertex getPrev() {
        return prev;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "{" + number + " " + fi1 + " " + fi2 + " " + fi3 + " " + fi4 + " " + fi5 + " " + fi6 + "}";
    }

}