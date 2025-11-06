import java.util.ArrayList;

public class Inventory{
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
        System.out.println("Displaying inventory for " + name);
        for(Inventory inventory : Inventory.inventories){
            if(inventory.name.equals(name)){
                System.out.println("Inventory for " + name);
                inventory.displayInventory();
                break;
            }
        }
    }

    public void displayInventory(){
        if (items.isEmpty()) {
            System.out.println("No items");
        } else {
            System.out.printf("Items in %s:\n", this.name);
            //Do I keep as lambda expression?
            items.forEach(item -> System.out.println(item.getName()));
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
}
