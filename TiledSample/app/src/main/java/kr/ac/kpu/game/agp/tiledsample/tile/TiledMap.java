package kr.ac.kpu.game.agp.tiledsample.tile;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class TiledMap {
    private static final String TAG = TiledMap.class.getSimpleName();
    private int x;
    private int y;
    private boolean wraps;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void scrollTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int dstTileWith;
    public int getDstTileWith() {
        return dstTileWith;
    }
    private int dstTileHeight;
    public int getDstTileHeight() {
        return dstTileHeight;
    }

    public int getFullWidth() {
        return dstTileWith * width;
    }
    public int getFullHeight() {
        return dstTileHeight * height;
    }

    public static TiledMap load(Resources res, int resId, boolean wraps) {
        TiledMap map = new MapJsonReader().read(res, resId);
        map.wraps = wraps;
        return map;
    }
    public void loadImage(Resources res, int[] ints, int dstTileWidth, int dstTileHeight) {
        if (ints.length != layers.size()) {
            return;
        }
        for (int i = 0; i < ints.length; i++) {
            TiledLayer layer = layers.get(i);
            layer.loadBitmap(res, ints[i]);
//            layer.bitmap = BitmapFactory.decodeResource(res, ints[i]);
            Log.d(TAG, "Bitmap size = " + layer.bitmap.getWidth() + "," + layer.bitmap.getHeight());
        }
        this.dstTileWith = dstTileWidth;
        this.dstTileHeight = dstTileHeight;
        paint.setTextSize(40);
    }
    ArrayList<TiledTileset> tilesets;
    ArrayList<TiledLayer> layers;
    public int width, height;
    public int tilewidth, tileheight;

    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();

    private Paint paint = new Paint();

    public void draw(Canvas canvas) {
        draw(canvas, 0, 0);
    }
    public void draw(Canvas canvas, int tilesetIndex, int layerIndex) {
//        Log.d(TAG, "tileWidth=" + tilewidth);
//        if (tilewidth == 0) {
//            return;
//        }
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        TiledTileset ts = tilesets.get(tilesetIndex);
        TiledLayer layer = layers.get(layerIndex);

        int sx = x, sy = y;
        if (wraps) {
            int fullWidth = getFullWidth();
            sx %= fullWidth;
            if (sx < 0) {
                sx += fullWidth;
            }
            int fullHeight = getFullHeight();
            sy %= fullHeight;
            if (sy < 0) {
                sy += getFullHeight();
            }
        }

        int tile_x = (int) (sx / dstTileWith);
        int tile_y = (int) (sy / dstTileHeight);

        int beg_x = - (int)(sx % dstTileWith);
        int beg_y = - (int)(sy % dstTileHeight);
        dstRect.top = beg_y;
        dstRect.bottom = beg_y + dstTileHeight;
        int ty = tile_y;
        while (dstRect.top < canvasHeight) {
            if (ty >= 0) {
                dstRect.left = beg_x;
                dstRect.right = beg_x + dstTileWith;
                int tx = tile_x;
                int ti = ty * width + tx;
                while (dstRect.left < canvasWidth) {
                    if (ti >= layer.data.length) {
                        break;
                    }
                    int tile = layer.data[ti];
                    ts.getRectForTile(tile, srcRect);
//                    Log.d(TAG, "src=" + srcRect + " dst=" + dstRect + " tx=" + tx + " ty=" + ty + " ti=" + ti);
                    canvas.drawBitmap(layer.bitmap, srcRect, dstRect, null);
                    dstRect.left += dstTileWith;
                    dstRect.right += dstTileWith;
                    ti++;
                    tx = (tx + 1) % layer.width;
                }
            }
            dstRect.top += dstTileHeight;
            dstRect.bottom += dstTileHeight;
            ty = (ty + 1) % layer.height;
        }

        // for debug
        canvas.drawText("X=" + x + ", Y=" + y, 100, 100, paint);
    }

}
