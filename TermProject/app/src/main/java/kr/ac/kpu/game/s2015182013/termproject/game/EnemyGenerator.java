package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class EnemyGenerator implements GameObject {

    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private float time;
    private float spawnInterval;
    private int wave;
    private static boolean isGenerate;

    public EnemyGenerator() {
        time = INITIAL_SPAWN_INTERVAL;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        wave = 0;
        isGenerate =true;
    }

    public static void startGenerate() {
        isGenerate=true;
    }

    @Override
    public void update() {
        if(isGenerate) {
            MainGame game = MainGame.get();
            time += game.frameTime;
            if (time >= spawnInterval) {
                generate();
                time -= spawnInterval;
                spawnInterval -= game.frameTime;
                if (spawnInterval < 1)
                    spawnInterval = 1;
            }
        }
    }

    private void generate() {
        wave++;
        //Log.d(TAG, "Generate now !!");
        MainGame game = MainGame.get();
        Random r = new Random();
        int w=GameView.view.getWidth();
        int x = r.nextInt(w-100)+50;
        int y = 0;

        int level = wave / 5 - r.nextInt(2);
        if (level < 1) level = 1;
        if (level > 4) level = 4;
        if(wave%4 ==0){
            Boss boss = Boss.get(wave/4,w/2,y,100);
            game.add(MainGame.Layer.enemy,boss);
            isGenerate = false;
        }
        else {
            Enemy enemy = Enemy.get(level, x, y, (r.nextInt(50) + 50) * level);
            game.add(MainGame.Layer.enemy, enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }

}