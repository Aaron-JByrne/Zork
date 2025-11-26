public class Arrow extends Item{
    private Room targetRoom;

    Arrow(){
        super("Arrow", "A mysterious arrow");
    }

    public void use(){

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
