import java.util.HashMap;
public class Minimap {
    public static HashMap<String, Room> rooms = new HashMap<>();

    public Minimap(Room... inrooms) {
        for (Room room : inrooms) {
            this.rooms.put(room.getTitle(), room);
        }
    }

    public static double getTargetDirection(Room source, Room target){
        return Math.atan((double) (source.getY() - target.getY()) / (source.getX() - target.getX()));
    }
}

