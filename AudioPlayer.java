import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

    private String soundFolder = "Background music" + File.separator;
    private String backgroundMusicPath = soundFolder + "Background-music.wav";
    private String clearLinePath = soundFolder + "Clear-line.wav";

    private Clip backgroundMusic, clearLineSound;

    public AudioPlayer() {
        try {
            backgroundMusic = AudioSystem.getClip();
            clearLineSound = AudioSystem.getClip();

            backgroundMusic.open(AudioSystem.getAudioInputStream(new File(backgroundMusicPath).getAbsoluteFile()));
            clearLineSound.open(AudioSystem.getAudioInputStream(new File(clearLinePath).getAbsoluteFile()));
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playBackground() {

        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playClearLine() {
        clearLineSound.setFramePosition(0);
        clearLineSound.start();
    }
}
