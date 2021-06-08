package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.AnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Bomb implements GameObject, BoxCollidable, Recyclable {
    private static final float FRAMES_PER_SECOND = 13.0f;
    private static final String TAG = Bomb.class.getSimpleName();
    private float x;
    private AnimationGameBitmap bitmap;
    private float y;
    private int speed;

    private Bomb(float x, float y) {

        Log.d(TAG, "loading bitmap for Bomb");
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Bomb get(float x, float y) {
        MainGame game = MainGame.get();
        Bomb bomb = (Bomb) game.get(Bomb.class);
        if (bomb == null) {
            bomb = new Bomb(x, y);
        }
        bomb.init(x, y);
        return bomb;
    }

    private void init(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 500;

        bitmap = new AnimationGameBitmap(R.mipmap.missile,FRAMES_PER_SECOND,13);
        bitmap.setSize(300,600);
        this.y+=bitmap.getHeight()/2;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y -= speed * game.frameTime;

        if (y+bitmap.getHeight()/2<0) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void recycle() {
        // 재활용통에 들어가는 시점에 불리는 함수. 현재는 할일없음.
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x,y,rect);
    }

    @Override
    public void hitBullet(int damage) {

    }
}