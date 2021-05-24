package kr.ac.kpu.game.s2015182013.cookierun.framework.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s2015182013.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s2015182013.cookierun.framework.bitmap.GameBitmap;
import kr.ac.kpu.game.s2015182013.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;

public class HorizontalScrollBackground implements GameObject {
    private final Bitmap bitmap;
    private final float speed;
    private float scroll;

    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    public HorizontalScrollBackground(int resId, int speed) {
        this.speed = speed * GameView.MULTIPLIER;
        bitmap = GameBitmap.load(resId);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        srcRect.set(0, 0, w, h);
        float l = 0;
        float t = 0;
        float b = GameView.view.getHeight();
        float r = b * w / h;
        dstRect.set(l, t, r, b);
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        float amount = speed * game.frameTime;
        scroll += amount;
    }

    @Override
    public void draw(Canvas canvas) {
        int vw = GameView.view.getWidth();
        int vh = GameView.view.getHeight();
        int iw = bitmap.getWidth();
        int ih = bitmap.getHeight();
        int dw = vh * iw / ih;

        int curr = (int)scroll % dw;
        if (curr > 0) curr -= dw;

        while (curr < vw) {
            dstRect.set(curr, 0, curr + dw, vh);
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            curr += dw;
        }
    }
}
