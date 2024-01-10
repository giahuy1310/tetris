import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Title extends JPanel implements KeyListener{
    private static final long serialVersionUID = 1L;
    private BufferedImage instructions;
    private MainBoard mainBoard;
    private BufferedImage [] playButton = new BufferedImage[2];
    private Timer timer;
    public Title( MainBoard frame){
        instructions = ImageLoader.loadImage("/arrow.png");
        timer = new Timer(1000/60, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }

        });
        timer.start();
        this.mainBoard = frame;

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, MainBoard.WIDTH, MainBoard.HEIGHT);


        g.drawImage(instructions, MainBoard.WIDTH / 2 - instructions.getWidth() / 2,
                30 - instructions.getHeight() / 2 + 150, null);

        g.setColor(Color.BLUE);
        g.drawString("Press space to play!", 150, MainBoard.HEIGHT / 2 + 100);

    }
        @Override
    public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == KeyEvent.VK_SPACE) {
                mainBoard.startTetris();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    
}
