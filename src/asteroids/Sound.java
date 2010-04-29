/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import java.applet.*;

/**
 *
 * @author erikprice
 */
public class Sound {

    String name;
    AudioInputStream as;
    AudioClip audio;
    Clip clip;
    boolean looping;
    DataLine.Info info;

    public Sound(String filename) {
        name = filename;
        //InputStream in;
// Create an AudioStream object from the input stream.
        try {
            URL in = this.getClass().getResource("sounds/" + name);
            //as = AudioSystem.getAudioInputStream(new File(in.toURI()));
            //DataLine.Info info = new DataLine.Info(Clip.class, as.getFormat());
            //clip = (Clip) AudioSystem.getLine(info);
            //clip.open(as);
            audio = Applet.newAudioClip(in);
        } catch (Exception ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void play() {
        audio.play();


    }

    public void stop() {
       audio.stop();
    }
}
