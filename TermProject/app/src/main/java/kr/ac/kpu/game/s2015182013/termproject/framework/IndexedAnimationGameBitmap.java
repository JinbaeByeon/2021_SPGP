package kr.ac.kpu.game.s2015182013.termproject.framework;


import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class IndexedAnimationGameBitmap extends AnimationGameBitmap {
    private int xcount, border, spacing;

    private static final String TAG = IndexedAnimationGameBitmap.class.getSimpleName();
    private final int frameHeight;

    public IndexedAnimationGameBitmap(int resId, float framesPerSecond, int frameCount, int width, int height, int xcount, int border, int spacing) {
        super(resId, framesPerSecond, frameCount);
        this.frameWidth = width;
        this.frameHeight = height;
        this.xcount = xcount;
        this.border = border;
        this.spacing = spacing;
    }

    protected ArrayList<Rect> srcRects;
    public void setIndices(int... indices) {
        srcRects = new ArrayList<>();
        for (int index: indices) {
//            int x = index % 100;
//            int y = index / 100;
//            int l = 2 + x * 272;
//            int t = 2 + y * 272;
//            int r = l + 270;
//            int b = t + 270;
            int x = index % xcount;
            int y = index / xcount;
            int l = border + x * (frameWidth + spacing);
            int t = border + y * (frameHeight + spacing);
            int r = l + frameWidth;
            int b = t + frameHeight;
            Rect rect = new Rect(l, t, r, b);
            Log.d(TAG, "Adding rect: " + rect);
            srcRects.add(rect);
        }
        frameCount = indices.length;
    }

    @Override
    public void draw(Canvas canvas, float x, float y) {
        int elapsed = (int)(System.currentTimeMillis() - createdOn);
        frameIndex = Math.round(elapsed * 0.001f * framesPerSecond) % frameCount;
        //Log.d(TAG, "frameIndex=" + frameIndex + " frameCount=" + frameCount);

        int fw = frameWidth;
        int h = frameHeight;
        float hw = fw / 2 * GameView.MULTIPLIER;
        float hh = h / 2 * GameView.MULTIPLIER;
        dstRect.set(x - hw, y - hh, x + hw, y + hh);
        canvas.drawBitmap(bitmap, srcRects.get(frameIndex), dstRect, null);
    }
}