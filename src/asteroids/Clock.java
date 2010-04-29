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
public class Clock {

    private long startMillis;
    private long seconds;

    public Clock() {
        this.startMillis = System.currentTimeMillis();
        this.seconds = 0;
    }

    public String toString() {
        this.seconds = (System.currentTimeMillis() - this.startMillis) / 1000;
        return (this.seconds / 60) + ":" + String.format("%02d", (this.seconds % 60));

    }
}
