import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUI {
    private GameController controller;
    GameState state;
    JFrame frame = new JFrame("ZorkUL");

    JPanel panel = new JPanel();
    JLabel label = new JLabel();


    JButton attack1 = new JButton("attack 1");
    JButton attack2 = new JButton("attack 2");
    JButton attack3 = new JButton("attack 3");
    JButton attack4 = new JButton("attack 4");
    JButton[] abilityButtons = {attack1,attack2,attack3,attack4};

    static JTextField textField = new JTextField();

    static JTextArea consoleDisplay = new JTextArea();

    static JScrollPane scrollPane = new JScrollPane(consoleDisplay);

    public GUI(GameController controller) {
        this.state = GameState.EXPLORATION;
        this.controller = controller;
        controller.setView(this);

        //frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(textField, BorderLayout.SOUTH);

//        frame.add(consoleDisplay, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);



        frame.add(label, BorderLayout.NORTH);
        //frame.add(panel, BorderLayout.EAST);
        panel.setLayout(new GridLayout(2, 2));
        label.setText("");


        attack1.addActionListener(new abilityListener());
        attack2.addActionListener(new abilityListener());
        attack3.addActionListener(new abilityListener());
        attack4.addActionListener(new abilityListener());


        frame.setSize(500, 500);
        frame.setVisible(true);

        textField.setEditable(true);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleDisplay.append("> " + textField.getText() + "\n");
                controller.takeString(textField.getText());
                textField.setText("");
            }
        });

        consoleDisplay.setEditable(false);
    }

    public static void writeTo(String text){
        consoleDisplay.append(text + "\n");
        consoleDisplay.setCaretPosition(consoleDisplay.getDocument().getLength());
    }


    public void updateState(GameState state){
        this.state = state;
        if(this.state == GameState.FIGHT){
            frame.add(panel, BorderLayout.EAST);
            panel.add(attack1);
            panel.add(attack2);
            panel.add(attack3);
            panel.add(attack4);
        }
    }

    public void setAbilityButtons(String[] abilityNames){
        for(int i=0;i<abilityNames.length;i++){
            abilityButtons[i].setText(abilityNames[i]);
        }
    }

    class abilityListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JButton source = (JButton)e.getSource();
            controller.onAbilityClick(source.getText());
        }
    }
}
