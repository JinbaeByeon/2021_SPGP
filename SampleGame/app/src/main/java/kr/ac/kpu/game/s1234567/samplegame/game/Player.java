package kr.ac.kpu.game.s1234567.samplegame.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;

import kr.ac.kpu.game.s1234567.samplegame.R;
import kr.ac.kpu.game.s1234567.samplegame.framework.GameObject;
import kr.ac.kpu.game.s1234567.samplegame.framework.Sound;
import kr.ac.kpu.game.s1234567.samplegame.ui.view.GameView;

public class Player implements GameObject {
    private static final String TAG = Player.class.getSimpleName();
    private static int imageWidth;
    private static int imageHeight;
    private float x, y;
    private float dx, dy;
    private float tx, ty;
    private float speed;
    private static Bitmap bitmap;
    private float angle = 0;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.tx = 0;
        this.ty = 0;
        this.speed = 800;
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
    }

    public void moveTo(float x, float y) {
        Sound.play(R.raw.hadouken);
        Bullet bullet = new Bullet(this.x, this.y, x, y);
        MainGame game = MainGame.get();
        game.add(bullet);
//        this.tx = x;
//        this.ty = y;
        float delta_x = x - this.x;
        float delta_y = y - this.y;
        this.angle = (float) Math.atan2(delta_y, delta_x);
        Log.d(TAG, "Angle = " + angle);
//        MainGame game = MainGame.get();
//        float move_dist = speed * game.frameTime;
//        this.dx = (float) (move_dist * Math.cos(angle));
//        this.dy = (float) (move_dist * Math.sin(angle));
    }

    public void update() {
        x += dx;
        if ((dx > 0 && x > tx) || (dx < 0 && x < tx)) {
            x = tx;
        }
        y += dy;
        if ((dy > 0 && y > ty) || (dy < 0 && y < ty)) {
            y = ty;
        }
    }

    public void draw(Canvas canvas) {
        float left = x - imageWidth / 2;
        float top = y - imageWidth / 2;
        float degree = (float) (angle * 180 / Math.PI) + 90;
        canvas.save();
        canvas.rotate(degree, x, y);
        canvas.drawBitmap(bitmap, left, top, null);
        canvas.restore();
    }
}
