package ru.vsu.cs.course2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class PuddlePanel extends JPanel implements ActionListener {
    protected WaveGenerator waveGenerator;
    protected Puddle puddle;
    int frame = 0;
    int[] colors;

    public PuddlePanel(WaveGenerator waveGenerator) {
        this.waveGenerator = waveGenerator;
        colors = new int[256];
        for (int i = 0; i < 256; i++) {
            colors[i] = Color.getHSBColor(14 / 24.0f, 1f, i / 255.0f).getRGB(); //Blue
        }
    }

    public void setPuddle(Puddle puddle) {
        this.puddle = puddle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        IntStream.range(0, img.getHeight()).parallel().forEach(i -> {
            for (int j = 0; j < img.getWidth(); j++) {
                double value = puddle.getValueAt(j, i, frame);
                int cv = (int) (255 * (value * 0.5 + 0.5));
                img.setRGB(j, i, colors[cv]);
            }
        });
        g.drawImage(img, 0, 0, null);
    }

    public int getFrame() {
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame++;
        repaint();
    }
}
