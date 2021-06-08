package kr.ac.kpu.game.s2015182013.termproject.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.HashMap;

import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class GameBitmap {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();
    protected int hw;
    protected int hh;

    public static Bitmap load(int resId) {
        Bitmap bitmap = bitmaps.get(resId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = false;
            bitmap = BitmapFactory.decodeResource(res, resId, opts);
            bitmaps.put(resId, bitmap);
        }
        return bitmap;
    }

    protected final Bitmap bitmap;
    protected RectF dstRect = new RectF();
    public GameBitmap(int resId) {
        bitmap = load(resId);
        setSize( bitmap.getWidth(),bitmap.getHeight());
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        ox =0;
    }

    protected Paint paint = new Paint();
    public void draw(Canvas canvas, float x, float y) {
        drawBoundingRect(canvas);
        //Rect srcRect = new Rect(left, )
        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;
        dstRect.set(dl, dt, dr, db);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    // 바운딩박스 그리기
    public void drawBoundingRect(Canvas canvas){
//        canvas.drawRect(dstRect.left + ox/2,dstRect.top,dstRect.right- ox/2,dstRect.bottom,paint);
    }

    protected int ox;
    public void setOffset(int x) {
        ox =x;
    }
    public int getHeight() {
        return hh*2*(int)GameView.MULTIPLIER;
    }

    public int getWidth() {
        return hw*2*(int)GameView.MULTIPLIER;
    }

    public void getBoundingRect(float x, float y, RectF rect) {
        //Rect srcRect = new Rect(left, )
        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;
        rect.set(dl, dt, dr, db);
    }

    public void setSize(int w, int h) {
        setWidth(w);
        setHeight(h);
    }

    public void setWidth(int w) {
        hw =w/2;
    }

    private void setHeight(int h) {
        hh=h/2;
    }
}