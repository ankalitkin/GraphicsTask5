package ru.vsu.cs.course2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class PuddlePanel extends JPanel implements ActionListener {
    private Puddle puddle;
    private int frame = 0;
    private int[] colors;
    private double scale = 2.5;
    private BufferedImage img;

    public PuddlePanel() {
        colors = new int[256];
        for (int i = 0; i < 256; i++) {
            colors[i] = Color.getHSBColor(14 / 24.0f, 1f, i / 255.0f).getRGB(); //Blue
            //colors[i] = (i << 16) + (i << 8) + i;
        }
    }

    public void setPuddle(Puddle puddle) {
        this.puddle = puddle;
        addMouseListener(puddle);
    }

    @Override
    protected void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        img = new BufferedImage(getImageWidth(), getImageHeight(), BufferedImage.TYPE_INT_RGB);
        IntStream.range(0, img.getHeight()).parallel().forEach(i -> {
            for (int j = 0; j < img.getWidth(); j++) {
                double value = puddle.getValueAt(j, i, frame);
                int cv = (int) (255 * (value * 0.5 + 0.5));
                img.setRGB(j, i, colors[cv]);
            }
        });
        g.scale(scale, scale);
        g.drawImage(img, 0, 0, null);
    }

    public int getFrame() {
        return frame;
    }

    public int getImageWidth() {
        return (int) (getWidth() / scale);
    }

    public int getImageHeight() {
        return (int) (getHeight() / scale);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame++;
        repaint();
    }

    public double getScale() {
        return scale;
    }
}
