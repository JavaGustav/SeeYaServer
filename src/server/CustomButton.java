package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author zeddan
 *
 */
public class CustomButton extends JButton implements MouseListener {

    public CustomButton(String text) {
        super(text);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0),
                Color.WHITE,
                new Point(0, 0),
                Color.WHITE));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(new Color(56, 56, 56));
        g2.setStroke(new BasicStroke(7));
        g2.drawRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }

    public JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane;
        if (parent instanceof JOptionPane)
            pane = (JOptionPane) parent;
        else
            pane = getOptionPane((JComponent) parent.getParent());
        return pane;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
