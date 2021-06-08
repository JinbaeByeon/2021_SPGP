package kr.ac.kpu.game.agp.tiledsample;

public class IndexTimer {
    public static long currentTimeNanos;
    private final int count;
    private final int fps;
    private long time;

    public IndexTimer(int count, int framesPerSecond) {
        this.count = count;
        this.fps = framesPerSecond;
        this.time = currentTimeNanos;
    }

    public int getIndex() {
        int index = getRawIndex();
        return index % count;
    }
    public boolean done() {
        int index = getRawIndex();
        return index >= count;
    }

    public int getRawIndex() {
        long elapsed = currentTimeNanos - this.time;
        return (int) (((elapsed * fps + 500) / 1000000000));
    }

    public void reset() {
        this.time = currentTimeNanos;
    }
}
