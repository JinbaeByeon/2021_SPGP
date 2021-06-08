package kr.ac.kpu.game.agp.tiledsample.tile;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class TiledLayer {
    public int x, y, width, height;
    public int[] data;
    public Bitmap bitmap;

    public void draw(Canvas canvas) {

    }

    public void loadBitmap(Resources res, int resId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        bitmap = BitmapFactory.decodeResource(res, resId, opts);
    }
}
