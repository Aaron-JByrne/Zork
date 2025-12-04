import java.io.*;
import java.util.List;

public class SaveData implements Serializable {
    Player player;
    List<Room> rooms;
    QuestManager questManager;
    Arrow arrow;

    public SaveData(Player player, List<Room> rooms, QuestManager questManager, Arrow arrow){
        this.player = player;
        this.rooms = rooms;
        this.questManager = questManager;
        this.arrow = arrow;
    }

    public void save(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Savedata.ser"))) {
            out.writeObject(this);
        } catch (IOException e) {
            Console.print("Could not save game");
        }
    }

    public static SaveData load(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Savedata.ser"))) {
            return (SaveData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Console.print("Could not load Savedata");
        }
        return null;
    }

    public Player getPlayer(){
        return player;
    }

    public List<Room> getRooms(){
        return rooms;
    }

    public QuestManager getQuestManager(){
        return questManager;
    }

    public Arrow getArrow(){
        return arrow;
    }
}
