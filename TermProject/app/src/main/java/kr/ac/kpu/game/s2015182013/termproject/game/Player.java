package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Player implements GameObject, BoxCollidable {
    private static final String TAG = kr.ac.kpu.game.s2015182013.termproject.game.Player.class.getSimpleName();
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f / 7.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;
    private float fireTime;
    private float x, y;
    private float tx, ty;
    private IndexedGameBitmap planeBitmap;
    private GameBitmap fireBitmap;
    private float cx;
    private float cy;
    private float index;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        tx = x;
        ty = y;
        cx = x;
        cy = y;
        planeBitmap = new IndexedGameBitmap(R.mipmap.fighters,67,80,11,0,0);
        fireBitmap = new GameBitmap(R.mipmap.laser_0);
        fireTime = 0.0f;
        index = 5;
    }

    public void moveTo(float x, float y) {
        this.tx = x;
        this.ty = y;
    }

    public void update() {
        MainGame game = MainGame.get();

        float dx = tx-cx;
        if(dx<0&& index>0)
            index= index -0.1f;
        else if(dx>0&&index<10)
            index= index +0.1f;
//        else
//            index =5;
        planeBitmap.setIndex((int)index);
        float dy = ty-cy;
        cx=tx;
        cy=ty;

        float w =GameView.view.getWidth();
        float h =GameView.view.getHeight();

        x+=dx;
        y+=dy;
        x = x<0? 0: x>w?w:x;
        y = y<0? 0: y>h?h:y;


        fireTime += game.frameTime;
        if (fireTime >= FIRE_INTERVAL) {
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }
    }

    private void fireBullet() {
        Bullet bullet = Bullet.get(this.x, this.y, BULLET_SPEED);
        MainGame game = MainGame.get();
        game.add(MainGame.Layer.bullet, bullet);
    }

    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
        if (fireTime < LASER_DURATION) {
            fireBitmap.draw(canvas, x, y - 50);
        }
    }

    @Override
    public void getBoundingRect(RectF rect) {
        planeBitmap.getBoundingRect(x, y, rect);
    }

    public void setPivot(float x, float y) {
        cx = x;
        cy = y;
        tx = x;
        ty = y;
    }
}
