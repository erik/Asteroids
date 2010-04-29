/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import javax.swing.*;

/**
 *
 * @author judithprice
 */
public class Runner extends JFrame {

    public static Stage stage;
    public static boolean masterDebug = true;

    @SuppressWarnings("static-access")
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
