package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.AnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Coin implements GameObject, BoxCollidable, Recyclable {
    private static final float FRAMES_PER_SECOND = 8.0f;
    private static final String TAG = Coin.class.getSimpleName();
    private float x;
    private IndexedAnimationGameBitmap bitmap;
    private float y;
    private int speed;

    private Coin(float x, float y) {

        Log.d(TAG, "loading bitmap for coin");
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Coin get(float x, float y) {
        MainGame game = MainGame.get();
        Coin coin = (Coin) game.get(Coin.class);
        if (coin == null) {
            coin = new Coin(x, y);
        }
        coin.init(x, y);
        return coin;
    }

    private void init(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 500;

        bitmap = new IndexedAnimationGameBitmap(R.mipmap.coin,FRAMES_PER_SECOND,0,48,48,2,0,0);
        bitmap.setIndices(0,1,2,3);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;

        if (y> GameView.view.getHeight()) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void hitBullet(int damage) {

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