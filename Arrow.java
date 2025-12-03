public class Arrow extends Item{
    private Room targetRoom;
    private boolean active;
    private boolean hasTarget;

    Arrow(){
        super("Arrow", "A mysterious arrow");
        active = false;
        System.out.println(targetRoom);
    }

    public void use(){
        if (hasTarget){
            active = !active;
        }else {
            System.out.println("no current goal");
            active = false;
        }
    }

    public double getAngle() {
//        System.out.println(target.getX() + " " + target.getY());
        Room room = ZorkGame.getInstance().getPlayer().currentRoom;
        int yDif = targetRoom.getY() - ZorkGame.getInstance().getPlayer().getCurrentRoom().getY();
        int xDif = targetRoom.getX() - ZorkGame.getInstance().getPlayer().getCurrentRoom().getX();
        double result = Math.atan2(yDif, xDif);
//        Console.print(String.format("xdif - %d, ydif - %d", xDif, yDif));
//        System.out.printf("xdif - %d, ydif - %d\n", xDif, yDif);
//        System.out.println((result*(180/Math.PI)));

        return result;
    }

    public void deactivate(){
        active = false;
    }

    public void activate(){
        active = true;
    }

    public boolean hasReachedTarget(){
        return ZorkGame.getInstance().getPlayer().currentRoom == targetRoom;

    }

    public boolean isActivated(){
        return active;
    }

    public void setTargetRoom(Room targetRoom){
        this.targetRoom = targetRoom;
        hasTarget = true;
        if(targetRoom != null) {
            System.out.printf("setting te targetroom to %s\n", targetRoom.getTitle());
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
        return targetRoom;
    }

}
