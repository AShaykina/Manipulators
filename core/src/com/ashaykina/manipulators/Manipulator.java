package com.ashaykina.manipulators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import static java.lang.Math.*;

class Manipulator {

    private int n;
    private byte[][][] space;
    private short fi1, fi2, fi3;
    double l1, l2, l3;
    private Graph graph;
    double x0;
    double y0;
    byte f;
    private double error;

    boolean goaling = false;
    boolean goalDone = false;
    boolean calcing = false;
    boolean calcDone = false;
    boolean itering = false;
    boolean iterDone = false;

    ArrayList<Point3> steps;

    //ArrayList<Sprite> grid;

    Manipulator(int n, double l1, double l2, double l3, double fi1, double fi2, double fi3, double x, double y, double error) {//, ArrayList<Sprite> grid) {
        //    this.grid = grid;
        this.n = n;
        space = new byte[n][2 * n + 2][2 * n + 2];
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        setPosition((short) (fi1 * n / PI), (short) ((fi2 + PI) * (n + 1) / PI), (short) ((fi3 + PI) * (n + 1) / PI));
        this.x0 = x;
        this.y0 = y;
        this.error = error;
        if (y == 0) f = 1;
        else f = -1;
        setBounds();
        graph = new Graph(space);
    }

    void setPosition(short fi1, short fi2, short fi3) {
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        System.out.println("position " + this.fi1 + " " + this.fi2 + " " + this.fi3);
    }

    private void setBounds() {
        float x1;
        float y1;
        float x2;
        float y2;
        float x3;
        float y3;
        //Углы
        double a; // Угол первого звена
        double b;
        double c;
        for (short i = 0; i < n; i++) {
            for (short j = 0; j < 2 * n + 2; j++) {
                for (short k = 0; k < 2 * n + 2; k++) {
                    if (space[i][j][k] != -1) {
                        a = (i + 1) * PI / (n + 1);
                        b = (j + 1) * 2 * PI / (2 * n + 3) - PI;
                        c = (k + 1) * 2 * PI / (2 * n + 3) - PI;

                        x1 = (float) (x0 + f * (l1 * cos(a)));
                        y1 = (float) (y0 + f * (l1 * sin(a)));
                        if (x1 < error || y1 < error || x1 > 1 - error || y1 > 1 - error) {
                            space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                            //    System.out.println("Coords2 " + 1000 * x1 + " " + 1000 * y1);
                        } else {
                            x2 = (float) (x0 + f * (l1 * cos(a) + l2 * cos(a + b)));
                            y2 = (float) (y0 + f * (l1 * sin(a) + l2 * sin(a + b)));
                            if (x2 < error || y2 < error || x2 > 1 - error || y2 > 1 - error) {
                                space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                                //    System.out.println("Coords3 " + 1000 * x2 + " " + 1000 * y2);
                            } else {
                                x3 = (float) (x0 + f * (l1 * cos(a) + l2 * cos(a + b) + l3 * cos(a + b + c)));
                                y3 = (float) (y0 + f * (l1 * sin(a) + l2 * sin(a + b) + l3 * sin(a + b + c)));
                                if (x3 < error || y3 < error || x3 > 1 - error || y3 > 1 - error) {
                                    space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                                    //    System.out.println("Coords4 " + 1000 * x3 + " " + 1000 * y3);
                                } else {
                                    if (b * c >= 0 && l2 * sin(PI - abs(b)) / sin(abs(c) + abs(b) - PI) <= l3 && (2 * PI - abs(c) - abs(b)) < PI) {
                                        space[i][j][k] = -1; // Задание границ самопересечения.
                                        // System.out.println("C = " + C + "       b = " + b * 180 / PI + "       b' = " + (PI - abs(b)) * 180 / PI + "       c = " + c * 180 / PI + "       c' = " + (PI - abs(c)) * 180 / PI + "       abs(c) + abs(b) - PI = " + (abs(c) + abs(b) - PI) * 180 / PI);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
/*
        if (y0 == 0) {
            Texture t1 = new Texture(Gdx.files.internal("core/assets/linkLavender.png"));
            Sprite sp;
            int p = 0;
            for (int i1 = 0; i1 < n; i1++) {
                for (int j1 = 0; j1 < 2 * n + 2; j1++) {
                    for (int k1 = 0; k1 < 2 * n + 2; k1++) {
                        if (space[i1][j1][k1] != -1) {
                            sp = new Sprite(t1, 7, 373 - 7, 2, 2);
                            x1 = (float) (0.5 + l1 * cos((i1 + 1) * PI / (n + 1)) + l2 * cos((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * cos((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k1 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                            y1 = (float) (l1 * sin((i1 + 1) * PI / (n + 1)) + l2 * sin((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * sin((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k1 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                            sp.setPosition(x1 * 750, y1 * 750);
                            grid.add(sp);

                            p++;

                        }
                    }
                }
            }
            System.out.println("Length " + grid.size());
            System.out.println(p);
        }
*/
    }

    boolean setGoal(double x, double y) {
        if (x > error && x < 1 - error && y > error && y < 1 - error) {
            int p = 0;
            double a; // Угол первого звена
            double b;
            double c;
            double x3;
            double y3;
            for (short i = 0; i < n; i++) {
                for (short j = 0; j < 2 * n + 2; j++) {
                    for (short k = 0; k < 2 * n + 2; k++) {
                        if (space[i][j][k] == -2)
                            space[i][j][k] = 0;  // Нивелирование влияния предыдущего запуска setArea.
                        if (space[i][j][k] != -1 && space[i][j][k] != -3) {
                            a = (i + 1) * PI / (n + 1);
                            b = (j + 1) * 2 * PI / (2 * n + 3) - PI;
                            c = (k + 1) * 2 * PI / (2 * n + 3) - PI;

                            x3 = (float) (x0 + f * (l1 * cos(a) + l2 * cos(a + b) + l3 * cos(a + b + c)));
                            y3 = (float) (y0 + f * (l1 * sin(a) + l2 * sin(a + b) + l3 * sin(a + b + c)));

                            if (abs(x - x3) < error && abs(y - y3) < error) {
                                space[i][j][k] = -2;  // Задание целевой области в конфигурационном пространстве.
                                p++;
                                //    System.out.println(0.5 + x1 + " " + y1);
                            }
                        }
                    }
                }
            }
            return p != 0;
        } else return false;
    }

    boolean setCollision(double x20, double y20, double x21, double y21, double x22, double y22, double x23, double y23) {
        double x1;
        double y1;
        double x2;
        double y2;
        double x3;
        double y3;
        //Углы
        double a; // Угол первого звена
        double b;
        double c;

        for (short i = 0; i < n; i++) {
            for (short j = 0; j < 2 * n + 2; j++) {
                for (short k = 0; k < 2 * n + 2; k++) {
                    if (space[i][j][k] == -3) space[i][j][k] = 0;
                    if (space[i][j][k] != -1) {
                        a = (i + 1) * PI / (n + 1);
                        b = (j + 1) * 2 * PI / (2 * n + 3) - PI;
                        c = (k + 1) * 2 * PI / (2 * n + 3) - PI;

                        x1 = (float) (x0 + f * (l1 * cos(a)));
                        y1 = (float) (y0 + f * (l1 * sin(a)));

                        x2 = (float) (x0 + f * (l1 * cos(a) + l2 * cos(a + b)));
                        y2 = (float) (y0 + f * (l1 * sin(a) + l2 * sin(a + b)));

                        x3 = (float) (x0 + f * (l1 * cos(a) + l2 * cos(a + b) + l3 * cos(a + b + c)));
                        y3 = (float) (y0 + f * (l1 * sin(a) + l2 * sin(a + b) + l3 * sin(a + b + c)));

                        if (intersect(x0, y0, x1, y1, x22, y22, x23, y23) ||
                                intersect(x0, y0, x1, y1, x22, y22, x21, y21) ||
                                intersect(x1, y1, x2, y2, x20, y20, x21, y21) ||
                                intersect(x1, y1, x2, y2, x21, y21, x22, y22) ||
                                intersect(x1, y1, x2, y2, x22, y22, x23, y23) ||
                                intersect(x2, y2, x3, y3, x20, y20, x21, y21) ||
                                intersect(x2, y2, x3, y3, x21, y21, x22, y22) ||
                                intersect(x2, y2, x3, y3, x22, y22, x23, y23)) space[i][j][k] = -3;

                    }
                }
            }
        }
        return true;
    }

    private boolean intersect(double x11, double y11, double x12, double y12, double x21, double y21, double x22, double y22) {
        return intersect_1(x11, x12, x21, x22)
                && intersect_1(y11, y12, y21, y22)
                && area(x11, y11, x12, y12, x21, y21) * area(x11, y11, x12, y12, x22, y22) <= 0
                && area(x21, y21, x22, y22, x11, y11) * area(x21, y21, x22, y22, x12, y12) <= 0;
    }

    private boolean intersect_1(double a, double b, double c, double d) {
        double e;
        if (a > b) {
            e = a;
            a = b;
            b = e;
        }
        if (c > d) {
            e = c;
            c = d;
            d = e;
        }
        return max(a, c) <= min(b, d);
    }

    private double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
    }

    ArrayList<Point3> calculate() {
        //  System.out.println("angles " + fi1 + " " + fi2 + " " + fi3);
        return graph.dijkstra(fi1, fi2, fi3);
    }

}
