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
    private Character player;

    public ZorkULGame() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office, classroom;
        Room room1, room2, room3, room4, room5, room6;

        ArrayList<Item> outsideItems = new ArrayList<>();
        Item testitem = new Item("testitem", "this is a test item");
        outsideItems.add(testitem);
        Inventory outsideInventory = new Inventory("Outside", outsideItems);

        // create rooms
        room1 = new Room("room1");
        room2 = new Room("room2");
        room3 = new Room("room3");
        room4 = new Room("room4");
        room5 = new Room("room5");
        room6 = new Room("room6");

        new Minimap(room1,room2,room3,room4,room5,room6);

        // initialise room exits
        room1.setExit("east", room3);
        room1.setExit("south", room4);
        room1.setExit("west", room5);
        room1.setExit("north", room2);

        room3.setExit("west", room1);

        room5.setExit("east", room1);

        room4.setExit("east", room6);
        room4.setExit("north", room1);

        room6.setExit("west", room4);

        room2.setExit("south", room1);

        // create the player character and start outside
        Item torch = new Item("torch", "a torch");
        ArrayList<Item> playerInventory = new ArrayList<>();
        playerInventory.add(torch);
        player = new Character("player", room1, playerInventory);
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
        System.out.println("Thank you for playing. Goodbye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the University adventure!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getTitle());
    }

    private boolean processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            System.out.println("I don't understand your command...");
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
                    System.out.println("Quit what?");
                    return false;
                } else {
                    return true; // signal to quit
                }
            case "tp":
                System.out.printf("Teleporting to %s\n", command.getSecondWord());
                if (command.hasSecondWord()) {
                    player.setCurrentRoom(Minimap.rooms.get(command.getSecondWord()));
                }
                break;
            case "where":
                System.out.printf("You are in %s\n", player.getCurrentRoom().getTitle());
                break;
            case "look":
                player.getCurrentRoom().displayInventory();
                break;
            case "open":
                if (command.hasSecondWord()) {
                    Inventory.displayInventory(command.getSecondWord());
                }
                else{
                    System.out.println("open what?");
                }
                break;
            case "take":
                if (command.hasSecondWord()) {
                    System.out.println("Taking " + command.getSecondWord());
                    player.getCurrentRoom().getInventory().sendItem(player.getInventory(), player.getCurrentRoom().getInventory().getItem(command.getSecondWord()));
                }else{
                    System.out.println("take what?");
                }
                break;
            case "drop":
                if (command.hasSecondWord()) {
                    System.out.println("Dropping " + command.getSecondWord());
                    player.getInventory().sendItem(player.getCurrentRoom().getInventory(), player.getInventory().getItem(command.getSecondWord()));
                } else{
                    System.out.println("drop what?");
                }
                break;
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return false;
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander around the university.");
        System.out.print("Your command words are: ");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getTitle());
        }
    }

    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        GUI gui = new GUI();
        game.play();

    }
}
