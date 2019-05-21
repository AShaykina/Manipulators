package com.ashaykina.manipulators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.zip.DataFormatException;

import static java.lang.Math.*;

public class Graph {

    private ArrayList<Vertex> graph;
    private int [][][] field;
    private byte [][][] space;
    private int n;

    Graph(byte space[][][]) {
        this.space = space;
        this.graph = new ArrayList<>();
        this.field = new int[space.length][space[0].length][space[0][0].length];
        this.n = field.length;

        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field[i].length; j++) {
                for (int k = 0; k < this.field[i][j].length; k++) {
                    this.field[i][j][k] = space[i][j][k];
                }
            }
        }

        // Заполнение графа вершинами, в соответствии с лабиринтом
        int l = 0;
        for (int i = 0; i < this.field.length; i++){
            for (int j = 0; j < this.field[i].length; j++){
                for (int k = 0; k < this.field[i][j].length; k++) {
                    if (this.field[i][j][k] != -1) {
                        graph.add(new Vertex(l, i, j, k));
                        this.field[i][j][k] = l;
                        l++;
                    }
                }
            }
        }

        // Проверка соседних ячеек лабиринта
        for (int i = 0; i < this.field.length; i++) {
            for (int j = 0; j < this.field[i].length; j++) {
                for (int k = 0; k < this.field[i][j].length; k++) {
                    if (this.field[i][j][k] != -1) {
                        if (i - 1 >= 0 && this.field[i - 1][j][k] != -1) {
                            graph.get(this.field[i][j][k]).addArk(new Ark(graph.get(this.field[i][j][k]), graph.get(this.field[i - 1][j][k]), 1));
                        }
                        if (i + 1 <= this.field.length - 1 && this.field[i + 1][j][k] != -1) {
                            graph.get(this.field[i][j][k]).addArk(new Ark(graph.get(this.field[i][j][k]), graph.get(this.field[i + 1][j][k]), 1));
                        }
                        if (j - 1 >= 0 && this.field[i][j - 1][k] != -1) {
                            graph.get(this.field[i][j][k]).addArk(new Ark(graph.get(this.field[i][j][k]), graph.get(this.field[i][j - 1][k]), 1));
                        }
                        if (j + 1 <= this.field[i].length - 1 && this.field[i][j + 1][k] != -1) {
                            graph.get(this.field[i][j][k]).addArk(new Ark(graph.get(this.field[i][j][k]), graph.get(this.field[i][j + 1][k]), 1));
                        }
                        if (k - 1 >= 0 && this.field[i][j][k - 1] != -1) {
                            graph.get(this.field[i][j][k]).addArk(new Ark(graph.get(this.field[i][j][k]), graph.get(this.field[i][j][k - 1]), 1));
                        }
                        if (k + 1 <= this.field[i][j].length - 1 && this.field[i][j][k + 1] != -1) {
                            graph.get(this.field[i][j][k]).addArk(new Ark(graph.get(this.field[i][j][k]), graph.get(this.field[i][j][k + 1]), 1));
                        }
                    }
                }

            }
        }

    }

    // Алгоритм Дейкстры поиска минимального расстояния
    public ArrayList<Point> dijkstra(int fi1, int fi2, int fi3) throws Exception {
        ArrayList<Point> result = new ArrayList<>();
        //System.out.println(this.field.length + " " + this.field[0].length + " " + this.field[0][0].length + " " + fi1 + " " + fi2 + " " + fi3);
            int a = this.field[fi1][fi2][fi3];
            int b = a;
            // Проверка на корректность введённых параметров
            if (a == -1 || graph.get(a).getList().isEmpty()) {
                throw new Exception();
            } else {

                PriorityQueue<Ark> qu = new PriorityQueue((Comparator<Ark>) (a1, a2) -> {
                    double g = a1.getFrom().getDistance() + a1.getCapacity() - a2.getFrom().getDistance() - a2.getCapacity();
                    if (g < 0) return -1;
                    else if (g > 0) return 1;
                    else return 0;
                });

                // Инициализация бесконечной начальной дистанции. Очевидно, что в лабиринте
                // максимальный путь будет хотя бы на единицу меньше размера лабиринта.
                // Поэтому бесконечной начальной дистанцией принимается размер лабиринта.
                for (Vertex v : graph) {
                    v.setDistance(graph.size());
                    for (Ark t : v.getList()) {
                        t.getTo().setDistance(graph.size());
                    }
                    v.setMark(false);
                }

                // А начальная вершина имеет дистанцию 0.
                graph.get(a).setDistance(0);

                // Заполнение очереди с приоритетом рёбрами, инцидентные начальной вершине.
                for (Ark t : graph.get(a).getList()) {
                    if (!t.getTo().isMark()) qu.offer(t);
                }
                graph.get(a).setMark(true);

                Ark t;

                // Пока очередь не пуста, вынимаем из неё ребро, изменяем дистанцию
                // до смежной непосещённой вершины, и добавляем в очередь смежные для этой вершины рёбра,
                // если эти рёбра были не пройдены.
                while (!qu.isEmpty()) { // && graph.get(b).getDistance() == graph.size()
                    t = qu.poll();
                    //   System.out.println(t);
                    if (t.getTo().getDistance() > t.getFrom().getDistance() + t.getCapacity()) {
                        t.getTo().setDistance(t.getFrom().getDistance() + t.getCapacity());
                        t.getTo().setPrev(t.getFrom());
                        for (Ark k : t.getTo().getList()) {
                            if (!k.getTo().isMark()) qu.offer(k); // && space[k.getTo().getFi1()][k.getTo().getFi2()][k.getTo().getFi3()] != -3
                        }
                    }

                    t.getTo().setMark(true);
                    if (this.space[t.getTo().getFi1()][t.getTo().getFi2()][t.getTo().getFi3()] == -2) {
                    //    double l1 = 0.372677996;
                    //    double l2 = 0.372677996;
                    //    double l3 = 0.372677996;
                    //    int n = 100;
                        int i = t.getTo().getFi1();
                        int j = t.getTo().getFi2();
                        int k = t.getTo().getFi3();
                    //    double x1 = l1 * cos(i * PI / (n - 1)) + l2 * cos(i * PI / (n - 1) + j * 2 * PI / (2 * n - 1) - PI) + l3 * cos(i * PI / (n - 1) + j * 2 * PI / (2 * n - 1) + k * 2 * PI / (2 * n - 1) - 2 * PI);
                    //    double y1 = l1 * sin(i * PI / (n - 1)) + l2 * sin(i * PI / (n - 1) + j * 2 * PI / (2 * n - 1) - PI) + l3 * sin(i * PI / (n - 1) + j * 2 * PI / (2 * n - 1) + k * 2 * PI / (2 * n - 1) - 2 * PI);
                    //    System.out.println("Reached: " + (0.5 + x1) + " " + y1);
                        System.out.println("Angles: " + (i * PI / (n - 1)) + " " + (j * 2 * PI / (2 * n - 1) - PI) + " " + (k * 2 * PI / (2 * n - 1) - PI));
                        b = t.getTo().getNumber();
                        break;
                    }
                }

                Vertex v = graph.get(b);
                while (v.getNumber() != a) {
                    //    System.out.println(v.getNumber() + " " + v.getX() + " " + v.getY());
                //    mm[v.getFi1()][v.getFi2()][v.getFi3()] = 2;
                //    System.out.println("NewAngles: " + (v.getFi1() * PI /(mm.length - 1)) + " " + (v.getFi2() * 2 * PI/(mm[0].length - 1)) + " " + (v.getFi3() * 2 * PI/(mm[0][0].length - 1)));
                    result.add(new Point(v.getFi1(), v.getFi2(), v.getFi3()));
                    v = v.getPrev();
                }
                //    System.out.println(v.getNumber() + " " + v.getX() + " " + v.getY());
                // mm[v.getFi1()][v.getFi2()][v.getFi3()] = 2;
                result.add(new Point(v.getFi1(), v.getFi2(), v.getFi3()));
                System.out.println("NewAngles: " + (v.getFi1() * PI /(space.length - 1)) + " " + (v.getFi2() * 2 * PI/(space[0].length - 1) - PI) + " " + (v.getFi3() * 2 * PI/(space[0][0].length - 1) - PI));
                System.out.println("Distance " + graph.get(b).getDistance());

                Collections.reverse(result);
                return result;
            }
    }
}