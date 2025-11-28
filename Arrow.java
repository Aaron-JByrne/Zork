public class Arrow extends Item{
    private Room targetRoom;
    private boolean active;
    private Player player;

    Arrow(){
        super("Arrow", "A mysterious arrow");
        this.player = ZorkGame.getInstance().getPlayer();
        active = false;
        this.targetRoom = Minimap.rooms.get("Clearing");
    }

    public void use(){
        if (targetRoom == null){
            System.out.println("no current goal");
            active = false;
        }else{
            active = !active;
            System.out.println(active);
        }
    }

    public double getAngle() {
//        System.out.println(target.getX() + " " + target.getY());
        Room room = player.currentRoom;
        int yDif = targetRoom.getY() - player.getCurrentRoom().getY();
        int xDif = targetRoom.getX() - player.getCurrentRoom().getX();
        double result = Math.atan((double) yDif / xDif);
        Console.print(String.format("xdif - %d, ydif - %d", xDif, yDif));
        System.out.printf("xdif - %d, ydif - %d\n", xDif, yDif);
        System.out.println((result*(180/Math.PI)));

        return result;
    }

    public void deactivate(){
        active = false;
    }

    public void activate(){
        active = true;
    }

    public boolean hasReachedTarget(){
        return player.currentRoom == targetRoom;
    }

    public boolean isActivated(){
        return active;
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


    public boolean hasTargetRoom(){
        return targetRoom != null;
    }

    public Room getTargetRoom(){
        return targetRoom;
    }

}
