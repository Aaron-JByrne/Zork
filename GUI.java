import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUI {
    ZorkULGame game;
    JFrame frame = new JFrame("ZorkUL");
    JPanel panel = new JPanel();
    JTextField textField = new JTextField("hello");

    static JTextArea consoleDisplay = new JTextArea();

    public GUI(ZorkULGame game) {
        this.game = game;
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.SOUTH);
        frame.add(consoleDisplay, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setVisible(true);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Console.print("You pressed enter");
            }
        });

        consoleDisplay.setEditable(false);
        }

        public static void writeTo(String text){
            consoleDisplay.append(text + "\n");
        }


    }
