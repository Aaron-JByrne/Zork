import java.util.HashMap;
public class Minimap {
    public static HashMap<String, Room> rooms = new HashMap<>();

    public Minimap(Room... inrooms) {
        for (Room room : inrooms) {
            this.rooms.put(room.getTitle(), room);
        }
    }

    public static double getTargetDirection(Room source, Room target){
        System.out.println(target.getX() + " " + target.getY());
        int yDif = target.getY() - source.getY();
        int xDif = target.getX() - source.getX();
        double result = Math.atan((double) yDif / xDif);
        Console.print( String.format("xdif - %d, ydif - %d", xDif, yDif));

        return result;
    }
}

