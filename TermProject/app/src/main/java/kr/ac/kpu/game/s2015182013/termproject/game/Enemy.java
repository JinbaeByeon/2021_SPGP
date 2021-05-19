package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Enemy implements GameObject, BoxCollidable, Recyclable {
    private static final int BULLET_SPEED = -1500;
    private static float FIRE_INTERVAL = 4.0f ;
    private float fireTime;
    private static final int[] RESOURCE_IDS = {
            R.mipmap.enemy_a, R.mipmap.enemy_b, R.mipmap.enemy_c, R.mipmap.enemy_d
    };
    private static final String TAG = kr.ac.kpu.game.s2015182013.termproject.game.Enemy.class.getSimpleName();
    private float x;
    private GameBitmap planeBitmap;
    private int hp;
    private float y;
    private int speed;
    Paint paint = new Paint();
    private int maxHp;
    private int score;
    private int range;

    private Enemy() {
        Log.d(TAG, "Enemy constructor");
    }

    public static Enemy get(int level, int x, int y, int speed) {
        MainGame game = MainGame.get();
        Enemy enemy = (Enemy) game.get(Enemy.class);
        if (enemy == null) {
            enemy = new Enemy();
        }

        enemy.init(level, x, y, speed);
        return enemy;
    }

    private void init(int level, int x, int y, int speed) {
        Random r = new Random();
        this.x = x;
        this.y = y;
        this.speed = speed;
        score = hp =maxHp = level  *100;
        range = r.nextInt(2000)-1000;
        fireTime = 0.0f;
        FIRE_INTERVAL -= level-1;



        int resId = RESOURCE_IDS[level - 1];

        planeBitmap = new GameBitmap(resId);
        planeBitmap.setSize(60,60);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;


        if (y > GameView.view.getHeight()) {
            game.remove(this);
        }

        fireTime += game.frameTime;
        if (fireTime >= FIRE_INTERVAL) {
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }
    }

    private void fireBullet() {
        Bullet bullet = Bullet.get(this.x, this.y, BULLET_SPEED);
        MainGame game = MainGame.get();
        game.add(MainGame.Layer.eBullet, bullet);
    }

    @Override
    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
        drawHealthBar(canvas);
    }

    private void drawHealthBar(Canvas canvas) {
        float w = 100;
        float h = planeBitmap.getHeight();
        float l = x- w/2;
        float t = y + h + 10;
        float r = x+w/2;
        float b = t + 30;
        paint.setColor(Color.GRAY);
        canvas.drawRect(l,t,r,b,paint);
        paint.setColor(Color.RED);
        r = l + w*hp/maxHp;
        canvas.drawRect(l,t,r,b,paint);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        planeBitmap.getBoundingRect(x, y, rect);
    }

    @Override
    public void recycle() {
        // 재활용통에 들어가는 시점에 불리는 함수. 현재는 할일없음.
        FIRE_INTERVAL = 4.0f ;
    }

    @Override
    public void hitBullet(int damage) {
        MainGame game = MainGame.get();
        hp -= damage;

        if(hp<0) {
            game.remove(this, false);
            game.score.addScore(score);
            Coin coin = Coin.get(x,y);
            game.add(MainGame.Layer.coin,coin);
        }
    }

}