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

public class Title extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private BufferedImage instructions;
    private MainBoard frame;
    private BufferedImage[] playButton = new BufferedImage[2];
    private Timer timer;

    public Title(MainBoard frame) {
        instructions = ImageLoader.loadImage("/wasd4.png");
        timer = new Timer(1000 / 60, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }

        });
        timer.start();
        this.frame = frame;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, MainBoard.width, MainBoard.height);
        g.drawImage(instructions, MainBoard.width / 2 - instructions.getWidth() / 2,
                20 - instructions.getHeight() / 2 + 150, null);

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(35.0f));
        g.drawString("Press enter to play!", 55, MainBoard.HEIGHT / 2 + 400);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
            if (frame.getGameState() == GameArea.STATE_GAME_START) {
                frame.startGame();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
