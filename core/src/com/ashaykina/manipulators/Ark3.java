package com.ashaykina.manipulators;

public class Ark3 {
    private Vertex3 from;
    private Vertex3 to;
//    private int capacity;

    Ark3(Vertex3 from, Vertex3 to) {//, int capacity){
        this.from = from;
        this.to = to;
        //    this.capacity = capacity;
    }

    public Vertex3 getFrom() {
        return from;
    }

    public Vertex3 getTo() {
        return to;
    }

//    public int getCapacity() {
//        return capacity;
//    }

}