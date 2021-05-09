package kr.ac.kpu.game.s1234567.samplegame.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.HashMap;

import kr.ac.kpu.game.s1234567.samplegame.R;
import kr.ac.kpu.game.s1234567.samplegame.ui.view.GameView;

public class AnimationGameBitmap extends GameBitmap {
    private static final int PIXEL_MULTIPLIER = 4;
    private Bitmap bitmap;
    private final int imageWidth;
    private final int imageHeight;
    private final int frameWidth;
    private final long createdOn;
    private int frameIndex;
    private final float framesPerSecond;
    private final int frameCount;

    public AnimationGameBitmap(int resId, float framesPerSecond, int frameCount) {
        bitmap = GameBitmap.load(resId);
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
        int hw = fw / 2 * PIXEL_MULTIPLIER;
        int hh = h / 2 * PIXEL_MULTIPLIER;
        Rect src = new Rect(fw * frameIndex, 0, fw * frameIndex + fw, h);
        RectF dst = new RectF(x - hw, y - hh, x + hw, y + hh);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public int getWidth() {
        return frameWidth * PIXEL_MULTIPLIER;
    }

    public int getHeight() {
        return imageHeight * PIXEL_MULTIPLIER;
    }
}












