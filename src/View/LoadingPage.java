package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoadingPage extends JPanel {
     static int numOfDots = 5;
     static  int dotDiameter = 20;
     static  int hSpace = 10;
     static  int highestPoint = 15;
     static  int waveDiff = 25;
     static  int repeating = 3;
     int[] dotY = new int[numOfDots];
     int[] diffOfDots = new int[numOfDots];
     int counter = 0;
     boolean showWelcome = false;
     float welcomeAlpha = 0.0f;
     Timer welcomeFadeTimer;
     int timeStep = 0;

    public LoadingPage() {

        setBackground(MainFrame.darkGray);
        for (int i = 0; i < numOfDots; i++) {
            diffOfDots[i] = i * waveDiff;
        }

        Timer animationTimer = new Timer(1, e -> animateDots(e));
        animationTimer.start();

        welcomeFadeTimer = new Timer(60, e -> fadeInWelcome());
    }

    private void animateDots(ActionEvent e) {
        if (showWelcome) {
            ((Timer) e.getSource()).stop();
            return;
        }
        timeStep += 4;
        for (int i = 0; i < numOfDots; i++) {
            double posOnArch = Math.toRadians((timeStep + diffOfDots[i]) % 360);
            dotY[i] = (int) (Math.sin(posOnArch) * highestPoint);
        }

        if (timeStep % 360 == 0) {
            counter++;
            if (counter >= repeating) {
                showWelcome = true;
                welcomeFadeTimer.start();
            }
        }
        repaint();
    }

     void fadeInWelcome() {
        welcomeAlpha += 0.1f;
        if (welcomeAlpha >= 1.0f) {
            welcomeAlpha = 1.0f;
            welcomeFadeTimer.stop();

            Timer hideTimer = new Timer(800, e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.remove(this);
            });
            hideTimer.setRepeats(false);
            hideTimer.start();

        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!showWelcome) {

            int totalWidth = (numOfDots * dotDiameter) + ((numOfDots - 1) * hSpace);
            int startX = (getWidth() - totalWidth) / 2;
            int centerY = getHeight() / 2;
            for (int i = 0; i < numOfDots; i++) {
                int x = startX + i * (dotDiameter + hSpace);
                int y = centerY - dotDiameter / 2 + dotY[i];
                g2d.setColor(MainFrame.orange);
                g2d.fillOval(x, y, dotDiameter, dotDiameter);
            }
        } else {
            g2d.setFont(MainFrame.fontBold);
            g2d.setColor(new Color(MainFrame.orange.getRed(),MainFrame.orange.getGreen(),MainFrame.orange.getBlue(), (int) (welcomeAlpha * 255)));
            String message = "Welcome To El-Resturanto";
            int x = (getWidth() -256 ) / 2;
            int y = (getHeight() -20) / 2;
            g2d.drawString(message, x, y);
        }
    }}