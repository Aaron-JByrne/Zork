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


import java.sql.Array;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class ZorkULGame {
    private Parser parser;
    private Parser guiParser;
    private Character player;

    public ZorkULGame() {
        createRooms();
        parser = new Parser();
        guiParser = new Parser();
    }

    private void createRooms() {
        Room Outside, Forest, Hut;
        //Room room1, room2, room3, room4, room5, room6;

        ArrayList<Item> outsideItems = new ArrayList<>();
        Item testitem = new Item("testItem", "this is a test item");
        outsideItems.add(testitem);
        Inventory outsideInventory = new Inventory("Outside", outsideItems);

        // create rooms
        Outside = new Room("Outside");
        Forest = new Room("Forest");
        Hut = new Room("Hut");

        new Minimap(Outside, Forest, Hut);

        // initialise room exits
        Outside.setExit("south", Forest);
        Forest.setExit("north", Outside);
        Forest.setExit("east", Hut);
        Hut.setExit("west", Forest);

        // create the player character and start outside
        Item arrow = new Item("Arrow", "A mysterious arrow");
        ArrayList<Item> playerInventory = new ArrayList<>();
        playerInventory.add(arrow);
        player = new Character("player", Outside, playerInventory);
    }
    /*
    public void createRooms(){
        Room[] rooms = new Room[27];
        for (byte x=0,i=0;x<3;x++){
            for (byte y=0;y<3;y++){
                for (byte z=0;z<3;z++){
                    rooms[i++] = new Room("room"+i,x,y,z);
                }
            }
        }
        new Minimap(rooms);


        Item torch = new Item("torch", "a torch");
        ArrayList<Item> playerInventory = new ArrayList<>();
        playerInventory.add(torch);
        player = new Character("player", rooms[0], playerInventory);

    }*/

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        //System.out.println("Thank you for playing. Goodbye.");
        Console.print("Thank you for playing. Goodbye.");
    }

    private void printWelcome() {
//        System.out.println();
//        System.out.println("Welcome to the University adventure!");
//        System.out.println("Type 'help' if you need help.");
//        System.out.println();
//        System.out.println(player.getCurrentRoom().getTitle());

        Console.print("Welcome to the University adventure!");
        Console.print("Type 'help' if you need help.");
        Console.print(player.getCurrentRoom().getTitle());
    }

    private boolean processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            //System.out.println("I don't understand your command...");
            Console.print("I don't understand your command...");
            return false;
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    //System.out.println("Quit what?");
                    Console.print("Quit what?");
                    return false;
                } else {
                    return true; // signal to quit
                }
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
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return false;
    }

    private void printHelp() {
        //System.out.println("You are lost. You are alone. You wander around the university.");
        //System.out.print("Your command words are: ");
        Console.print("You are lost. You are alone. You wander around the university.");
        Console.print("Your command words are: ");
        parser.showCommands();
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
            System.out.println(player.getCurrentRoom().getTitle());
        }
    }

    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        //Serializer serializer = new Serializer();
        //serializer.read("room1");
        GUI gui = new GUI(game);
        game.play();
    }
}
