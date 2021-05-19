package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Health implements GameObject {
    private final int left;
    private final int top;
    Paint paint = new Paint();
    private int maxHp;
    private int hp;

    public void setHP(int hp) {
        this.hp = hp;
    }


    public Health(int left, int top,int hp) {
        this.left = left;
        this.top = top;
        maxHp = this.hp = hp;
    }
    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        int x = left;
        float dw = 300;
        int dh = 80;
        paint.setColor(Color.GRAY);
        canvas.drawRect(x,top,x+dw,top+dh,paint);
        paint.setColor(Color.RED);
        dw *= (float)hp/maxHp;
        canvas.drawRect(x,top,x+dw,top+dh,paint);
    }

}