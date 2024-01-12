import javax.swing.JFrame;
import java.awt.*;
class MainBoard extends JFrame{
    private JFrame frame;
    private Title title ;
    public static final int  width = 445, height = 629;
    private GameArea board  ;
    public MainBoard(){
        frame = new JFrame("Tetris");
        board = new GameArea();
        title = new Title(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addKeyListener(board);
        frame.add(title);
        frame.addKeyListener(title);
    }
    public void startTetris(){
       frame.remove(title);
        frame.addMouseListener(board);
        frame.addMouseMotionListener(board);
        frame.add(board);
        board.startGame();
        frame.revalidate();


    }




    public static void main(String[] args){
        MainBoard main = new MainBoard();
    }
}
