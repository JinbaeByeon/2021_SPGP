package kr.ac.kpu.game.s2015182013.termproject.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.game.s2015182013.termproject.R;
import kr.ac.kpu.game.s2015182013.termproject.framework.BGSound;
import kr.ac.kpu.game.s2015182013.termproject.framework.BoxCollidable;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameBitmap;
import kr.ac.kpu.game.s2015182013.termproject.framework.GameObject;
import kr.ac.kpu.game.s2015182013.termproject.framework.Recyclable;
import kr.ac.kpu.game.s2015182013.termproject.ui.activity.MainActivity;
import kr.ac.kpu.game.s2015182013.termproject.ui.view.GameView;
import kr.ac.kpu.game.s2015182013.termproject.utils.CollisionHelper;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    // singleton
    private static MainGame instance;
    private Player player;
    public Score score;
    private Health health;
    private VerticalScrollBackground bg,clouds;
    private GameBitmap bgTitle;
    private Button button;

    public static MainGame get() {
        if (instance == null) {
            instance = new MainGame();
        }
        return instance;
    }
    public float frameTime;
    private boolean initialized;
    private Scene scene = Scene.START;
    private enum Scene{
        START,INGAME,END
    }

//    Player player;
    ArrayList<ArrayList<GameObject>> layers;
    private static HashMap<Class, ArrayList<GameObject>> recycleBin = new HashMap<>();

    public void reset(){
        recycleBin.clear();
        layers.clear();
        initialized = false;
        if(scene ==Scene.INGAME) {
            initLayers(Layer.LAYER_COUNT.ordinal());
            int w = GameView.view.getWidth();
            int h = GameView.view.getHeight();
            scene = Scene.END;
            Button btn_restart = new Button(w/4,h/2,R.mipmap.btn_restart);
            Button btn_exit = new Button(w*3/4,h/2,R.mipmap.btn_exit);
            add(Layer.button,btn_restart);
            add(Layer.button,btn_exit);
        }
    }

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
        bg1, coin,item,eBullet,enemy, pBullet, player, bg2, ui,button, controller, LAYER_COUNT
    }

    public void addPlayer(int type){
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();
        player = new Player(w / 2, h - 300,type);

        add(Layer.player, player);
    }
    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.LAYER_COUNT.ordinal());

//        addPlayer(w,h);


        add(Layer.controller, new EnemyGenerator());

        int margin = (int) (20 * GameView.MULTIPLIER);
        if(score == null)
            score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        if(bg ==null)
            bg = new VerticalScrollBackground(R.mipmap.bg_city, 30);
        add(Layer.bg1, bg);

        if(clouds ==null)
            clouds = new VerticalScrollBackground(R.mipmap.clouds, 40);
        add(Layer.bg2, clouds);
        bgTitle = new GameBitmap(R.mipmap.bg_title);
        bgTitle.setSize(w/(int)GameView.MULTIPLIER,h/(int)GameView.MULTIPLIER);



        for (int i = 0; i < 4; i++) {
            Button2 p1 = new Button2(w*(i*2+1)/8,h*11/16,i);
            add(Layer.button,p1);
        }


        initialized = true;

        if(BGSound.get().isPlaying())
            BGSound.get().pause();
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
                    if((o1 instanceof Bomb)){}
//                        continue;
                    else if(o2 instanceof Bullet)
                        ((Bullet) o2).attack((BoxCollidable) o1);
                    else if(o2 instanceof Coin)
                        score.addScore(100);
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
        switch (scene){
            case START: {

            } break;
            case INGAME: {

                //if (!initialized) return;
                for (ArrayList<GameObject> objects : layers) {
                    for (GameObject o : objects) {
                        o.update();
                    }
                }

                // 플레이어 - 적 총알
                collides(layers.get(Layer.player.ordinal()), layers.get(Layer.eBullet.ordinal()));

                // 플레이어 - 코인
                collides(layers.get(Layer.player.ordinal()), layers.get(Layer.coin.ordinal()));

                // 플레이어 - 아이템
                collides(layers.get(Layer.player.ordinal()), layers.get(Layer.item.ordinal()));

                // 적 - 플레이어 총알
                collides(layers.get(Layer.enemy.ordinal()), layers.get(Layer.pBullet.ordinal()));

                button.setBomb(player.getBomb());
            }break;
            case END:{

            }
        }
    }

    Paint bgpaint = new Paint();
    public void draw(Canvas canvas) {
        switch (scene) {
            case START: {
                int w = GameView.view.getWidth();
                int h = GameView.view.getHeight();
                bgTitle.draw(canvas,w/2,h/2);
                bgpaint.setColor(Color.BLACK);
                canvas.drawRect(0,h*5.f/8,w,h*6.f/8, bgpaint);
                for(GameObject o : layers.get(Layer.button.ordinal()))
                    o.draw(canvas);
            }
            break;
            case INGAME:  {

                //if (!initialized) return;
                for (ArrayList<GameObject> objects : layers) {
                    for (GameObject o : objects) {
                        o.draw(canvas);
                    }
                }
            }
            break;
            case END:{
                ArrayList<GameObject> buttons = layers.get(Layer.button.ordinal());
                for (GameObject o : buttons) {
                    o.draw(canvas);
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if(scene== Scene.START){
            ArrayList<GameObject> btns =  layers.get(Layer.button.ordinal());
            if(action==MotionEvent.ACTION_UP) {
                for (int i = 0; i < btns.size(); i++) {
                    Button2 btn = (Button2) btns.get(i);
                    if (btn.isClicked(event.getX(), event.getY())) {
                        scene = Scene.INGAME;
                        BGSound.get().playBGM();
                        addPlayer(i);
                        layers.remove(Layer.button.ordinal());

                        if (button == null) {
                            int w = GameView.view.getWidth();
                            int h = GameView.view.getHeight();
                            button = new Button(w - 100.f, h - 100.f);
                        }
                        add(Layer.button, button);

                        return true;
                    }
                }
            }
            else {
                boolean isSelect = false;
                for (int i = 0; i < btns.size(); i++) {
                    Button2 btn = (Button2)btns.get(i);
                    if(btn.isClicked(event.getX(), event.getY())){
                        btn.selected = true;
                        isSelect =true;
                    }
                    else
                        btn.selected =false;
                }
                if(isSelect)
                    return true;
            }
        }
        else if(scene == Scene.INGAME) {
            if (action == MotionEvent.ACTION_DOWN) {
                if (button.isClicked(event.getX(), event.getY())) {
                    player.shotBomb();
                }
                player.setPivot(event.getX(), event.getY());

            }
            if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                player.moveTo(event.getX(), event.getY());
                return true;
            }
        }
        else if(scene == Scene.END){
            ArrayList<GameObject> btns =  layers.get(Layer.button.ordinal());

            if(action==MotionEvent.ACTION_DOWN) {
                if (((Button) btns.get(0)).isClicked(event.getX(), event.getY())) {
                    scene = Scene.START;
                    layers.remove(Layer.button.ordinal());
                    initResources();
                    Log.d(TAG,"restart");
                } else if (((Button) btns.get(1)).isClicked(event.getX(), event.getY())) {
                    GameView.view.finishActivity();
                    scene = Scene.START;
                    layers.remove(Layer.button.ordinal());
                    Log.d(TAG,"exit");
                }
            }

        }

        return false;
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                if(layers==null) return;
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
