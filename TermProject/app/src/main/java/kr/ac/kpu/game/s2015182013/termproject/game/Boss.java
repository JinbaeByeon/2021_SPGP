package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.util.Log;


import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.AnimationGameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.Sound;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;

public class Boss extends Enemy {
    private static final int[] RESOURCE_IDS = {
            R.mipmap.boss_a,R.mipmap.boss_b
    };
    private static final String TAG = Boss.class.getSimpleName();
    private static final float[] SKILL_INTERVAL = {5.f,1.f};
    private float bullet_rotate;
    private int fireDir;
    private float[] skillTime;
    private int nSkill;


    private Boss()
    {
        super();
        Log.d(TAG, "Boss constructor");
    }

    public static Boss get(int level, int x, int y, int speed) {
        MainGame game = MainGame.get();
        Boss boss = (Boss) game.get(Boss.class);
        if (boss == null) {
            boss = new Boss();
        }

        boss.init(level, x, y, speed);
        return boss;
    }

    private void init(int level, int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        score = hp =maxHp = level *1000;
        fireTime = 0.0f;
        FIRE_INTERVAL = 1.f/5.f;
        power = level*10;
        bullet_rotate =(float)Math.PI/2.f;
        fireDir = 1;

        expBitmap = new AnimationGameBitmap(R.mipmap.hit,8,6);
        expBitmap.setSize(100,100);
        expTime = 0.0f;
        isHitted=false;


        int type = (level - 1)%2;
        int resId = RESOURCE_IDS[type];
        nSkill = type+1;
        skillTime = new float[nSkill];
        for(int i=0;i<nSkill;++i)
            skillTime[i] = 0.0f;

        if(type ==0)
            planeBitmap = new GameBitmap(resId);
        else
            planeBitmap = new AnimationGameBitmap(resId,12,3);
        planeBitmap.setSize(400,400);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        int h =GameView.view.getHeight();
        if(y<h/4)
            y += speed * game.frameTime;


        if(isHitted)
            expTime += game.frameTime;
        if(expTime>1.f){
            expTime =0.f;
            isHitted = false;
        }

        fireTime += game.frameTime;
        if (fireTime >= FIRE_INTERVAL) {
            fireBullet(0);
            fireTime -= FIRE_INTERVAL;
            bullet_rotate+= fireDir * Math.PI /20.f;
            if(bullet_rotate>=Math.PI*5/6&& fireDir>0)
                fireDir = -1;
            else if(bullet_rotate<=Math.PI/6&&fireDir<0)
                fireDir = 1;
        }

        for(int i=0;i<nSkill;++i) {
            skillTime[i] += game.frameTime;
            if (skillTime[i] >= SKILL_INTERVAL[i]) {
                fireBullet(i+1);
                skillTime[i] -= SKILL_INTERVAL[i];
            }
        }
    }

    private void fireBullet(int type) {
        MainGame game = MainGame.get();
        Bullet bullet;
        if(type==0) {
            bullet = Bullet.get(this.x, this.y, BULLET_SPEED, power,
                    (float) Math.cos(bullet_rotate), (float) Math.sin(bullet_rotate));
            game.add(MainGame.Layer.eBullet, bullet);
        }
        else if(type==1){
            int w = GameView.view.getWidth();
            for(int i=0; i<w;i+=200){
                bullet = Bullet.get(i,y,BULLET_SPEED,power, type);
                game.add(MainGame.Layer.eBullet,bullet);
            }
        }
        else if(type==2) {
            Player p = (Player) game.layers.get(MainGame.Layer.player.ordinal()).get(0);
            float tx = p.getX() - x;
            float ty = p.getY() - y;
            double dist = Math.sqrt(tx * tx + ty * ty);

            bullet = Bullet.get(this.x, this.y, BULLET_SPEED, power,
                    tx / (float) dist, ty / (float) dist);
            game.add(MainGame.Layer.eBullet, bullet);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
        drawHealthBar(canvas);
        if(isHitted)
            expBitmap.draw(canvas,x,y+50);
    }



    @Override
    public void recycle() {
        // 재활용통에 들어가는 시점에 불리는 함수. 현재는 할일없음.
        FIRE_INTERVAL = 1.0f ;
        fireTime = 0;
    }

    @Override
    public void hitBullet(int damage) {
        MainGame game = MainGame.get();
        hp -= damage;
        Sound.play(R.raw.enemy_hit);

        if(hp<0) {
            game.remove(this, false);
            game.score.addScore(score);
            Coin coin = Coin.get(x,y);
            game.add(MainGame.Layer.coin,coin);
            Sound.play(R.raw.enemy_destroy);
            EnemyGenerator.startGenerate();
        }
        expBitmap.reset();
        isHitted=true;
    }

}