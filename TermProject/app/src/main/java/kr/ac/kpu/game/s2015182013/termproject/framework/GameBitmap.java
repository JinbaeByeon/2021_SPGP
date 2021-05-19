package kr.ac.kpu.game.s2015182013.termproject.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.HashMap;

import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class GameBitmap {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();
    private int hw;
    private int hh;

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
        setSize(getWidth(),getHeight());
    }

    public void draw(Canvas canvas, float x, float y) {
        //Rect srcRect = new Rect(left, )
        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;
        dstRect.set(dl, dt, dr, db);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public int getWidth() {
        return bitmap.getWidth();
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