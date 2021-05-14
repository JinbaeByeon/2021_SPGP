package kr.ac.kpu.game.s2015182013.cookierun;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s2015182013.cookierun.ui.view.GameView;


public class HorizontalScrollBackground implements GameObject {
    private final Bitmap bitmap;
    private final float speed;
    private float scroll;

    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    public HorizontalScrollBackground(int resId, int speed) {
        this.speed = speed * kr.ac.kpu.game.s2015182013.cookierun.ui.view.GameView.MULTIPLIER;
        bitmap = kr.ac.kpu.game.s2015182013.cookierun.framework.GameBitmap.load(resId);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        srcRect.set(0, 0, w, h);
        float l = 0;//x - w / 2 * GameView.MULTIPLIER;
        float t = 0; //y - h / 2 * GameView.MULTIPLIER;
        float r = kr.ac.kpu.game.s2015182013.cookierun.ui.view.GameView.view.getWidth();
        float b = r * h / w;
        dstRect.set(l, t, r, b);
    }
    @Override
    public void update() {
        kr.ac.kpu.game.s2015182013.cookierun.framework.MainGame game = kr.ac.kpu.game.s2015182013.cookierun.framework.MainGame.get();
        float amount = speed * game.frameTime;
        scroll += amount;
    }

    @Override
    public void draw(Canvas canvas) {
        int vw = kr.ac.kpu.game.s2015182013.cookierun.ui.view.GameView.view.getWidth();
        int vh = kr.ac.kpu.game.s2015182013.cookierun.ui.view.GameView.view.getHeight();
        int iw = bitmap.getWidth();
        int ih = bitmap.getHeight();
        int dw = vh * iw / ih;

        int curr = (int)scroll % dw;
        if (curr > 0) curr -= dw;

        while (curr < vw) {
            dstRect.set(curr, 0, curr+dw, vh);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            curr += dw;
        }
    }
}
