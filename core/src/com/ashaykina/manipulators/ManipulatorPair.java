package com.ashaykina.manipulators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import static java.lang.Math.*;
import static java.lang.Math.abs;

public class ManipulatorPair {
    private int n;
    private byte[][][][][][] space;
    private int fi1, fi2, fi3, fi4, fi5, fi6;
    private double l1, l2, l3, l4, l5, l6;
//    private GraphPair graph;
    private double error;

    ArrayList<Sprite> grid;

    long time;

    public ManipulatorPair(int n, double l1, double l2, double l3, double fi1, double fi2, double fi3, double l4, double l5, double l6, double fi4, double fi5, double fi6, ArrayList<Sprite> grid, double error) {
        this.grid = grid;
        this.error = error;
        time = System.currentTimeMillis();
        this.n = n;
        space = new byte[n][2 * n + 2][2 * n + 2][n][2 * n + 2][2 * n + 2];
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.l5 = l5;
        this.l6 = l6;
        System.out.println("Space " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
     //   System.out.println(fi1 + " " + fi2 + " " + fi3 + " " + fi4 + " " + fi5 + " " + fi6);
        setPosition((int) (fi1 * n / PI), (int) ((fi2 + PI) * (n + 1) / PI), (int) ((fi3 + PI) * (n + 1) / PI), (int) (fi4 * n / PI), (int) ((fi5 + PI) * (n + 1) / PI), (int) ((fi6 + PI) * (n + 1) / PI));
    //    System.out.println("Position " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        setBounds();
        System.out.println("Bounds " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        //  setBoundsR();
    //    graph = new GraphPair(space);
        System.out.println("Graph " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
    }


    public void setPosition(int fi1, int fi2, int fi3, int fi4, int fi5, int fi6) {
        this.fi1 = fi1;
        this.fi2 = fi2;
        this.fi3 = fi3;
        this.fi4 = fi4;
        this.fi5 = fi5;
        this.fi6 = fi6;
        System.out.println("position " + this.fi1 + " " + this.fi2 + " " + this.fi3 + " " + this.fi4 + " " + this.fi5 + " " + this.fi6);
    }

    public void setBounds() {
        float x11;
        float y11;
        float x21;
        float y21;
        float x31;
        float y31;
        float x12;
        float y12;
        float x22;
        float y22;
        float x32;
        float y32;
        float x01 = (float) 0.5;
        float y01 = 0;
        float x02 = (float) 0.5;
        float y02 = 1;
        //Углы
        double a; // Угол первого звена
        double b;
        double c;
        double a2;
        double b2;
        double c2;
        //Стороны
        double B = l2;
        double C;
        double C2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * n + 2; j++) {
                for (int k = 0; k < 2 * n + 2; k++) {
                    for (int i1 = 0; i1 < n; i1++) {
                        for (int j1 = 0; j1 < 2 * n + 2; j1++) {
                            for (int k1 = 0; k1 < 2 * n + 2; k1++) {
                                if (space[i][j][k][i1][j1][k1] != -1) {
                                    a = (i + 1) * PI / (n + 1);
                                    b = (j + 1) * 2 * PI / (2 * (n + 1) + 1) - PI;
                                    c = (k + 1) * 2 * PI / (2 * (n + 1) + 1) - PI;
                                    a2 = (i1 + 1) * PI / (n + 1);
                                    b2 = (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI;
                                    c2 = (k1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI;
                                //    if (i == 0 || i == n - 1 || j == 0 || j == 2 * n - 1 || k == 0 || k == 2 * n - 1 || i1 == 0 || i1 == n - 1 || j1 == 0 || j1 == 2 * n - 1 || k1 == 0 || k1 == 2 * n - 1) {
                                //        space[i][j][k][i1][j1][k1] = -1;
                                //    } else {
                                        x11 = (float) (0.5 + l1 * cos(a));
                                        y11 = (float) (l1 * sin(a));
                                        x12 = (float) (0.5 - l1 * cos(a2));
                                        y12 = (float) (1 - l1 * sin(a2));
                                    //    if (x11 < 0.1 || y11 < 0.1 || x11 > 0.9 || y11 > 0.9 || x12 < 0.1 || y12 < 0.1 || x12 > 0.9 || y12 > 0.9) {
                                    //        space[i][j][k][i1][j1][k1] = -1;  // Задание границ (стенок) рабочего пространства. Это не надо - оно уже заведомо не выходит!
                                    //    } else {
                                            x21 = (float) (0.5 + l1 * cos(a) + l2 * cos(a + b));
                                            y21 = (float) (l1 * sin(a) + l2 * sin(a + b));
                                            x22 = (float) (0.5 - (l1 * cos(a2) + l2 * cos(a2 + b2)));
                                            y22 = (float) (1 - (l1 * sin(a2) + l2 * sin(a2 + b2)));
                                            if (x21 < error || y21 < error || x21 > 1 - error || y21 > 1 - error || x22 < error || y22 < error || x22 > 1 - error || y22 > 1 - error) {
                                                space[i][j][k][i1][j1][k1] = -1;  // Задание границ (стенок) рабочего пространства.
                                            } else {
                                                if (!(min(x11, x21) > max(x12, x22) || max(x11, x21) < min(x12, x22) || min(y11, y21) > max(y12, y22) || max(y11, y21) < min(y12, y22))) { // Прямоугольники
                                                    // if () { // Отрезки
                                                    space[i][j][k][i1][j1][k1] = -1;
                                                    //  }
                                                } else {
                                                    x31 = (float) (0.5 + l1 * cos(a) + l2 * cos(a + b) + l3 * cos(a + b + c));
                                                    y31 = (float) (l1 * sin(a) + l2 * sin(a + b) + l3 * sin(a + b + c));
                                                    x32 = (float) (0.5 - (l1 * cos(a2) + l2 * cos(a2 + b2) + l3 * cos(a2 + b2 + c2)));
                                                    y32 = (float) (1 - (l1 * sin(a2) + l2 * sin(a2 + b2) + l3 * sin(a2 + b2 + c2)));
                                                    if (x31 < error || y31 < error || x31 > 1 - error || y31 > 1 - error || x32 < error || y32 < error || x32 > 1 - error || y32 > 1 - error) {
                                                        space[i][j][k][i1][j1][k1] = -1;  // Задание границ (стенок) рабочего пространства.
                                                    } else {
                                                        C = B * sin(PI - abs(b)) / sin(abs(c) + abs(b) - PI);
                                                        C2 = B * sin(PI - abs(b2)) / sin(abs(c2) + abs(b2) - PI);
                                                        if ((b * c >= 0 && C <= l3 && (2 * PI - abs(c) - abs(b)) < PI) || ((b2 * c2 >= 0 && C2 <= l3 && (2 * PI - abs(c2) - abs(b2)) < PI))) {
                                                            space[i][j][k][i1][j1][k1] = -1; // Задание границ самопересечения.
                                                        } else if (!(min(x11, x01) > max(x22, x32) || max(x11, x01) < min(x22, x32) || min(y11, y01) > max(y22, y32) || max(y11, y01) < min(y22, y32)) ||
                                                                !(min(x12, x02) > max(x21, x31) || max(x12, x02) < min(x21, x31) || min(y12, y02) > max(y21, y31) || max(y12, y02) < min(y21, y31)) ||
                                                                !(min(x11, x21) > max(x22, x32) || max(x11, x21) < min(x22, x32) || min(y11, y21) > max(y22, y32) || max(y11, y21) < min(y22, y32)) ||
                                                                !(min(x12, x22) > max(x21, x31) || max(x12, x22) < min(x21, x31) || min(y12, y22) > max(y21, y31) || max(y12, y22) < min(y21, y31)) ||
                                                                !(min(x32, x22) > max(x21, x31) || max(x32, x22) < min(x21, x31) || min(y32, y22) > max(y21, y31) || max(y32, y22) < min(y21, y31))) { // Прямоугольники
                                                            // if () { // Отрезки
                                                            space[i][j][k][i1][j1][k1] = -1;
                                                            //  }
                                                        }
                                                    }

                                                }
                                        //    }
                                    //    }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        double x1;
        double y1;
        double x2;
        double y2;
        Texture t1 = new Texture(Gdx.files.internal("core/assets/linkLavender.png"));
        Texture t2 = new Texture(Gdx.files.internal("core/assets/linkRed.png"));
        Sprite sp;
        int p = 0;
        for (int i1 = 0; i1 < n; i1++) {
            for (int j1 = 0; j1 < 2 * n + 2; j1++) {
                for (int k1 = 0; k1 < 2 * n + 2; k1++) {
                    for (int i2 = 0; i2 < n; i2++) {
                        for (int j2 = 0; j2 < 2 * n + 2; j2++) {
                            for (int k2 = 0; k2 < 2 * n + 2; k2++) {
                                if (space[i1][j1][k1][i2][j2][k2] != -1) {
                                    sp = new Sprite(t1, 7, 373 - 7, 2, 2);
                                    x1 = 0.5 + l1 * cos((i1 + 1) * PI / (n + 1)) + l2 * cos((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * cos((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k1 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI);
                                    y1 = l1 * sin((i1 + 1) * PI / (n + 1)) + l2 * sin((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * sin((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k1 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI);
                                    sp.setPosition((float) x1 * 750, (float) y1 * 750);
                                    grid.add(sp);
                                //    sp = new Sprite(t2, 7, 373 - 7, 2, 2);
                                //    x2 = 0.5 - (l4 * cos((i2 + 1) * PI / (n + 1)) + l5 * cos((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l6 * cos((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k2 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                                //    y2 = 1 - (l4 * sin((i2 + 1) * PI / (n + 1)) + l5 * sin((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l6 * sin((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k2 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                                //    sp.setPosition((float) x2 * 750, (float) y2 * 750);
                                //    grid.add(sp);

                                    p++;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Length " + grid.size());
        System.out.println(p);
    }

    public boolean setArea(double x0, double y0, double x, double y) {
        if (x0 > error && x0 < 1 - error && y0 > error && y0 < 1 - error && x > error && x < 1 - error && y > error && y < 1 - error) {
            double x1;
            double y1;
            double x2;
            double y2;
            int p = 0;
            int o = 1; //коэффициент для области
            for (int i1 = 0; i1 < n; i1++) {
                for (int j1 = 0; j1 < 2 * n + 2; j1++) {
                    for (int k1 = 0; k1 < 2 * n + 2; k1++) {
                        for (int i2 = 0; i2 < n; i2++) {
                            for (int j2 = 0; j2 < 2 * n + 2; j2++) {
                                for (int k2 = 0; k2 < 2 * n + 2; k2++) {
                                    if (space[i1][j1][k1][i2][j2][k2] != -1) {
                                        x1 = 0.5 + l1 * cos((i1 + 1) * PI / (n + 1)) + l2 * cos((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * cos((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k1 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI);
                                        y1 = l1 * sin((i1 + 1) * PI / (n + 1)) + l2 * sin((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l3 * sin((i1 + 1) * PI / (n + 1) + (j1 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k1 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI);
                                        x2 = 0.5 - (l4 * cos((i2 + 1) * PI / (n + 1)) + l5 * cos((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l6 * cos((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k2 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                                        y2 = 1 - (l4 * sin((i2 + 1) * PI / (n + 1)) + l5 * sin((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) - PI) + l6 * sin((i2 + 1) * PI / (n + 1) + (j2 + 1) * 2 * PI / (2 * (n + 1) + 1) + (k2 + 1) * 2 * PI / (2 * (n + 1) + 1) - 2 * PI));
                                        if (abs(x0 - x1) < o * 0.01 && abs(y0 - y1) < o * 0.01 && abs(x - x2) < o * 0.01 && abs(y - y2) < o * 0.01) {
                                            space[i1][j1][k1][i2][j2][k2] = -2;  // Задание целевой области в конфигурационном пространстве.
                                            p++;
                                            //    System.out.println(0.5 + x1 + " " + y1);
                                        } else if (space[i1][j1][k1][i2][j2][k2] == -2)
                                            space[i1][j1][k1][i2][j2][k2] = 0;  // Нивелирование влияния предыдущего запуска setArea.
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (p != 0) return true;
            else return false;
        } else {
            return false;
        }
    }
/*
    public ArrayList<Point3> calculate() throws Exception {
        //    System.out.println("angles " + fi1 + " " + fi2 + " " + fi3);
        return graph.dijkstra(fi1, fi2, fi3, fi4, fi5, fi6);
    }
*/
}
