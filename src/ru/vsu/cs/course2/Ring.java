package ru.vsu.cs.course2;

public class Ring {
    int x0, y0, startFrame;
    WaveGenerator generator;
    Puddle parent;

    public Ring(int x0, int y0, int startFrame, WaveGenerator generator, Puddle parent) {
        this.x0 = x0;
        this.y0 = y0;
        this.startFrame = startFrame;
        this.generator = generator;
        this.parent = parent;
    }

    private double getRadius(int frame) {
        return (frame - startFrame) * generator.getSpeed() / generator.getFps();
    }

    public double getValueAt(int x, int y, int frame) {
        if (frame < startFrame || frame > startFrame + generator.getMaxFrames())
            return 0;
        return generator.getValueAt(x - x0, y - y0, frame - startFrame);
    }

    public boolean toBeDestroyed(int frame) {
        return getRadius(frame) > generator.getSize() * 2;
    }
}
