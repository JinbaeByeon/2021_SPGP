package kr.ac.kpu.game.s2015182013.cookierun.game;

import android.util.Log;

import java.util.Random;

import kr.ac.kpu.game.s2015182013.cookierun.R;
import kr.ac.kpu.game.s2015182013.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s2015182013.cookierun.framework.object.ImageObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;

public class Platform extends ImageObject {
    private static final String TAG = Platform.class.getSimpleName();
    public static int UNIT_SIZE = 40;
    public static int SPEED = 150;

    public enum Type {
        T_10x2, T_2x2, T_3x1, RANDOM;

        float width() {
            int w = 1;
            switch (this) {
                case T_10x2: w = 10; break;
                case T_2x2: w = 2; break;
                case T_3x1: w = 3; break;
            }
            return w * UNIT_SIZE * GameView.MULTIPLIER;
        }
        float height() {
            int h = 1;
            switch (this) {
                case T_10x2: case T_2x2: h = 2; break;
                case T_3x1: h = 1; break;
            }
            return h * UNIT_SIZE * GameView.MULTIPLIER;
        }
        int resId() {
            switch (this) {
                case T_10x2:
                    return R.mipmap.cookierun_platform_480x48;
                case T_2x2:
                    return R.mipmap.cookierun_platform_124x120;
                case T_3x1:
                    return R.mipmap.cookierun_platform_120x40;
                default:
                    Log.e(TAG, "This may not be called !!!");
                    return 0;
            }
        }
    }
    public Platform(Type type, float x, float y) {
        if (type == Type.RANDOM) {
            Random r = new Random();
            type = r.nextInt(2) == 0 ? Type.T_10x2 : Type.T_2x2;
        }
        init(type.resId(), x, y);
        float w = type.width();
        float h = type.height();
        dstRect.set(x, y, x + w, y + h);
    }

    @Override
    public void update() {
        BaseGame game = BaseGame.get();
        float dx = SPEED * game.frameTime;
        dstRect.offset(-dx, 0);
        if (getRight() < 0) {
            game.remove(this);
        }
    }
}
