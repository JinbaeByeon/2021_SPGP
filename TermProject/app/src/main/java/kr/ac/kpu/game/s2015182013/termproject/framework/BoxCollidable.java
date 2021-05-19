package kr.ac.kpu.game.s2015182013.termproject.framework;

import android.graphics.RectF;

public interface BoxCollidable {
    //public RectF getBoundingRect();
    public void getBoundingRect(RectF rect);

    public void hitBullet(int damage);
}
