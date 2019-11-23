package ru.vsu.cs.course2;

import javax.swing.*;
import java.awt.*;

public class Main {
    private PuddlePanel puddlePanel;
    private WaveGenerator waveGenerator;
    private Timer updateTimer;
    private Timer rainTimer;
    private Puddle puddle;
    private static JFrame frame;
    private int rps = 5;
    private int fps = 30;

    private Main() {
        waveGenerator = new WaveGenerator(15, 15, 150, 1000, fps);
        puddlePanel = new PuddlePanel();
        puddle = new Puddle(puddlePanel, waveGenerator);
        puddlePanel.setPuddle(puddle);
        frame = new JFrame("Just rain");
        frame.add(puddlePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setVisible(true);
        updateTimer = new Timer(1000/fps, puddlePanel);
        updateTimer.start();
        rainTimer = new Timer(1000/rps, puddle);
        rainTimer.start();
    }

    public static void main(String[] args) {
        new Main();
    }
}
