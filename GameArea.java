import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameArea extends JPanel implements KeyListener {
    public static int STATE_GAME_PLAY = 0;
    public static int STATE_GAME_PAUSE = 1;
    public static int STATE_GAME_OVER = 2;
    private int countdown = 0; 

    private int state = STATE_GAME_PLAY;

    private static int FPS = 60;
    private static int delay = 1000/FPS;

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private Shape[] shapes = new Shape[7];
    private Color[] colors = { Color.decode("#00ffff"), Color.MAGENTA, Color.ORANGE, Color.decode("#0000ff"), Color.GREEN, Color.RED,
            Color.yellow };
    private Random random;

    private Shape currentShape;

    private Timer looper;

    public GameArea() {
        random = new Random();

        // create the shapes
        shapes[0] = new Shape(new int[][] { { 1, 1, 1, 1 } },
                this, colors[0]); // I shape
        shapes[1] = new Shape(new int[][] {
                { 1, 1, 1 },
                { 0, 1, 0 } },
                this, colors[1]); // T shape
        shapes[2] = new Shape(new int[][] {
                { 1, 1, 1 },
                { 1, 0, 0 } },
                this, colors[2]); // L shape
        shapes[3] = new Shape(new int[][] {
                { 1, 1, 1 },
                { 0, 0, 1 } },
                this, colors[3]); // J shape
        shapes[4] = new Shape(new int[][] {
                { 1, 1, 0 },
                { 0, 1, 1 } },
                this, colors[4]); // S shape
        shapes[5] = new Shape(new int[][] {
                { 0, 1, 1 },
                { 1, 1, 0 } },
                this, colors[5]); // Z shape
        shapes[6] = new Shape(new int[][] {
                { 1, 1 },
                { 1, 1 } },
                this, colors[6]); // O shape

        currentShape = shapes[random.nextInt(shapes.length)];
        // Lopper
        looper = new Timer(delay, new ActionListener() {
            int n = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        looper.start();
    }

    private void update() {
        if (state == STATE_GAME_PLAY){
            currentShape.update();
        }
    }
    // random new shape
    public void setCurrentShape() {
        currentShape = shapes[random.nextInt(shapes.length)];
        currentShape.reset();
        checkoverGame();
    }
    // check game over
    public void checkoverGame(){
        int [] [] coords = currentShape.getCoords();
        for (int row = 0; row < coords.length; row++){
            for (int col = 0; col < coords[0].length; col++){
                if (coords[row][col]!= 0){
                    if (board[row + currentShape.getY()][col+currentShape.getX()] != null){
                        state = STATE_GAME_OVER;
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        currentShape.render(g);
        //set the shape on the board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

                // Draw a border around the square
                    g.setColor(Color.BLACK); // Set the border color
                    g.drawRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);;
                }
            }
        }
        //show game over
        if (state == STATE_GAME_OVER) {
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(50.0f)); // Set the font size
            g.drawString("Game Over", BOARD_WIDTH/2,(BLOCK_SIZE * BOARD_HEIGHT)/2);
        }
        // show game pause
        if (state == STATE_GAME_PAUSE && countdown == 0) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(50.0f)); // Set the font size
            g.drawString("Game Pause", BOARD_WIDTH /2,(BLOCK_SIZE * BOARD_HEIGHT)/2);
        }
        // draw the board
        g.setColor(Color.WHITE);

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }
        for (int col = 0; col < BOARD_WIDTH + 1; col++) {
            g.drawLine(BLOCK_SIZE * col, 0, BLOCK_SIZE * col, BLOCK_SIZE * BOARD_HEIGHT);
        }
        // show count down when unpause
        if (countdown > 0) {
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(35.0f));
            g.drawString("Resuming in " + countdown + "...", BOARD_WIDTH/2, (BLOCK_SIZE * BOARD_HEIGHT)/2);
        }
        
    }


    public Color[][] getBoard() {
        return board;
    }

    // game control
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            currentShape.speedUp();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            currentShape.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            currentShape.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_W){
            currentShape.rotateShape();
        } // clean the board
         else if (state == STATE_GAME_OVER &&  (e.getKeyCode() == KeyEvent.VK_ENTER)){
            for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                    board[row][col] = null;
                }
            }
            setCurrentShape();
            state = STATE_GAME_PLAY;
        }
        // pause
        if (e.getKeyCode() == KeyEvent.VK_P){
            if (state == STATE_GAME_PLAY){
                state = STATE_GAME_PAUSE;
            } else if (state == STATE_GAME_PAUSE){
                if(countdown ==0){
                        countdown = 3;
                        new Timer(1000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                countdown --;
                                if (countdown <= 0) {
                                    // Unpause the game
                                    state = STATE_GAME_PLAY;
                                    ((Timer) e.getSource()).stop();
                                }
                            }
                        }).start();
                    }
                    return;
                    }
                } 
          // add drop immediately       
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            if(state == STATE_GAME_OVER){
                return;
            }
                currentShape.immediatelyDrop();
                currentShape.update();
            }
        }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            currentShape.speedDown();
        }
    }
}
