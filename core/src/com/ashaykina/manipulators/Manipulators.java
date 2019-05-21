package com.ashaykina.manipulators;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class Manipulators extends ApplicationAdapter {
    int field = 750;
    double error = 0.001;
    int n = 150; // 12 - уже падает

    SpriteBatch batch;
    //Параметры лавандового манипулятора
    Sprite link1L;
    Sprite link2L;
    Sprite link3L;
    Sprite goalL;

    double l1L = 0.372677996;
    double l2L = 0.372677996;
    double l3L = 0.372677996;

    double fi1L = 1.1071487177941;
    double fi2L = PI - fi1L + 0.2;
    double fi3L = - fi2L + 1;

    //Параметры красного манипулятора
    Sprite link1R;
    Sprite link2R;
    Sprite link3R;
    Sprite goalR;


    ArrayList<Sprite> grid;

    double l1R = 0.372677996;
    double l2R = 0.372677996;
    double l3R = 0.372677996;

    double fi1R = 1.1071487177941;
    double fi2R = PI - fi1R + 0.2;
    double fi3R = - fi2R + 1;

    double f1;
    double f2;
    double f3;
    double f4;
    double f5;
    double f6;

    Manipulator maniLavender;
    Manipulator maniRed;
    ManipulatorPair maniPair;
    Random random;
    ArrayList<Point> stepsL;
    ArrayList<Point> stepsR;
    ArrayList<Point> steps;

    Thread ml;

    Boolean goingRed;

    boolean goalingL = false;
    boolean goalDoneL = false;
    boolean calcingL = false;
    boolean calcDoneL = false;
    boolean iteringL = false;
    boolean iterDoneL = false;

    boolean goalingR = false;
    boolean goalDoneR = false;
    boolean calcingR = false;
    boolean calcDoneR = false;
    boolean iteringR = false;
    boolean iterDoneR = false;

    boolean goaling = false;
    boolean goalDone = false;
    boolean calcing = false;
    boolean calcDone = false;
    boolean itering = false;
    boolean iterDone = false;

    int r = 9;
    int height = (int)round(373 * field / 1000.0);

    double dt = 0;

    long time = System.currentTimeMillis();
    int i = 0;
    int j = 0;

    @Override
    public void create() {

        random = new Random();

        maniLavender = new Manipulator(n, l1L, l2L, l3L, fi1L, fi2L, fi3L, 0.5, 0, 'L', grid);
        maniRed = new Manipulator(n, l1R, l2R, l3R, fi1R, fi2R, fi3R, 0.5, 1, 'R', grid);

        batch = new SpriteBatch();

        goalL = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373 - 2*r, 2*r, 2*r);
        goalL.setOrigin(r, r);

        goalR = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373 - 2*r, 2*r, 2*r);
        goalR.setOrigin(r, r);


        link1L = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373-height, 2*r, height);
        link1L.setOrigin(r, r);  // Центр вращения.
        link1L.setPosition(field * (float) 0.5 - r, 0 - r);
        link1L.setRotation((float) (fi1L * 180 / PI - 90));

        link2L = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373-height, 2*r, height);
        link2L.setOrigin(r, r);  // Центр вращения.
        link2L.setPosition(field * (float) (0.5 + l1L * cos(fi1L)) - r, field * (float) (l1L * sin(fi1L)) - r);
        link2L.setRotation((float) ((fi2L + fi1L) * 180 / PI - 90));

        link3L = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373-height, 2*r, height);
        link3L.setOrigin(r, r);  // Центр вращения.
        link3L.setPosition(field * (float) (0.5 + l1L * cos(fi1L) + l2L * cos(fi1L + fi2L)) - r, field * (float) (l1L * sin(fi1L) + l2L * sin(fi1L + fi2L)) - r);
        link3L.setRotation((float) ((fi3L + fi2L + fi1L) * 180 / PI - 90));

        link1R = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373-height, 2*r, height);
        link1R.setOrigin(r, r);  // Центр вращения.
        link1R.setPosition(field * (float) 0.5 - r, field - r);
        link1R.setRotation((float) (fi1R * 180 / PI + 90));

        link2R = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373-height, 2*r, height);
        link2R.setOrigin(r, r);  // Центр вращения.
        link2R.setPosition(field * (float) (0.5 - l1R * cos(fi1R)) - r, field * (float) (1 -(l1R * sin(fi1R))) - r );
        link2R.setRotation((float) ((fi2R + fi1R) * 180 / PI + 90));

        link3R = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373-height, 2*r, height);
        link3R.setOrigin(r, r);  // Центр вращения.
        link3R.setPosition(field * (float) (0.5 - (l1R * cos(fi1R) + l2R * cos(fi1R + fi2R))) - r, field * (float) (1-(l1R * sin(fi1R) + l2R * sin(fi1R + fi2R))) - r);
        link3R.setRotation((float) ((fi3R + fi2R + fi1R) * 180 / PI + 90));

        grid = new ArrayList<>();


      //  maniPair = new ManipulatorPair(n, l1L, l2L, l3L, fi1L, fi2L, fi3L, l1R, l2R, l3R, fi1R, fi2R, fi3R, grid, error);

        setGoalR();
        goalDoneR = true;
        calcR();
        calcDoneR = true;
        goingRed = true;

    }

    void runCalcL(final Manipulators ms) {
        ml = new Thread(new Runnable() {
            @Override
            public void run() {
                ms.calcL();
            }
        });
        ml.start();
    }

    void runGoalL(final Manipulators ms) {
        ml = new Thread(new Runnable() {
            @Override
            public void run() {
                ms.setGoalL();
            }
        });
        ml.start();
    }

    void runCalcR(final Manipulators ms) {
        ml = new Thread(new Runnable() {
            @Override
            public void run() {
                ms.calcR();
            }
        });
        ml.start();
    }

    void runGoalR(final Manipulators ms) {
        ml = new Thread(new Runnable() {
            @Override
            public void run() {
                ms.setGoalR();
            }
        });
        ml.start();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        link1L.draw(batch);
        link2L.draw(batch);
        link3L.draw(batch);
        goalL.draw(batch);

        link1R.draw(batch);
        link2R.draw(batch);
        link3R.draw(batch);
        goalR.draw(batch);

    //    for(Sprite sp : grid) {
    //        sp.draw(batch);
    //    }

        batch.end();

/*
        if (!goalDone) {
            if (!goaling) setGoal();
        } else if (!calcDone) {
            if (!calcing) calc();
        } else if (!iterDone) {
            if (!itering) {
                if (dt > 15) {
                    dt = 0;
                    iter();
                } else {
                    dt++;
                }
            }
        } else {
            if (dt > 60) {
                dt = 0;
                goalDone = false;
                calcDone = false;
                iterDone = false;
            } else {
                dt++;
            }
        }
        */

        if (goingRed) {
            if (!goalDoneL && !goalingL) runGoalL(this);
            else if (!calcDoneL && !calcingL) runCalcL(this);
            if (!iterDoneR && !iteringR) {
                dt = 0;
                iterR();
            }
            if (goalDoneL && calcDoneL && iterDoneR) {
                goalDoneR = false;
                calcDoneR = false;
                iterDoneR = false;
                goingRed = false;
            }
        } else {
            if (!goalDoneR && !goalingR) runGoalR(this);
            else if (!calcDoneR && !calcingR) runCalcR(this);
            if (!iterDoneL && !iteringL) {
                dt = 0;
                iterL();
            }
            if (goalDoneR && calcDoneR && iterDoneL) {
                goalDoneL = false;
                calcDoneL = false;
                iterDoneL = false;
                goingRed = true;
            }
        }

    }

    //Генерирование цели и обозначение её в матрице
    public void setGoalL() {
    //    long l = System.currentTimeMillis();
        goalingL = true;
        float xL = 0;
        float yL = 0;
        while(!goalDoneL) {
            xL = random.nextFloat();
            yL = random.nextFloat();
        //    System.out.println("GoalL: " + xL + " " + yL);
            goalDoneL = maniLavender.setArea(xL, yL);
        }
        goalL.setPosition(field * xL - r, field * yL - r);
        goalingL = false;
    //    System.out.println("Goal: " + (System.currentTimeMillis() - l));
    }

    public void setGoalR() {
        goalingR = true;
        float xR = 0;
        float yR = 0;
        while(!goalDoneR) {
            xR = random.nextFloat();
            yR = random.nextFloat();
        //    System.out.println("GoalR: " + xR + " " + yR);
            goalDoneR = maniRed.setAreaR(xR, yR);
        }
        goalR.setPosition(field * xR - r, field * yR - r);
        goalingR = false;
    }

    public void calcL() {
        calcingL = true;
        try {
        //    long l = System.currentTimeMillis();
            stepsL = maniLavender.calculate();
        //    System.out.println("Time: " + (System.currentTimeMillis() - l));
        //    System.out.println();
        //    System.out.println(stepsL);
            calcDoneL = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        calcingL = false;
    }

    public void calcR() {
        calcingR = true;
        try {
        //    long l = System.currentTimeMillis();
            stepsR = maniRed.calculate();
        //    System.out.println("TimeR: " + (System.currentTimeMillis() - l));
        //    System.out.println();
        //    System.out.println(stepsR);
            calcDoneR = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        calcingR = false;
    }

    public void iterL() {
        iteringL = true;

        Point point = stepsL.get(i);

        f1 = (point.getFi1() + 1) * PI / (n + 1);
        f2 = (point.getFi2() + 1) * 2 * PI / (2 * n + 3)- PI;
        f3 = (point.getFi3() + 1) * 2 * PI / (2 * n + 3)- PI;

        link2L.setPosition(field * (float) (0.5 + l1L * cos(f1)) - r, field * (float) (l1L * sin(f1)) - r);
        link3L.setPosition(field * (float) (0.5 + l1L * cos(f1) + l2L * cos(f1 + f2)) - r, field * (float) (l1L * sin(f1) + l2L * sin(f1 + f2)) - r);
        //System.out.println("Coordinates2 " + (field * (float) (0.5 + l1 * cos(point.getFi1()))) + " " + (field * (float) (l1 * sin(point.getFi1()))));
        //System.out.println("Coordinates3 " + (field * (float) (0.5 + l1 * cos(point.getFi1()) + l2 * cos(point.getFi1() + point.getFi2()))) + " " + (field * (float) (l1 * sin(point.getFi1()) + l2 * sin(point.getFi1() + point.getFi2()))));

        link1L.setRotation((float) (f1 * 180 / PI - 90));
        link2L.setRotation((float) ((f1 + f2) * 180 / PI - 90));
        link3L.setRotation((float) ((f1 + f2 + f3) * 180 / PI - 90));

        i++;
        if (i == stepsL.size()) {
            System.out.println("Now: " + (float) (0.5 + l1L * cos(f1) + l2L * cos(f1 + f2) + l3L * cos(f1 + f2 + f3)) + " " + (float) (l1L * sin(f1) + l2L * sin(f1 + f2) + l3L * sin(f1 + f2 + f3)));
            maniLavender.setPosition(point.getFi1(),point.getFi2(),point.getFi3());
            iterDoneL = true;
            i = 0;
        }
        iteringL = false;
    }

    public void iterR() {
        iteringR = true;

        Point point = stepsR.get(j);

        f1 = (point.getFi1() + 1) * PI / (n + 1);
        f2 = (point.getFi2() + 1) * 2 * PI / (2 * n + 3)- PI;
        f3 = (point.getFi3() + 1) * 2 * PI / (2 * n + 3)- PI;

        link2R.setPosition(field * (float) (0.5 - l1R * cos(f1)) - r, field * (float) (1 - l1R * sin(f1)) - r);
        link3R.setPosition(field * (float) (0.5 - (l1R * cos(f1) + l2R * cos(f1 + f2))) - r, field * (float) (1 - (l1R * sin(f1) + l2R * sin(f1 + f2))) - r);
        //System.out.println("Coordinates2 " + (field * (float) (0.5 + l1 * cos(point.getFi1()))) + " " + (field * (float) (l1 * sin(point.getFi1()))));
        //System.out.println("Coordinates3 " + (field * (float) (0.5 + l1 * cos(point.getFi1()) + l2 * cos(point.getFi1() + point.getFi2()))) + " " + (field * (float) (l1 * sin(point.getFi1()) + l2 * sin(point.getFi1() + point.getFi2()))));

        link1R.setRotation((float) (f1 * 180 / PI + 90));
        link2R.setRotation((float) ((f1 + f2) * 180 / PI + 90));
        link3R.setRotation((float) ((f1 + f2 + f3) * 180 / PI + 90));

        j++;
        if (j == stepsR.size()) {
            System.out.println("NowR: " + (float) (0.5 + l1R * cos(f1) + l2R * cos(f1 + f2) + l3R * cos(f1 + f2 + f3)) + " " + (float) (l1R * sin(f1) + l2R * sin(f1 + f2) + l3R * sin(f1 + f2 + f3)));
            maniRed.setPosition(point.getFi1(),point.getFi2(),point.getFi3());
            iterDoneR = true;
            j = 0;
        }
        iteringR = false;
    }

    public void setGoal() {
        //    time = System.currentTimeMillis();
        goaling = true;
        float x1 = random.nextFloat();
        float y1 = random.nextFloat();
        goalL.setPosition(field * x1 - r, field * y1 - r);
        float x2 = random.nextFloat();
        float y2 = random.nextFloat();
        goalR.setPosition(field * x2 - r, field * y2 - r);
        goalDone = maniPair.setArea(x1, y1, x2, y2);
        //    System.out.println("Goal " + (System.currentTimeMillis() - time));
        goaling = false;
    }

    //Просчёт пути
    public void calc() {
        calcing = true;
        try {
            //    long l = System.currentTimeMillis();
            steps = maniPair.calculate();
            //    System.out.println("Time: " + (System.currentTimeMillis() - l));
            //    System.out.println();
            //    System.out.println(steps);
            calcDone = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        calcing = false;
    }

    //Одна итерация по массиву Steps
    public void iter() {
        itering = true;

        Point point = steps.get(i);

        f1 = (point.getFi1() + 1) * PI / (n + 1);
        f2 = (point.getFi2() + 1) * 2 * PI / (2 * n + 3)- PI;
        f3 = (point.getFi3() + 1) * 2 * PI / (2 * n + 3)- PI;
        f4 = (point.getFi4() + 1) * PI / (n + 1);
        f5 = (point.getFi5() + 1) * 2 * PI / (2 * n + 3)- PI;
        f6 = (point.getFi6() + 1) * 2 * PI / (2 * n + 3)- PI;

        link2L.setPosition(field * (float) (0.5 + l1L * cos(f1)) - r, field * (float) (l1L * sin(f1)) - r);
        link3L.setPosition(field * (float) (0.5 + l1L * cos(f1) + l2L * cos(f1 + f2)) - r, field * (float) (l1L * sin(f1) + l2L * sin(f1 + f2)) - r);
        //System.out.println("Coordinates2 " + (field * (float) (0.5 + l1 * cos(point.getFi1()))) + " " + (field * (float) (l1 * sin(point.getFi1()))));
        //System.out.println("Coordinates3 " + (field * (float) (0.5 + l1 * cos(point.getFi1()) + l2 * cos(point.getFi1() + point.getFi2()))) + " " + (field * (float) (l1 * sin(point.getFi1()) + l2 * sin(point.getFi1() + point.getFi2()))));

        link1L.setRotation((float) (f1 * 180 / PI - 90));
        link2L.setRotation((float) ((f1 + f2) * 180 / PI - 90));
        link3L.setRotation((float) ((f1 + f2 + f3) * 180 / PI - 90));

        link2R.setPosition(field * (float) (0.5 - l1R * cos(f4)) - r, field * (float) (1 - l1R * sin(f4)) - r);
        link3R.setPosition(field * (float) (0.5 - (l1R * cos(f4) + l2R * cos(f4 + f5))) - r, field * (float) (1 - (l1R * sin(f4) + l2R * sin(f4 + f5))) - r);
        //System.out.println("Coordinates2 " + (field * (float) (0.5 + l1 * cos(point.getFi1()))) + " " + (field * (float) (l1 * sin(point.getFi1()))));
        //System.out.println("Coordinates3 " + (field * (float) (0.5 + l1 * cos(point.getFi1()) + l2 * cos(point.getFi1() + point.getFi2()))) + " " + (field * (float) (l1 * sin(point.getFi1()) + l2 * sin(point.getFi1() + point.getFi2()))));

        link1R.setRotation((float) (f4 * 180 / PI + 90));
        link2R.setRotation((float) ((f4 + f5) * 180 / PI + 90));
        link3R.setRotation((float) ((f4 + f5 + f6) * 180 / PI + 90));

        i++;
        if (i == steps.size()) {
            //    System.out.println("Now: " + (float) (0.5 + l1L * cos(f1) + l2L * cos(f1 + f2) + l3L * cos(f1 + f2 + f3)) + " " + (float) (l1L * sin(f1) + l2L * sin(f1 + f2) + l3L * sin(f1 + f2 + f3)));
            maniPair.setPosition(point.getFi1(),point.getFi2(),point.getFi3(),point.getFi4(),point.getFi5(),point.getFi6());
            iterDone = true;
            i = 0;
        }

        dt = 0;
        itering = false;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
