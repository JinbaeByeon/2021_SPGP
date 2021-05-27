package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Item implements GameObject, BoxCollidable, Recyclable {
    private static final float FRAMES_PER_SECOND = 9.0f;
    private static final String TAG = Item.class.getSimpleName();
    private static final float LIFE_TIME = 10.f;
    private float x,y;
    private GameBitmap bitmap;
    private float sx, sy;
    private float life;
    private Type type;

    private Item(float x, float y) {

        Log.d(TAG, "loading bitmap for item");
    }

    public void upgrade(Player p) {
        p.increase(type);
    }


    enum Type{
        Power, Bomb, Health
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static Item get(float x, float y,float sx,float sy,Type type) {
        MainGame game = MainGame.get();
        Item item = (Item) game.get(Item.class);
        if (item == null) {
            item = new Item(x, y);
        }
        item.init(x, y,sx,sy,type);
        return item;
    }

    private void init(float x, float y,float sx,float sy,Type type) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        life=0;
        this.type = type;

        if(type == Type.Power){
            bitmap = new IndexedAnimationGameBitmap(R.mipmap.items,FRAMES_PER_SECOND,
                    0,17,17,9,0,0);
            ((IndexedAnimationGameBitmap)bitmap).setIndices(0,1,2,3,4,5,6,7,8);
        }
        else if(type == Type.Bomb) {
            bitmap = new IndexedAnimationGameBitmap(R.mipmap.items,FRAMES_PER_SECOND,
                    0,17,17,9,0,0);
            ((IndexedAnimationGameBitmap)bitmap).setIndices(9, 10, 11, 12, 13, 14, 15, 16, 17);
        }
        else if(type == Type.Health){
            bitmap = new GameBitmap(R.mipmap.hpicon);
        }

        bitmap.setSize(50,50);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        life += game.frameTime;
        x += sx * game.frameTime;
        y += sy * game.frameTime;

        int w =GameView.view.getWidth();
        int h =GameView.view.getHeight();
        if(x>w&& sx >0){
            sx =-sx;
            x = w;
        }
        else if(x<0&& sx <0){
            sx =-sx;
            x = 0;
        }
        if(y>h&& sy >0){
            sy =-sy;
            y=h;
        }
        else if(y<0&& sy <0){
            sy =-sy;
            y=0;
        }

        if (life>LIFE_TIME) {
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
        life = 0;
    }
}