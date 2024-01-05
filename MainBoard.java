import javax.swing.*;
import java.awt.*;

class MainBoard extends JFrame {
    private JFrame frame;
    private int width = 445, height = 629;
    private GameArea board = new GameArea();

    public MainBoard() {
        frame = new JFrame("Tetris");
        board = new GameArea();
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addKeyListener(board);
    }

    public static void main(String[] args) {
        MainBoard main = new MainBoard();
    }
}
