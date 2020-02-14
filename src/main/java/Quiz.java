import views.Home;

import javax.swing.*;
import java.io.IOException;


public class Quiz {
    public static void main(String[] args) throws IOException {
        // Impostiamo il nome del frame
        JFrame f = new JFrame("Quiz");

        Home home = new Home();
        f.add(home);
        f.setResizable(false);
        f.setSize(650, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }


}
