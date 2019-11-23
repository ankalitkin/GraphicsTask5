package ru.vsu.cs.course2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Puddle implements ActionListener {
    PuddlePanel parent;
    List<Ring> rings = new ArrayList<>();
    WaveGenerator generator;
    Random random = new Random();
    private final static double probability = 0.15;

    public Puddle(PuddlePanel parent, WaveGenerator generator) {
        this.parent = parent;
        this.generator = generator;
    }

    public void addRandomRing(int frame) {
        if(parent.getWidth() == 0 || parent.getWidth() == 0)
            return;
        int x = random.nextInt(parent.getWidth());
        int y = random.nextInt(parent.getHeight());
        rings.add(new Ring(x, y, frame, generator, this));
    }

    public double getValueAt(int x, int y, int frame) {
        double value = 0;
        for (Ring ring : rings) {
            value += ring.getValueAt(x, y, frame);
        }
        return Math.max(0, Math.min(1, value));
    }

    public void optimize() {
        rings.removeIf(ring -> ring.toBeDestroyed(parent.getFrame()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        optimize();
        if (random.nextDouble() < probability) {
            addRandomRing(parent.getFrame());
        }
    }
}
