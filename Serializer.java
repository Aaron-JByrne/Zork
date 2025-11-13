import java.io.*;

public class Serializer {
    public void write(Room inRoom){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(String.format("%s.ser", inRoom.getTitle())))) {
            out.writeObject(inRoom);
            System.out.println("Object has been serialized to " + String.format("%s.ser", inRoom.getTitle()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(String roomName){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(String.format("%s.ser", roomName)))) {
            Room deserializedRoom = (Room) in.readObject();
            System.out.println("Object has been deserialized:");
            deserializedRoom.getTitle();
            deserializedRoom.getInventory().displayInventory();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
