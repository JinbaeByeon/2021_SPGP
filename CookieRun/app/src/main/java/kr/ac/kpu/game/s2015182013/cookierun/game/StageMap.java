package kr.ac.kpu.game.s2015182013.cookierun.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.s2015182013.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s2015182013.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s2015182013.cookierun.framework.view.GameView;

public class StageMap implements GameObject {
    private static final String TAG = StageMap.class.getSimpleName();
    private final ArrayList<String> lines= new ArrayList<String>();
    private int columns;
    private int raws;

    public StageMap(String filename){
        AssetManager assets = GameView.view.getContext().getAssets();
        try {
            InputStream is = assets.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String header = reader.readLine();
            String[] comps = header.split(" ");
            columns = Integer.parseInt(comps[0]);
            raws = Integer.parseInt(comps[1]);
            Log.d(TAG,"Col = " + columns + ", Raw = " + raws);
            while (true){
                String line = reader.readLine();
                if(line==null){
                    break;
                }
                Log.d(TAG,"Line = " + line);
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update() {
        MainGame game = (MainGame) BaseGame.get();
        ArrayList<GameObject> objects = game.objectsAt(MainGame.Layer.platform);
        float rightMost = 0;
        for (GameObject obj: objects) {
            Platform platform = (Platform) obj;
            float right = platform.getRight();
            if (rightMost < right) {
                rightMost = right;
            }
        }
        float vw = GameView.view.getWidth();
        float vh = GameView.view.getHeight();
        if (rightMost < vw) {
            Log.d(TAG, "create a Platform here !! @" + rightMost + " Platforms=" + objects.size());
            float tx = rightMost, ty = vh - Platform.Type.T_2x2.height();
            Platform platform = new Platform(Platform.Type.RANDOM, tx, ty);
            game.add(MainGame.Layer.platform, platform);

            Random r = new Random();
            game.add(MainGame.Layer.item, new Jelly(r.nextInt(60), tx, r.nextInt((int) ty)));
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
