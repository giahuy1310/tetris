import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.Timer;

import javax.swing.JPanel;

public class Title extends JPanel implements KeyListener{
    private static final long serialVersionUID = 1L;
    private BufferedImage instruction;
    private MainBoard mainBoard;
    private BufferedImage [] playButton = new BufferedImage[2];
    private Timer timer;


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    
}
