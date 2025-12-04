public class Arrow extends Item{
    private Room targetRoom;
    private boolean active;
    private boolean hasTarget;

    Arrow(){
        super("Arrow", "A mysterious arrow");
        active = false;
    }

    public void use(){
        if (hasTarget){
            active = !active;
        }else {
            Console.print("No current goal");
            active = false;
        }
    }

    public double getAngle() {
        int yDif = targetRoom.getY() - ZorkGame.getInstance().getPlayer().getCurrentRoom().getY();
        int xDif = targetRoom.getX() - ZorkGame.getInstance().getPlayer().getCurrentRoom().getX();
        return Math.atan2(yDif, xDif);
    }

    public void deactivate(){
        active = false;
    }

    public boolean hasReachedTarget(){
        return ZorkGame.getInstance().getPlayer().hasBeenTo(targetRoom);
    }

    public boolean isActivated(){
        return active;
    }

    public void setTargetRoom(Room targetRoom){
        this.targetRoom = targetRoom;
        hasTarget = true;
        if(targetRoom != null) {
            System.out.printf("setting the target room to %s\n", targetRoom.getTitle());
        }
    }

    public void targetReached(){
        hasTarget = false;
        targetRoom = null;
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
        if(targetRoom == null){
            System.out.println("trying to get target room but its null");
        }
        return targetRoom;
    }
}
