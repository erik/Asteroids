/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author erikprice
 */
public class Key implements KeyListener {

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'p') {
            Runner.stage.gameOn = !Runner.stage.gameOn;
            //System.out.println("VALUE:" + Runner.stage.gameOn);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Runner.stage.keyUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Runner.stage.keyDown = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Runner.stage.keyRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Runner.stage.keyLeft = true;
        }
        if (e.getKeyChar() == ' ') {
            Runner.stage.keySpace = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Runner.stage.keyShift = true;
        }
        if (e.getKeyChar() == 's') {
            Stage.keyStart = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (Runner.masterDebug) {
            if (e.getKeyChar() == 'd') {
                Stage.debug = !Stage.debug;
            }
            if (e.getKeyChar() == '=') {
                AdditionalMethods.maxBullets++;
            }
            if (e.getKeyChar() == '-') {
                AdditionalMethods.maxBullets--;
            }
            if (e.getKeyChar() == 'i') {
                Stage.invin = !Stage.invin;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Runner.stage.keyUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Runner.stage.keyDown = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Runner.stage.keyRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Runner.stage.keyLeft = false;
        }
        if (e.getKeyChar() == ' ') {
            Runner.stage.keySpace = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Runner.stage.keyShift = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}
