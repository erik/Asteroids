/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 *
 * @author erikprice
 */
public class Ship {

    public Point position, pull;
    public int moveAmt = 10;
    public Rectangle hitBox;
    public int width, height, xPos, yPos;
    public int degrees;
    public Image img;
    private int xMove, yMove;
    public static int moveSpeed = 5;

    public Ship(int x, int y, Image i) {
        xPos = x;
        yPos = y;
        img = i;
        pull = new Point(0, 0);
        position = new Point(xPos, yPos);
        degrees = 270;
        width = img.getWidth(null);
        height = img.getHeight(null);
        hitBox = new Rectangle(xPos, yPos, width, height);
        xMove = -1;
        yMove = 0;
    }

    public boolean collidesWith(Rectangle other) {
        if (other.intersects(this.hitBox)) {
            return true;
        }
        return false;
    }

    public void move(Point where) {
        //accelerate(where);
        xPos += where.x * xMove;
        yPos += where.y * yMove;
        position = new Point(xPos, yPos);
        hitBox = new Rectangle(xPos, yPos, width, height);
        pull.x += xMove;
        pull.y += -(yMove);
    }

    public void drift() {
        xPos += pull.x;
        yPos += pull.y;
        position = new Point(xPos, yPos);
        hitBox = new Rectangle(xPos, yPos, width, height);
    }

    public void accelerate(double acceleration) {
        pull.x += (int) (acceleration);
        pull.y += (int) -(acceleration);
    }

    public Point getPosition() {
        return position;
    }

    public void rotate(int deg) {
        degrees = (deg + degrees) % 360;
        degrees = (degrees < 0) ? 360 - -degrees : degrees;
        //X = x + RcosA and
        //Y = y + RsinA.

        yMove = (((int) (5 * Math.cos(Math.toRadians(degrees)))));
        xMove = (((int) (5 * Math.sin(Math.toRadians(degrees)))));
        //System.out.println(xMove + " " + yMove);
        /*
        if (degrees > 0 && degrees < 45) {
        xMove = 0;
        yMove = 1;
        } else if (degrees >= 45 && degrees < 90) {
        xMove = 1;
        yMove = 1;
        } else if (degrees >= 90 && degrees < 135) {
        xMove = 1;
        yMove = 0;

        } else if (degrees >= 135 && degrees < 180) {
        xMove = 1;
        yMove = -1;

        } else if (degrees >= 180 && degrees < 225) {
        xMove = 0;
        yMove = -1;

        } else if (degrees >= 225 && degrees < 270) {
        xMove = -1;
        yMove = -1;

        } else if (degrees >= 270 && degrees < 315) {
        xMove = -1;
        yMove = 0;
        } else if (degrees >= 315) {
        xMove = -1;
        yMove = 1;
        }*//*
        switch (degrees) {
        case 0:
        xMove = 0;
        yMove = 1;
        break;
        case 45:
        xMove = 1;
        yMove = 1;
        break;
        case 90:
        xMove = 1;
        yMove = 0;
        break;
        case 135:
        xMove = 1;
        yMove = -1;
        break;
        case 180:
        xMove = 0;
        yMove = -1;
        break;
        case 225:
        xMove = -1;
        yMove = -1;
        break;
        case 270:
        xMove = -1;
        yMove = 0;
        break;
        case 315:
        xMove = -1;
        yMove = 1;
        break;
        default:
        xMove = 0;
        yMove = 0;
        }*/


    }

    public void setXPos(int width) {
        xPos = width;
        position = new Point(xPos, yPos);
        hitBox = new Rectangle(xPos, yPos, this.width, height);


    }

    public void setYPos(int width) {
        yPos = width;
        position = new Point(xPos, yPos);
        hitBox = new Rectangle(xPos, yPos, this.width, height);



    }

    public Bullet fire() {
        AdditionalMethods.stopAllSounds();

        /*if (degrees > 315 || degrees < 45) {
        return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * 0, moveSpeed * -1);
        } else if (degrees >= 45 && degrees < 135) {
        return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * 1, moveSpeed * -1);
        //} else if (degrees >= 90 && degrees < 135) {
        //    return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * 1, moveSpeed * 0);
        } else if (degrees >= 135 && degrees < 225) {
        return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * 1, moveSpeed * 1);
        //} else if (degrees >= 180 && degrees < 225) {
        //   return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * 0, moveSpeed * 1);
        } else if (degrees >= 225 && degrees < 315) {
        return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * -1, moveSpeed * 1);
        //} else if (degrees >= 270 && degrees < 315) {
        //    return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * -1, moveSpeed * 0);
        } else {// if (degrees >= 315) {
        return new Bullet((int) (this.xPos + this.hitBox.getWidth() / 2), (int) (this.yPos + this.hitBox.getHeight() / 2), moveSpeed * -1, moveSpeed * -1);
        }*/
        /*int x = (int) (this.xPos + this.hitBox.getWidth() / 2);
        int y = (int) (this.yPos + this.hitBox.getHeight() / 2);
        switch (degrees) {
        case 0:
        return new Bullet(x, y, moveSpeed * 0, moveSpeed * -1);
        default:
        System.out.println("IMPLEMENT ME");
        return new Bullet(0, 0, 0, 0);
        }*/

        int newX = (int) (xPos + ((this.hitBox.width) / 2) + 2 * Math.cos(degrees));
        int newY = (int) (yPos + ((this.hitBox.height) / 2) + 2 * Math.sin(degrees));
        //System.out.printf("(%d,%d)", newX - (this.hitBox.x - this.hitBox.width), newY - (this.hitBox.x - this.hitBox.width));
        int xDir = moveSpeed * xMove;//((Math.abs(newX - (this.hitBox.x - this.hitBox.width)) / (newX - (this.hitBox.x - this.hitBox.width))));
        int yDir = moveSpeed * -yMove;//((Math.abs(newY - (this.hitBox.y - this.hitBox.height)) / (newY - (this.hitBox.y - this.hitBox.height))));
        //System.out.printf("-> (%d, %d)\n", xDir, yDir);
        return new Bullet(newX, newY, xDir, yDir);

    }
}
