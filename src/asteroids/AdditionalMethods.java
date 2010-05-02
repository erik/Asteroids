package asteroids;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author erikprice
 */
public class AdditionalMethods {

    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public static Point[] explosionLoc = new Point[1];
    public static int[] explosionNum = new int[1];
    public static Point explosionLocation;
    public static Sound spawn, laser, explosionShip, explosionAsteroid, teleport;
    public static int maxDist = 200;
    public static int maxBullets = 5;

    public static void moveAsteroids() {
        for (int i = 0; i < Stage.roids.length; i++) {
            Stage.roids[i].drift();
        }
    }

    public static void instantiateAsteroids() throws Exception {
        for (int i = 0; i < Stage.roids.length; i++) {
            Stage.roids[i] = new Asteroid((int) (Math.random() * 3) + 1);
            Stage.roids[i].setXPos((int) (Math.random() * Stage.width));
            Stage.roids[i].setYPos((int) (Math.random() * Stage.height));
            while (Stage.roids[i].collidesWith(Stage.craft.hitBox) || Math.abs(Stage.roids[i].xPos - Stage.craft.xPos) < maxDist) {
                Stage.roids[i] = new Asteroid((int) (Math.random() * 3) + 1);
                Stage.roids[i].setXPos((int) (Math.random() * Stage.width));
                Stage.roids[i].setYPos((int) (Math.random() * Stage.height));

            }
        }
    }

    public static void checkCollisions() {
        checkBulletCollisions();
        if (!Stage.invin && !Stage.gameOver ) {
            for (int i = 0; i < Stage.roids.length; i++) {
                if (Stage.craft.collidesWith(Stage.roids[i].hitBox)) {
                    AdditionalMethods.explosionShip.play();
                    Stage.exploding = true;
                    Stage.explosionStage = 0;
                    AdditionalMethods.explosionLocation = new Point(Stage.roids[i].xPos, Stage.roids[i].yPos);
                    Stage.craft.setXPos(Stage.origin.x);
                    Stage.craft.setYPos(Stage.origin.y);
                    Stage.craft.pull = new Point(0, 0);
                    Stage.lives--;
                    AdditionalMethods.removeAsteroid(i);
                    if (Stage.lives == 0) {
                        Stage.gameOver = true;
                    }
                }
            }
        }
    }

    public static void removeAsteroid(int index) {
        Asteroid[] temp = new Asteroid[Stage.roids.length - 1];
        for (int i = 0, j = 0; i < Stage.roids.length; i++) {
            if (!(i == index)) {
                temp[j] = Stage.roids[i];
                j++;
            } else {
                Stage.score += 25 * Stage.roids[i].scale;
            }
        }
        Stage.roids = temp;
    }

    public static void addAsteroid(Asteroid a) {
        Asteroid temp[] = new Asteroid[Stage.roids.length + 1];
        temp[0] = a;
        System.arraycopy(Stage.roids, 0, temp, 1, Stage.roids.length);
        Stage.roids = temp;
    }

    public static void addBullet(Bullet fire) {
        if (bullets.size() < maxBullets) {
            AdditionalMethods.laser.play();
            AdditionalMethods.bullets.add(fire);
        }
    }

    private static void checkBulletCollisions() {
        if (!(bullets.size() == 0)) {
            for (int i = 0; i < Stage.roids.length && Stage.roids.length != 0; i++) {
                for (int j = 0; j < bullets.size(); j++) {
                    if (bullets.size() > 0) {
                        Bullet temp = bullets.get(j);
                        if (temp.collidesWith(Stage.roids[i].hitBox)) {
                            AdditionalMethods.explosionAsteroid.play();
                            Stage.score += 25 * Stage.roids[i].scale;
                            addExplosion(Stage.roids[i].xPos, Stage.roids[i].yPos);
                            if (Stage.roids[i].scale == 1) {
                                AdditionalMethods.removeAsteroid(i);
                                i = 0;
                            } else {
                                try {
                                    Stage.roids[i] = new Asteroid(Stage.roids[i].scale - 1, Stage.roids[i].xPos, Stage.roids[i].yPos);
                                    addAsteroid(new Asteroid(Stage.roids[i].scale, Stage.roids[i].xPos, Stage.roids[i].yPos));
                                } catch (Exception ex) {
                                    Logger.getLogger(AdditionalMethods.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            bullets.remove(j);
                        }
                    }
                }
            }
        }
    }

    public static void moveBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet temp = bullets.get(i);
            temp.move();
            bullets.remove(i);
            if (!temp.die) {
                bullets.add(temp);
            }
        }
    }

    public static void addExplosion(int x, int y) {
        if (AdditionalMethods.explosionLoc != null) {
            int[] tempNum = AdditionalMethods.explosionNum;
            Point[] tempLoc = AdditionalMethods.explosionLoc;
            AdditionalMethods.explosionLoc = new Point[tempLoc.length + 1];
            AdditionalMethods.explosionNum = new int[tempNum.length + 1];
            AdditionalMethods.explosionNum[tempNum.length] = 1;
            AdditionalMethods.explosionLoc[tempLoc.length] = new Point(x, y);
            for (int i = 0; i < tempNum.length - 1; i++) {
                AdditionalMethods.explosionLoc[i] = tempLoc[i];
                AdditionalMethods.explosionNum[i] = tempNum[i];
            }
        } else {
            AdditionalMethods.explosionNum = new int[1];
            AdditionalMethods.explosionNum[0] = 1;
            AdditionalMethods.explosionLoc = new Point[1];
            AdditionalMethods.explosionLoc[0] = new Point(x, y);
        }
    }

    public static void loadSounds() {
        spawn = new Sound("spawn.wav");
        laser = new Sound("fire2.wav");
        explosionAsteroid = new Sound("explosion2.wav");
        explosionShip = new Sound("explosion.wav");
        teleport = new Sound("fire.wav");
    }
}
