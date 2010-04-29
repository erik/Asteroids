/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author judithprice
 */
public class Stage extends JPanel implements ActionListener {

    public static double fps = System.currentTimeMillis(), newFPS = System.currentTimeMillis();
    public static int lives;
    public static Asteroid[] roids;
    public static boolean debug;
    public static Image background, explosion[];
    public static Point origin;
    public static boolean gameOn;
    public static boolean keyUp = false, keyDown = false, keyLeft = false,
            keyRight = false, keySpace = false, keyShift = false, keyStart = false, replay = false;
    public static Ship craft;
    public static boolean gameOver, exploding;
    public static boolean invin;
    private final int moveAmt = 1, radians = 3;
    public static int width, height, score, asteroidCount;
    public static double explosionStage;
    public static Clock time;

    public Stage(int w, int x, String backImg) throws Exception {
        this.setBackground(Color.BLACK);
        this.setBackground(this.loadImage(backImg));
        super.setSize(w, x);

        origin = new Point(this.getWidth() / 2, this.getHeight() / 2);
        craft = new Ship(origin.x, origin.y, this.loadImage("ship.png"));

        gameOn = true;
        gameOver = false;
        debug = false;

        width = this.getWidth();
        height = this.getHeight();

        Asteroid.large = this.loadImage("asteroidLarge.png");
        Asteroid.medium = this.loadImage("asteroidMedium.png");
        Asteroid.small = this.loadImage("asteroidSmall.png");
        Asteroid.driftSpeed = 5;

        asteroidCount = 5;

        //roids = new Asteroid[asteroidCount];
        //AdditionalMethods.instantiateAsteroids();

        lives = 3;
        score = 0;

        explosion = new Image[12];
        this.loadExplosions();

        AdditionalMethods.loadSounds();

        Timer clock = new Timer(25, this);
        clock.start();

        time = new Clock();

        //AdditionalMethods.spawn.play();
    }

    public void actionPerformed(ActionEvent e) {
        fps = 1000 / (System.currentTimeMillis() - newFPS);
        //if (!gameOver) {
        if (roids == null || roids.length == 0) {
            try {
                AdditionalMethods.spawn.play();
                asteroidCount += 3;
                roids = new Asteroid[asteroidCount];
                AdditionalMethods.instantiateAsteroids();
            } catch (Exception ex) {
            }
        }
        repaint();
        //}
        newFPS = System.currentTimeMillis();

    }

    public void paintComponent(Graphics g) {
        if (gameOn) {
            super.paintComponent(g);
            //this must be called first!
            DrawMethods.drawBackground(g);
            parseInput();
            craft.drift();
            AdditionalMethods.moveAsteroids();
            AdditionalMethods.moveBullets();
            boundsCheck();



            if (!gameOver) {
                AdditionalMethods.checkCollisions();
                if (invin) {
                    DrawMethods.drawInvinNotice(g);
                }
                DrawMethods.drawBullets(g);
                DrawMethods.drawTimer(g);
                if (Stage.exploding) {
                    Stage.craft = new Ship(Stage.origin.x, Stage.origin.y, this.loadImage("ship.png"));
                    DrawMethods.drawExplosions(g);
                } else {
                    DrawMethods.drawCraft(g);
                }
            } else {
                if (Stage.exploding) {
                    DrawMethods.drawExplosions(g);
                }
            }
            DrawMethods.drawAsteroids(g);
            DrawMethods.drawLives(g);
            DrawMethods.drawScore(g);
            DrawMethods.drawFPS(g, this.getHeight());
            DrawMethods.drawAmmunition(g);
            if (gameOver) {
                DrawMethods.drawGameOver(g);
            }

        } else {
            if (!gameOver) {
                DrawMethods.drawPauseScreen(g);
            }
        }
        //AdditionalMethods.stopAllSounds();
    }

    public void parseInput() {
        if (keyRight) {
            craft.rotate(radians);
            // keyRight = false;
        }
        if (keyLeft) {
            craft.rotate(-radians);
            //keyLeft = false;
        }
        if (keyUp) {
            craft.move(new Point(moveAmt, -moveAmt));
            keyUp = false;
        }
        if (keyDown) {
            int tempX = craft.pull.x;
            int tempY = craft.pull.y;

            tempX = tempX == 0 ? 0 : tempX > 0 ? tempX - 1 : tempX + 1;
            tempY = tempY == 0 ? 0 : tempY > 0 ? tempY - 1 : tempY + 1;
            craft.pull = new Point(tempX, tempY);
        }
        if (keySpace) {
            if (!gameOver) {
                AdditionalMethods.addBullet(craft.fire());
                keySpace = false;
            }
        }
        if (keyShift) {
            craft.setXPos((int) (Math.random() * this.getWidth()));
            craft.setYPos((int) (Math.random() * this.getHeight()));
            AdditionalMethods.teleport.play();
            keyShift = false;
        }
        if (keyStart) {
            keyStart = false;
            if (gameOver || !Stage.gameOn) {
                AdditionalMethods.stopAllSounds();
                AdditionalMethods.spawn.play();
                replay = true;
                Stage.score = 0;
                Stage.craft = new Ship(Stage.origin.x, Stage.origin.y, this.loadImage("ship.png"));
                Stage.asteroidCount = 5;
                roids = new Asteroid[asteroidCount];
                try {
                    AdditionalMethods.instantiateAsteroids();
                } catch (Exception ex) {
                    Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, ex);
                }

                lives = 3;
                score = 0;
                gameOver = false;
                Stage.gameOn = true;

            }
        }
    }

    public void setBackground(Image img) {
        this.background = img;
    }

    @SuppressWarnings("empty-statement")
    public Image loadImage(String imageName) {
        Image image = null;
        URL fileLocation = getClass().getResource("images/" + imageName);
        image = Toolkit.getDefaultToolkit().getImage(fileLocation);
        this.prepareImage(image, null);
        while (image.getWidth(null) == -1); //wait for image to be ready
        return image;
    }

    public void loadExplosions() {
        for (int i = 1; i < explosion.length + 1; i++) {
            String fetchName = String.format("Explosion%d.png", i);
            explosion[i - 1] = this.loadImage(fetchName);
        }
    }

    public void boundsCheck() {
        if (!this.getBounds().intersects(craft.hitBox)) {
            if (craft.hitBox.x < 0) {
                craft.setXPos(this.getWidth());
            } else if (craft.hitBox.x > this.getWidth()) {
                craft.setXPos(0);
            }
            if (craft.hitBox.y < 0) {
                craft.setYPos(this.getHeight());
            } else if (craft.hitBox.y > this.getHeight()) {
                craft.setYPos(0);
            }
        }
        asteroidBoundsCheck();
        bulletsBoundsCheck();
    }

    public void asteroidBoundsCheck() {
        for (int i = 0; i < roids.length; i++) {
            if (!this.getBounds().intersects(roids[i].hitBox)) {
                if (roids[i].hitBox.x < 0) {
                    roids[i].setXPos(this.getWidth());
                } else if (roids[i].hitBox.x > this.getWidth()) {
                    roids[i].setXPos(0 - roids[i].hitBox.width);
                }
                if (roids[i].hitBox.y < 0) {
                    roids[i].setYPos(this.getHeight());
                } else if (roids[i].hitBox.y > this.getHeight()) {
                    roids[i].setYPos(0 - roids[i].hitBox.height);
                }
            }
        }
    }

    public void bulletsBoundsCheck() {
        ArrayList<Bullet> tempList = new ArrayList<Bullet>();
        for (int i = 0; i < AdditionalMethods.bullets.size(); i++) {
            Bullet temp = AdditionalMethods.bullets.get(i);
            if (!this.getBounds().intersects(temp.hitBox)) {
                if (temp.hitBox.x < 0) {
                    temp.setXPos(this.getWidth());
                } else if (temp.hitBox.x > this.getWidth()) {
                    temp.setXPos(0);
                }
                if (temp.hitBox.y < 0) {
                    temp.setYPos(this.getHeight());
                } else if (temp.hitBox.y > this.getHeight()) {
                    temp.setYPos(0);
                }
                AdditionalMethods.bullets.remove(i);
                tempList.add(temp);
            }
        }
        AdditionalMethods.bullets.addAll(tempList);
    }
}


