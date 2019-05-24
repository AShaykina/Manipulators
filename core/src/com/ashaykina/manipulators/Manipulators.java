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
    private short field = 750;
    private short n = 120; // 12 - уже падает // 175 максимум

    private SpriteBatch batch;
    //Параметры лавандового манипулятора
    private Sprite link1L;
    private Sprite link2L;
    private Sprite link3L;
    private Sprite goalL;


    //Параметры красного манипулятора
    private Sprite link1R;
    private Sprite link2R;
    private Sprite link3R;
    private Sprite goalR;


    private Manipulator maniLav;
    private Manipulator maniRed;
    private Random random;

    private Thread ml;

    private Boolean goingRed;

    private short r = 9;

    private double dt = 0;

    private int i = 0;

    //ArrayList<Sprite> grid;

    @Override
    public void create() {

        int height = (int) round(373 * field / 1000.0);

        double error = 0.001;

        double l1L = 0.372677996;
        double l2L = 0.372677996;
        double l3L = 0.372677996;

        double fi1L = 1.1071487177941;
        double fi2L = PI - fi1L + 0.2;
        double fi3L = -fi2L + 1;

        double l1R = 0.372677996;
        double l2R = 0.372677996;
        double l3R = 0.372677996;

        double fi1R = 1.1071487177941;
        double fi2R = PI - fi1R + 0.2;
        double fi3R = -fi2R + 1;

        random = new Random();

        //grid = new ArrayList<>();

        maniLav = new Manipulator(n, l1L, l2L, l3L, fi1L, fi2L, fi3L, 0.5, 0, error);//, grid);
        maniRed = new Manipulator(n, l1R, l2R, l3R, fi1R, fi2R, fi3R, 0.5, 1, error);//, grid);

        batch = new SpriteBatch();

        goalL = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373 - 2 * r, 2 * r, 2 * r);
        goalL.setOrigin(r, r);

        goalR = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373 - 2 * r, 2 * r, 2 * r);
        goalR.setOrigin(r, r);

        link1L = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373 - height, 2 * r, height);
        link1L.setOrigin(r, r);  // Центр вращения.
        link1L.setPosition(field * (float) 0.5 - r, 0 - r);
        link1L.setRotation((float) (fi1L * 180 / PI - 90));

        link2L = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373 - height, 2 * r, height);
        link2L.setOrigin(r, r);  // Центр вращения.
        link2L.setPosition(field * (float) (0.5 + l1L * cos(fi1L)) - r, field * (float) (l1L * sin(fi1L)) - r);
        link2L.setRotation((float) ((fi2L + fi1L) * 180 / PI - 90));

        link3L = new Sprite(new Texture(Gdx.files.internal("core/assets/linkLavender.png")), 0, 373 - height, 2 * r, height);
        link3L.setOrigin(r, r);  // Центр вращения.
        link3L.setPosition(field * (float) (0.5 + l1L * cos(fi1L) + l2L * cos(fi1L + fi2L)) - r, field * (float) (l1L * sin(fi1L) + l2L * sin(fi1L + fi2L)) - r);
        link3L.setRotation((float) ((fi3L + fi2L + fi1L) * 180 / PI - 90));

        link1R = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373 - height, 2 * r, height);
        link1R.setOrigin(r, r);  // Центр вращения.
        link1R.setPosition(field * (float) 0.5 - r, field - r);
        link1R.setRotation((float) (fi1R * 180 / PI + 90));

        link2R = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373 - height, 2 * r, height);
        link2R.setOrigin(r, r);  // Центр вращения.
        link2R.setPosition(field * (float) (0.5 - l1R * cos(fi1R)) - r, field * (float) (1 - (l1R * sin(fi1R))) - r);
        link2R.setRotation((float) ((fi2R + fi1R) * 180 / PI + 90));

        link3R = new Sprite(new Texture(Gdx.files.internal("core/assets/linkRed.png")), 0, 373 - height, 2 * r, height);
        link3R.setOrigin(r, r);  // Центр вращения.
        link3R.setPosition(field * (float) (0.5 - (l1R * cos(fi1R) + l2R * cos(fi1R + fi2R))) - r, field * (float) (1 - (l1R * sin(fi1R) + l2R * sin(fi1R + fi2R))) - r);
        link3R.setRotation((float) ((fi3R + fi2R + fi1R) * 180 / PI + 90));

        setGoal(maniRed);
        maniRed.goalDone = true;
        calc(maniRed);
        maniRed.calcDone = true;
        goingRed = true;

    }

    private void runCalc(final Manipulators ms, Manipulator mani) {
        ml = new Thread(() -> ms.calc(mani));
        ml.start();
    }

    private void runGoal(final Manipulators ms, Manipulator mani) {
        ml = new Thread(() -> ms.setGoal(mani));
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

        if (goingRed) {
            if (!maniLav.goalDone && !maniLav.goaling) runGoal(this, maniLav);
            else if (!maniLav.calcDone && !maniLav.calcing) runCalc(this, maniLav);
            if (!maniRed.iterDone && !maniRed.itering) {
                dt = 0;
                iter(maniRed);
            }
            if (maniLav.goalDone && maniLav.calcDone && maniRed.iterDone) {
                maniRed.goalDone = false;
                maniRed.calcDone = false;
                maniRed.iterDone = false;
                goingRed = false;
            }
        } else {
            if (!maniRed.goalDone && !maniRed.goaling) runGoal(this, maniRed);
            else if (!maniRed.calcDone && !maniRed.calcing) runCalc(this, maniRed);
            if (!maniLav.iterDone && !maniLav.itering) {
                dt = 0;
                iter(maniLav);
            }
            if (maniRed.goalDone && maniRed.calcDone && maniLav.iterDone) {
                maniLav.goalDone = false;
                maniLav.calcDone = false;
                maniLav.iterDone = false;
                goingRed = true;
            }
        }

    }

    //Генерирование цели и обозначение её в матрице
    private void setGoal(Manipulator mani) {
        //    long l = System.currentTimeMillis();
        mani.goaling = true;
        float x = 0;
        float y = 0;
        while (!mani.goalDone) {
            x = random.nextFloat();
            y = random.nextFloat();
            System.out.println("Goal: " + x + " " + y);
            mani.goalDone = mani.setGoal(x, y);
        }
        if (mani.y0 == 0) goalL.setPosition(field * x - r, field * y - r);
        else goalR.setPosition(field * x - r, field * y - r);
        mani.goaling = false;
        //    System.out.println("Goal: " + (System.currentTimeMillis() - l));
    }

    private void calc(Manipulator mani) {
        mani.calcing = true;
        //    long l = System.currentTimeMillis();
        mani.steps = mani.calculate();
        Point3 p = mani.steps.get(mani.steps.size() - 1);

        double f1 = (p.getFi1() + 1) * PI / (n + 1);
        double f2 = (p.getFi2() + 1) * 2 * PI / (2 * n + 3) - PI;
        double f3 = (p.getFi3() + 1) * 2 * PI / (2 * n + 3) - PI;

        double x1 = (float) (mani.x0 + mani.f * (mani.l1 * cos(f1)));
        double y1 = (float) (mani.y0 + mani.f * (mani.l1 * sin(f1)));

        double x2 = (float) (mani.x0 + mani.f * (mani.l1 * cos(f1) + mani.l2 * cos(f1 + f2)));
        double y2 = (float) (mani.y0 + mani.f * (mani.l1 * sin(f1) + mani.l2 * sin(f1 + f2)));

        double x3 = (float) (mani.x0 + mani.f * (mani.l1 * cos(f1) + mani.l2 * cos(f1 + f2) + mani.l3 * cos(f1 + f2 + f3)));
        double y3 = (float) (mani.y0 + mani.f * (mani.l1 * sin(f1) + mani.l2 * sin(f1 + f2) + mani.l3 * sin(f1 + f2 + f3)));
        if (mani.y0 == 0) maniRed.setCollision(mani.x0, mani.y0, x1, y1, x2, y2, x3, y3);
        else maniLav.setCollision(maniRed.x0, maniRed.y0, x1, y1, x2, y2, x3, y3);
        //    System.out.println("Time: " + (System.currentTimeMillis() - l));
        //    System.out.println();
        //    System.out.println(stepsL);
        mani.calcDone = true;
        mani.calcing = false;
    }

    private void iter(Manipulator mani) {
        mani.itering = true;

        Point3 point3 = mani.steps.get(i);

        double f1 = (point3.getFi1() + 1) * PI / (n + 1);
        double f2 = (point3.getFi2() + 1) * 2 * PI / (2 * n + 3) - PI;
        double f3 = (point3.getFi3() + 1) * 2 * PI / (2 * n + 3) - PI;

        if (mani.y0 == 0) {
            link2L.setPosition(field * (float) (mani.x0 + mani.f * (mani.l1 * cos(f1))) - r, field * (float) (mani.y0 + mani.f * (mani.l1 * sin(f1))) - r);
            link3L.setPosition(field * (float) (mani.x0 + mani.f * (mani.l1 * cos(f1) + mani.l2 * cos(f1 + f2))) - r, field * (float) (mani.y0 + mani.f * (mani.l1 * sin(f1) + mani.l2 * sin(f1 + f2))) - r);

            link1L.setRotation((float) (f1 * 180 / PI - 90));
            link2L.setRotation((float) ((f1 + f2) * 180 / PI - 90));
            link3L.setRotation((float) ((f1 + f2 + f3) * 180 / PI - 90));
        } else {
            link2R.setPosition(field * (float) (mani.x0 + mani.f * (mani.l1 * cos(f1))) - r, field * (float) (mani.y0 + mani.f * (mani.l1 * sin(f1))) - r);
            link3R.setPosition(field * (float) (mani.x0 + mani.f * (mani.l1 * cos(f1) + mani.l2 * cos(f1 + f2))) - r, field * (float) (mani.y0 + mani.f * (mani.l1 * sin(f1) + mani.l2 * sin(f1 + f2))) - r);

            link1R.setRotation((float) (f1 * 180 / PI + 90));
            link2R.setRotation((float) ((f1 + f2) * 180 / PI + 90));
            link3R.setRotation((float) ((f1 + f2 + f3) * 180 / PI + 90));
        }

        i++;
        if (i == mani.steps.size()) {
            //    System.out.println("NowL: " + (float) (0.5 + l1L * cos(f1) + l2L * cos(f1 + f2) + l3L * cos(f1 + f2 + f3)) + " " + (float) (l1L * sin(f1) + l2L * sin(f1 + f2) + l3L * sin(f1 + f2 + f3)));
            mani.setPosition(point3.getFi1(), point3.getFi2(), point3.getFi3());
            mani.iterDone = true;
            i = 0;
        }
        mani.itering = false;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
