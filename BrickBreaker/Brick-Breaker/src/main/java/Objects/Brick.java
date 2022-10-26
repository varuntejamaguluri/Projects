package main.java.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;

import main.java.Interfaces.IBrick;

import java.io.File;
import java.io.IOException;

public class Brick extends Sprite {

    private boolean destroyed;
    private int health;
    private boolean cement;
    private boolean dangerBrick;
    private boolean containsItem;
    private boolean removeLife;
    private boolean containsLife;
    private int createdHealthBrick = 0;
    int noOfDangerBricks = 0;
    private boolean switchArrowDirction;
    private IBrick brickMode;


    // Fifty Brick
    private final IBrick FiftyBrick = () -> {
        health += 50;
    };
// Hundred brick
    private final IBrick HundredBrick = () -> {
        health += 100;
    };
// Cement brick
    private final IBrick cementBrick = () -> {

        cement = true;
        try {
            image = loadImageResource("Brick-Breaker/src/images/cement.jpg");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        getImageDimensions();
    };

    // Item brick
    private final IBrick itemContainerBrick = () -> {
        containsItem = true;
        try {
            image = loadImageResource("Brick-Breaker/src/images/itemBrick.jpg");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    };

    // Dangerous brick
    private final IBrick dangerousBrick = () -> {
        if (noOfDangerBricks < 3) {
            noOfDangerBricks += 1;
            dangerBrick = true;
            removeLife = true;
            try {
                image = loadImageResource("Brick-Breaker/src/images/redBrick.jpg");
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            cement = true;
            try {
                image = loadImageResource("Brick-Breaker/src/images/cement.jpg");
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            getImageDimensions();
        }
    };

    // Arrow switch brick
    private final IBrick switchArrowBrick = () -> {
        try {
            image = loadImageResource("Brick-Breaker/src/images/switchDirectionBrick.jpg");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        getImageDimensions();
        switchArrowDirction = true;
    };

    // Life brick
    private final IBrick lifeBrick = () -> {
        if (createdHealthBrick < 1) {
            createdHealthBrick++;
            containsLife = true;
            try {
                image = loadImageResource("Brick-Breaker/src/images/greenBrick.jpg");
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        } else {
            try {
                image = loadImageResource("Brick-Breaker/src/images/brick1.jpg");
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        getImageDimensions();

    };

    // Default brick
    private final IBrick defaultBrick = () -> {
        try {
            image = loadImageResource("Brick-Breaker/src/images/brick1.jpg");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        getImageDimensions();
    };

    public Brick(int x, int y) throws IOException {
        noOfDangerBricks = 0;
        initBrick(x, y);
    }

    // Brick Init
    private void initBrick(int x, int y) throws IOException {
        this.x = x;
        this.y = y;

        destroyed = false;
        cement = false;
        dangerBrick = false;
        switchArrowDirction = false;
        health = 1;
        containsLife = false;

        // boolean for item drop bricks
        containsItem = false;
        removeLife = false;

        try {
            image = loadImageResource("Brick-Breaker/src/images/brick1.jpg");
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        getImageDimensions();

        int random = (int) (Math.random() * 100) + 1;

        if (random > 50 && random <= 80) {
            brickMode = FiftyBrick;

        } else if (random > 80 && random <= 95) {
            brickMode = HundredBrick;
        } else if (random > 95) {
            brickMode = cementBrick;

        } else if (random > 5 && random < 20) {

            brickMode = itemContainerBrick;
        } else if (random > 3 && random <= 5) {
            brickMode = dangerousBrick;

        } else if (random == 3 || random == 2) {
            brickMode = switchArrowBrick;

        } else if (random < 2) {

            brickMode = lifeBrick;

        } else {
            brickMode = defaultBrick;
        }

        brickMode.setupBrick();

    }

    // 50% chance health = 0 (dies in one hit)
    // 30% change health = 50 (dies in two hits)
    // 15% chance health = 100 (dies in three hits)
    // 5% chance cement (invincible)
    private int getHealth() {
        return health;
    }

    // Set brick health
    private void setHealth() {
        health -= 50;
    }

    
    public boolean isCement() {
        return cement;
    }

    public boolean isDangerBrick() {
        return dangerBrick;
    }

    // Damage brick
    public void doDamage() throws IOException {

        if (!isCement()) {
            setHealth();
            if (getHealth() <= 0) {
                destroyed = true;
            } else if (getHealth() == 1) {
                try {
                    image = loadImageResource("Brick-Breaker/src/images/brick_cracked_2_copy.jpg");
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
                getImageDimensions();
            } else if (getHealth() == 51) {
                destroyed = true;
            }
        }

    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean hasItem() {
        return containsItem;
    }

    public boolean removeLife() {
        return removeLife;
    }

    public boolean containsLife() {
        return containsLife;
    }

    public boolean isSwitchDirectionBrick() {
        return switchArrowDirction;
    }

    // Load Image
    private Image loadImageResource(String filePath) throws IOException {
        try {
            var ii = new ImageIcon(ImageIO.read(new File(filePath)));
            return ii.getImage();
        } catch (IOException e) {
            System.out.println("Exception with reading file while reading from :" + filePath);
            throw e;
        }
    }

}
