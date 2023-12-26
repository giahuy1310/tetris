import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameArea extends JPanel {
    private Timer looper;
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;
    private Color [] [] board = new Color[BOARD_WIDTH][BOARD_HEIGHT];
    private Color [] [] shape ={{Color.RED, Color.RED, Color.RED}, 
    {null, Color.RED, null}}
    ; 
    public GameArea(){
        looper = new Timer(500, new ActionListener(){
            int n = 0;
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println(n++);
            }
        });
        looper.start();

    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());

        g.setColor(Color.WHITE);

        for (int i = 0; i<BOARD_HEIGHT; i++){
                g.drawLine(0, BLOCK_SIZE*i, BLOCK_SIZE*BOARD_WIDTH, BLOCK_SIZE*i);
                
            }
        for (int j = 0; j<BOARD_WIDTH + 1; j++){
            g.drawLine(BLOCK_SIZE*j, 0, BLOCK_SIZE*j, BLOCK_SIZE*BOARD_HEIGHT);
        }

        for (int i = 0; i< shape.length; i++){
            for (int j = 0; j<shape[0].length; j++){
                g.setColor(shape[i][j]);
                g.fillRect(i*BLOCK_SIZE, j*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

        }
    }
}
}

