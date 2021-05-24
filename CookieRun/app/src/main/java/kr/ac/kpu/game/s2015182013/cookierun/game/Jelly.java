package kr.ac.kpu.game.s2015182013.cookierun.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.cookierun.R;
import kr.ac.kpu.game.s2015182013.cookierun.framework.bitmap.GameBitmap;
import kr.ac.kpu.game.s2015182013.cookierun.framework.bitmap.IndexedGameBitmap;
import kr.ac.kpu.game.s2015182013.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s2015182013.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.object.ImageObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;

public class Jelly extends ImageObject {
    private static final String TAG = Jelly.class.getSimpleName();
    public static final int JELLY_WIDTH = 66;
    public static final int JELLY_HEIGHT = 66;
    public static final int JELLIES_IN_A_ROW = 30;
    public static final int BORDER_WIDTH = 2;
    public static final int SPACING_WIDTH = 2;
    private final IndexedGameBitmap ibmp;

    public Jelly(int index, float x, float y) {
        ibmp = new IndexedGameBitmap(R.mipmap.jelly, JELLY_WIDTH, JELLY_HEIGHT, JELLIES_IN_A_ROW, BORDER_WIDTH, SPACING_WIDTH);
        ibmp.setIndex(index);
        float l = x - JELLY_WIDTH / 2 * GameView.MULTIPLIER;
        float t = y - JELLY_HEIGHT / 2 * GameView.MULTIPLIER;
        float r = x + JELLY_WIDTH / 2 * GameView.MULTIPLIER;
        float b = y + JELLY_HEIGHT / 2 * GameView.MULTIPLIER;
        dstRect.set(l, t, r, b);
        Log.d(TAG, "index = " + index + " Rect = " + dstRect);
    }
    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        float dx = Platform.SPEED * game.frameTime;
        dstRect.offset(-dx, 0);
        if (getRight() < 0) {
            game.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        ibmp.draw(canvas, dstRect);
    }
}
