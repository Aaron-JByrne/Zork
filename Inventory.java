import java.util.ArrayList;

public class Inventory implements java.io.Serializable{
    static ArrayList<Inventory> inventories = new ArrayList<>();

    private String name;
    private ArrayList<Item> items;

    public Inventory(String name){
        this.name = name;
        this.items = new ArrayList<>();
        Inventory.inventories.add(this);
    }

    public Inventory(String name, ArrayList<Item> defaultInventory){
        this.name = name;
        this.items = defaultInventory;
        Inventory.inventories.add(this);
    }

    public static void displayInventory(String name){
        for(Inventory inventory : Inventory.inventories){
            if(inventory.name.equals(name)){
                inventory.displayInventory();
                break;
            }
        }
    }

    public void displayInventory(){
        if (items.isEmpty()) {
            //System.out.println("No items");
            Console.print("No items");
        } else {
            //System.out.printf("Items in %s:\n", this.name);
            Console.print("Items in " + this.name + ":\n");
            //Do I keep as lambda expression?
            //items.forEach(item -> System.out.println(item.getName()));
            items.forEach(item -> Console.print(item.getName()));

        }
    }

    public void sendItem(Inventory destination, Item item){
        this.items.remove(item);
        destination.items.add(item);
    }

    public Item getItem(String name){
        for(Item item : items){
            if(item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }

    public boolean hasItem(String name){
        return getItem(name) != null;
    }
}
