package kr.ac.kpu.game.agp.tiledsample.tile;

import android.graphics.Rect;

public class TiledTileset {
    public int columns, tilecount;
    public int tilewidth, tileheight;
    public int imagewidth, imageheight;
    public int margin, spacing;

    public void getRectForTile(int tile, Rect rect) {
        int x = (tile - 1) % columns;
        int y = (tile - 1) / columns;
        rect.left = x * (tilewidth + spacing) + margin;
        rect.top = y * (tileheight + spacing) + margin;
        rect.right = rect.left + tilewidth;
        rect.bottom = rect.top + tileheight;
    }
}
