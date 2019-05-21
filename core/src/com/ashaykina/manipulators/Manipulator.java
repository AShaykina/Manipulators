package com.ashaykina.manipulators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Manipulator {

    private int n;
    private byte[][][] space;
    private int fi1, fi2, fi3;
    private double l1, l2, l3;
    private Graph graph;

    ArrayList<Sprite> grid;

    public Manipulator(int n, double l1, double l2, double l3, double fi1, double fi2, double fi3, double X, double Y, char C, ArrayList<Sprite> grid) {
        this.grid = grid;
        this.n = n;
        space = new byte[n][2 * n + 2][2 * n + 2];
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        setPosition((int) (fi1 * n / PI), (int) ((fi2 + PI) * (n + 1) / PI), (int) ((fi3 + PI) * (n + 1) / PI));
        if (C == 'L') {
            setBoundsL();
        } else setBoundsR();
        graph = new Graph(space);
    }


    public void setPosition(int fi1, int fi2, int fi3) {
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        System.out.println("position " + this.fi1 + " " + this.fi2 + " " + this.fi3);
    }

    public void setBoundsL() {
        float x1 = 0;
        float y1 = 0;
        float x2 = 0;
        float y2 = 0;
        float x3 = 0;
        float y3 = 0;
        //Углы
        double a = 0; // Угол первого звена
        double b = 0;
        double c = 0;
        // b'= PI - abs(b)
        //Стороны
        double B = l2;
        double C = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * n + 2; j++) {
                for (int k = 0; k < 2 * n + 2; k++) {
                    if (space[i][j][k] != -1) {
                        a = (i + 1) * PI / (n + 1);
                        b = (j + 1) * 2 * PI / (2 * n + 3) - PI;
                        c = (k + 1) * 2 * PI / (2 * n + 3) - PI;

                        x1 = (float) (0.5 + l1 * cos(a));
                        y1 = (float) (l1 * sin(a));
                        if (x1 < 0.001 || y1 < 0.001 || x1 > 0.999 || y1 > 0.999) {
                            space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                            //    System.out.println("Coords2 " + 1000 * x1 + " " + 1000 * y1);
                        } else {
                            x2 = (float) (0.5 + l1 * cos(a) + l2 * cos(a + b));
                            y2 = (float) (l1 * sin(a) + l2 * sin(a + b));
                            if (x2 < 0.001 || y2 < 0.001 || x2 > 0.999 || y2 > 0.999) {

                                space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                                //    System.out.println("Coords3 " + 1000 * x2 + " " + 1000 * y2);
                            } else {
                                x3 = (float) (0.5 + l1 * cos(a) + l2 * cos(a + b) + l3 * cos(a + b + c));
                                y3 = (float) (l1 * sin(a) + l2 * sin(a + b) + l3 * sin(a + b + c));
                                if (x3 < 0.001 || y3 < 0.001 || x3 > 0.999 || y3 > 0.999) {
                                    space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                                    //    System.out.println("Coords4 " + 1000 * x3 + " " + 1000 * y3);
                                } else {
                                    C = B * sin(PI - abs(b)) / sin(abs(c) + abs(b) - PI);
                                    if (b * c >= 0 && C <= l3 && (2 * PI - abs(c) - abs(b)) < PI) {
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
        */
    }

    public void setBoundsR() {
        float x1 = 0;
        float y1 = 0;
        float x2 = 0;
        float y2 = 0;
        float x3 = 0;
        float y3 = 0;
        //Углы
        double a = 0; // Угол первого звена
        double b = 0;
        double c = 0;
        // b'= PI - abs(b)
        //Стороны
        double B = l2;
        double C = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * n; j++) {
                for (int k = 0; k < 2 * n; k++) {
                //    a = i * PI / (n - 1);
                //    b = j * 2 * PI / (2 * n - 1) - PI;
                //    c = k * 2 * PI / (2 * n - 1) - PI;

                    a = (i + 1) * PI / (n + 1);
                    b = (j + 1) * 2 * PI / (2 * n + 3) - PI;
                    c = (k + 1) * 2 * PI / (2 * n + 3) - PI;

                    x1 = (float) (0.5 - l1 * cos(a));
                    y1 = (float) (1 - l1 * sin(a));
                    if (x1 < 0.001 || y1 < 0.001 || x1 > 0.999 || y1 > 0.999) {
                        space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                        //  System.out.println("Coords2 " + 1000 * x1 + " " + 1000 * y1);
                    } else {
                        x2 = (float) (0.5 - (l1 * cos(a) + l2 * cos(a + b)));
                        y2 = (float) (1 - (l1 * sin(a) + l2 * sin(a + b)));
                        if (x2 < 0.001 || y2 < 0.001 || x2 > 0.999 || y2 > 0.999) {
                            space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                            //       System.out.println("Coords3 " + 1000 * x2 + " " + 1000 * y2);
                        } else {
                            x3 = (float) (0.5 - (l1 * cos(a) + l2 * cos(a + b) + l3 * cos(a + b + c)));
                            y3 = (float) (1 - (l1 * sin(a) + l2 * sin(a + b) + l3 * sin(a + b + c)));
                            if (x3 < 0.001 || y3 < 0.001 || x3 > 0.999 || y3 > 0.999) {
                                space[i][j][k] = -1;  // Задание границ (стенок) рабочего пространства.
                                //         System.out.println("Coords4 " + 1000 * x3 + " " + 1000 * y3);
                            } else {
                                C = B * sin(PI - abs(b)) / sin(abs(c) + abs(b) - PI);
                                if (b * c >= 0 && C <= l3 && (2 * PI - abs(c) - abs(b)) < PI) {
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

    public boolean setArea(double x, double y) {
        if (x > 0.001 && x < 0.999 && y > 0.001 && y < 0.999) {
            double x1 = 0;
            double y1 = 0;
            int o = 1; //коэффициент для области
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 2 * n; j++) {
                    for (int k = 0; k < 2 * n; k++) {
                        if (space[i][j][k] != -1) {
                            x1 = 0.5 + l1 * cos((i + 1) * PI / (n + 1)) + l2 * cos((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * cos((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) + (k + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI);
                            y1 = l1 * sin((i + 1) * PI / (n + 1)) + l2 * sin((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * sin((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) + (k + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI);
                            if (abs(x - x1) < o * 0.001 && abs(y - y1) < o * 0.001) {
                                space[i][j][k] = -2;  // Задание целевой области в конфигурационном пространстве.
                                //    System.out.println(0.5 + x1 + " " + y1);
                            } else if (space[i][j][k] == -2)
                                space[i][j][k] = 0;  // Нивелирование влияния предыдущего запуска setArea.
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean setAreaR(double x, double y) {
        if (x > 0.001 && x < 0.999 && y > 0.001 && y < 0.999) {
            double x1 = 0;
            double y1 = 0;
            int o = 1; //коэффициент для области
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 2 * n; j++) {
                    for (int k = 0; k < 2 * n; k++) {
                        if (space[i][j][k] != -1) {
                            x1 = 0.5 - (l1 * cos((i + 1) * PI / (n + 1)) + l2 * cos((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * cos((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) + (k + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                            y1 = 1 - (l1 * sin((i + 1) * PI / (n + 1)) + l2 * sin((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * sin((i + 1) * PI / (n + 1) + (j + 1) * 2 * PI / (2 * (n + 1) + 1) + (k + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                            if (abs(x - x1) < o * 0.001 && abs(y - y1) < o * 0.001) {
                                space[i][j][k] = -2;  // Задание целевой области в конфигурационном пространстве.
                                //    System.out.println(0.5 + x1 + " " + y1);
                            } else if (space[i][j][k] == -2)
                                space[i][j][k] = 0;  // Нивелирование влияния предыдущего запуска setArea.
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Point> calculate() throws Exception {
        System.out.println("angles " + fi1 + " " + fi2 + " " + fi3);
        return graph.dijkstra(fi1, fi2, fi3);
    }

    public void clearArea() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * n; j++) {
                for (int k = 0; k < 2 * n; k++) {
                    if (space[i][j][k] == -2) space[i][j][k] = 0;  // Нивелирование влияния предыдущего запуска setArea.
                }
            }
        }
    }
}
