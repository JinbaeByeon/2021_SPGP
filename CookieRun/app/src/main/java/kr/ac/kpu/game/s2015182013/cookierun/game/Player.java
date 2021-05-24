package kr.ac.kpu.game.s2015182013.cookierun.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.cookierun.R;
import kr.ac.kpu.game.s2015182013.cookierun.framework.iface.BoxCollidable;
import kr.ac.kpu.game.s2015182013.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.bitmap.IndexedAnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.cookierun.framework.game.BaseGame;

public class Player implements GameObject, BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f / 7.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;
    private static final float GRAVITY = 2500;
    private static final float JUMP_POWER = 1200;
    private final IndexedAnimationGameBitmap charBitmap;
    private final float ground_y;
    private float x, y;
    private float vertSpeed;
    private int[] ANIM_INDICES_RUNNING = { 100, 101, 102, 103 };
    private int[] ANIM_INDICES_JUMP = { 7, 8 };
    private int[] ANIM_INDICES_DOUBLE_JUMP = { 1, 2, 3, 4 };

    private int[][] BB_DIFFS= {
            {-60,0,60,135},
            {-60,20,60,135},
            {-60,20,60,135}};

    private enum State {
        running, jump, doubleJump, slide, hit
    }

    public void setState(State state) {
        this.state = state;
        int[] indices = ANIM_INDICES_RUNNING;
        switch (state) {
            case running:       indices = ANIM_INDICES_RUNNING; break;
            case jump:          indices = ANIM_INDICES_JUMP; break;
            case doubleJump:    indices = ANIM_INDICES_DOUBLE_JUMP; break;
        }
        charBitmap.setIndices(indices);
    }

    private State state = State.running;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.ground_y = y;
        this.charBitmap = new IndexedAnimationGameBitmap(R.mipmap.cookie, 7.5f, 0);
        setState(State.running);
    }

    public void update() {
        BaseGame game = BaseGame.get();
        if (state == State.jump || state == State.doubleJump) {
            float y = this.y + vertSpeed * game.frameTime;
//            charBitmap.move(0, y - this.y);
            vertSpeed += GRAVITY * game.frameTime;
            if (y >= ground_y) {
                y = ground_y;
                setState(State.running);
//                state = State.running;
//                this.charBitmap.setIndices(ANIM_INDICES_RUNNING);
            }
            this.y = y;
        }
    }

    public void draw(Canvas canvas) {
        charBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        charBitmap.getBoundingRect(x,y,rect,BB_DIFFS[state.ordinal()]);
        //planeBitmap.getBoundingRect(x, y, rect);
    }

    public void jump() {
        //if (state != State.running && state != State.jump && state != State.slide) {
        if (state == State.running) {
            setState(State.jump);
//            state = State.jump;
//            charBitmap.setIndices(ANIM_INDICES_JUMP);
            vertSpeed = -JUMP_POWER;
        } else if (state == State.jump) {
            setState(State.doubleJump);
//            state = State.doubleJump;
//            charBitmap.setIndices(ANIM_INDICES_DOUBLE_JUMP);
            vertSpeed = -JUMP_POWER;
        } else {
                Log.d(TAG, "Not in a state that can jump: " + state);
            return;
        }
    }
}
