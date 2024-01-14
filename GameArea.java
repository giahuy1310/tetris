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
import java.util.Random;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
public class GameArea extends JPanel implements KeyListener, MouseListener, MouseMotionListener{
    public static int STATE_GAME_PLAY = 0;
    public static int STATE_GAME_PAUSE = 1;
    public static int STATE_GAME_OVER = 2;
    public static int STATE_GAME_START = 3;
    private int countdown = 0;
    private int n = 0;
    private static final long serialVersionUID = 1L;

    private BufferedImage pause, refresh;
    private int mouseX, mouseY;

    private boolean leftClick = false;

    private Rectangle stopBounds, refreshBounds;


    private int state = STATE_GAME_START;

    private static int FPS = 60;
    private static int delay = 1000 / FPS;

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private Shape[] shapes = new Shape[7];
    private Shape nextShape;
    private Color[] colors = { Color.decode("#C8C3FA"), Color.decode("#FAB3F5"), Color.decode("#F9D5BD"),
            Color.decode("#B3F6E6"), Color.decode("#CCE9AF"), Color.decode("#FF6961"),
            Color.decode("#FF910C") };
    private Random random;

    private Shape currentShape;

    private Timer looper;
    private boolean gamePaused = false;

    private boolean gameOver = false;
    private Timer buttonLapse = new Timer(300, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });


    private int score = 0;
    private int clearLines = 0;

    public GameArea() {
        // add the button to the game
        pause = ImageLoader.loadImage("/Pause.png");
        refresh = ImageLoader.loadImage("/Refresh.png");
        state = STATE_GAME_START;

        mouseX = 0;
        mouseY = 0;

        stopBounds = new Rectangle(350, 500, pause.getWidth(), pause.getHeight() + pause.getHeight() / 2);
        refreshBounds = new Rectangle(350, 500 - refresh.getHeight() - 20, refresh.getWidth(),
                refresh.getHeight() + refresh.getHeight() / 2);

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

        setNextShape();
        setCurrentShape();
        // Lopper
        looper = new Timer(delay, new GameLooper() {

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        looper.start();
    }

    private void update() {
        if (stopBounds.contains(mouseX, mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver) {
            buttonLapse.start();
            if (state == STATE_GAME_PLAY) {
                state = STATE_GAME_PAUSE;
            } else if (state == STATE_GAME_PAUSE) {
                if (countdown == 0) {
                    countdown = 3;
                    new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            countdown--;
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
        if (refreshBounds.contains(mouseX, mouseY) && leftClick) {
            if(state == STATE_GAME_PAUSE){
                if(countdown == 0){
                state = STATE_GAME_PLAY;
                startGame();
            } else return;
            }
            else startGame();
        }

        if (gamePaused || gameOver) {
            return;
        }


        if (state == STATE_GAME_PLAY) {
            currentShape.update();
        }
    }

    // random new shape
    public void setNextShape() {
        nextShape = shapes[random.nextInt(shapes.length)];
    }

    public void setCurrentShape() {
        // Create a new instance of the shape
        currentShape = new Shape(nextShape.getCoords(), this, nextShape.getColor());
        setNextShape();
        currentShape.reset();
        checkoverGame();
    }

    // check game over
    public void checkoverGame() {
        int[][] coords = currentShape.getCoords();
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    if (board[row + currentShape.getY()][col + currentShape.getX()] != null) {
                        state = STATE_GAME_OVER;
                        n =1;
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
        // draw the button
        if (stopBounds.contains(mouseX, mouseY)) {
            g.drawImage(pause.getScaledInstance(pause.getWidth() + 3, pause.getHeight() + 3, BufferedImage.SCALE_DEFAULT), stopBounds.x + 3, stopBounds.y + 3, null);
        } else {
            g.drawImage(pause, stopBounds.x, stopBounds.y, null);
        }

        if (refreshBounds.contains(mouseX, mouseY)) {
            g.drawImage(refresh.getScaledInstance(refresh.getWidth() + 3, refresh.getHeight() + 3,
                    BufferedImage.SCALE_DEFAULT), refreshBounds.x + 3, refreshBounds.y + 3, null);
        } else {
            g.drawImage(refresh, refreshBounds.x, refreshBounds.y, null);
        }
        // set the shape on the board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

                    // Draw a border around the square
                    g.setColor(Color.BLACK); // Set the border color
                    g.drawRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    
                }
            }
        }


        // show game over
        if (state == STATE_GAME_OVER) {
            g.setColor(Color.white);
            g.setFont(new Font("Georgia", Font.BOLD, 40)); // Set the font size
            g.drawString("Game Over!!", BOARD_WIDTH / 2, (BLOCK_SIZE * BOARD_HEIGHT) / 2);

        }

        if (state == STATE_GAME_OVER && n == 1) {
            n=0;
            // Show a dialog asking for the player's name
            String playerName = JOptionPane.showInputDialog("Enter your name:");
            if (playerName != null && !playerName.isEmpty()) {
                // Create a new Player object and add it to the leaderboard
                Player player = new Player(playerName, score);
                MainBoard.addPlayerToLeaderboard(player);
            }
        }
        // show game pause
        if (state == STATE_GAME_PAUSE && countdown == 0) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);

            g.setFont(new Font("Georgia", Font.BOLD, 35)); // Set the font size
            g.drawString("Game Paused", BOARD_WIDTH / 2, (BLOCK_SIZE * BOARD_HEIGHT) / 2);

            if (stopBounds.contains(mouseX, mouseY)) {
                g.drawImage(pause.getScaledInstance(pause.getWidth() + 3, pause.getHeight() + 3, BufferedImage.SCALE_DEFAULT), stopBounds.x + 3, stopBounds.y + 3, null);
            } else {
                g.drawImage(pause, stopBounds.x, stopBounds.y, null);
            }
    
            if (refreshBounds.contains(mouseX, mouseY)) {
                g.drawImage(refresh.getScaledInstance(refresh.getWidth() + 3, refresh.getHeight() + 3,
                        BufferedImage.SCALE_DEFAULT), refreshBounds.x + 3, refreshBounds.y + 3, null);
            } else {
                g.drawImage(refresh, refreshBounds.x, refreshBounds.y, null);
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
        // show count down when unpause
        if (countdown > 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Georgia", Font.BOLD, 35));
            g.drawString("Resuming in " + countdown + "...", BOARD_WIDTH / 2, (BLOCK_SIZE * BOARD_HEIGHT) / 2);
        }
        // Draw the preview of the next shape
        showNextShapePreview(g);
        // Draw the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.BOLD, 15));
        g.drawString("Score: " + score, BOARD_WIDTH + 300, 200);
    }

    private void showNextShapePreview(Graphics g) {
        int[][] nextShapeCoords = nextShape.getCoords();
        Color nextShapeColor = nextShape.getColor();

        // Determine the position of the preview
        int previewX = BOARD_WIDTH + 325;
        int previewY = 50;

        for (int row = 0; row < nextShapeCoords.length; row++) {
            for (int col = 0; col < nextShapeCoords[row].length; col++) {
                if (nextShapeCoords[row][col] != 0) {
                    g.setColor(nextShapeColor);
                    g.fillRect(previewX + col * BLOCK_SIZE, previewY + row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.WHITE); // Set the border color
                    g.drawRect(previewX + col * BLOCK_SIZE, previewY + row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    public void startGame() {
        stopGame();
        state = STATE_GAME_PLAY;
        setNextShape();
        setCurrentShape();
        looper.start();
        checkoverGame();
        gameOver =false;
    }

    public void stopGame() {
        score = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = null;
            }
        }
        looper.stop();
    }

    public void increaseScore(int value) {
        score += value;
        MainBoard.playClearLineSound();
    }
    

    public Color[][] getBoard() {
        return board;
    }
    public int getState() {
        return state;
    }
    public int getScore() {
        return score;
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
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            if( state == STATE_GAME_PLAY){
            currentShape.rotateShape();
        } else return;
        } // clean the board
        else if (e.getKeyCode() == KeyEvent.VK_ENTER){  
            if (state == STATE_GAME_OVER){
                for (int row = 0; row < board.length; row++) {
                    for (int col = 0; col < board[row].length; col++) {
                        board[row][col] = null;
                    }
                }
                score = 0;
                setNextShape();
                setCurrentShape();
                state = STATE_GAME_PLAY;
             }
             else return;
        }
        // pause
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (state == STATE_GAME_PLAY) {
                state = STATE_GAME_PAUSE;
            } else if (state == STATE_GAME_PAUSE) {
                if (countdown == 0) {
                    countdown = 3;
                    new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            countdown--;
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (state == STATE_GAME_PLAY) {
            currentShape.immediatelyDrop();
            currentShape.update();
        } else return;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            currentShape.speedDown();
        }
    }
    class GameLooper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseX = e.getY();
    }






    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
