/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.awt.*;
import java.net.URL;
import java.util.Random;

/**
 *
 * @author erikprice
 */
public class Asteroid {

    public int scale;
    public int xDrift, yDrift, xPos, yPos;
    public Image img;
    public Rectangle hitBox;
    private Random rand;
    public static int driftSpeed = 10;
    public int rotation, degrees = 0;

    public Asteroid(int scale, int xPos, int yPos, Image imgS, Image imgM, Image imgL) throws Exception {
        this.scale = scale;
        this.xPos = xPos;
        this.yPos = yPos;
        small = imgS;
        medium = imgM;
        large = imgL;
        rand = new Random();
        switch (scale) {
            case LARGE:
                this.img = large;
                break;
            case MEDIUM:
                this.img = medium;
                break;
            case SMALL:
                this.img = small;
                break;
            default:
                throw new Exception("Scale incorrect!");
        }
        this.hitBox = new Rectangle(xPos, yPos, this.img.getWidth(null), this.img.getHeight(null));
        xDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        yDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        while (xDrift == 0 || yDrift == 0) {
            xDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
            yDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        }
        rotation = rand.nextInt(20);

    }

    public Asteroid(int scale) throws Exception {
        this.scale = scale;
        rand = new Random();
        switch (scale) {
            case LARGE:
                this.img = large;
                break;
            case MEDIUM:
                this.img = medium;
                break;
            case SMALL:
                this.img = small;
                break;
            default:
                throw new Exception("Scale incorrect!");
        }
        xDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        yDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        while (xDrift == 0 || yDrift == 0) {
            xDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
            yDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        }
        rotation = rand.nextInt(20);
        this.hitBox = new Rectangle(xPos, yPos, this.img.getWidth(null), this.img.getHeight(null));
    }

    public Asteroid(int scale, int x, int y) throws Exception {
        this.scale = scale;
        this.xPos = x;
        this.yPos = y;
        rand = new Random();
        switch (scale) {
            case LARGE:
                this.img = large;
                break;
            case MEDIUM:
                this.img = medium;
                break;
            case SMALL:
                this.img = small;
                break;
            default:
                throw new Exception("Scale incorrect!");
        }
        xDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        yDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        rotation = rand.nextInt(20);
        while (xDrift == 0 || yDrift == 0) {
            xDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
            yDrift = rand.nextInt(driftSpeed) - driftSpeed / 2;
        }
        this.hitBox = new Rectangle(xPos, yPos, this.img.getWidth(null), this.img.getHeight(null));

    }

    public boolean collidesWith(Rectangle other) {
        if (this.hitBox.intersects(other)) {
            return true;
        }
        return false;
    }

    public void drift() {
        // rotate();
        xPos += xDrift;
        yPos += yDrift;
        this.hitBox = new Rectangle(xPos, yPos, this.img.getWidth(null),
                this.img.getHeight(null));
    }

    public void setXPos(int pos) {
        this.xPos = pos;
    }

    public void setYPos(int pos) {
        this.yPos = pos;
    }

    public void rotate() {
        degrees = (rotation + degrees) % 360;
        degrees = (degrees < 0) ? 360 - -degrees : degrees;
        //System.out.println("Degrees: " + degrees);
    }
    public static Image large;
    public static Image medium;
    public static Image small;
    public static final int LARGE = 3;
    public static final int MEDIUM = 2;
    public static final int SMALL = 1;
}
