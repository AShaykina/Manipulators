package com.ashaykina.manipulators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class GraphPair {

    private ArrayList<Vertex> graph;
    private int [][][][][][] field;
    private byte [][][][][][] space;
    private int n;

    GraphPair(byte space[][][][][][]) {

        long time = System.currentTimeMillis();

        this.space = space;
        this.graph = new ArrayList<>();
        this.field = new int[space.length][space[0].length][space[0][0].length][space[0][0][0].length][space[0][0][0][0].length][space[0][0][0][0][0].length];
        this.n = field.length;

        for (int i1 = 0; i1 < this.field.length; i1++) {
            for (int j1 = 0; j1 < this.field[i1].length; j1++) {
                for (int k1 = 0; k1 < this.field[i1][j1].length; k1++) {
                    for (int i2 = 0; i2 < this.field.length; i2++) {
                        for (int j2 = 0; j2 < this.field[i2].length; j2++) {
                            for (int k2 = 0; k2 < this.field[i2][j2].length; k2++) {
                                this.field[i1][j1][k1][i2][j2][k2] = space[i1][j1][k1][i2][j2][k2];
                            }
                        }
                    }
                }
            }
        }

        // Заполнение графа вершинами, в соответствии с лабиринтом
        int l = 0;
        for (int i1 = 0; i1 < this.field.length; i1++) {
            for (int j1 = 0; j1 < this.field[i1].length; j1++) {
                for (int k1 = 0; k1 < this.field[i1][j1].length; k1++) {
                    for (int i2 = 0; i2 < this.field.length; i2++) {
                        for (int j2 = 0; j2 < this.field[i2].length; j2++) {
                            for (int k2 = 0; k2 < this.field[i2][j2].length; k2++) {
                                if (this.field[i1][j1][k1][i2][j2][k2] != -1) {
                                    graph.add(new Vertex(l, i1, j1, k1, i2, j2, k2));
                                    this.field[i1][j1][k1][i2][j2][k2] = l;
                                    l++;
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Time " + (System.currentTimeMillis() - time));

        // Проверка соседних ячеек лабиринта
        for (int i1 = 0; i1 < this.field.length; i1++) {
            for (int j1 = 0; j1 < this.field[i1].length; j1++) {
                for (int k1 = 0; k1 < this.field[i1][j1].length; k1++) {
                    for (int i2 = 0; i2 < this.field.length; i2++) {
                        for (int j2 = 0; j2 < this.field[i2].length; j2++) {
                            for (int k2 = 0; k2 < this.field[i2][j2].length; k2++) {
                                if (this.field[i1][j1][k1][i2][j2][k2] != -1) {
                                    if (i1 - 1 >= 0 && this.field[i1 - 1][j1][k1][i2][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1 - 1][j1][k1][i2][j2][k2]), 1));
                                    }
                                    if (i1 + 1 <= this.field.length - 1 && this.field[i1 + 1][j1][k1][i2][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1 + 1][j1][k1][i2][j2][k2]), 1));
                                    }
                                    if (j1 - 1 >= 0 && this.field[i1][j1 - 1][k1][i2][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1 - 1][k1][i2][j2][k2]), 1));
                                    }
                                    if (j1 + 1 <= this.field[i1].length - 1 && this.field[i1][j1 + 1][k1][i2][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1 + 1][k1][i2][j2][k2]), 1));
                                    }
                                    if (k1 - 1 >= 0 && this.field[i1][j1][k1 - 1][i2][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1 - 1][i2][j2][k2]), 1));
                                    }
                                    if (k1 + 1 <= this.field[i1][j1].length - 1 && this.field[i1][j1][k1 + 1][i2][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1 + 1][i2][j2][k2]), 1));
                                    }

                                    if (i2 - 1 >= 0 && this.field[i1][j1][k1][i2 - 1][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1][i2 - 1][j2][k2]), 1));
                                    }
                                    if (i2 + 1 <= this.field[i1][j1][k1].length - 1 && this.field[i1][j1][k1][i2 + 1][j2][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1][i2 + 1][j2][k2]), 1));
                                    }
                                    if (j2 - 1 >= 0 && this.field[i1][j1][k1][i2][j2 - 1][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1][i2][j2 - 1][k2]), 1));
                                    }
                                    if (j2 + 1 <= this.field[i1][j1][k1][i2].length - 1 && this.field[i1][j1][k1][i2][j2 + 1][k2] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1][i2][j2 + 1][k2]), 1));
                                    }
                                    if (k2 - 1 >= 0 && this.field[i1][j1][k1][i2][j2][k2 - 1] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1][i2][j2][k2 - 1]), 1));
                                    }
                                    if (k2 + 1 <= this.field[i1][j1][k1][i2][j2].length - 1 && this.field[i1][j1][k1][i2][j2][k2 + 1] != -1) {
                                        graph.get(this.field[i1][j1][k1][i2][j2][k2]).addArk(new Ark(graph.get(this.field[i1][j1][k1][i2][j2][k2]), graph.get(this.field[i1][j1][k1][i2][j2][k2 + 1]), 1));
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

    }

    // Алгоритм Дейкстры поиска минимального расстояния
    public ArrayList<Point> dijkstra(int fi1, int fi2, int fi3, int fi4, int fi5, int fi6) throws Exception {
        ArrayList<Point> result = new ArrayList<>();
        //System.out.println(this.field.length + " " + this.field[0].length + " " + this.field[0][0].length + " " + fi1 + " " + fi2 + " " + fi3);
        int a = this.field[fi1][fi2][fi3][fi4][fi5][fi6];
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
                        if (!k.getTo().isMark()) qu.offer(k);
                    }
                }

                t.getTo().setMark(true);
                if (this.space[t.getTo().getFi1()][t.getTo().getFi2()][t.getTo().getFi3()][t.getTo().getFi4()][t.getTo().getFi5()][t.getTo().getFi6()] == -2) {
                    //    double l1 = 0.372677996;
                    //    double l2 = 0.372677996;
                    //    double l3 = 0.372677996;
                    //    int n = 100;
                //    int i1 = t.getTo().getFi1();
                //    int j1 = t.getTo().getFi2();
                //    int k1 = t.getTo().getFi3();
                //    int i2 = t.getTo().getFi4();
                //    int j2 = t.getTo().getFi5();
                //    int k2 = t.getTo().getFi6();

                    //    double x1 = l1 * cos(i1 * PI / (n - 1)) + l2 * cos(i1 * PI / (n - 1) + j1 * 2 * PI / (2 * n - 1) - PI) + l3 * cos(i1 * PI / (n - 1) + j1 * 2 * PI / (2 * n - 1) + k1 * 2 * PI / (2 * n - 1) - 2 * PI);
                    //    double y1 = l1 * sin(i1 * PI / (n - 1)) + l2 * sin(i1 * PI / (n - 1) + j1 * 2 * PI / (2 * n - 1) - PI) + l3 * sin(i1 * PI / (n - 1) + j1 * 2 * PI / (2 * n - 1) + k1 * 2 * PI / (2 * n - 1) - 2 * PI);
                    //    System.out.println("Reached: " + (0.5 + x1) + " " + y1);
                //    System.out.println("Angles: " + (i1 * PI / (n - 1)) + " " + (j1 * 2 * PI / (2 * n - 1) - PI) + " " + (k1 * 2 * PI / (2 * n - 1) - PI));
                    b = t.getTo().getNumber();
                    break;
                }
            }

            Vertex v = graph.get(b);
            while (v.getNumber() != a) {
                //    System.out.println(v.getNumber() + " " + v.getX() + " " + v.getY());
                //    mm[v.getFi1()][v.getFi2()][v.getFi3()] = 2;
                //    System.out.println("NewAngles: " + (v.getFi1() * PI /(mm.length - 1)) + " " + (v.getFi2() * 2 * PI/(mm[0].length - 1)) + " " + (v.getFi3() * 2 * PI/(mm[0][0].length - 1)));
                result.add(new Point(v.getFi1(), v.getFi2(), v.getFi3(), v.getFi4(), v.getFi5(), v.getFi6()));
                v = v.getPrev();
            }
            //    System.out.println(v.getNumber() + " " + v.getX() + " " + v.getY());
            // mm[v.getFi1()][v.getFi2()][v.getFi3()] = 2;
            result.add(new Point(v.getFi1(), v.getFi2(), v.getFi3(), v.getFi4(), v.getFi5(), v.getFi6()));
        //    System.out.println("NewAngles: " + (v.getFi1() * PI /(space.length - 1)) + " " + (v.getFi2() * 2 * PI/(space[0].length - 1) - PI) + " " + (v.getFi3() * 2 * PI/(space[0][0].length - 1) - PI));
        //    System.out.println("Distance " + graph.get(b).getDistance());

            Collections.reverse(result);
            return result;
        }
    }
}
