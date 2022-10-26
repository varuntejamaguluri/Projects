package main.java.com;

import javax.swing.*;

import main.java.Config.Configurations;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuScreen extends JPanel {
    JButton StartButton = new JButton("1 Player");
    JButton HSButton = new JButton("High Scores");
    JButton ThemeButton = new JButton("Change Theme");
    JButton TwoPlayerButton = new JButton("2 Players");

    public MenuScreen() {
        initMenu();
    }

    // Menu initialization
    private void initMenu() {
        setFocusable(true);
        setPreferredSize(new Dimension(Configurations.WIDTH, Configurations.HEIGHT));

        setLayout(null);
        StartButton.setBounds((Configurations.WIDTH - 210) / 2, 200, 200, 60);
        TwoPlayerButton.setBounds((Configurations.WIDTH - 210) / 2, 300, 200, 60);
        HSButton.setBounds((Configurations.WIDTH - 210) / 2, 400, 200, 60);
        ThemeButton.setBounds((Configurations.WIDTH - 210) / 2, 500, 200, 60);
        
        add(StartButton);
        add(HSButton);
        add(ThemeButton);
        add(TwoPlayerButton);
        StartHandler stHandler = new StartHandler();
        StartButton.addActionListener(stHandler);
        HSHandler hsHandler = new HSHandler();
        HSButton.addActionListener(hsHandler);
        ThemeHandler THandler = new ThemeHandler();
        ThemeButton.addActionListener(THandler);
        TwoPlayerHandler tpHandler = new TwoPlayerHandler();
        TwoPlayerButton.addActionListener(tpHandler);
    }

    // Click listener for Start Button
    private class StartHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                setLayout(new FlowLayout());
                revalidate();
                repaint();
                GameBoard gameBoard = new GameBoard("one");
                add(gameBoard);
                gameBoard.requestFocusInWindow();
                remove(HSButton);
                remove(StartButton);
                remove(ThemeButton);
                remove(TwoPlayerButton);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Click Listener for High score button
    private class HSHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                setLayout(new BorderLayout());
                revalidate();
                repaint();
                HighScoreBoard hsBoard = new HighScoreBoard();
                add(hsBoard);
                hsBoard.requestFocusInWindow();
                remove(HSButton);
                remove(StartButton);
                remove(ThemeButton);
                remove(TwoPlayerButton);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        }
    }

    // Click Listener for High score button
    private class ThemeHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                setLayout(new BorderLayout());
                revalidate();
                repaint();
                ThemeMenu TMenu = new ThemeMenu();
                add(TMenu);
                TMenu.requestFocusInWindow();
                remove(HSButton);
                remove(StartButton);
                remove(ThemeButton);
                remove(TwoPlayerButton);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    // Click Listener for Two Player mode
    private class TwoPlayerHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                setLayout(new FlowLayout());
                revalidate();
                repaint();
                GameBoard gameBoard = new GameBoard("two");
                add(gameBoard);
                gameBoard.requestFocusInWindow();
                remove(HSButton);
                remove(StartButton);
                remove(ThemeButton);
                remove(TwoPlayerButton);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Paint components
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        drawTitle(g2d);
    }
// Draw titles
    private void drawTitle(Graphics2D g2d) {
        var font = new Font("Verdana", Font.BOLD, 60);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.GRAY);
        g2d.setFont(font);
        g2d.drawString("Brick Breaker!",
                (Configurations.WIDTH - fontMetrics.stringWidth("Brick Breaker!")) / 2,
                65);
        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(font);
        g2d.drawString("Brick Breaker!",
                ((Configurations.WIDTH - fontMetrics.stringWidth("Brick Breaker!")) / 2) + 3,
                67);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString("Brick Breaker!",
                ((Configurations.WIDTH - fontMetrics.stringWidth("Brick Breaker!")) / 2) + 6,
                69);
    }

}
