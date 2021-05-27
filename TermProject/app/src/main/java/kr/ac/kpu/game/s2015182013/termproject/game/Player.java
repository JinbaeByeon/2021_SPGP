package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.AnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Player implements GameObject, BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f / 7.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;
    private final Health hpBar;
    private final AnimationGameBitmap expBitmap;
    private float expTime;
    private boolean isHitted;
    private float fireTime;
    private float x, y;
    private float tx, ty;
    private IndexedGameBitmap planeBitmap;
    private GameBitmap fireBitmap;

    private float cx;
    private float cy;
    private float index;
    private int hp;
    private int power;
    private int bomb;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        tx = x;
        ty = y;
        cx = x;
        cy = y;
        planeBitmap = new IndexedGameBitmap(R.mipmap.fighters,67,80,11,0,0);
        fireBitmap = new GameBitmap(R.mipmap.laser_0);
        expBitmap = new AnimationGameBitmap(R.mipmap.hit,8,6);
        expBitmap.setSize(40,40);
        fireTime = 0.0f;
        expTime = 0.0f;
        index = 5;
        power =10;
        isHitted=false;

        hp =100;
        int w = GameView.view.getWidth();
        int margin = (int) (20 * GameView.MULTIPLIER);
        hpBar = new Health(margin,margin,hp);

        MainGame game = MainGame.get();
        game.add(MainGame.Layer.ui, hpBar);
    }

    public void moveTo(float x, float y) {
        this.tx = x;
        this.ty = y;
    }

    public void update() {
        MainGame game = MainGame.get();

        float dx = tx-cx;
        if(dx<0&& index>0)
            index= index -0.5f;
        else if(dx>0&&index<10)
            index= index +0.5f;
        else
            index =5;
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

        if(isHitted)
            expTime += game.frameTime;
        if(expTime>1.f){
            expTime =0.f;
            isHitted = false;
        }
        fireTime += game.frameTime;

        if (fireTime >= FIRE_INTERVAL) {
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }
    }

    private void fireBullet() {
        Bullet bullet = Bullet.get(this.x, this.y, BULLET_SPEED,power);
        MainGame game = MainGame.get();
        game.add(MainGame.Layer.pBullet, bullet);
    }

    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
        if (fireTime < LASER_DURATION) {
            fireBitmap.draw(canvas, x, y - 50);
        }
        if(isHitted)
            expBitmap.draw(canvas,x,y-10);
    }


    @Override
    public void hitBullet(int damage) {
        hp -= damage;
        hpBar.setHP(hp);
        if(hp<=0){
            reset();
            MainGame.get().reset();
        }
        expBitmap.reset();
        isHitted=true;
    }

    private void reset() {
        fireTime = 0.0f;
        index = 5;
        power =10;
        hp =100;
        hpBar.setHP(hp);
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

    public void increase(Item.Type type) {
        switch (type){
            case Power:
                power+=10;
                if(power>100)
                    power=100;
                break;
            case Bomb:
                bomb+=1;
                break;
            case Health:
                hp+=30;
                if(hp>100)
                    hp=100;
                hpBar.setHP(hp);
                break;

        }
    }
}
