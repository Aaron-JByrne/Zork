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
    private Character player;
    private GameController controller;
    private GameState state;
    private Battle currentBattle;
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

    public static ZorkGame getInstance(){
        return instance;
    }

    private void createRooms() {
        Room Outside, Forest, Hut;

        ArrayList<Item> outsideItems = new ArrayList<>();
        Item testitem = new Item("testItem", "this is a test item");
        outsideItems.add(testitem);
        Inventory outsideInventory = new Inventory("Outside", outsideItems);

        // create rooms
        Outside = new Room("Outside", "You are outside", outsideInventory);
        Forest = new Room("Forest", "You are in the forest.");
        Hut = new Room("Hut", "you are in a hut");

        new Minimap(Outside, Forest, Hut);

        // initialise room exits
        Outside.setExit("south", Forest);
        Forest.setExit("north", Outside);
        Forest.setExit("east", Hut);
        Hut.setExit("west", Forest);

        // create the player character and start outside
        Item arrow = new Item("Arrow", "A mysterious arrow");
        List<Item> playerInventory = new ArrayList<>();
        playerInventory.add(arrow);
        player = new Character("player", Outside, playerInventory);
        Ability takyon = new Ability("takyon", "an attack that moves faster then the speed of light", 50);
        Ability firestarter = new Ability("firestarter", "sets opponent ablaze", 40);
        player.addAbility(firestarter);
        player.addAbility(takyon);
        player.setActiveAbilities(new Ability[]{firestarter, takyon, takyon, firestarter});

        Ability tabulaRasa = new Ability("tabulaRasa", "", 40);
        //Item cd = new Item("CD", "A mysterious CD");
        Item tabulaRasaCD = new CD(tabulaRasa);
        ArrayList<Item> enemyInventory = new ArrayList<>();
        enemyInventory.add(tabulaRasaCD);
        Ability punch = new Ability("punch", "punch", 40);
        Character enemy = new Character("enemy", Forest, enemyInventory, 1);
        enemy.addAbility(punch);
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

        Console.print("Welcome to the adventure!");
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

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            //System.out.println("There is no door!");
            Console.print("There is no door!");
        } else {
            player.setCurrentRoom(nextRoom);
            nextRoom.describe();
            //System.out.println(player.getCurrentRoom().getTitle());
        }
    }

    public Character getPlayer(){
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
