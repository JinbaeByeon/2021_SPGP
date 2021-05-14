package kr.ac.kpu.game.s2015182013.cookierun.game;

import kr.ac.kpu.game.s2015182013.cookierun.R;
import kr.ac.kpu.game.s2015182013.cookierun.framework.ImageObject;
import kr.ac.kpu.game.s2015182013.cookierun.ui.view.GameView;

public class Platform  extends ImageObject {
    public enum Type{
        T_10x2,T_2x2,T_3x1
    }
    private static final int  resId[]={
            R.mipmap.cookierun_platform_480x48,
            R.mipmap.cookierun_platform_124x120,
            R.mipmap.cookierun_platform_120x40
    };
    public Platform(Type type,float x,float y) {
        super(resId[type.ordinal()], x, y);
        final float UNIT = 40* GameView.MULTIPLIER;
        float[] w ={10*UNIT,2*UNIT,3*UNIT};
        float[] h ={2*UNIT,2*UNIT,1*UNIT};
        dstRect.set(x,y,x+w[type.ordinal()],y+h[type.ordinal()]);
    }
}
