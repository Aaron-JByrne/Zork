import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private Map<String, String> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", "Move to another room");
        validCommands.put("quit", "End the game");
        validCommands.put("help", "Show help");
        validCommands.put("look", "Look around");
        validCommands.put("eat", "Eat something");
        validCommands.put("tp", "teleport");
        validCommands.put("where", "where am I");
        validCommands.put("open", "open an inventory");
        validCommands.put("take", "take an item");
        validCommands.put("drop", "drop an item from your inventory");
        validCommands.put("save", "saves states of rooms");
        validCommands.put("fight", "starts a fight with selected character");
    }

    public boolean isCommand(String commandWord) {
        return validCommands.containsKey(commandWord);
    }

    public void showAll() {
        //System.out.print("Valid commands are: ");
        Console.print("Valid commands are: ");
        for (String command : validCommands.keySet()) {
            //System.out.print(command + " ");
            Console.print(command + " ");
        }
        System.out.println();
    }
}
