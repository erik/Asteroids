
package asteroids;

import java.awt.*;

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

        yMove = (((int) (5 * Math.cos(Math.toRadians(degrees)))));
        xMove = (((int) (5 * Math.sin(Math.toRadians(degrees)))));
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
        int newX = (int) (xPos + ((this.hitBox.width) / 2) + 2 * Math.cos(degrees));
        int newY = (int) (yPos + ((this.hitBox.height) / 2) + 2 * Math.sin(degrees));
        int xDir = moveSpeed * xMove;
        int yDir = moveSpeed * -yMove;
        return new Bullet(newX, newY, xDir, yDir);

    }
}
