package ru.vsu.cs.course2;

import static java.lang.Math.PI;

public class WaveGenerator {
    private double[][] sqrtTable;
    private double[][] waveTable;
    private double[] expTable;
    private double c;
    private double l;
    private double speed;
    private double size;
    private int fps;
    private int maxFrames;
    private static final double EPS = 0.1;

    public WaveGenerator(double c, double l, double speed, int size, int fps) {
        this.size = size;
        this.speed = speed;
        this.fps = fps;
        this.maxFrames = Math.max(
                (int) (-Math.log(EPS) * fps / c),
                (int) (size * fps / speed));

        sqrtTable = new double[size][];
        for (int i = 0; i < size; i++) {
            sqrtTable[i] = new double[i + 1];
            for (int j = 0; j < sqrtTable[i].length; j++) {
                sqrtTable[i][j] = Math.sqrt(i * i + j * j);
            }
        }

        waveTable = new double[size + 1][maxFrames];
        for (int r = 0; r <= size; r++) {
            for (int frame = 0; frame < maxFrames; frame++) {
                waveTable[r][frame] =
                        Math.sin(2 * PI * r / l - 2 * PI * speed / l * frame / fps);
            }
        }

        expTable = new double[maxFrames + 1];
        for (int frame = 0; frame <= maxFrames; frame++) {
            expTable[frame] = -Math.exp(-c * frame / fps);
        }
    }

    double getWaveValue(double r, int frame) {
        int index = (int) r;
        double amount = r - index;
        double value1 = waveTable[index][frame];
        double value2 = waveTable[index + 1][frame];
        return value1 * (1 - amount) + value2 * amount;
    }

    double getExpValue(double frameNo) {
        int index = (int) frameNo;
        double amount = frameNo - index;
        double value1 = expTable[index];
        double value2 = expTable[index + 1];
        return value1 * (1 - amount) + value2 * amount;
    }

    public double getValueAt(int dx, int dy, int frame) {
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        if (dx >= size || dy >= size || frame >= maxFrames)
            return 0;
        double r = (dx < dy) ? sqrtTable[dy][dx] : sqrtTable[dx][dy];
        if (r > frame * speed / fps)
            return 0;
        double frameNo = frame - (r * fps / speed);
        if (frameNo >= expTable.length)
            return 0;
        return getWaveValue(r, frame) * getExpValue(frameNo);
        //return waveTable[(int) r][frame] * expTable[(int) frameNo];
    }

    public double getC() {
        return c;
    }

    public double getL() {
        return l;
    }

    public double getSpeed() {
        return speed;
    }

    public double getSize() {
        return size;
    }

    public int getFps() {
        return fps;
    }

    public int getMaxFrames() {
        return maxFrames;
    }
}
