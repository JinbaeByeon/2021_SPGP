package kr.ac.kpu.game.s2015182013.termproject.framework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class IndexedGameBitmap extends GameBitmap {

    private static final String TAG = IndexedGameBitmap.class.getSimpleName();
    private int width, height, xcount, border, spacing;

    public IndexedGameBitmap(int resId, int width, int height, int xcount, int border, int spacing) {
        super(resId);
        this.width = width;
        this.height = height;
        this.xcount = xcount;
        this.border = border;
        this.spacing = spacing;

        hw = (int) (width / 2 * GameView.MULTIPLIER);
        hh = (int) (height / 2 * GameView.MULTIPLIER);
        setIndex(xcount/2);
    }

    protected Rect srcRect = new Rect();
    public void setIndex(int index) {
        int x = index % xcount;
        int y = index / xcount;
        int l = border + x * (width + spacing);
        int t = border + y * (height + spacing);
        int r = l + width;
        int b = t + height;
        srcRect.set(l, t, r, b);
    }

    @Override
    public void draw(Canvas canvas, float x, float y) {
        dstRect.set(x - hw, y - hh, x + hw, y + hh);
        drawBoundingRect(canvas);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
    public void draw(Canvas canvas, RectF dstRect) {
        drawBoundingRect(canvas);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public void getBoundingRect(float x, float y, RectF rect) {
        rect.set(dstRect.left + ox/2,dstRect.top,dstRect.right- ox/2,dstRect.bottom);
    }


    public int getXcount() {
        return xcount;
    }
}