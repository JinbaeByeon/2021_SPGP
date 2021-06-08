package kr.ac.kpu.game.agp.smoothingpath;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PathView extends View {
    private static final String TAG = Path.class.getSimpleName();
    private static final int DIRECTION_FACTOR = 6;

    public interface Listener {
        public void onAdd();
    }
    public PathView(Context context) {
        super(context);
        init();
    }
    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public int getCount() {
        return points.size();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setColor(Color.BLUE);

        alphaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        alphaPaint.setAlpha(60 * 255 / 100); // 60% opacity

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plane);
        hw = bitmap.getWidth() / 2;
        hh = bitmap.getHeight() / 2;
    }

    class Point {
        float x, y;
        float dx, dy;
    }
    Listener listener;
    ArrayList<Point> points = new ArrayList<>();
    Paint paint;
    Path path;
    Bitmap bitmap;
    int hw, hh;
    PointF fighterPos = new PointF();
    Paint alphaPaint;
    float angle;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getPointerCount() > 1) {
                points.clear();
                return false;
            }
            Point pt = new Point();
            pt.x = event.getX();
            pt.y = event.getY();
            points.add(pt);
            buildPath();
            if (points.size() == 1) {
                fighterPos.set(pt.x, pt.y);
            }
            Log.d(TAG, "Points:" + points.size());
            if (listener != null) {
                listener.onAdd();
            }

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void buildPath() {
        int ptCount = points.size();
        if (ptCount < 2) { return; }

        for (int i = ptCount - 2; i < ptCount; i++) {
            Point pt = points.get(i);
            if (i == 0) { // only next
                Point next = points.get(i + 1);
                pt.dx = ((next.x - pt.x) / DIRECTION_FACTOR);
                pt.dy = ((next.y - pt.y) / DIRECTION_FACTOR);
            } else if (i == ptCount - 1) { // only prev
                Point prev = points.get(i - 1);
                pt.dx = ((pt.x - prev.x) / DIRECTION_FACTOR);
                pt.dy = ((pt.y - prev.y) / DIRECTION_FACTOR);
            } else { // prev and next
                Point next = points.get(i + 1);
                Point prev = points.get(i - 1);
                pt.dx = ((next.x - prev.x) / DIRECTION_FACTOR);
                pt.dy = ((next.y - prev.y) / DIRECTION_FACTOR);
            }
        }

        path = new Path();
        Point prev = points.get(0);
        path.moveTo(prev.x, prev.y);
        for (int i = 1; i < ptCount; i++) {
            Point pt = points.get(i);
            path.cubicTo(
                    prev.x + prev.dx, prev.y + prev.dy,
                    pt.x - pt.dx, pt.y - pt.dy,
                    pt.x, pt.y);
            prev = pt;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int ptCount = points.size();
        if (ptCount == 0) { return; }
        Point first = points.get(0);
        if (ptCount == 1) {
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
        } else {
            canvas.drawPath(path, paint);
        }
        canvas.save();
        canvas.rotate(angle, fighterPos.x, fighterPos.y);
        canvas.drawBitmap(bitmap, fighterPos.x - hw, fighterPos.y - hh, alphaPaint);
        canvas.restore();
    }

    public void start(int msecPerPoint) {
        int ptCount = points.size();
        if (ptCount < 2) { return; }
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(ptCount * msecPerPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float[] pos = new float[2];
            float[] tan = new float[2];
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = animation.getAnimatedFraction();
                pm.getPosTan(length * progress, pos, tan);
                fighterPos.x = pos[0];
                fighterPos.y = pos[1];
                angle = (float)(Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
                //Log.d(TAG, "pos:" + fighterPos);
                invalidate();
            }
        });
        anim.start();
    }
    public void clear() {
        points.clear();
        invalidate();
    }
}
