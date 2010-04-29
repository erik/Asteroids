/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author erikprice
 */
public class Focus implements FocusListener {

    public void focusGained(FocusEvent e) {
        //  Runner.stage.gameOn = true;
    }

    public void focusLost(FocusEvent e) {
        Runner.stage.gameOn = false;
    }
}
