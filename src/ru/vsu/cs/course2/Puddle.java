package ru.vsu.cs.course2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Puddle implements ActionListener, MouseListener {
    private static double probability = 0.15;
    private PuddlePanel parent;
    private List<Ring> rings = new ArrayList<>();
    private WaveGenerator generator;
    private Random random = new Random();

    public Puddle(PuddlePanel parent, WaveGenerator generator) {
        this.parent = parent;
        this.generator = generator;
    }

    public void addRandomRing(int frame) {
        if (parent.getWidth() == 0 || parent.getWidth() == 0)
            return;
        int x = random.nextInt(parent.getImageWidth());
        int y = random.nextInt(parent.getImageHeight());
        rings.add(new Ring(x, y, frame, generator, this));
    }

    public double getValueAt(int x, int y, int frame) {
        double value = 0;
        for (Ring ring : rings) {
            value += ring.getValueAt(x, y, frame);
        }
        if (value < -1)
            return -1;
        if (value > 1)
            return 1;
        return value;
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            probability = -probability;
            return;
        }
        int x = (int) (e.getX() / parent.getScale());
        int y = (int) (e.getY() / parent.getScale());
        rings.add(new Ring(x, y, parent.getFrame(), generator, this));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
