package kr.ac.kpu.game.s2015182013.termproject.framework;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class AnimationGameBitmap extends GameBitmap {
//    private static final int PIXEL_MULTIPLIER = 4;
    private final int imageWidth;
    private final int imageHeight;
    protected int frameWidth;
    protected long createdOn;
    protected int frameIndex;
    protected final float framesPerSecond;
    protected int frameCount;
    protected int fw;
    protected int h;

    protected Rect srcRect = new Rect();
    public AnimationGameBitmap(int resId, float framesPerSecond, int frameCount) {
        super(resId);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        if (frameCount == 0) {
            frameCount = imageWidth / imageHeight;
        }
        frameWidth = imageWidth / frameCount;
        this.framesPerSecond = framesPerSecond;
        this.frameCount = frameCount;
        createdOn = System.currentTimeMillis();
        frameIndex = 0;
    }

    //    public void update() {
//        int elapsed = (int)(System.currentTimeMillis() - createdOn);
//        frameIndex = Math.round(elapsed * 0.001f * framesPerSecond) % frameCount;
//    }

    public void draw(Canvas canvas, float x, float y) {
        int elapsed = (int)(System.currentTimeMillis() - createdOn);
        frameIndex = Math.round(elapsed * 0.001f * framesPerSecond) % frameCount;

        int fw = frameWidth;
        int h = imageHeight;
        float hw = this.fw / 2 * GameView.MULTIPLIER;
        float hh = this.h / 2 * GameView.MULTIPLIER;
        srcRect.set(fw * frameIndex, 0, fw * frameIndex + fw, h);
        dstRect.set(x - hw, y - hh, x + hw, y + hh);
        drawBoundingRect(canvas);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    @Override
    public void getBoundingRect(float x, float y, RectF rect) {
        rect.set(dstRect);
    }

    public int getWidth() {
        return (int) (frameWidth * GameView.MULTIPLIER);
    }

    public int getHeight() {
        return (int) (h * GameView.MULTIPLIER);
    }

    public void reset() {
        createdOn = System.currentTimeMillis();
    }

    public void setSize(int w, int h) {
        setWidth(w);
        setHeight(h);
    }

    public void setWidth(int w) {
        fw = w;
    }

    private void setHeight(int h) {
        this.h=h;
    }


}












