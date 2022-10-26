package main.java.com;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


import main.java.Config.Configurations;
import main.java.Objects.*;
import main.java.Observer.LivesObserver;
import main.java.Observer.ScoreObserver;
import main.java.Observer.SpeedObserver;
import main.java.Observer.SubjectLives;
import main.java.Observer.SubjectScore;
import main.java.Observer.SubjectSpeed;
import main.java.Interfaces.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

// Constructor
public class GameBoard extends JPanel {

    interface IGameModeStrategy {
        void gameInit() throws IOException;
        
    }

    
    AudioChanger ac = new AudioChanger();
    private static Timer timer;
    private String message = "Game Over!";
    private String yourScore = "Your Score: ";
    private Ball ball;
    public IRacket racket1;
    public IImageDisplay gameOverImage;
    public IImageDisplay victoryImage;
    public IImageDisplay I ;
    public IRacket racket2;
       static AudioInputStream as = null;
   static File f = null;
   static Clip c = null;


    public Brick[] bricks;
    private Item drop;
    private boolean itemDrop;
    public int racketType;
    private boolean inGame = true;
     static boolean musicIsPlaying = false;
    private int arrowDir = 0;
    double speed = 2;
    JButton pauseButton = new JButton("Pause");
    JButton resumeButton = new JButton("Resume");
    JButton restartButton = new JButton("Restart");
    boolean restartClicked = false;
    JButton arrowButton = new JButton("Arrow");
    JButton aswdButton = new JButton("ASWD");
    JButton toggleMusic = new JButton("Music");
    private IGameModeStrategy currentMode;
    int keySelect1 = 0;
    int keySelect2 = 1;
    int jCount = 0;
    int breakableBricksCount = 0;

    private SubjectSpeed speedLevel = new SubjectSpeed("x1");
    private SubjectLives livesLeft = new SubjectLives(3);
    private SubjectScore score = new SubjectScore(0);

    private LivesObserver livesObserver = new LivesObserver(livesLeft);
    private ScoreObserver scoreObserver = new ScoreObserver(score);
    private SpeedObserver speedObserver = new SpeedObserver(speedLevel);


    // Game Strategy Mode
    private final IGameModeStrategy onePlayerMode = () -> {

        initializeDisplayInfo();
        toggleMusic.setText("Turn Off Music");
        bricks = new Brick[Configurations.N_OF_BRICKS];
      

        ball = new Ball();
        racket1 = new Racket(racketType, keySelect1);
       

        livesLeft.setState(3);

        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 85 + 30, i * 25 + 50);
                k++;
            }
        }

        timer = new Timer(Configurations.PERIOD, new GameCycle());
        timer.start();
    };

    //Game Strategy Mode

    private final IGameModeStrategy twoPlayerMode = () -> {

        initializeDisplayInfo();

        bricks = new Brick[Configurations.N_OF_BRICKS];

        ball = new Ball();
        racket1 = new Racket(racketType, keySelect1);
        racket2 = new Racket(racketType, keySelect2);
        gameOverImage = new GameOverImage(I) ;
       
        livesLeft.setState(3);

        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 85 + 30, i * 25 + 50);
                k++;
            }
        }

        timer = new Timer(Configurations.PERIOD, new GameCycle());
        timer.start();

        aswdButton.setText("Switch");
        arrowButton.setText("Switch");
    };

    // Constructor

    public GameBoard(String gameMode) throws IOException {
        
        if (gameMode.equalsIgnoreCase("two")) {
            currentMode = twoPlayerMode;
        } else {
            currentMode = onePlayerMode;
        }
        initBoard(currentMode);
    }

    // GameBoard Initialization
    private void initBoard(IGameModeStrategy gameMode) throws IOException {

        PauseHandler settingHandler = new PauseHandler();
        ResumeHandler resumeHandler = new ResumeHandler();
        RestartHandler restartHandler = new RestartHandler();
        ArrowKeyHandler arrowKeyHandler = new ArrowKeyHandler();
        ASWDKeyHandler aswdKeyHandler = new ASWDKeyHandler();
        ToggleMusicHandler toggleMusicHandler = new ToggleMusicHandler();
        arrowDir = 0;

        // Read from BackGroundColor.txt to get background color
        FileReader fr = new FileReader("BackGroundColor.txt");
        BufferedReader br = new BufferedReader(fr);
        String color = br.readLine();

        // Read Color object String and convert to Color object
        final Scanner scan = new Scanner(color);
        scan.useDelimiter("(r|\\,g|\\,b)=|\\]").next(); // Use proper delimiter and ignore first part (which is the
                                                        // class name)
        final int r, g, b;
        // Verify RGB Values
        System.out.println(r = scan.nextInt());
        System.out.println(g = scan.nextInt());
        System.out.println(b = scan.nextInt());

        Color c = new Color(r, g, b);
        setBackground(c);

        System.out.println(color);
        fr.close();
        br.close();

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(0, 4));
        buttonPane.setPreferredSize(new Dimension(500, 30));
        JPanel blank = new JPanel();
        blank.setVisible(false);
        buttonPane.add(pauseButton);
        buttonPane.add(restartButton);
        buttonPane.add(aswdButton);
        buttonPane.add(toggleMusic);

        add(buttonPane);

        pauseButton.addActionListener(settingHandler);
        resumeButton.addActionListener(resumeHandler);
        restartButton.addActionListener(restartHandler);
        aswdButton.addActionListener(aswdKeyHandler);
        arrowButton.addActionListener(arrowKeyHandler);
        toggleMusic.addActionListener(toggleMusicHandler);

        pauseButton.setFocusable(false);
        restartButton.setFocusable(false);
        resumeButton.setFocusable(false);
        arrowButton.setFocusable(false);
        aswdButton.setFocusable(false);
        toggleMusic.setFocusable(false);

        addKeyListener(new TAdapter());

        setFocusable(true);
        setPreferredSize(new Dimension(Configurations.WIDTH, Configurations.HEIGHT));
        currentMode.gameInit();
    }

    // Paint components
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;
        Image im = new ImageIcon(getClass().getResource("/images/lava.gif")).getImage();
        g.drawImage(im, 0, 600, null);
        g.drawImage(im, 100, 600, null);
        g.drawImage(im, 250, 600, null);

        g.drawImage(im, 0, 580, null);
        g.drawImage(im, 100, 580, null);
        g.drawImage(im, 250, 580, null);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (inGame) {

            try {

                drawObjects(g2d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                gameFinished(g2d);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    // Draw the UI objects
    private void drawObjects(Graphics2D g2d) throws IOException {
        var font = new Font("Verdana", Font.BOLD, 15);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.black);
        g2d.setFont(font);
        // Draw current score at bottom of panel

        g2d.drawString("Score: " + scoreObserver.getScore(), 230, 620);
        g2d.drawString("Lives: " + livesObserver.getLives(), 350, 620);

        if (restartClicked) {
            racketType = 0;
            speed = 2;

            speedLevel.setState("x1");

            itemDrop = false;
            restartClicked = false;
            racket1 = new Racket(racketType, keySelect1);
          
            if (currentMode == twoPlayerMode) {
                racket2 = new Racket(racketType, keySelect2);
            }
        }

        /** Change this part to get the speedLevel from observer */
        g2d.drawString("Speed: " + speedObserver.getSpeed(), 120, 620);

        g2d.drawImage(ball.getImage(), (int) ball.getX(), (int) ball.getY(),
                ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(racket1.getImage(), (int) racket1.getX(), (int) racket1.getY(),
                racket1.getImageWidth(), racket1.getImageHeight(), this);

        if (currentMode == twoPlayerMode) {
            g2d.drawImage(racket2.getImage(), (int) racket2.getX(), (int) racket2.getY(),
                    racket2.getImageWidth(), racket2.getImageHeight(), this);
        }

        if (itemDrop) {
            g2d.drawImage(drop.getImage(), (int) drop.getX(), (int) drop.getY(),
                    drop.getImageWidth(), drop.getImageHeight(), this);
        }

        for (int i = 0; i < Configurations.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) {

                g2d.drawImage(bricks[i].getImage(), (int) bricks[i].getX() - 8,
                        (int) bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
    }

    // Game Finish method
    private void gameFinished(Graphics2D g2d)
            throws Exception {

        var font = new Font("Verdana", Font.BOLD, 30);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        if (message == "Game Over!") {
            gameOverImage = new GameOverImage(I) ;
            g2d.drawImage(gameOverImage.getImage(), (Configurations.WIDTH - fontMetrics.stringWidth(message)) / 2 - 25,
                    130, null);
                    ac.changeStrategy(new StopMusic());
                    ac.setAudioOutput();
                    ac.changeStrategy(new DogMusic());
                    ac.setAudioOutput();
                    toggleMusic.setText("On Game Music");
                    
        }

        else {
            System.out.println("Loading Victory Image...");
            victoryImage = new VictoryImage(I) ;
            g2d.drawImage(victoryImage.getImage(), (Configurations.WIDTH - fontMetrics.stringWidth(message)) / 2 - 40,
                    130, null);
                    ac.changeStrategy(new StopMusic());
                    ac.setAudioOutput();
                    ac.changeStrategy(new VictoryMusic());
                    ac.setAudioOutput();
                    toggleMusic.setText("On Game Music");
        }

        g2d.setColor(Color.red);

        setLayout(new BorderLayout());

        g2d.setFont(font);
        g2d.drawString(message,
                (Configurations.WIDTH - fontMetrics.stringWidth(message)) / 2,
                100);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        yourScore = "Your Score: ";
        yourScore += scoreObserver.getScore();
        g2d.drawString(yourScore, (Configurations.WIDTH - fontMetrics.stringWidth(yourScore)) / 2, 455);

        FileWriter out = new FileWriter("ScoreList.txt", true);
        BufferedWriter bw = new BufferedWriter(out);

        int temp = scoreObserver.getScore();
        if (temp != 0) {
            bw.write(Integer.toString(temp));
            bw.newLine();
        }

        bw.close();
        out.close();

        timer.stop();

        
    }

    // Adapter class

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            racket1.keyReleased(e, keySelect1);
            if (currentMode == twoPlayerMode) {
                racket2.keyReleased(e, keySelect2);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            racket1.keyPressed(e, keySelect1);
            if (currentMode == twoPlayerMode) {
                racket2.keyPressed(e, keySelect2);
            }
        }
    }

    // Game Cycle method
    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                doGameCycle();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // Perform Game Cycle
    private void doGameCycle() throws IOException {

        ball.move();
        racket1.move();
        if (currentMode == twoPlayerMode) {
            racket2.move();
        }
        if (itemDrop) {
            drop.move();
            if (drop.getY() > Configurations.INIT_PADDLE_Y) {
                itemDrop = false;
            }
        }
        checkCollision();
        repaint();
    }
    
// Stop the game
    private void stopGame() throws IOException {

        int tempLives = livesObserver.getLives();
        livesLeft.setState(tempLives - 1);
        itemDrop = false;
        racketType = 0;

        if (livesObserver.getLives() == 0) {
            inGame = false;
            timer.stop();
        }

        ball = new Ball();
        arrowDir = 0;
        racket1 = new Racket(racketType, keySelect1);
        if (currentMode == twoPlayerMode) {
            racket2 = new Racket(racketType, keySelect2);
        }

        timer.stop();
        timer = new Timer(Configurations.PERIOD, new GameCycle());
        timer.start();

    }

    // pause game once click on pause button
    private class PauseHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            pauseGame();
        }
    }

    // method to pause game
    private void pauseGame() {
        Container parent = pauseButton.getParent();
        try {
          //  GameBoard.stopMusic();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        parent.add(resumeButton, 0, 0);
        parent.remove(pauseButton);
        parent.revalidate();
        parent.repaint();
        timer.stop();
    }
// Game resume handler
    private class ResumeHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            resumeGame();
        }
    }

    // Resume method
    private void resumeGame() {
        Container parent = resumeButton.getParent();
        try {
           // dataset.doSort();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        parent.add(pauseButton, 0, 0);
        parent.remove(resumeButton);
        parent.revalidate();
        parent.repaint();
        timer.stop();
        timer = new Timer(Configurations.PERIOD, new GameCycle());
        timer.start();
    }

    // Game restart handler
    private class RestartHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ac.changeStrategy(new StopMusic());
                ac.setAudioOutput();
                speed = 2;
                speedLevel.setState("x1");
                restartClicked = true;
                inGame = true;
                timer.stop();
                currentMode.gameInit();
                
                ac.changeStrategy(new GameMusic());
                message = "Game Over!";
                try {
                    ac.setAudioOutput();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } catch (IOException er) {
                er.printStackTrace();
            } catch (Exception e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

        }
    }

// Arrow keys handler
    private class ArrowKeyHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            selectArrowKey();
            --keySelect1;
            if (currentMode == twoPlayerMode) {
                ++keySelect2;
            }
        }
    }
// Selected arrow keys
    private void selectArrowKey() {
        Container key = arrowButton.getParent();
        key.add(aswdButton, 3);
        key.remove(arrowButton);
        key.revalidate();
        key.repaint();
    }

    // Music Toggle handler
    private class ToggleMusicHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(musicIsPlaying){
                try {
                    toggleMusic.setText("Turn On Music");
                    ac.changeStrategy(new StopMusic());
                    ac.setAudioOutput();
                } catch (Exception io_E) {
                    // TODO Auto-generated catch block
                    io_E.printStackTrace();
                }
            }
            else{
                try {
                    toggleMusic.setText("Turn Off Music");
                    ac.changeStrategy(new StopMusic());
                    ac.setAudioOutput();
                    ac.changeStrategy(new GameMusic());
                    ac.setAudioOutput();
                } catch (Exception io_E) {
                    // TODO Auto-generated catch block
                    io_E.printStackTrace();
                }
            }
            
        }
        
    }
//ASWD keys handler
    private class ASWDKeyHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            selectASWDKey();
            ++keySelect1;
            if (currentMode == twoPlayerMode) {
                --keySelect2;
            }
        }
    }

    // Selected ASWD keys
    private void selectASWDKey() {
        Container key = aswdButton.getParent();
        key.add(arrowButton, 0, 3);
        key.remove(aswdButton);
        key.getComponentAt(250, 30);
        key.revalidate();
        key.repaint();
    }

    // Method to check collision
    public void checkCollision() throws IOException {

        if (ball.getRect().getMaxY() > Configurations.BOTTOM_EDGE) {
            stopGame();
        }

        int tempScore = scoreObserver.getScore();

        // Speeds up the ball every time
        // 5 bricks are destroyed until the 15th destroyed brick
        if (tempScore >= 5 && tempScore < 10) {
            speed = 2.2;
            speedLevel.setState("x1.2");

        } else if (tempScore >= 10 && tempScore < 15) {
            speed = 2.5;
            speedLevel.setState("x1.5");

        } else if (tempScore >= 15) {
            speed = 2.8;
            speedLevel.setState("x2");
        }

        for (int i = 0, j = 0; i < Configurations.N_OF_BRICKS; i++) {
            int n_of_cement = numCementBricks(bricks);
            int breakableBricks = Configurations.N_OF_BRICKS - n_of_cement;
            if (bricks[i].isDestroyed()) {
                j++;
            }

            // added score keeper
            score.setState(j);

            if (j == breakableBricks) {
                jCount = j;
                breakableBricksCount = breakableBricks;
                message = "Victory";
                stopGame();
            }

            else {
                message = "Game Over!";

            }
        }

        checkIntersects(racket1);
        if (currentMode == twoPlayerMode) {
            checkIntersects(racket2);
        }

        // check if the user caught a dropped item
        checkCaught(racket1, keySelect1, "1");
        if (currentMode == twoPlayerMode) {
            checkCaught(racket2, keySelect2, "2");
        }

        for (int i = 0; i < Configurations.N_OF_BRICKS; i++) {

            if ((ball.getRect()).intersects(bricks[i].getRect())) {
                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {

                    if (bricks[i].getRect().contains(pointRight)) {

                        ball.setXDir(-speed);
                    } else if (bricks[i].getRect().contains(pointLeft)) {

                        ball.setXDir(speed);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {

                        ball.setYDir(speed);
                    } else if (bricks[i].getRect().contains(pointBottom)) {

                        ball.setYDir(-speed);
                    }

                    // if it should drop an item
                    if (bricks[i].hasItem()) {
                        itemDrop = true;
                        drop = new Item(bricks[i].x, bricks[i].y);
                    }

                    if (bricks[i].containsLife()) {
                        int tempLife = livesObserver.getLives();
                        livesLeft.setState(tempLife + 1);
                    }

                    if (bricks[i].removeLife()) {
                        if (livesObserver.getLives() == 1) {
                            inGame = false;
                            timer.stop();
                        } else {
                            int tempLife = livesObserver.getLives();
                            livesLeft.setState(tempLife - 1);
                            ;
                        }
                    }

                    if (bricks[i].isSwitchDirectionBrick()) {
                        if (arrowDir == 0) {
                            arrowDir = 1;
                        } else if (arrowDir == 1) {
                            arrowDir = 0;
                        }
                        racket1.setDirectionState(arrowDir);
                        if (currentMode == twoPlayerMode) {
                            racket2.setDirectionState(arrowDir);
                        }
                    }

                    bricks[i].doDamage();
                }
            }
        }
    }


    // Method to check the racket caught the ball
    private void checkCaught(IRacket racket, int keySelect, String player) throws IOException {
        if (itemDrop && (drop.getRect()).intersects(racket.getRect())) {
            int random = (int) (Math.random() * 100) + 1;

            // case 1: lengthen paddle
            if (random < 50) {
                racketType = 1;
            }
            // case 2: shorten paddle
            else {
                racketType = 2;
            }

            // have new racket appear under ball
            double temp = ball.getX();
            if (player.equals("1")) {
                if (racketType == 1) {
                    racket1 = new LargeRacket(new Racket(racketType, keySelect));
                    racket1.setX(temp);
                } else if (racketType == 2) {
                    racket1 = new SmallRacket(new Racket(racketType, keySelect));
                    racket1.setX(temp);
                }
                 
                
                // racket1 = new Racket(racketType, keySelect);
                // racket1.setX(temp);
            } else if (player.equals("2")) {
                if (racketType == 1) {
                    racket1 = new LargeRacket(new Racket(racketType, keySelect));
                    racket1.setX(temp);
                } else if (racketType == 2) {
                    racket1 = new SmallRacket(new Racket(racketType, keySelect));
                    racket1.setX(temp);
                }
                // racket2 = new Racket(racketType, keySelect);
                // racket2.setX(temp);
            }

            // reset itemDrop condition
            itemDrop = false;

        }
    }
// Check Intersection method
    private void checkIntersects(IRacket racket) {
        if ((ball.getRect()).intersects(racket.getRect())) {

            int paddleLPos = (int) racket.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {
                ball.setXDir(-speed);
                ball.setYDir(-speed);
            }

            if (ballLPos >= first && ballLPos < second) {
                ball.setXDir(-speed);
                ball.setYDir(-speed * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {
                ball.setXDir(0);
                ball.setYDir(-speed);
            }

            if (ballLPos >= third && ballLPos < fourth) {
                ball.setXDir(speed);
                ball.setYDir(-speed * ball.getYDir());
            }

            if (ballLPos > fourth) {
                ball.setXDir(speed);
                ball.setYDir(-speed);
            }
        }
    }


    // Number of cement bricks
    public int numCementBricks(Brick[] bricks) {
        int numCement = 0;

        for (int i = 0; i < Configurations.N_OF_BRICKS; i++) {
            if (bricks[i].isCement()) {
                numCement += 1;
            }

        }
        return numCement;
    }

    // Function to set the observer pattern
    private void initializeDisplayInfo() {

        speedLevel.attach(speedObserver);
        livesLeft.attach(livesObserver);
        score.attach(scoreObserver);

    }

    
}
