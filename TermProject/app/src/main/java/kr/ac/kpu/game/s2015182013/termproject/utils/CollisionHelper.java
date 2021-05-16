package kr.ac.kpu.game.s2015182013.termproject.utils;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;

public class CollisionHelper {
    private static final String TAG = kr.ac.kpu.game.s2015182013.termproject.utils.CollisionHelper.class.getSimpleName();
    private static RectF rect1 = new RectF();
    private static RectF rect2 = new RectF();
    public static boolean collides(BoxCollidable o1, BoxCollidable o2) {
        o1.getBoundingRect(rect1);
        o2.getBoundingRect(rect2);

        if (rect1.left > rect2.right) return false;
        if (rect1.top > rect2.bottom) return false;
        if (rect1.right < rect2.left) return false;
        if (rect1.bottom < rect2.top) return false;

        Log.d(TAG, "1:" + rect1 + " 2:" + rect2);
        return true;
    }
}
