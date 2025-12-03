import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.List;


public class GUI {
    private GameController controller;
    GameState state;
    JFrame frame = new JFrame("ZorkUL");

    JPanel fightPanel = new JPanel();
    JPanel abilityButtonsPanel = new JPanel();
    JLabel enemyHPLabel = new JLabel("Enemy health");

    JPanel statPanel = new JPanel();

    JLabel lvlLabel = new JLabel();
    JLabel hpLabel = new JLabel();

    JLabel inventoryLabel = new JLabel("items in Inventory:");

    JLabel chooseAbilityLabel = new JLabel("Select the move you want to overwrite");

    JButton abilityButton0 = new JButton();
    JButton abilityButton1 = new JButton();
    JButton abilityButton2 = new JButton();
    JButton abilityButton3 = new JButton();

    JButton[] abilityButtons = {abilityButton0,abilityButton1,abilityButton2,abilityButton3};

    BufferedImage arrowImage;
    JLabel arrowLabel = new JLabel();

    JPanel westPanel = new JPanel();

    JPanel roomPanel = new JPanel();
    JLabel roomLabel = new JLabel();
    JLabel roomCharactersLabel = new JLabel();
    JLabel roomItemsLabel = new JLabel();

    JPanel movementButtonsPanel = new JPanel();
    JButton northButton = new JButton("North");
    JButton southButton = new JButton("South");
    JButton eastButton = new JButton("East");
    JButton westButton = new JButton("West");

    JPanel centerPanel = new JPanel();

    JButton struggleButton = new JButton("Struggle");

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

//        frame.add(textField, BorderLayout.SOUTH);

//        frame.add(consoleDisplay, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(textField, BorderLayout.SOUTH);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(statPanel, BorderLayout.NORTH);
        statPanel.setLayout(new FlowLayout());
//        statPanel.setPreferredSize(new Dimension(200, 100));

        //frame.add(panel, BorderLayout.EAST);
        fightPanel.setLayout(new BorderLayout());
//        fightPanel.add(enemyHPLabel);
        abilityButtonsPanel.setLayout(new GridLayout(2, 2));
        statPanel.add(hpLabel);
        statPanel.add(roomLabel);
        statPanel.add(lvlLabel);
        statPanel.add(arrowLabel);

        abilityButton0.addActionListener(new AbilityListener(0));
        abilityButton1.addActionListener(new AbilityListener(1));
        abilityButton2.addActionListener(new AbilityListener(2));
        abilityButton3.addActionListener(new AbilityListener(3));

        abilityButtonsPanel.add(abilityButton0);
        abilityButtonsPanel.add(abilityButton1);
        abilityButtonsPanel.add(abilityButton2);
        abilityButtonsPanel.add(abilityButton3);

        struggleButton.addActionListener(new AbilityListener(-1));

        westPanel.setLayout(new BorderLayout());
        movementButtonsPanel.setLayout(new GridLayout(3,3));
        frame.add(westPanel, BorderLayout.WEST);
        westPanel.add(movementButtonsPanel, BorderLayout.SOUTH);
        movementButtonsPanel.add(new JLabel(""));
        movementButtonsPanel.add(northButton);
        movementButtonsPanel.add(new JLabel(""));
        movementButtonsPanel.add(westButton);
        movementButtonsPanel.add(new JLabel(""));
        movementButtonsPanel.add(eastButton);
        movementButtonsPanel.add(new JLabel(""));
        movementButtonsPanel.add(southButton);
        movementButtonsPanel.add(new JLabel(""));

        northButton.addActionListener(new movementListener());
        southButton.addActionListener(new movementListener());
        eastButton.addActionListener(new movementListener());
        westButton.addActionListener(new movementListener());

        westPanel.add(roomPanel, BorderLayout.CENTER);
        westPanel.add(inventoryLabel, BorderLayout.NORTH);
        roomPanel.setLayout(new FlowLayout());
        roomPanel.add(roomLabel);
        roomPanel.add(roomCharactersLabel);
        roomPanel.add(roomItemsLabel);

        frame.setSize(1000, 600);
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

        try{
            this.arrowImage = ImageIO.read(new File("arrow.png"));
//            this.arrowImage = rotate(arrowImage, Math.PI/2);
            this.arrowLabel.setIcon(new ImageIcon(arrowImage));
            this.arrowLabel.setEnabled(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void writeTo(String text){
        consoleDisplay.append(text + "\n");
        consoleDisplay.setCaretPosition(consoleDisplay.getDocument().getLength());
    }

    public void updateState(GameState state){
//        System.out.printf("%s is current state\n request for %s\n",this.state,state);
        if(this.state != state) {
            this.state = state;
//            System.out.println("state changed");
            if (this.state == GameState.FIGHT) {
                frame.add(fightPanel, BorderLayout.EAST);
                fightPanel.add(abilityButtonsPanel, BorderLayout.CENTER);
                fightPanel.add(enemyHPLabel, BorderLayout.NORTH);
            }
            if (this.state == GameState.EXPLORATION) {
                frame.remove(fightPanel); //panel is removed from the frame and the heirarchy
                frame.revalidate(); //revalidates the heirarchy
            }
        }
    }

    public void setAbilityButtons(Ability[] abilities){
        for(int i=0;i<4;i++){
            if(abilities[i] == null){
                abilityButtons[i].setText("");
                continue;
            }
            abilityButtons[i].setText("<html>"+abilities[i].getName()+"<small>"+abilities[i].getCurrentUses()+"/"+abilities[i].getInitialUses()+"<br>"+abilities[i].getAbilityInfo()+"</small></html>");
        }
    }

    public void updateAbilityButtons(boolean[] abilityBooleanToggles){
        for(int i=0;i<abilityBooleanToggles.length;i++){
            abilityButtons[i].setEnabled(abilityBooleanToggles[i]);
        }
    }

    class AbilityListener implements ActionListener{
        private int index;

        AbilityListener(int index){
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e){
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

    class movementListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            controller.onMovementButton(source.getText());
        }
    }

    public void updateHP(int hp){
        hpLabel.setText("HP: " + hp);
    }

    public void updateEnemyHP(int hp){
        enemyHPLabel.setText("HP: " + hp);
    }

    public void updateRoom(String roomName){
        roomLabel.setText("\uD83D\uDCCD"+roomName);
    }

    public void updateLevel(int level){
        lvlLabel.setText("Level: " + level);
    }

    public void showAbilityIndexSelector(String abilityName){
        updateAbilityButtons(new boolean[]{true, true, true, true});
        frame.add(fightPanel, BorderLayout.EAST);
        fightPanel.remove(enemyHPLabel);
        fightPanel.add(chooseAbilityLabel,BorderLayout.NORTH);
        fightPanel.add(abilityButtonsPanel,BorderLayout.CENTER);
        frame.revalidate();
    }

    public void updateArrow(double theta){
        arrowLabel.setIcon(new ImageIcon(rotate(arrowImage, theta)));
    }

    public void enableArrow(){
        arrowLabel.setEnabled(true);
    }
    public void disableArrow(){
        arrowLabel.setEnabled(false);
    }

    public void updateRoomCharacters(List<Character> characters){
        updateList(roomCharactersLabel, characters, "Characters in room");
    }

    public void updateRoomItems(List<Item> items){
        updateList(roomItemsLabel, items, "Items in room");
    }

    public void updateInventory(List<Item> items){
        updateList(inventoryLabel, items, "Items in Inventory");
    }

    public void showStruggleButton(){
        fightPanel.add(struggleButton, BorderLayout.SOUTH);
    }

    private <T extends Nameable> void updateList(JLabel listLabel, List<T> elements, String listTitle){
        String listString = "<html>" + listTitle + ": <ul>";
        if(elements.isEmpty()){
            listString += "<li><i>None</i></li></ul></html>";
            listLabel.setText(listString);
            return;
        }
        for(T element : elements){
            listString += "<li>" + element.getName() + "</li>";
        }
        listString += "</ul></html>";
        listLabel.setText(listString);
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
//        System.out.println(theta);

        return imgRotated;

    }
}
