import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    public GameArea(){
        this.setSize(400,500);
        

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
    }
}
