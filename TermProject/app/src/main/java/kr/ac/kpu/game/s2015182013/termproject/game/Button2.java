package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.IndexedGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Button2 implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Button2.class.getSimpleName();
    private final Paint boxPaint = new Paint();
    public boolean selected;
    private float x;
    private GameBitmap bitmap;
    private float y;
    private float r;

    enum Type{
        p1,p2,p3,p4,bomb
    }

    public Button2(float x, float y, int type) {

        Log.d(TAG, "loading bitmap for button");
        this.x = x;
        this.y = y;

        bitmap = new IndexedGameBitmap(Player.RESOURCE_IDS[type],
                Player.RESOURCE_STATS[type].width,
                Player.RESOURCE_STATS[type].height,
                Player.RESOURCE_STATS[type].xcount,
                Player.RESOURCE_STATS[type].border,
                Player.RESOURCE_STATS[type].spacing);
        bitmap.setSize(150,150);
//        bitmap = new IndexedGameBitmap(R.mipmap.fighters,67,80,11,0,0);
//        bitmap.setIndex(5);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        r = w<h?w/2:h/2;

        selected = false;
        boxPaint.setColor(Color.RED);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(3);
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
        if(selected)
            drawBox(canvas);
    }

    private void drawBox(Canvas canvas) {
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        canvas.drawRect(x-w/8,h*5.f/8,x+w/8,h*6.f/8, boxPaint);
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

    public boolean isClicked(float mx, float my) {
        float dx = x- mx;
        float dy = y-my;
        if(dx*dx+dy*dy <= r*r)
            return true;
        return false;
    }

}