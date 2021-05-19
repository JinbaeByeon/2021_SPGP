package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.game.Enemy;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class EnemyGenerator implements GameObject {

    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;
    private static final String TAG = kr.ac.kpu.game.s2015182013.termproject.game.EnemyGenerator.class.getSimpleName();
    private float time;
    private float spawnInterval;
    private int wave;

    public EnemyGenerator() {
        time = INITIAL_SPAWN_INTERVAL;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        wave = 0;
    }
    @Override
    public void update() {
        MainGame game = MainGame.get();
        time += game.frameTime;
        if (time >= spawnInterval) {
            generate();
            time -= spawnInterval;
        }
    }

    private void generate() {
        wave++;
        //Log.d(TAG, "Generate now !!");
        MainGame game = MainGame.get();
        int tenth = GameView.view.getWidth() / 10;
        Random r = new Random();
        int w=GameView.view.getWidth()-200;
        int x = r.nextInt(w);
        int y = 0;

        int level = wave / 20 - r.nextInt(2);
        if (level < 1) level = 1;
        if (level > 4) level = 4;
        spawnInterval = INITIAL_SPAWN_INTERVAL-level-1;

        Enemy enemy = Enemy.get(level, x, y, 300);
        game.add(MainGame.Layer.enemy, enemy);
    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }

}