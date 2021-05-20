package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Score implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    private final int top;

    public void setScore(int score) {
        this.score = score;
        this.displayScore = score;
    }
    public void addScore(int amount) {
        score += amount;

        MainGame game = MainGame.get();
        Random r = new Random();
        if(score%1501==0){
            Item item = Item.get(
                    r.nextInt(1000)+500,
                    r.nextInt(1000)+500,
                    r.nextBoolean()?300:-300,
                    r.nextBoolean()?300:-300, Item.Type.Bomb);

            game.add(MainGame.Layer.item,item);
        }
        else if(score%1001==0){
            Item item = Item.get(
                    r.nextInt(1000)+500,
                    r.nextInt(1000)+500,
                    r.nextBoolean()?300:-300,
                    r.nextBoolean()?300:-300, Item.Type.Health);

            game.add(MainGame.Layer.item,item);
        }
        else if(score%500==0){
            Item item = Item.get(
                    r.nextInt(1000)+500,
                    r.nextInt(1000)+500,
                    r.nextBoolean()?300:-300,
                    r.nextBoolean()?300:-300, Item.Type.Power);

            game.add(MainGame.Layer.item,item);
        }
    }

    private int score, displayScore;
    private Rect src = new Rect();
    private RectF dst = new RectF();

    public Score(int right, int top) {
        bitmap = GameBitmap.load(R.mipmap.number_24x32);
        this.right = right;
        this.top = top;
    }
    @Override
    public void update() {
        if (displayScore < score) {
            displayScore++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.displayScore;
        int nw = bitmap.getWidth() / 10;
        int nh = bitmap.getHeight();
        int x = right;
        int dw = (int) (nw * GameView.MULTIPLIER);
        int dh = (int) (nh * GameView.MULTIPLIER);
        while (value > 0) {
            int digit = value % 10;
            src.set(digit * nw, 0, (digit + 1) * nw, nh);
            x -= dw;
            dst.set(x, top, x + dw, top + dh);
            canvas.drawBitmap(bitmap, src, dst, null);
            value /= 10;
        }
    }

}