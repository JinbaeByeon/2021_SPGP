package kr.ac.kpu.game.s2015182013.cookierun;


import android.graphics.Rect;

import java.util.ArrayList;

public class IndexedAnimationGameBitmap extends kr.ac.kpu.game.s2015182013.cookierun.framework.AnimationGameBitmap {

    private final int frameHeight;
    protected ArrayList<Rect> srcRects;

    public IndexedAnimationGameBitmap(int resId, float framesPerSecond, int frameCount) {
        super(resId, framesPerSecond, frameCount);
        frameWidth =270;
        frameHeight =270;
    }

    public void setIndices(int... indices){
        srcRects = new ArrayList<>();
        for(int index:indices){
            int x =index%100;
            int y = index/100;

            int l= 2+x*(frameWidth+2);
            int t = 2+y*(frameHeight+2);
            int r= l+frameWidth;
            int b = t+frameHeight;
            srcRects.add(new Rect(l,t,r,b));
        }
        frameCount = indices.length;
    }



}
