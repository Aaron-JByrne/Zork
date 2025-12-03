import java.io.*;
import java.util.List;

public class SaveData implements Serializable {
    Player player;
    List<Room> rooms;
    QuestManager questManager;

    public SaveData(Player player, List<Room> rooms, QuestManager questManager){
        this.player = player;
        this.rooms = rooms;
        this.questManager = questManager;
    }

    public void save(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Savedata.ser"))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SaveData load(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Savedata.ser"))) {
            SaveData deserializedData = (SaveData) in.readObject();
            for(Ability ab : deserializedData.getPlayer().getActiveAbilities()){
                if(ab == null) continue;
                System.out.println(ab.getName());
            }
            return deserializedData;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
}
