import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameArea extends JPanel implements KeyListener {
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
        currentShape.update();
    }
    // random new shape
    public void setCurrentShape() {
        currentShape = shapes[random.nextInt(shapes.length)];
        currentShape.reset();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        currentShape.render(g);

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
            // draw the board
        g.setColor(Color.WHITE);

        for (int row = 0; row < BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }
        for (int col = 0; col < BOARD_WIDTH + 1; col++) {
            g.drawLine(BLOCK_SIZE * col, 0, BLOCK_SIZE * col, BLOCK_SIZE * BOARD_HEIGHT);
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
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE){
            currentShape.rotateShape();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            currentShape.speedDown();
        }
    }
}
