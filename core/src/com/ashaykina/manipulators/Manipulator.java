package com.ashaykina.manipulators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import static java.lang.Math.*;

class Manipulator {

    private Graph graph;
    private byte[][][] space;
    private int n;
    private double error;
    private short fi1, fi2, fi3;
    double l1, l2, l3;
    double x0, y0;
    byte f;
    Point3 a, w;

    byte state = 0;
    boolean doing = false;

    ArrayList<Point3> steps, forces;

    //ArrayList<Sprite> grid;

    Manipulator(int n, double l1, double l2, double l3, double fi1, double fi2, double fi3, double x, double y, double error) {//, ArrayList<Sprite> grid) {
        //    this.grid = grid;
        this.n = n;
        space = new byte[n][2 * n + 1][2 * n + 1];
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        setPosition((short) (fi1 * (n + 1) / PI - 1), (short) ((fi2 + PI) * (n + 1) / PI - 1), (short) ((fi3 + PI) * (n + 1) / PI - 1));
        this.x0 = x;
        this.y0 = y;
        this.error = error;
        if (y == 0) f = 1;
        else f = -1;
        a = new Point3((this.fi1 + 1) * PI / (space.length + 1), (this.fi2 + 1) * 2 * PI / (space[0].length + 1) - PI, (this.fi3 + 1) * 2 * PI / (space[0][0].length + 1) - PI);
        //System.out.println(fi1 + " " + a.fi1 + " " + fi2 + " " + a.fi2 + " " + fi3 + " " + a.fi3);
        w = new Point3(0, 0, 0);
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
        float x1, y1, x2, y2, x3, y3;
        double a, b, c; //Углы
        for (short i = 0; i < n; i++) {
            for (short j = 0; j < 2 * n + 1; j++) {
                for (short k = 0; k < 2 * n + 1; k++) {
                    if (space[i][j][k] != -1) {
                        a = (i + 1) * PI / (n + 1);
                        b = (j + 1) * PI / (n + 1) - PI;
                        c = (k + 1) * PI / (n + 1) - PI;

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
            double x3, y3;
            double a, b, c; //Углы
            for (short i = 0; i < n; i++) {
                for (short j = 0; j < 2 * n + 1; j++) {
                    for (short k = 0; k < 2 * n + 1; k++) {
                        if (space[i][j][k] == -2)
                            space[i][j][k] = 0;  // Нивелирование влияния предыдущего запуска setArea.
                        if (space[i][j][k] != -1 && space[i][j][k] != -3) {
                            a = (i + 1) * PI / (n + 1);
                            b = (j + 1) * PI / (n + 1) - PI;
                            c = (k + 1) * PI / (n + 1) - PI;

                            x3 = (float) (x0 + f * (l1 * cos(a) + l2 * cos(a + b) + l3 * cos(a + b + c)));
                            y3 = (float) (y0 + f * (l1 * sin(a) + l2 * sin(a + b) + l3 * sin(a + b + c)));

                            if (abs(x - x3) < error && abs(y - y3) < error) {
                                space[i][j][k] = -2;  // Задание целевой области в конфигурационном пространстве.
                                p++;
                            }
                        }
                    }
                }
            }
            System.out.println();
            System.out.println("This is p " + p);
            System.out.println();
            return p != 0;
        } else return false;
    }

    boolean setCollision(double x20, double y20, double x21, double y21, double x22, double y22, double x23, double y23) {
        float x1, y1, x2, y2, x3, y3;
        double a, b, c; //Углы
        for (short i = 0; i < n; i++) {
            for (short j = 0; j < 2 * n + 1; j++) {
                for (short k = 0; k < 2 * n + 1; k++) {
                    if (space[i][j][k] == -3) space[i][j][k] = 0;
                    if (space[i][j][k] != -1) {
                        a = (i + 1) * PI / (n + 1);
                        b = (j + 1) * PI / (n + 1) - PI;
                        c = (k + 1) * PI / (n + 1) - PI;

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
            e = a; a = b; b = e;
        }
        if (c > d) {
            e = c; c = d; d = e;
        }
        return max(a, c) <= min(b, d);
    }

    private double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
    }

    boolean calculate() {
        //  System.out.println("angles " + fi1 + " " + fi2 + " " + fi3);
        steps = graph.dijkstra(fi1, fi2, fi3);
        if (steps != null) forces = forcing(steps, 2, 1 / 60f);
        return steps != null && !steps.isEmpty();
    }

    private ArrayList<Point3> forcing(ArrayList<Point3> lined, double fm, float dt) {
        ArrayList<Point3> forces = new ArrayList<>();
        double df1, df2, df3, df, fk;
        int m;

        for (int i = 0; i < lined.size() - 1; i++) {
            df1 = lined.get(i + 1).fi1 - lined.get(i).fi1;
            df2 = lined.get(i + 1).fi2 - lined.get(i).fi2;
            df3 = lined.get(i + 1).fi3 - lined.get(i).fi3;
            df = max(max(abs(df1), abs(df2)), abs(df3));

            m = (int) ceil(-0.5 + sqrt(1.0 / 4 + df / (fm * dt * dt)));
            //m = (int) ceil(sqrt(df / (fm * dt * dt)));

            for (int k = 1; k < m; k++) {
                fk = (df - (m - k) * (m + k + 1) * fm * dt * dt) / ((k + 1) * k * dt * dt);
                // fk = df / ((k + 1) * k * dt * dt) - (m - k) * fm * (m + 1 + k) / (k * (k + 1));
                //fk = (df - fm * dt * dt * (m - k) * (k + m)) / (k * k * dt * dt);
                if (abs(fk) < fm) {
                    for (int j = 0; j < m - k; j++)
                        forces.add(new Point3(signum(df1) * fm, signum(df2) * fm, signum(df3) * fm));
                    for (int j = 0; j < k; j++)
                        forces.add(new Point3(signum(df1) * signum(df1) * fk, signum(df2) * signum(df2) * fk, signum(df3) * signum(df3) * fk));
                    forces.add(new Point3(0, 0, 0));
                    for (int j = 0; j < k; j++)
                        forces.add(new Point3((-1) * signum(df1) * signum(df1) * fk, (-1) * signum(df2) * signum(df2) * fk, (-1) * signum(df3) * signum(df3) * fk));
                    for (int j = 0; j < m - k; j++)
                        forces.add(new Point3((-1) * signum(df1) * fm, (-1) * signum(df2) * fm, (-1) * signum(df3) * fm));
                    break;
                }
            }
/*
            Point3 p0 = new Point3(0, 0, 0);
            Point3 v0 = new Point3(0, 0, 0);
            Point3 p;
            for (int j = forces.size() - 2 * m - 1; j < forces.size(); j++) {
                p = forces.get(j);
                p0.fi1 += v0.fi1 * dt;
                p0.fi2 += v0.fi2 * dt;
                p0.fi3 += v0.fi3 * dt;
                v0.fi1 += p.fi1 * dt;
                v0.fi2 += p.fi2 * dt;
                v0.fi3 += p.fi3 * dt;
            }
            System.out.println(df - max(max(abs(p0.fi1), abs(p0.fi2)), abs(p0.fi3)) + " " + df1 + " " + df2 + " " + df3);
*/
        }
        return forces;
    }
}
