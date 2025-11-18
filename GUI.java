import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUI {
    ZorkGame game;
    JFrame frame = new JFrame("ZorkUL");
    JPanel panel = new JPanel();
    static JTextField textField = new JTextField();

    static JTextArea consoleDisplay = new JTextArea();

    public GUI(ZorkGame game) {
        this.game = game;
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.SOUTH);
        frame.add(consoleDisplay, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setVisible(true);

        textField.setEditable(true);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //textField.notify();
                //Console.print("You pressed enter");
            }
        });

        consoleDisplay.setEditable(false);
        }

        public static void writeTo(String text){
            consoleDisplay.append(text + "\n");
        }

        public static void getCommand(){
            textField.setEditable(true);//chopped code figure out
            Console.print("You pressed enter");
        }
    }
