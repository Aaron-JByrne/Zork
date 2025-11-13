import javax.swing.*;


public class GUI {
    JFrame frame = new JFrame("ZorkUL");
    JPanel panel = new JPanel();
    public GUI() {
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
