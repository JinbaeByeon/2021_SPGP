package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;
import kr.ac.kpu.game.s2015182013.termproject.utils.CollisionHelper;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    // singleton
    private static MainGame instance;
    private Player player;
    public Score score;
    private Health health;

    public static MainGame get() {
        if (instance == null) {
            instance = new MainGame();
        }
        return instance;
    }
    public float frameTime;
    private boolean initialized;

//    Player player;
    ArrayList<ArrayList<GameObject>> layers;
    private static HashMap<Class, ArrayList<GameObject>> recycleBin = new HashMap<>();

    public void recycle(GameObject object) {
        Class clazz = object.getClass();
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null) {
            array = new ArrayList<>();
            recycleBin.put(clazz, array);
        }
        array.add(object);
    }
    public GameObject get(Class clazz) {
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null || array.isEmpty()) return null;
        return array.remove(0);
    }

    public enum Layer {
        bg1, coin,item,eBullet,enemy, pBullet, player, bg2, ui, controller, LAYER_COUNT
    }
    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

        player = new Player(w/2, h - 300);
        //layers.get(Layer.player.ordinal()).add(player);
        add(Layer.player, player);
        add(Layer.controller, new EnemyGenerator());

        int margin = (int) (20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        VerticalScrollBackground bg = new VerticalScrollBackground(R.mipmap.bg_city, 30);
        add(Layer.bg1, bg);

        VerticalScrollBackground clouds = new VerticalScrollBackground(R.mipmap.clouds, 40);
        add(Layer.bg2, clouds);

        initialized = true;
        return true;
    }

    private void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    private void collides(ArrayList<GameObject> objects_PE, ArrayList<GameObject> objects_BCI){
        for (GameObject o1: objects_PE) {
            boolean collided = false;
            for (GameObject o2: objects_BCI) {
                if (CollisionHelper.collides((BoxCollidable)o1, (BoxCollidable)o2)) {
                    if(o2 instanceof Bullet)
                        ((Bullet) o2).attack((BoxCollidable)o1);
                    else if(o2 instanceof Coin)
                        score.addScore(50);
                    else if(o2 instanceof Item&& o1 instanceof Player) {
                        ((Item) o2).upgrade((Player)o1);
                    }

                    remove(o2, false);
                    collided = true;
                    break;
                }
            }
            if (collided) {
                break;
            }
        }

    }

    public void update() {
        //if (!initialized) return;
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }

        // 플레이어 - 적 총알
        collides(layers.get(Layer.player.ordinal()),layers.get(Layer.eBullet.ordinal()));

        // 플레이어 - 코인
        collides(layers.get(Layer.player.ordinal()),layers.get(Layer.coin.ordinal()));

        // 플레이어 - 아이템
        collides(layers.get(Layer.player.ordinal()),layers.get(Layer.item.ordinal()));

        // 적 - 플레이어 총알
        collides(layers.get(Layer.enemy.ordinal()),layers.get(Layer.pBullet.ordinal()));

    }

    public void draw(Canvas canvas) {
        //if (!initialized) return;
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//        if (action == MotionEvent.ACTION_DOWN) {
        if(action == MotionEvent.ACTION_DOWN){
            player.setPivot(event.getX(), event.getY());
        }
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            player.moveTo(event.getX(), event.getY());
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

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
//        Log.d(TAG, "<A> object count = " + objects.size());
    }

    public void remove(GameObject gameObject) {
        remove(gameObject, true);
    }
    public void remove(GameObject gameObject, boolean delayed) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> objects: layers) {
                    boolean removed = objects.remove(gameObject);
                    if (removed) {
                        if (gameObject instanceof Recyclable) {
                            ((Recyclable) gameObject).recycle();
                            recycle(gameObject);
                        }
                        //Log.d(TAG, "Removed: " + gameObject);
                        break;
                    }
                }
            }
        };
        if (delayed) {
            GameView.view.post(runnable);
        } else {
            runnable.run();
        }
    }
}
