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
                Stage.gameOn = !Stage.gameOn;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                stage.keyUp = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                stage.keyDown = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                stage.keyRight = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                stage.keyLeft = true;
            }
            if (e.getKeyChar() == ' ') {
                stage.keySpace = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                stage.keyShift = true;
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
                stage.keyUp = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                stage.keyDown = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                stage.keyRight = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                stage.keyLeft = false;
            }
            if (e.getKeyChar() == ' ') {
                stage.keySpace = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                stage.keyShift = false;
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    private class Focus implements FocusListener {

        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            stage.gameOn = false;
        }
    }
    
    public Stage stage;
    public static boolean masterDebug = true;

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
