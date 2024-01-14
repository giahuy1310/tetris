import javax.swing.*;
import java.awt.*;
import java.util.List;

class MainBoard extends JFrame {
    private static LeaderBoard leaderboard;

    private JFrame frame;
    private Title title;
    public  static final int  width = 445, height = 625;
    private GameArea board = new GameArea();

    private static AudioPlayer audio = new AudioPlayer();

    public static void playBackgroundMusic() {
        audio.playBackground();
    }

    public static void playClearLineSound() {
        audio.playClearLine();
    }
    public static void loadLeaderBoard() {
        leaderboard.load();
    }

    public MainBoard() {

        leaderboard = new LeaderBoard();
        playBackgroundMusic();

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
    public void startGame(){
        frame.remove(title);
        frame.add(board);
        board.startGame();
        frame.revalidate();
        frame.addMouseListener(board);
        frame.addMouseMotionListener(board);

    }
    public static void displayLeaderboard() {
        // Create a new JFrame to display the leaderboard
        JFrame leaderboardFrame = new JFrame("Leaderboard");
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setSize(300, 200);
        leaderboardFrame.setLocationRelativeTo(null);
    
        // Get the top 10 players
        List<Player> topPlayers = leaderboard.getTopPlayers(10);
    
        // Create a 2D array to hold the player data
        String[][] playerData = new String[topPlayers.size()][2];
        for (int i = 0; i < topPlayers.size(); i++) {
            playerData[i][0] = topPlayers.get(i).getName();
            playerData[i][1] = String.valueOf(topPlayers.get(i).getScore());
        }
    
        // Create a table to display the player data
        String[] columnNames = {"Player Name", "Score"};
        JTable table = new JTable(playerData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        leaderboardFrame.add(scrollPane, BorderLayout.CENTER);
    
        // Display the leaderboard
        leaderboardFrame.setVisible(true);
    }
    // Add a player to the leaderboard
    public static void addPlayerToLeaderboard(Player player) {
        leaderboard.addPlayer(player);
        displayLeaderboard();
    }
    
    // Get the current game state
    public int getGameState() {
        return board.getState();
    }
    
   
    
    public static void main(String[] args){
        MainBoard main = new MainBoard();
        displayLeaderboard();
    }
}
