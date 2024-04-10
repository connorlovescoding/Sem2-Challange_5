/*
 *  
 * Used code from Basic ScreenSaver template
 * 
 * 
 * @author Connor Benson, Billy Leenane
 * @version 10/04/2024
 * 
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ScreenSaver extends JFrame {
    private final int SHAPE_SIZE = 100;

    private int xPac = 0;
    private int yPac = 200;
    private int pacMouthSize = 90;
    private final int X_PAC_DELTA = 40;
    private final int PAC_SIZE = 120;
    private final Random rng = new Random();
    private Color shapeColor;
    private Font font = new Font("Arial", Font.PLAIN, 15);

    public ScreenSaver() {
        setTitle("Screen Saver");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawShape(g);
            }
        };

        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
        panel.setBackground(Color.BLACK);
        add(panel);

        setVisible(true);

        animate();
    }

    private void drawShape(Graphics g) {
        // Draw the ball
        g.setColor(shapeColor);
        g.fillOval(xPac, yPac, SHAPE_SIZE, SHAPE_SIZE);

        // Get current date and time
        Date currentDate = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MM yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        String dateText = sdfDate.format(currentDate);
        String timeText = sdfTime.format(currentDate);

        // Draw the date in the middle of the ball
        FontMetrics fm = g.getFontMetrics(font);
        int dateTextWidth = fm.stringWidth(dateText);
        int dateTextHeight = fm.getHeight();
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(dateText, xPac + (SHAPE_SIZE - dateTextWidth) / 2, yPac + (SHAPE_SIZE + dateTextHeight) / 2);

        // Draw the time under the date
        int timeTextWidth = fm.stringWidth(timeText);
        int timeTextHeight = fm.getHeight();
        g.drawString(timeText, xPac + (SHAPE_SIZE - timeTextWidth) / 2, yPac + (SHAPE_SIZE + dateTextHeight + timeTextHeight) / 2 + 20);
    }

    private void animate() {
        while (true) {
            moveShape();
            repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveShape() {
        // Update ball position
        xPac += X_PAC_DELTA;
        if (xPac > getWidth()) {
            xPac = -SHAPE_SIZE;
            yPac = rng.nextInt(getHeight() - SHAPE_SIZE);

            // Change color when the ball goes off the page
            shapeColor = getRandomColor();
        }
    }

    private Color getRandomColor() {
        return new Color(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
    }

    public static void main(String[] args) {
        new ScreenSaver();
    }
}
