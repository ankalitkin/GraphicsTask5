package ru.vsu.cs.course2;

import javax.swing.*;
import java.awt.*;

public class MainForm {
    private JPanel rootPanel;
    private PuddlePanel puddlePanel;
    private WaveGenerator waveGenerator;
    private Timer updateTimer;
    private Timer rainTimer;
    private Puddle puddle;
    private int fps = 60;
    private int rps = 5;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Just rain");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512,512);
        frame.setVisible(true);
    }

    private MainForm() {
        waveGenerator = new WaveGenerator(15, 20, 200, 1000, fps);
        puddlePanel = new PuddlePanel(waveGenerator);
        puddle = new Puddle(puddlePanel, waveGenerator);
        puddlePanel.setPuddle(puddle);

        rootPanel.setLayout(new GridLayout());
        rootPanel.add(puddlePanel);
        updateTimer = new Timer(1000/fps, puddlePanel);
        updateTimer.start();
        rainTimer = new Timer(1000/rps, puddle);
        rainTimer.start();
    }
}
