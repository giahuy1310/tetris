import javax.swing.*;
import java.awt.*;

class MainBoard extends JFrame {
    private JFrame frame;
    private Title title;
    private int width = 445, height = 625;
    private GameArea board = new GameArea();

    private static AudioPlayer audio = new AudioPlayer();

    public static void playBackgroundMusic() {
        audio.playBackground();
    }

    public static void playClearLineSound() {
        audio.playClearLine();
    }

    public MainBoard() {

        playBackgroundMusic();

        frame = new JFrame("Tetris");
        board = new GameArea();
        title = new Title(this);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(board);

        frame.setVisible(true);
        frame.addKeyListener(title);
        frame.add(title);

    }
    public void startGame(){
        frame.remove(title);
        frame.add(board);
        board.startGame();
        frame.revalidate();

    }
    
    public static void main(String[] args){
        MainBoard main = new MainBoard();
    }
}
