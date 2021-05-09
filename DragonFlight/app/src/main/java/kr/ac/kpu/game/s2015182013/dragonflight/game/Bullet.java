package kr.ac.kpu.game.s2015182013.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.s2015182013.dragonflight.R;
import kr.ac.kpu.game.s2015182013.dragonflight.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.dragonflight.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s2015182013.dragonflight.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.dragonflight.game.MainGame;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    private float x;
    private final GameBitmap bitmap;
    private float y;
    private int speed;

    private Bullet(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;

        Log.d(TAG, "loading bitmap for bullet");
        this.bitmap = new GameBitmap(R.mipmap.laser_1);
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Bullet get(float x, float y, int speed) {
        MainGame game = MainGame.get();
        Bullet bullet = (Bullet) game.get(Bullet.class);
        if (bullet == null) {
            return new Bullet(x, y, speed);
        }
        bullet.init(x, y, speed);
        return bullet;
    }

    private void init(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;

        if (y < 0) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x, y, rect);
    }

    @Override
    public void recycle() {
        // 재활용통에 들어가는 시점에 불리는 함수. 현재는 할일없음.
    }
}