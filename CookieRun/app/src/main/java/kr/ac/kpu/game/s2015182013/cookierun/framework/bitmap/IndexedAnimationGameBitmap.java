package kr.ac.kpu.game.s2015182013.cookierun.framework.bitmap;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;

public class IndexedAnimationGameBitmap extends AnimationGameBitmap {

    private static final String TAG = IndexedAnimationGameBitmap.class.getSimpleName();
    private final int frameHeight;

    public IndexedAnimationGameBitmap(int resId, float framesPerSecond, int frameCount) {
        super(resId, framesPerSecond, frameCount);
        this.frameWidth = 270;
        this.frameHeight = 270;
    }

    protected ArrayList<Rect> srcRects;
    public void setIndices(int... indices) {
        srcRects = new ArrayList<>();
        for (int index: indices) {
            int x = index % 100;
            int y = index / 100;
            int l = 2 + x * 272;
            int t = 2 + y * 272;
            int r = l + 270;
            int b = t + 270;
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
