import java.io.Serializable;

public class QuestManager implements Serializable {
    private int stage;
    private Character firstEnemy;
    private boolean sentinel1Defeated;
    private boolean sentinel2Defeated;

    QuestManager(Arrow arrow){
        this.stage = 0;
//        this.arrow int= arrow;
    }

//    public void update(){
//        System.out.printf("current stage: %d\n", stage);
//        switch (stage){
//            //case 0 completes when player has at least one active ability
//            //spawns the enemy when completed and increments stage up
//            case 0:
//                boolean playerHasAbilities = false;
//                for(Ability ability : ZorkGame.getInstance().getPlayer().getActiveAbilities()){
//                    if(ability != null){
//                        playerHasAbilities = true;
//                        break;
//                    }
//                }
//                if(playerHasAbilities){
//                    this.firstEnemy = spawnFirstEnemy();
//                    stage++;
//                }
//                break;
//            //case 1 completes when player defeats first enemy
//            //sets the target room to Home when completed
//            case 1:
//                System.out.println("case 1");
//                if(firstEnemy.getHealth() <= 0){
//                    stage++;
//                    ZorkGame.getInstance().getArrow().setTargetRoom(Minimap.rooms.get("Home"));
//                }
//                break;
//            //case 2 completes when player has reached their home
//            //sets target room to Forest when completed
//            case 2:
//                if(ZorkGame.getInstance().getPlayer().hasBeenTo(Minimap.rooms.get("Home"))){
//                    System.out.println("questmanager: player has been to Home");
//                    stage++;
//                    ZorkGame.getInstance().getArrow().setTargetRoom(Minimap.rooms.get("Forest"));
//                }else{
//                    System.out.println("questmanager: player has yet to reach home");
//                }
//                break;
//            case 3:
//                if(!Minimap.rooms.get("Mansion Courtyard").hasCharacter("sentinel")){
//                    sentinel1Defeated = true;
//                    Minimap.rooms.get("Mansion Courtyard").setExit(Dir.NORTH, Minimap.rooms.get("Entrance hall"));
//                    Minimap.rooms.get("Entrance hall").setExit(Dir.SOUTH, Minimap.rooms.get("Mansion Courtyard"));
//                    stage++;
//                }
//                break;
//            case 4:
//                if(!Minimap.rooms.get("Entrance hall").hasCharacter("sentinel")){
//                    sentinel2Defeated = true;
//                    Minimap.rooms.get("Entrance hall").setExit(Dir.NORTH, Minimap.rooms.get("Grand hall"));
//                    Minimap.rooms.get("Grand hall").setExit(Dir.SOUTH, Minimap.rooms.get("Entrance hall"));
//                }
//            case 5:
//                if(!Minimap.rooms.get("Grand hall").hasCharacter("Boss")){
//                    Console.print("You defeated the boss!, you win!");
//                }
//                break;
//        }
//    }
    public void update(){
        System.out.printf("current stage: %d\n", stage);
        switch (stage){
            //case 0 completes when player has at least one active ability
            //spawns the enemy when completed and increments stage up
            case 0:
                boolean playerHasAbilities = false;
                for(Ability ability : ZorkGame.getInstance().getPlayer().getActiveAbilities()){
                    if(ability != null){
                        playerHasAbilities = true;
                        break;
                    }
                }
                if(playerHasAbilities){
                    this.firstEnemy = spawnFirstEnemy();
                    stage++;
                }
                break;
            //case 1 completes when player defeats first enemy
            //sets the target room to Home when completed
            case 1:
                System.out.println("case 1");
                if(firstEnemy.getHealth() <= 0){
                    stage++;
                    ZorkGame.getInstance().getArrow().setTargetRoom(Minimap.rooms.get("Home"));
                }
                break;
            //case 2 completes when player has reached their home
            //sets target room to Forest when completed
            case 2:
                if(ZorkGame.getInstance().getPlayer().hasBeenTo(Minimap.rooms.get("Home"))){
//                    System.out.println("questmanager: player has been to Home");
                    stage++;
                    ZorkGame.getInstance().getArrow().setTargetRoom(Minimap.rooms.get("Forest"));
                }
//                else{
//                    System.out.println("questmanager: player has yet to reach home");
//                }
                break;
            case 3:
                if(ZorkGame.getInstance().getPlayer().hasBeenTo(Minimap.rooms.get("Forest"))){
                    stage++;
                    ZorkGame.getInstance().getArrow().setTargetRoom(Minimap.rooms.get("Factory"));
                }
                break;
            case 4:
                if(ZorkGame.getInstance().getPlayer().hasBeenTo(Minimap.rooms.get("Factory"))){
                    stage++;
                    ZorkGame.getInstance().getArrow().setTargetRoom(Minimap.rooms.get("Mansion Courtyard"));
                }
                break;
            case 5:
                if(!Minimap.rooms.get("Mansion Courtyard").hasCharacter("sentinel")){
                    sentinel1Defeated = true;
                    Minimap.rooms.get("Mansion Courtyard").setExit(Dir.NORTH, Minimap.rooms.get("Entrance hall"));
                    Minimap.rooms.get("Entrance hall").setExit(Dir.SOUTH, Minimap.rooms.get("Mansion Courtyard"));
                    stage++;
                }
                break;
            case 6:
                if(!Minimap.rooms.get("Entrance hall").hasCharacter("sentinel")){
                    sentinel2Defeated = true;
                    Minimap.rooms.get("Entrance hall").setExit(Dir.NORTH, Minimap.rooms.get("Grand hall"));
                    Minimap.rooms.get("Grand hall").setExit(Dir.SOUTH, Minimap.rooms.get("Entrance hall"));
                }
            case 7:
                if(!Minimap.rooms.get("Grand hall").hasCharacter("Boss")){
                    Console.print("You defeated the boss!, you win!");
                }
                break;
         }
    }

    public void reapplyWorldChanges(){
        if(sentinel1Defeated){
            Minimap.rooms.get("Mansion Courtyard").setExit(Dir.NORTH, Minimap.rooms.get("Entrance hall"));
            Minimap.rooms.get("Entrance hall").setExit(Dir.SOUTH, Minimap.rooms.get("Mansion Courtyard"));
        }
        if(sentinel2Defeated){
            Minimap.rooms.get("Entrance hall").setExit(Dir.NORTH, Minimap.rooms.get("Grand hall"));
            Minimap.rooms.get("Grand hall").setExit(Dir.SOUTH, Minimap.rooms.get("Entrance hall"));
        }
    }

    public Character spawnFirstEnemy(){
        Ability entombed = new Ability("entombed", "deftones", 10, 5);
        Disc entombedDisc = new Disc(entombed);
        Character firstEnemy = new Character("Thug", Minimap.rooms.get("vordhosbn street"), 1, entombedDisc);
        firstEnemy.setActiveAbilities(new Ability[]{entombed, null, null, null});
        return firstEnemy;
    }

    public void resetTo(QuestManager newQuestManager){
        this.stage = newQuestManager.stage;
        this.sentinel1Defeated = newQuestManager.sentinel1Defeated;
        reapplyWorldChanges();
    }
}
