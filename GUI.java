import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;


public class GUI {
    private GameController controller;
    GameState state;
    JFrame frame = new JFrame("ZorkUL");

    JPanel fightPanel = new JPanel();
    JPanel fightInnerPanel = new JPanel();

    JPanel statPanel = new JPanel();
    JLabel lvlLabel = new JLabel();
    JLabel hpLabel = new JLabel();
    JLabel roomLabel = new JLabel();

    JLabel chooseAbilityLabel = new JLabel("Select the move you want to overwrite");



    JButton abilityButton0 = new JButton();
    JButton abilityButton1 = new JButton();
    JButton abilityButton2 = new JButton();
    JButton abilityButton3 = new JButton();

    JButton[] abilityButtons = {abilityButton0,abilityButton1,abilityButton2,abilityButton3};

    BufferedImage arrowImage;
    JLabel arrowLabel = new JLabel();

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

        frame.add(statPanel, BorderLayout.NORTH);
        statPanel.setLayout(new FlowLayout());
//        statPanel.setPreferredSize(new Dimension(200, 100));

        //frame.add(panel, BorderLayout.EAST);
        fightInnerPanel.setLayout(new GridLayout(2, 2));
        statPanel.add(hpLabel);
        statPanel.add(roomLabel);
        statPanel.add(lvlLabel);
        statPanel.add(arrowLabel);

        abilityButton0.addActionListener(new abilityListener(0));
        abilityButton1.addActionListener(new abilityListener(1));
        abilityButton2.addActionListener(new abilityListener(2));
        abilityButton3.addActionListener(new abilityListener(3));


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

        fightInnerPanel.add(abilityButton0);
        fightInnerPanel.add(abilityButton1);
        fightInnerPanel.add(abilityButton2);
        fightInnerPanel.add(abilityButton3);

        try{
            this.arrowImage = ImageIO.read(new File("arrow.png"));
//            this.arrowImage = rotate(arrowImage, Math.PI/2);
            this.arrowLabel.setIcon(new ImageIcon(arrowImage));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writeTo(String text){
        consoleDisplay.append(text + "\n");
        consoleDisplay.setCaretPosition(consoleDisplay.getDocument().getLength());
    }

    public void updateState(GameState state){
        System.out.printf("%s is current state\n request for %s\n",this.state,state);
        if(this.state != state) {
            this.state = state;
            System.out.println("state changed");
            if (this.state == GameState.FIGHT) {
                frame.add(fightPanel, BorderLayout.EAST);
                fightPanel.add(fightInnerPanel, BorderLayout.SOUTH);
            }
            if (this.state == GameState.EXPLORATION) {
                frame.remove(fightPanel); //panel is removed from the frame and the heirarchy
                frame.revalidate(); //revalidates the heirarchy
            }
        }
    }

    public void setAbilityButtons(String[] abilityNames){
        for(int i=0;i<abilityNames.length;i++){
            abilityButtons[i].setText(abilityNames[i]);
        }
    }

    public void updateAbilityButtons(boolean[] abilityBooleanToggles){
        for(int i=0;i<abilityBooleanToggles.length;i++){
            abilityButtons[i].setEnabled(abilityBooleanToggles[i]);
        }
    }

    class abilityListener implements ActionListener{
        private int index;

        abilityListener(int index){
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            JButton source = (JButton) e.getSource();
            if (state == GameState.FIGHT) {
//                controller.onAbilityClick(source.getText());
            }else
            if(state == GameState.EXPLORATION){
                //if state is exploration, it should be because the player is selecting abilities,
//                controller.updateSelectedAbilities(index);
                frame.remove(fightPanel);
                fightPanel.remove(chooseAbilityLabel);
                frame.revalidate();
            }

            controller.onAbilityClick(index);
        }
    }

    public void updateHP(int hp){
        hpLabel.setText("HP: " + hp);
    }

    public void updateRoom(String roomName){
        roomLabel.setText("\uD83D\uDCCD"+roomName);
    }

    public void updateLevel(int level){
        lvlLabel.setText("Level: " + level);
    }

    public void showAbilityIndexSelector(String abilityName){
        frame.add(fightPanel, BorderLayout.EAST);
        fightPanel.add(chooseAbilityLabel,BorderLayout.NORTH);
        fightPanel.add(fightInnerPanel,BorderLayout.CENTER);
        frame.revalidate();
    }

    public void updateArrow(double theta){
        arrowLabel.setIcon(new ImageIcon(rotate(arrowImage, theta)));
    }

    public BufferedImage rotate(BufferedImage img, double theta){
        theta = (-theta);
        //changing clockwise rotation to counterclockwise rotation
        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage imgRotated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imgRotated.createGraphics();
        g2d.rotate(theta, (double) width/2,(double) height/2 );
        g2d.drawImage(img, 0, 0, null);
        System.out.println(theta);

        return imgRotated;

    }
}
