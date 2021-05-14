package kr.ac.kpu.game.s2015182013.cookierun;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.s2015182013.cookierun.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.cookierun.framework.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.cookierun.framework.MainGame;


public class Player implements GameObject, kr.ac.kpu.game.s2015182013.cookierun.framework.BoxCollidable {
    private static final String TAG = kr.ac.kpu.game.s2015182013.cookierun.game.Player.class.getSimpleName();
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f / 7.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;
    private float fireTime;
    private float x, y;
    private float tx, ty;
    private float speed;
    private kr.ac.kpu.game.s2015182013.cookierun.framework.IndexedAnimationGameBitmap charBitmap;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        charBitmap = new kr.ac.kpu.game.s2015182013.cookierun.framework.IndexedAnimationGameBitmap(R.mipmap.cookie,4.5f,0);
        charBitmap.setIndices(100,101,102,103);

        this.fireTime = 0.0f;
    }

    public void moveTo(float x, float y) {
        this.tx = x;
        //this.ty = this.y;
    }

    public void update() {
        kr.ac.kpu.game.s2015182013.cookierun.framework.MainGame game = kr.ac.kpu.game.s2015182013.cookierun.framework.MainGame.get();
        float dx = speed * game.frameTime;
        if (tx < x) { // move left
            dx = -dx;
        }
        x += dx;
        if ((dx > 0 && x > tx) || (dx < 0 && x < tx)) {
            x = tx;
        }
    }


    public void draw(Canvas canvas) {
        charBitmap.draw(canvas,x,y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
//        planeBitmap.getBoundingRect(x, y, rect);
    }
}
