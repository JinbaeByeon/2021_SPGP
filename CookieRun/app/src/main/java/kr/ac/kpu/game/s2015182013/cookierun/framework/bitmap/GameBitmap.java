package kr.ac.kpu.game.s2015182013.cookierun.framework.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.HashMap;

import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;

public class GameBitmap {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();

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
    }

    public void draw(Canvas canvas, float x, float y) {
        int hw = getWidth() / 2;
        int hh = getHeight() / 2;
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
        int hw = getWidth() / 2;
        int hh = getHeight() / 2;
        //Rect srcRect = new Rect(left, )
        float dl = x - hw * GameView.MULTIPLIER;
        float dt = y - hh * GameView.MULTIPLIER;
        float dr = x + hw * GameView.MULTIPLIER;
        float db = y + hh * GameView.MULTIPLIER;
        rect.set(dl, dt, dr, db);
    }
    public void getBoundingRect(float x, float y, RectF rect,int... diff) {
        float dl = x - diff[0] * GameView.MULTIPLIER;
        float dt = y - diff[1] * GameView.MULTIPLIER;
        float dr = x + diff[2] * GameView.MULTIPLIER;
        float db = y + diff[3] * GameView.MULTIPLIER;
        rect.set(dl, dt, dr, db);
    }

}
