import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ZorkGame {
    private Parser parser;
    private Player player;
    private GameController controller;
    private GameState state;
    private Battle currentBattle;
    private Arrow arrow;
    private QuestManager questManager;

    private static ZorkGame instance = null;

    public ZorkGame(GameController controller) {
        this.state = GameState.EXPLORATION;
        this.controller = controller;
        controller.setModel(this);
        instance = this;
        initGame();
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

    private void initGame() {
        Room alley, vordhosbnStreet, warehouse, pulsewidthStreet, home, aisatsanaStreet, park, forest;


        Ability firestarter = new Ability("firestarter", "sets opponent ablaze", 40, 4);
//        firstarter = new Ability("firestarter", "f", 2)
        Disc firestarterDisc = new Disc(firestarter);


        // create rooms
        alley = new Room("Alley", "you are in an Alley, graffiti covers the walls", 0, 0);
        vordhosbnStreet = new Room("vordhosbn street", "You are on vordhosbn street", 0, -1);
        warehouse = new Room("Warehouse", "you are in an abandoned warehouse", -1, -1, firestarterDisc);
        pulsewidthStreet = new Room("pulsewidth street", "you are on pulsewidth street", 1, -1);
        home = new Room("Home", "You are home, you may 'rest' here and replenish", 1, -2);
        aisatsanaStreet = new Room("aisatsana street", "you are on aisatsana street", 2, -1);
        park = new Room("Park", "you are in a park", 0, -2);
        forest = new Room("Forest", "you are in a forest",-1,-2);

        new Minimap(alley, vordhosbnStreet, warehouse, pulsewidthStreet, home, aisatsanaStreet, park, forest);


        // initialise room exits
        alley.setExit(Dir.SOUTH, vordhosbnStreet);
        vordhosbnStreet.setExit(Dir.NORTH, alley);

        vordhosbnStreet.setExit(Dir.WEST, warehouse);
        warehouse.setExit(Dir.EAST, vordhosbnStreet);

        vordhosbnStreet.setExit(Dir.EAST, pulsewidthStreet);
        pulsewidthStreet.setExit(Dir.WEST, vordhosbnStreet);

        home.setExit(Dir.NORTH, pulsewidthStreet);
        pulsewidthStreet.setExit(Dir.SOUTH, home);

        pulsewidthStreet.setExit(Dir.EAST, aisatsanaStreet);
        aisatsanaStreet.setExit(Dir.WEST, pulsewidthStreet);

        vordhosbnStreet.setExit(Dir.SOUTH, park);
        park.setExit(Dir.NORTH, vordhosbnStreet);

        forest.setExit(Dir.EAST, park);
        park.setExit(Dir.WEST, forest);



        player = new Player("player", alley);
        player.setActiveAbilities(new Ability[]{null, null, null, null});

        this.arrow = new Arrow();
        arrow.setTargetRoom(warehouse);
        player.getInventory().addItem(arrow);

        Ability tabulaRasa = new Ability("tabulaRasa", "blank slate", 40, 4);
        Item tabulaRasaCD = new Disc(tabulaRasa);

        Ability solace = new Ability("solace", "heals you for 15", -15, 10);
        Item solaceCD = new Disc(solace);

        this.questManager = new QuestManager(arrow);

//        Ability punch = new Ability("punch", "punch", 15, 5);
        Ability takyon = new Ability("takyon", "an attack that moves faster then the speed of light", 25, 8);
        Character enemy = new Character("hostile", forest, 2, solaceCD);
        enemy.setActiveAbilities(new Ability[]{takyon, null, null, null});
//        Character enemy = new Character("enemy", Forest, enemyInventory, 1);
//        enemy.setActiveAbilities(new Ability[]{takyon, null, null, null});
//        enemy.addAbility(takyon);

//        Character enemy = new Character("Bandit", Outside, 1);
//        enemy.setActiveAbilities(new Ability[]{takyon, null, null, null});
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
        Console.print("You were struck with an arrow, the arrow glows mysteriously,\nYou remove the arrow and place it into your Inventory.\nYou feel the arrow pulsing with energy.");
        Console.print("hint: type 'use Arrow' to activate it");
//        Console.print("Type 'help' if you need help.");
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
                Console.print("quit???????");
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
            case "fight":
                if(player.canAttack()) {
                    if (command.hasSecondWord() && player.getCurrentRoom().hasCharacter(command.getSecondWord())) {
                        Character target = player.getCurrentRoom().getCharacter(command.getSecondWord());
                        this.state = GameState.FIGHT;
                        Battle battle = new Battle(player, target);
                        this.setBattle(battle);
                    } else {
                        Console.print("fight who?");
                    }
                }else{
                    Console.print("You dont have any abilities to use!");
                }
                break;
            case "use":
                if (command.hasSecondWord() && player.getInventory().hasItem(command.getSecondWord())) {
                    player.getInventory().getItem(command.getSecondWord()).use();
                }else{
                    Console.print("use what?");
                }
                break;
            case "rest":
                if(player.getCurrentRoom().getTitle().equals("Home")){
                    player.rest();
                    Console.print("Your health has been replenished, and your ability uses have been refilled");
                    SaveData saveData = new SaveData(player, new ArrayList<>(Minimap.rooms.values()), this.questManager);
                    saveData.save();
                    Console.print("Game Saved.");
                }else{
                    Console.print("You may not rest here.");
                }
                break;
            case "load":
                SaveData loaded = SaveData.load();
                if(loaded == null){
                    Console.print("No save file found");
                    break;
                }
                this.player = loaded.getPlayer();
                this.questManager = loaded.getQuestManager();
                List<Room> rooms = loaded.getRooms();
                Minimap.rooms.clear();
                for(Room room : rooms){
                    Minimap.rooms.put(room.getTitle(), room);
                }
                break;
            default:
                Console.print("I don't know what you mean...");
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

    public void onPlayerDeath(){
        if(saveFileExists()){
            SaveData saveData = SaveData.load();
            this.player = saveData.getPlayer();
            this.questManager = saveData.getQuestManager();
            List<Room> rooms = saveData.getRooms();
            Minimap.rooms.clear();
            for(Room room : rooms){
                Minimap.rooms.put(room.getTitle(), room);
            }
        }else{
            System.out.println("No save file found");
            initGame();
        }
    }

    public boolean saveFileExists(){
        File saveFile = new File("Savedata.ser");
        return saveFile.exists();
    }

    public QuestManager getQuestManager(){
        return this.questManager;
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

        System.out.println(game.saveFileExists());

        //Serializer serializer = new Serializer();
        //serializer.read("room1");
    }
}
