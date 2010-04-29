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
public abstract class DrawMethods {

    public static void drawBackground(Graphics g) {
        g.drawImage(Stage.background, 0, 0, null);
    }

    public static void drawFPS(Graphics g, int h) {
        g.setColor(Color.DARK_GRAY);
        g.drawString(String.format("\tFPS: %.2f", Stage.fps), 0, h - 20);
    }

    public static void drawCraft(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform orig = g2d.getTransform();
        AffineTransform at = (AffineTransform) orig.clone();
        at.translate(Stage.craft.xPos, Stage.craft.yPos);
        at.translate(Stage.craft.img.getWidth(null) / 2, Stage.craft.img.getHeight(null) / 2);
        at.rotate(Math.toRadians(Stage.craft.degrees));
        //
        at.translate(-Stage.craft.xPos, -Stage.craft.yPos);
        at.translate(-Stage.craft.img.getWidth(null) / 2, -Stage.craft.img.getHeight(null) / 2);
        g2d.setTransform(at);
        g2d.drawImage(Stage.craft.img, at.getTranslateInstance(Stage.craft.xPos, Stage.craft.yPos), null);
        g2d.setTransform(orig);
        if (Stage.debug) {
            g2d.setColor(Color.WHITE);
            g2d.drawRect(Stage.craft.hitBox.x, Stage.craft.hitBox.y, Stage.craft.img.getWidth(null), Stage.craft.img.getHeight(null));
        }
    }

    public static void drawPauseScreen(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 36));
        g.drawString("PAUSED", ((int) (Stage.width
                - g.getFontMetrics().getStringBounds("PAUSED", g).getWidth())
                / 2), Stage.height / 2);
    }

    public static void drawAsteroids(Graphics g) {
        for (Asteroid a : Stage.roids) {
            g.drawImage(a.img, a.xPos, a.yPos, null);
            if (Stage.debug) {
                g.drawRect(a.hitBox.x, a.hitBox.y, a.img.getWidth(null), a.img.getHeight(null));
            }
        }
    }

    public static void drawLives(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        g.drawString("Lives remaining: " + Stage.lives, 20, 40);
    }

    public static void drawGameOver(Graphics g) {
        g.setColor(new Color(0x00, 0x72, 0xFF));
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 100));
        g.drawString("GAME OVER", ((int) (Stage.width
                - g.getFontMetrics().getStringBounds("GAME OVER", g).getWidth())
                / 2), Stage.height / 3);
        g.setColor(Color.BLUE);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
        String message = "s to start";
        g.drawString(message, ((int) (Stage.width
                - g.getFontMetrics().getStringBounds(message, g).getWidth())
                / 2), Stage.height / 2);
    }

    static void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        g.drawString("Score: " + Stage.score, Stage.width - 100, 40);
    }

    public static void drawBullets(Graphics g) {
        for (int i = 0; i < AdditionalMethods.bullets.size(); i++) {
            Bullet temp = AdditionalMethods.bullets.get(i);
            g.setColor(Color.RED);
            g.fillOval(temp.xPos, temp.yPos, temp.width, temp.height);
            if (Stage.debug) {
                g.setColor(Color.WHITE);
                g.drawRect(temp.hitBox.x, temp.hitBox.y, temp.width, temp.height);
            }
        }
    }

    public static void drawExplosions(Graphics g) {
        int x = AdditionalMethods.explosionLocation.x;
        int y = AdditionalMethods.explosionLocation.y;
        if (Stage.explosionStage < Stage.explosion.length - 1) {
            Stage.explosionStage += .3;
            g.drawImage(Stage.explosion[(int) Stage.explosionStage], x, y, null);
        } else {
            Stage.exploding = false;
        }
    }

    public static void drawAmmunition(Graphics g) {
        int startX = Stage.width - AdditionalMethods.maxBullets * 10;
        int startY = Stage.height - 40;
        int endX = (AdditionalMethods.maxBullets - AdditionalMethods.bullets.size()) * 10;
        int endY = Stage.height - 20;
        g.setColor(Color.RED);
        g.drawRect(startX, startY, endX, endY);
        g.setColor(Color.orange);
        g.fillRect(startX + 1, startY + 1, endX - 1, endY - 1);
    }

    public static void drawInvinNotice(Graphics g) {
        String message = "You are now invincible";
        g.setColor(Color.GREEN);
        g.drawString(message, ((int) (Stage.width
                - g.getFontMetrics().getStringBounds(message, g).getWidth())
                / 2), 40);
    }

    static void drawTimer(Graphics g) {
        g.setColor(Color.WHITE);
        String message = Stage.time.toString();
        g.drawString(message,((int) (Stage.width
                - g.getFontMetrics().getStringBounds(message, g).getWidth())
                / 2), 20);
    }
}
