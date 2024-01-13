import java.awt.*;

public class Shape {
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;

    private int x = 4, y = 0;
    private int normal = 600;
    private int fast = 50;
    private int delayTimeForMovement = normal;
    private long beginTime;
    private long endTime;

    private int deltaX = 0;
    private boolean collision = false;

    private int[][] coords;
    private GameArea board;
    public final Color color;

    public Shape(int[][] coords, GameArea board, Color color) {
        this.coords = coords;
        this.board = board;
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void reset() {
        this.x = 4;
        this.y = 0;
        collision = false;
    }

    public void update() {
        if (collision) {
            // fill color for the board
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[0].length; col++) {
                    if (coords[row][col] != 0) {
                        board.getBoard()[y + row][x + col] = color;
                    }
                }
            }
            checkLine();
            // set current shape
            board.setCurrentShape();
            return;
        }
        // check movent horizontal
        boolean moveX = true;
        if (!(x + deltaX + coords[0].length > BOARD_WIDTH) && !(x + deltaX < 0)) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (board.getBoard()[y + row][x + deltaX + col] != null) {
                            moveX = false;
                        }
                    }
                }
            }
            if (moveX) {
                x += deltaX;
            }
        }
        deltaX = 0;
        endTime = System.currentTimeMillis();
        if (endTime - beginTime > delayTimeForMovement) {
            // vertical movement
            if (!(y + 1 + coords.length > BOARD_HEIGHT)) {
                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[row].length; col++) {
                        if (coords[row][col] != 0) {
                            if (board.getBoard()[y + row + 1][x + deltaX + col] != null) {
                                collision = true;
                            }
                        }
                    }
                }
                if (!collision) {
                    y++;
                }
            } else {
                collision = true;
            }

            beginTime = endTime;
        }
    }

    private void checkLine() {
        int bottomLine = board.getBoard().length - 1;
        for (int topLine = board.getBoard().length - 1; topLine > 0; topLine--) {
            int count = 0;
            for (int col = 0; col < board.getBoard()[0].length; col++) {
                if (board.getBoard()[topLine][col] != null) {
                    count++;
                }
                board.getBoard()[bottomLine][col] = board.getBoard()[topLine][col];
            }
            if (count < board.getBoard()[0].length) {
                bottomLine--;
            }

        }
    }

    // rotate shape
    public void rotateShape() {
        int[][] rotatedShape = transposeMatrix(coords);
        reverseRow(rotatedShape);
        // check right side and bottom
        if ((x + rotatedShape[0].length > BOARD_WIDTH) || (y + rotatedShape.length > BOARD_HEIGHT)) {
            return;
        }
        // check if collapse with other shape before rotate
        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0 && (board.getBoard()[y + row][x + col] != null)) {
                    return;

                }
            }
        }
        coords = rotatedShape;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                temp[col][row] = matrix[row][col];
            }
        }
        return temp;
    }

    private void reverseRow(int[][] matrix) {
        int middle = matrix.length / 2;
        for (int row = 0; row < middle; row++) {
            int[] temp = matrix[row];
            matrix[row] = matrix[matrix.length - row - 1];
            matrix[matrix.length - row - 1] = temp;
        }
    }

    public void render(Graphics g) {
        // draw the shape
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    g.setColor(this.color);
                    g.fillRect(col * BLOCK_SIZE + x * BLOCK_SIZE, row * BLOCK_SIZE + y * BLOCK_SIZE, BLOCK_SIZE,
                            BLOCK_SIZE);

                }
            }
        }
        // Draw a border around each square in the shape
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[row].length; col++) {
                if (coords[row][col] != 0) {
                    g.setColor(Color.BLACK); // Set the border color
                    g.drawRect((x + col) * BLOCK_SIZE, (y + row) * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
    }

    // get color
    public Color getColor() {
        return color;
    }

    public int[][] getCoords() {
        return coords;
    }

    public void speedUp() {
        delayTimeForMovement = fast;
    }

    public void speedDown() {
        delayTimeForMovement = normal;
    }

    public void immediatelyDrop() {
        while (!collision) {
            y++;
            // Check for collision with the board or other shapes
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (y + row + 1 >= BOARD_HEIGHT || board.getBoard()[y + row + 1][x + deltaX + col] != null) {
                            collision = true;
                        }
                    }
                }
            }
        }
    }

    public void moveLeft() {
        deltaX = -1;
    }

    public void moveRight() {
        deltaX = 1;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Shape getShape() {
        return this;
    }
}
