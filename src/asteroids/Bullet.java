/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author erikprice
 */
public class Bullet {

    public Rectangle hitBox;
    public int xSpeed, ySpeed, xPos, yPos, xOrig, yOrig;
    public int lenXFromOrig, lenYFromOrig;
    public static int width = 10, height = 10, maxDist = 750;
    public boolean die;

    public Bullet(int x, int y, int xs, int ys) {
        this.xPos = x;
        this.yPos = y;
        this.xOrig = x;
        String s = "Hi there\0";
        this.yOrig = y;
        this.xSpeed = xs;
        this.ySpeed = ys;
        this.lenXFromOrig = 0;
        this.lenYFromOrig = 0;
        this.die = false;
        hitBox = new Rectangle(x, y, width, height);
    }

    public void move() {

        this.xPos += this.xSpeed;
        this.yPos += this.ySpeed;
        this.lenYFromOrig += Math.abs(this.ySpeed);
        this.lenXFromOrig += Math.abs(this.xSpeed);
        if (this.lenXFromOrig + this.lenYFromOrig > maxDist) {
            this.die = true;
        }
        hitBox = new Rectangle(xPos, yPos, width, height);
    }

    public boolean collidesWith(Rectangle other) {
        if (this.hitBox.intersects(other)) {
            return true;
        }
        return false;
    }

    public void setXPos(int width) {
        this.xPos = width;
        hitBox = new Rectangle(xPos, yPos, width, height);
    }

    public void setYPos(int width) {
        this.yPos = width;
        hitBox = new Rectangle(xPos, yPos, width, height);
    }
}
