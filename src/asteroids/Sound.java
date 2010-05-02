package asteroids;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import java.applet.*;

public class Sound {

    String name;
    AudioInputStream as;
    AudioClip audio;
    Clip clip;
    boolean looping;
    DataLine.Info info;

    public Sound(String filename) {
        name = filename;
        try {
            URL in = this.getClass().getResource("sounds/" + name);
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
