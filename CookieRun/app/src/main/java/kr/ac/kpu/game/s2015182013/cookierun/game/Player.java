package kr.ac.kpu.game.s2015182013.cookierun.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.s2015182013.cookierun.R;
import kr.ac.kpu.game.s2015182013.cookierun.framework.GameObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.MainGame;


public class Player implements GameObject, kr.ac.kpu.game.s2015182013.cookierun.framework.BoxCollidable {
    private static final String TAG = kr.ac.kpu.game.s2015182013.cookierun.game.Player.class.getSimpleName();
    private static final float GRAVITY = 2000;
    private static final float JUMP_POWER = 1200;
    private final float ground_y;
    private float x, y;
    private float tx, ty;
    private float speed;
    private kr.ac.kpu.game.s2015182013.cookierun.framework.IndexedAnimationGameBitmap charBitmap;
    private float vertSpeed;

    private enum State{
        Running, Jump,DoubleJump,Slide,Hit
    }

    private State state = State.Running;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.ground_y = y;
        this.tx = x;
        this.ty = 0;
        this.speed = 800;
        charBitmap = new kr.ac.kpu.game.s2015182013.cookierun.framework.IndexedAnimationGameBitmap(R.mipmap.cookie,4.5f,0);
        charBitmap.setIndices(100,101,102,103);

    }

    public void moveTo(float x, float y) {
        this.tx = x;
        //this.ty = this.y;
    }

    public void update() {
        MainGame game = MainGame.get();
        if(state==State.Jump){
            float y = this.y + vertSpeed* game.frameTime;
            vertSpeed += GRAVITY*game.frameTime;
            this.y = y;
            if(this.y>=ground_y) {
                this.y = ground_y;
                state = State.Running;
                charBitmap.setIndices(100,101,102,103);
            }
        }

    }


    public void draw(Canvas canvas) {
        charBitmap.draw(canvas,x,y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
//        planeBitmap.getBoundingRect(x, y, rect);
    }

    public void Jump(){
        if(state != State.Running&&state != State.Jump && state != State.Hit){
            Log.d(TAG,"점프");
            return;
        }
        state = State.Jump;
        charBitmap.setIndices(7,8);
        vertSpeed = -JUMP_POWER;
    }
}
