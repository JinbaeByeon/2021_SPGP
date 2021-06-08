package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Button implements GameObject {
    private static final String TAG = Button.class.getSimpleName();
    private float x;
    private GameBitmap button;
    private GameBitmap icon;
    private float y;
    private int r;
    private int nBomb;


    public Button(float x, float y) {
        Log.d(TAG, "loading bitmap for button");

        this.x = x;
        this.y = y;
        this.r = 80;

        button = new GameBitmap(R.mipmap.btn_bomb);
        button.setSize(r,r);

        icon = new GameBitmap(R.mipmap.icon_bomb);
        icon.setSize(r,r);
    }
    public Button(float x, float y,int resId) {
        Log.d(TAG, "loading bitmap for button");

        this.x = x;
        this.y = y;
        this.r = 80;

        button = new GameBitmap(resId);
        button.setSize(r,r);
    }


    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        button.draw(canvas, x, y);
        drawIcon(canvas);
    }

    public void drawIcon(Canvas canvas){
        for (int i = 1; i <= nBomb; i++) {
            icon.draw(canvas,x,y-i*r* GameView.MULTIPLIER);
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

    public void setBomb(int bomb) {
        nBomb =bomb;
    }
}