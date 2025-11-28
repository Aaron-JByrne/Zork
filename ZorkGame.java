/* This game is a classic text-based adventure set in a university environment.
   The player starts outside the main entrance and can navigate through different rooms like a 
   lecture theatre, campus pub, computing lab, and admin office using simple text commands (e.g. "go east", "go west").
    The game provides descriptions of each location and lists possible exits.

Key features include:
Room navigation: Moving among interconnected rooms with named exits.
Simple command parser: Recognises a limited set of commands like "go", "help", and "quit".
Player character: Tracks current location and handles moving between rooms.
Text descriptions: Provides immersive text output describing the player's surroundings and available options.
Help system: Lists valid commands to guide the player.
Overall, it recreates the classic Zork interactive fiction experience with a university-themed setting, 
emphasising exploration and simple command-driven gameplay
*/


import java.util.ArrayList;
import java.util.List;

public class ZorkGame {
    private Parser parser;
    private Player player;
    private GameController controller;
    private GameState state;
    private Battle currentBattle;
    private Arrow arrow;

    private static ZorkGame instance = null;

    public ZorkGame(GameController controller) {
        this.state = GameState.EXPLORATION;
        this.controller = controller;
        controller.setModel(this);
        instance = this;
        createRooms();
        parser = new Parser();
        //guiParser = new Parser();
    }

    public Arrow getArrow(){
        return arrow;
    }

    public static ZorkGame getInstance(){
        return instance;
    }
    public static GameController getController(){
        return instance.controller;
    }

    private void createRooms() {
        Room Outside, Forest, forestClearing;

        Ability firestarter = new Ability("firestarter", "sets opponent ablaze", 40, 4);
        Disc firestarterDisc = new Disc(firestarter);


        // create rooms
        Outside = new Room("Outside", "You are outside", 0, 0);
        Forest = new Room("Forest", "You are in the forest.", 0, -1);
        forestClearing = new Room("Clearing", "you enter a clearing in the forest", 1, -1, firestarterDisc);

        new Minimap(Outside, Forest, forestClearing);

        // initialise room exits
        Outside.setExit(Dir.SOUTH, Forest);
        Forest.setExit(Dir.NORTH, Outside);
        Forest.setExit(Dir.EAST, forestClearing);
        forestClearing.setExit(Dir.WEST, Forest);

        // create the player character and start outside


        List<Item> playerInventory = new ArrayList<>();

        player = new Player("player", Outside, playerInventory);
        player.setActiveAbilities(new Ability[]{null, null, null, null});

        this.arrow = new Arrow();
        arrow.setTargetRoom(forestClearing);
        playerInventory.add(arrow);

        Ability tabulaRasa = new Ability("tabulaRasa", "blank slate", 40, 4);
        Item tabulaRasaCD = new Disc(tabulaRasa);
        ArrayList<Item> enemyInventory = new ArrayList<>();
        enemyInventory.add(tabulaRasaCD);

//        Ability punch = new Ability("punch", "punch", 15, 5);
        Ability takyon = new Ability("takyon", "an attack that moves faster then the speed of light", 5, 10);
        Character enemy = new Character("enemy", Forest, enemyInventory, 1);
        enemy.setActiveAbilities(new Ability[]{takyon, null, null, null});
//        enemy.addAbility(takyon);

    }

    public void play() {
        printWelcome();

//        boolean finished = false;
//        while (!finished) {
//            Command command = parser.getCommand();
//            finished = processCommand(command);
//        }

        //System.out.println("Thank you for playing. Goodbye.");
        //Console.print("Thank you for playing. Goodbye.");
    }

    private void printWelcome() {
//        System.out.println();
//        System.out.println("Welcome to the University adventure!");
//        System.out.println("Type 'help' if you need help.");
//        System.out.println();
//        System.out.println(player.getCurrentRoom().getTitle());

        Console.print("You were struck with an arrow, the arrow glows mysteriously,\n    as you remove the arrow you feel a strange power and the arrow seems to point south");
        Console.print("Type 'help' if you need help.");
        player.getCurrentRoom().describe();
    }

    public GameState getState(){
        return this.state;
    }

    public GameState processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            //System.out.println("I don't understand your command...");
            Console.print("I don't understand your command...");
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "quit":
                Console.print("quit??");
            case "tp":
                //System.out.printf("Teleporting to %s\n", command.getSecondWord());
                Console.print("Teleporting to " + command.getSecondWord());

                if (command.hasSecondWord()) {
                    player.setCurrentRoom(Minimap.rooms.get(command.getSecondWord()));
                }
                break;
            case "where":
                //System.out.printf("You are in %s\n", player.getCurrentRoom().getTitle());
                Console.print("You are in " + player.getCurrentRoom().getTitle());
                break;
            case "look":
                player.getCurrentRoom().displayInventory();
                break;
            case "open":
                if (command.hasSecondWord()) {
                    Inventory.displayInventory(command.getSecondWord());
                }
                else{
                    //System.out.println("open what?");
                    Console.print("open what?");
                }
                break;
            case "take":
                if (command.hasSecondWord() && player.getCurrentRoom().getInventory().hasItem(command.getSecondWord())) {
                    //System.out.println("Taking " + command.getSecondWord());
                    Console.print("Taking " + command.getSecondWord());
                    player.getCurrentRoom().getInventory().sendItem(player.getInventory(), player.getCurrentRoom().getInventory().getItem(command.getSecondWord()));
                    }else{
                    //System.out.println("take what?");
                    Console.print("take what?");
                }
                break;
            case "drop":
                if (command.hasSecondWord() && player.getInventory().hasItem(command.getSecondWord())) {
                    //System.out.println("Dropping " + command.getSecondWord());
                    Console.print("Dropping " + command.getSecondWord());
                    player.getInventory().sendItem(player.getCurrentRoom().getInventory(), player.getInventory().getItem(command.getSecondWord()));
                } else{
                    //System.out.println("drop what?");
                    Console.print("drop what?");
                }
                break;
            case "save":
                Serializer serializer = new Serializer();
                serializer.write(player.getCurrentRoom());
                break;
            case "fight":
                if (command.hasSecondWord() && player.getCurrentRoom().hasCharacter(command.getSecondWord())) {
                    Character target = player.getCurrentRoom().getCharacter(command.getSecondWord());
                    this.state = GameState.FIGHT;
                    Battle battle = new Battle(player, target);
                    this.setBattle(battle);

                } else{
                    Console.print("fight who?");
                }
                break;
            case "use":
                if (command.hasSecondWord() && player.getInventory().hasItem(command.getSecondWord())) {
                    player.getInventory().getItem(command.getSecondWord()).use();
                }else{
                    Console.print("use what?");
                }
                break;
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return this.state;
    }

    public Battle getBattle(){
        if (this.state == GameState.FIGHT) {
            return this.currentBattle;
        }else {
            return null;
        }
    }

    public void setBattle(Battle battle){
        this.currentBattle = battle;
    }

    private void printHelp() {
        //System.out.println("You are lost. You are alone. You wander around the university.");
        //System.out.print("Your command words are: ");
        Console.print("You are lost. You are alone. You wander around the university.");
        Console.print("Your command words are: ");
        parser.showCommands();
    }

    public void onBattleEnd(){
        this.state = GameState.EXPLORATION;
        controller.updateState(this.state);
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            //System.out.println("Go where?");
            Console.print("Go Where?");
            return;
        }

        String directionString = command.getSecondWord();

        Dir direction = Dir.valueOf(directionString.toUpperCase());

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            //System.out.println("There is no door!");
            Console.print("There doesnt seem to be a way to go that direction.");
        } else {
            player.setCurrentRoom(nextRoom);
            nextRoom.describe();
            //System.out.println(player.getCurrentRoom().getTitle());
        }
    }

    public Player getPlayer(){
        return player;
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        ZorkGame game = new ZorkGame(gameController);
        GUI gui = new GUI(gameController);
        game.play();


        //Serializer serializer = new Serializer();
        //serializer.read("room1");
    }
}
