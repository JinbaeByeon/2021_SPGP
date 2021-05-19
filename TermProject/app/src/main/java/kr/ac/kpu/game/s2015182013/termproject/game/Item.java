package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Item implements GameObject, BoxCollidable, Recyclable {
    private static final float FRAMES_PER_SECOND = 8.0f;
    private static final String TAG = Item.class.getSimpleName();
    private float x;
    private IndexedAnimationGameBitmap bitmap;
    private float y;
    private int speed;

    private Item(float x, float y) {

        Log.d(TAG, "loading bitmap for item");
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Item get(float x, float y) {
        MainGame game = MainGame.get();
        Item item = (Item) game.get(Item.class);
        if (item == null) {
            item = new Item(x, y);
        }
        item.init(x, y);
        return item;
    }

    private void init(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 500;

        bitmap = new IndexedAnimationGameBitmap(R.mipmap.coin,FRAMES_PER_SECOND,0,34,50,12,20,0);
        bitmap.setIndices(0,1);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        x += speed * game.frameTime;
        y += speed * game.frameTime;
        if(x>GameView.view.getWidth()&&speed>0){
            speed =-speed;
        }

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