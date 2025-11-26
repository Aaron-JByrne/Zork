import java.util.HashMap;
public class Minimap {
    public static HashMap<String, Room> rooms = new HashMap<>();

    public Minimap(Room... inrooms) {
        for (Room room : inrooms) {
            this.rooms.put(room.getTitle(), room);
        }
    }


}

