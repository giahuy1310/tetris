import javax.swing.*;
import java.awt.*;
class MainBoard extends JFrame{
    private JFrame frame;
    private int width = 500, height = 700;
    public MainBoard(){
        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        MainBoard main = new MainBoard();
    }
}
