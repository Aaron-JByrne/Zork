public class Arrow extends Item{
    private Room targetRoom;
    private Player player;

    Arrow(){
        super("Arrow", "A mysterious arrow");
        this.player = ZorkGame.getInstance().getPlayer();
        this.targetRoom = Minimap.rooms.get("Clearing");
    }

    public void use(){
        if (targetRoom != null){
            System.out.println("no current goal");
        }else{
            System.out.println(Minimap.getTargetDirection(player.getCurrentRoom(), targetRoom));
        }
    }

    public void setTargetRoom(Room targetRoom){
        this.targetRoom = targetRoom;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

}
