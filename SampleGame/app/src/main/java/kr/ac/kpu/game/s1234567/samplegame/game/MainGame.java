package kr.ac.kpu.game.s1234567.samplegame.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.s1234567.samplegame.framework.GameObject;
import kr.ac.kpu.game.s1234567.samplegame.ui.view.GameView;

public class MainGame {
    private static final int BALL_COUNT = 10;
    private static final String TAG = MainGame.class.getSimpleName();
    // singleton
    private static MainGame instance;
    public static MainGame get() {
        if (instance == null) {
            instance = new MainGame();
        }
        return instance;
    }
    public float frameTime;
    private boolean initialized;

    Player player;
    ArrayList<GameObject> objects = new ArrayList<>();

    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        player = new Player(w/2, h/2, 0, 0);
        Random rand = new Random();
        for (int i = 0; i < BALL_COUNT; i++) {
            float x = rand.nextInt(1000);
            float y = rand.nextInt(1000);
            float dx = rand.nextFloat() * 1000 - 500;
            float dy = rand.nextFloat() * 1000 - 500;
            Ball b = new Ball(x, y, dx, dy);
            objects.add(b);
        }
        objects.add(player);

        initialized = true;
        return true;
    }

    public void update() {
        //if (!initialized) return;
        for (GameObject o : objects) {
            o.update();
        }
    }

    public void draw(Canvas canvas) {
        //if (!initialized) return;
        for (GameObject o: objects) {
            o.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
        //if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            player.moveTo(event.getX(), event.getY());
            return true;
        }
        return false;
    }

    public void add(GameObject gameObject) {
        objects.add(gameObject);
//        Log.d(TAG, "<A> object count = " + objects.size());
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                objects.remove(gameObject);
//                Log.d(TAG, "<R> object count = " + objects.size());
            }
        });
    }
}
