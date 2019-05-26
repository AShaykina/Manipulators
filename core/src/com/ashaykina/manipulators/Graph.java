package com.ashaykina.manipulators;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Arrays;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

class Graph {

    private class Ark3 {
        Vertex3 from, to;

        Ark3(Vertex3 from, Vertex3 to) {
            this.from = from;
            this.to = to;
        }
    }

    private class Vertex3 {
        int number; // Номер вершины
        short fi1, fi2, fi3;
        ArrayList<Ark3> list; // Список смежности
        int distance; // Расстояние от начальной вершины
        int changes;
        boolean mark; // Маркировка посещённой вершины
        Vertex3 prev;

        Vertex3(int number, short fi1, short fi2, short fi3) {
            this.number = number;
            this.fi1 = fi1;
            this.fi2 = fi2;
            this.fi3 = fi3;
            this.list = new ArrayList<>(6);
        }
    }

    private ArrayList<Vertex3> graph;
    private int[][][] field;
    private byte[][][] space;

    Graph(byte[][][] space) {
        this.space = space;
        this.graph = new ArrayList<>();
        this.field = new int[space.length][space[0].length][space[0][0].length];

        // Заполнение графа вершинами, в соответствии с лабиринтом
        int l = 0;
        for (short i = 0; i < field.length; i++) {
            for (short j = 0; j < field[i].length; j++) {
                for (short k = 0; k < field[i][j].length; k++) {
                    if (space[i][j][k] != -1) {
                        graph.add(new Vertex3(l, i, j, k));
                        field[i][j][k] = l;
                        l++;
                    } else field[i][j][k] = -1;
                }
            }
        }
        System.out.println("Size " + graph.size());

        // Проверка соседних ячеек лабиринта
        for (short i = 0; i < this.field.length; i++) {
            for (short j = 0; j < this.field[i].length; j++) {
                for (short k = 0; k < this.field[i][j].length; k++) {
                    if (this.field[i][j][k] != -1) {
                        if (i - 1 >= 0 && this.field[i - 1][j][k] != -1)
                            graph.get(this.field[i][j][k]).list.add(new Ark3(graph.get(this.field[i][j][k]), graph.get(this.field[i - 1][j][k])));//, 1));
                        if (i + 1 <= this.field.length - 1 && this.field[i + 1][j][k] != -1)
                            graph.get(this.field[i][j][k]).list.add(new Ark3(graph.get(this.field[i][j][k]), graph.get(this.field[i + 1][j][k])));//, 1));
                        if (j - 1 >= 0 && this.field[i][j - 1][k] != -1)
                            graph.get(this.field[i][j][k]).list.add(new Ark3(graph.get(this.field[i][j][k]), graph.get(this.field[i][j - 1][k])));//, 1));
                        if (j + 1 <= this.field[i].length - 1 && this.field[i][j + 1][k] != -1)
                            graph.get(this.field[i][j][k]).list.add(new Ark3(graph.get(this.field[i][j][k]), graph.get(this.field[i][j + 1][k])));//, 1));
                        if (k - 1 >= 0 && this.field[i][j][k - 1] != -1)
                            graph.get(this.field[i][j][k]).list.add(new Ark3(graph.get(this.field[i][j][k]), graph.get(this.field[i][j][k - 1])));//, 1));
                        if (k + 1 <= this.field[i][j].length - 1 && this.field[i][j][k + 1] != -1)
                            graph.get(this.field[i][j][k]).list.add(new Ark3(graph.get(this.field[i][j][k]), graph.get(this.field[i][j][k + 1])));//, 1));
                    }
                }

            }
        }

    }

    // Алгоритм Дейкстры поиска минимального расстояния
    ArrayList<Point3> dijkstra(short fi1, short fi2, short fi3) {
        ArrayList<Point3> result = new ArrayList<>();
        int a = this.field[fi1][fi2][fi3];
        int b = a;
        // Проверка на корректность введённых параметров
        if (a == -1 || graph.get(a).list.isEmpty()) return null;

        PriorityQueue<Ark3> qu = new PriorityQueue<>((a1, a2) -> {
            double g = (a1.from.distance + 1) * sqrt(change(a1)) - (a2.from.distance + 1) * sqrt(change(a2));
            return (g < 0) ? -1 : (g > 0) ? 1 : 0;
        });

        // Инициализация бесконечной начальной дистанции. Очевидно, что в лабиринте
        // максимальный путь будет хотя бы на единицу меньше размера лабиринта.
        // Поэтому бесконечной начальной дистанцией принимается размер лабиринта.
        for (Vertex3 v : graph) {
            v.distance = graph.size();
            v.changes = 1;
            v.mark = space[v.fi1][v.fi2][v.fi3] == -3;
            v.prev = null;
        }

        // А начальная вершина имеет дистанцию 0.
        graph.get(a).distance = 0;
        // Заполнение очереди с приоритетом рёбрами, инцидентными начальной вершине.
        for (Ark3 t : graph.get(a).list) qu.offer(t);
        graph.get(a).mark = true;

        Ark3 t;
        // Пока очередь не пуста, вынимаем из неё ребро, изменяем дистанцию
        // до смежной непосещённой вершины, и добавляем в очередь смежные для этой вершины рёбра,
        // если эти рёбра были не пройдены.
        while (!qu.isEmpty()) {
            t = qu.poll();
            int changes = change(t);
            if (t.to.distance * sqrt(t.to.changes) > (t.from.distance + 1) * sqrt(changes)) {
                t.to.distance = t.from.distance + 1;
                t.to.changes = changes;
                t.to.prev = t.from;
                for (Ark3 k : t.to.list) if (!k.to.mark) qu.offer(k);
            }
            t.to.mark = true;
            if (space[t.to.fi1][t.to.fi2][t.to.fi3] == -2) {
                b = t.to.number;
                break;
            }
        }

        Vertex3 temp = graph.get(b);
        Vertex3 good = temp;
        Vertex3 last;
        while (temp != null && temp.number != a) {
            last = good;
            result.add(new Point3((good.fi1 + 1) * PI / (space.length + 1), (good.fi2 + 1) * 2 * PI / (space[0].length + 1) - PI, (good.fi3 + 1) * 2 * PI / (space[0][0].length + 1) - PI));
            //result.add(new Point3(temp.fi1, temp.fi2, temp.fi3));
            while (temp != null && (last.fi1 == temp.fi1 && last.fi2 == temp.fi2 ||
                    last.fi2 == temp.fi2 && last.fi3 == temp.fi3 ||
                    last.fi1 == temp.fi1 && last.fi3 == temp.fi3)) {
                good = temp;
                temp = temp.prev;
            }
        }
        result.add(new Point3((good.fi1 + 1) * PI / (space.length + 1), (good.fi2 + 1) * 2 * PI / (space[0].length + 1) - PI, (good.fi3 + 1) * 2 * PI / (space[0][0].length + 1) - PI));

        Collections.reverse(result);
        System.out.println("result" + Arrays.toString(result.toArray()));
        return result;
    }

    private int change(Ark3 t) {
        if (t.from.prev == null || t.to.fi1 - t.from.fi1 == t.from.fi1 - t.from.prev.fi1 && t.to.fi2 - t.from.fi2 == t.from.fi2 - t.from.prev.fi2 && t.to.fi3 - t.from.fi3 == t.from.fi3 - t.from.prev.fi3)
            return t.from.changes;
        else return t.from.changes + 1;
    }
}