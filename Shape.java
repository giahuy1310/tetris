import java.awt.*;
public class Shape {
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;

    private int x= 0, y =0;
    private int normal = 600;
    private int fast = 100;
    private int delayTimeForMovement = normal;
    private long beginTime;

    private int deltaX = 0;
    private boolean collision = false;

    private int [] [] coords;
    private GameArea board;
    public Color color;
    public Shape(int[][] coords, GameArea board, Color color){
        this.coords = coords;
        this.board = board;
        this.color = color;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void reset(){
        this.x = 4;
        this.y = 0;
        collision = false;
    }

    public void update(){
        if (collision){
            // fill color for the board
            for ( int row = 0; row < coords.length; row++){
                for (int col = 0; col < coords[0].length; col++){
                    if (coords[row][col] !=0){
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
        if (!(x + deltaX + coords[0].length > BOARD_WIDTH) && !(x + deltaX < 0)){
            for (int row = 0; row < coords.length; row++){
                for (int col = 0; col < coords[row].length; col++){
                    if (coords[row][col] != 0){
                        if (board.getBoard()[y + row][x + deltaX + col] != null){
                            moveX = false;
                        }
                    }
                }
            }
            if (moveX) {
              x+= deltaX;  
            }
        }
        deltaX = 0;
        if (System.currentTimeMillis() - beginTime > delayTimeForMovement){
            // vertical movement
            if (!(y + 1 + coords.length > BOARD_HEIGHT)){
                for (int row = 0 ; row < coords.length; row++){
                    for (int col = 0; col < coords[row].length; col++){
                        if (coords[row][col] != 0){
                            if (board.getBoard()[y + row + 1][x + deltaX + col] != null){
                                collision = true;
                            }
                        }
                    }
                }
                if (!collision){
                y++;
                }
            } 
            else {
                collision = true;
            }
        
            beginTime = System.currentTimeMillis();
        }
    }
    private void checkLine(){
        int bottomLine = board.getBoard().length - 1;
        for (int topLine = board.getBoard().length -1; topLine > 0; topLine--){
            int count = 0;
            for ( int col = 0; col < board.getBoard()[0].length; col++){
                if (board.getBoard()[topLine][col] != null){
                    count++;
                }
                board.getBoard()[bottomLine][col] = board.getBoard()[topLine][col];
            }
            if (count < board.getBoard()[0].length){
                bottomLine--;
            }
        }
    }
    public void render(Graphics g){
        //draw the shape
        for (int row = 0; row< coords.length; row++){
            for (int col  = 0; col<coords[0].length; col++){
                if (coords[row][col]!=0){
                    g.setColor(this.color);
                g.fillRect(col * BLOCK_SIZE + x * BLOCK_SIZE , row * BLOCK_SIZE + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

        }
    }
}
    // 
    }
    public void speedUp(){
        delayTimeForMovement = fast;
    }
    public void speedDown(){
        delayTimeForMovement = normal;
    }
    public void moveLeft(){
        deltaX = -1;
    }
    public void moveRight(){
        deltaX = 1;
    }
}
