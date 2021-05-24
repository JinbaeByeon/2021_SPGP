package kr.ac.kpu.game.s2015182013.cookierun.game;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.s2015182013.cookierun.R;
import kr.ac.kpu.game.s2015182013.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s2015182013.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.object.HorizontalScrollBackground;
import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;

public class MainGame extends BaseGame {
    private boolean initialized;
    private Player player;
    private Score score;

    public enum Layer {
        bg, platform, item, player, ui, controller, LAYER_COUNT
    }

    public void add(Layer layer, GameObject obj) {
        add(layer.ordinal(), obj);
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }

    @Override
    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        float y = h - Platform.Type.T_2x2.height() - 255;
        player = new Player(200, y);
        //layers.get(Layer.player.ordinal()).add(player);
        add(Layer.player, player);
//        add(Layer.controller, new EnemyGenerator());

        int margin = (int) (20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_1, -10));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_2, -20));
        add(Layer.bg, new HorizontalScrollBackground(R.mipmap.cookie_run_bg_3, -30));
//
        add(Layer.controller, new StageMap("stage01.txt"));

        float tx = 0, ty = h - Platform.Type.T_2x2.height();
        while (tx < w) {
            Platform platform = new Platform(Platform.Type.RANDOM, tx, ty);
            add(Layer.platform, platform);
            tx += platform.getDstWidth();
//        VerticalScrollBackground clouds = new VerticalScrollBackground(R.mipmap.clouds, 20);
//        add(Layer.bg2, clouds);
        }

        initialized = true;
        return true;

    }

    @Override
    public void update() {
        super.update();

        // collision check
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        if (action == MotionEvent.ACTION_DOWN) {
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
//            player.moveTo(event.getX(), event.getY());
            player.jump();
//            int li = 0;
//            for (ArrayList<GameObject> objects: layers) {
//                for (GameObject o : objects) {
//                    Log.d(TAG, "L:" + li + " " + o);
//                }
//                li++;
//            }
            return true;
        }
        return false;
    }
}
