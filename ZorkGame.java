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

    private boolean waitingForRespawn;
    private boolean hasSeenDiscHint;

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
        waitingForRespawn = false;


        Room alley, vordhosbnStreet, warehouse, pulsewidthStreet, home, aisatsanaStreet, park, forest, mansionCourtyard, factory, clearing, entranceHall, balcony, grandHall;


        Ability firestarter = new Ability("firestarter", "the prodigy", 40, 4);
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
        mansionCourtyard = new Room("Mansion Courtyard", "you are at the entrance to the mansion", 2, 0);
        factory = new Room("Factory", "you are in an old Factory", 2, -2);
        clearing = new Room("Clearing", "you enter a clearing in the forest", -2, -2);
        entranceHall = new Room("Entrance hall", "you are in the main entrance hall of the mansion", 2, 1);
        balcony = new Room("Balcony", "you are on a balcony, this must be where the arrow that pierced you came from", 1,1);
        grandHall = new Room("Grand hall", "You enter the grand hall of the mansion", 2,2);

        new Minimap(alley, vordhosbnStreet, warehouse, pulsewidthStreet, home, aisatsanaStreet, park, forest, mansionCourtyard, factory, clearing, entranceHall, balcony, grandHall);


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

        aisatsanaStreet.setExit(Dir.NORTH, mansionCourtyard);
        mansionCourtyard.setExit(Dir.SOUTH, aisatsanaStreet);

        aisatsanaStreet.setExit(Dir.SOUTH, factory);
        factory.setExit(Dir.NORTH, aisatsanaStreet);

        forest.setExit(Dir.WEST, clearing);
        clearing.setExit(Dir.EAST, forest);

        balcony.setExit(Dir.EAST, entranceHall);
        entranceHall.setExit(Dir.WEST, balcony);


        this.arrow = new Arrow();
        arrow.setTargetRoom(warehouse);
        player = new Player("player", alley);
        player.setActiveAbilities(new Ability[]{null, null, null, null});


        player.getInventory().addItem(arrow);

        Ability tabulaRasa = new Ability("tabulaRasa", "earl sweatshirt", 40, 3);
        Item tabulaRasaCD = new Disc(tabulaRasa);

        Ability solace = new Ability("solace", "earl sweatshirt", -15, 10);
        Item solaceCD = new Disc(solace);


        this.questManager = new QuestManager(arrow);

//        Ability punch = new Ability("punch", "punch", 15, 1);
        Ability takyon = new Ability("takyon", "death grips", 25, 8);
        Character enemy = new Character("hostile", forest, 2, solaceCD);
        enemy.setActiveAbilities(new Ability[]{takyon, null, null, null});

        Ability diamondEyes = new Ability("diamondEyes", "deftones", 35, 5);
        Item diamondEyesCD = new Disc(diamondEyes);
        Character sentinel = new Character("sentinel", mansionCourtyard, 3, diamondEyesCD);
        sentinel.setActiveAbilities(new Ability[]{diamondEyes, null, null, null});

        Ability underASiege = new Ability("underASiege", "snow strippers", 50, 1);
        Item underASiegeCD = new Disc(underASiege);
        Character hostile = new Character("hostile", factory, 2, underASiegeCD);
        hostile.setActiveAbilities(new Ability[]{underASiege, takyon, null, null});

        Ability jasco = new Ability("jasco", "sepultura", 40, 1);
        Item jascoCD = new Disc(jasco);
        Character hostile2 = new Character("hostile", clearing, 2, jascoCD);
        hostile2.setActiveAbilities(new Ability[]{jasco, takyon, null, null});

        Ability ataraxia = new Ability("ataraxia", "team sleep", -100, 3);
        Item ataraxiaCD = new Disc(ataraxia);
        Character sentinel2 = new Character("sentinel", entranceHall, 3, ataraxiaCD);
        sentinel2.setActiveAbilities(new Ability[]{tabulaRasa, takyon, null, null});

        Ability sadeness = new Ability("sadeness", "enigma", 50, 1);
        Character boss = new Character("Boss",grandHall, 5);
        boss.setActiveAbilities(new Ability[]{sadeness, tabulaRasa, takyon, null});

    }

    public void play() {
        printWelcome();

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

        switch (commandWord) {
            case null:
                Console.print("I DONT UNDERSTAND");
                break;
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
                    if(player.getInventory().getItem(command.getSecondWord()) instanceof Disc){
                        tryShowDiscHint();
                    }
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
                    SaveData saveData = new SaveData(player, new ArrayList<>(Minimap.rooms.values()), this.questManager, arrow);
                    saveData.save();
                    Console.print("Game Saved.");
                }else{
                    Console.print("You may not rest here.");
                }
                break;
            case "load":
                if(saveFileExists()){
                    respawn();
                }else{
                    Console.print("No save file found");
                }
//                SaveData loaded = SaveData.load();
//                if(loaded == null){
//                    Console.print("No save file found");
//                    break;
//                }
//                this.player = loaded.getPlayer();
//                this.questManager = loaded.getQuestManager();
//                List<Room> rooms = loaded.getRooms();
//                Minimap.rooms.clear();
//                for(Room room : rooms){
//                    Minimap.rooms.put(room.getTitle(), room);
//                }
                break;
            case "read":
                if(command.hasSecondWord() && player.getInventory().hasItem(command.getSecondWord()) && player.getInventory().getItem(command.getSecondWord()) instanceof Disc) {
                    Console.print(((Disc) player.getInventory().getItem(command.getSecondWord())).readDisc());
                }else{
                    Console.print("read what?");
                }
                break;
            default:
                Console.print("I don't know what you mean............ default casee");
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

    private void setBattle(Battle battle){
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

    public boolean isWaitingForRespawn(){
        return waitingForRespawn;
    }

    public void onPlayerDeath(){
        waitingForRespawn = true;
        Console.print("You Died!");
        Console.print("Press enter in text field below to respawn");
    }

    public void respawn(){
        Console.clear();
        if(saveFileExists()){
            SaveData saveData = SaveData.load();
//            this.player = saveData.getPlayer();
            this.player.resetTo(saveData.getPlayer());
//            this.questManager = saveData.getQuestManager();
            this.questManager.resetTo(saveData.getQuestManager());
            this.arrow = saveData.getArrow();
            System.out.println(saveData.getArrow());
            System.out.printf("your arrow after loading has targetRoom %s\n", this.arrow.getTargetRoom().getTitle());
            List<Room> rooms = saveData.getRooms();
            Minimap.rooms.clear();
            for(Room room : rooms){
                Minimap.rooms.put(room.getTitle(), room);
            }
            Console.print("Your last save was restored.");
        }else{
            System.out.println("No save file found");
            initGame();
        }
        waitingForRespawn = false;
        controller.refreshUI();
    }

    private boolean saveFileExists(){
        File saveFile = new File("Savedata.ser");
        return saveFile.exists();
    }

    private void tryShowDiscHint(){
        if(!hasSeenDiscHint){
            Console.print("You picked up a mysterious disc, hint: try 'read' to see what ability it contains, and 'use' to equip it");
            hasSeenDiscHint = true;
        }
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
    }
}
