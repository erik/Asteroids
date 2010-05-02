package asteroids;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Runner extends JFrame {

    private class Key implements KeyListener {

        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 'p') {
                if (stage.gameOn()) {
                    stage.setGameOff();
                } else {
                    stage.setGameOn();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                stage.setKeyUp(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                stage.setKeyDown(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                stage.setKeyRight(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                stage.setKeyLeft(true);
            }
            if (e.getKeyChar() == ' ') {
                stage.setKeySpace(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                stage.setKeyShift(true);
            }
            if (e.getKeyChar() == 's') {
                stage.setKeyStart(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            if (masterDebug) {
                if (e.getKeyChar() == 'd') {
                    if (stage.getDebug()) {
                        stage.setDebugOff();
                    } else {
                        stage.setDebugOn();
                    }
                }
                if (e.getKeyChar() == '=') {
                    stage.maxBullets++;
                }
                if (e.getKeyChar() == '-') {
                    stage.maxBullets--;
                }
                if (e.getKeyChar() == 'i') {
                    if (stage.getInvin()) {
                        stage.setInvinOff();
                    } else {
                        stage.setInvinOn();
                    }
                }
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                stage.setKeyUp(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                stage.setKeyDown(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                stage.setKeyRight(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                stage.setKeyLeft(false);
            }
            if (e.getKeyChar() == ' ') {
                stage.setKeySpace(false);
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                stage.setKeyShift(false);
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    private class Focus implements FocusListener {

        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            stage.setGameOff();
        }
    }
    public Stage stage;
    public boolean masterDebug = true;

    public Runner() throws Exception {
        super("Asteroids");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        stage = new Stage(this.getWidth(), this.getHeight(), "backdrop.jpg");
        this.add(stage);
        this.addFocusListener(new Focus());
        this.addKeyListener(new Key());
        this.setLocationRelativeTo(null); //center on screen

    }
}
