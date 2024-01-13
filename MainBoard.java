import javax.swing.*;
import java.awt.*;

class MainBoard extends JFrame {
    private JFrame frame;
    private int width = 445, height = 629;
    private GameArea board = new GameArea();

    private static AudioPlayer audio = new AudioPlayer();

    public static void playBackgroundMusic() {
        audio.playBackground();
    }

    public MainBoard() {

        playBackgroundMusic();

        frame = new JFrame("Tetris");
        board = new GameArea();
        frame.add(board);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(board);

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        MainBoard main = new MainBoard();
    }
}
