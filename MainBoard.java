import javax.swing.*;
import java.awt.*;
class MainBoard extends JFrame{
    private JFrame frame;
    private Title title ;
    private int width = 445, height = 629;
    private GameArea board = new GameArea();
    public MainBoard(){
        frame = new JFrame("Tetris");
        board = new GameArea();
        title = new Title(this);
        frame.add(board);
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(board);
        frame.add(title);
        frame.addKeyListener(title);
        

        frame.setVisible(true);
        
    }
    public void startGame(){
        frame.remove(title);
        frame.add(board);
        board.startGame();
        frame.revalidate();
        frame.repaint();
    }
    
    public static void main(String[] args){
        MainBoard main = new MainBoard();
    }
}



