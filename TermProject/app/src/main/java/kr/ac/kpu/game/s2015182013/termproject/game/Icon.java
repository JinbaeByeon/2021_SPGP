package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;

public class Icon implements GameObject {
    private static final String TAG = Icon.class.getSimpleName();
    private float x;
    private GameBitmap button;
    private GameBitmap icon;
    private float y;
    private int r;

    public Icon(float x, float y) {
        Log.d(TAG, "loading bitmap for icon");

        this.x = x;
        this.y = y;
        this.r = 80;

        button = new GameBitmap(R.mipmap.btn_bomb);
        button.setSize(r,r);

        icon = new GameBitmap(R.mipmap.icon_bomb);
        icon.setSize(r,r);
    }


    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        button.draw(canvas, x, y);
    }

    public void drawIcon(Canvas canvas,int nBomb){
        for (int i = 0; i < nBomb; i++) {
            icon.draw(canvas,x,y-i*r);
        }
    }


    public boolean isClicked(float x, float y) {
        float dx = this.x-x;
        float dy = this.y-y;
        if(dx*dx+dy*dy<=r*r){
            return true;
        }
        return false;
    }
}