public class GameController{
    private ZorkGame model;
    private GUI view;
    private Parser parser;
    private GameState state;

    public GameController(){
        this.parser = new Parser();
    }

    public void setModel(ZorkGame model){
        this.model = model;
    }
    public void setView(GUI view){
        this.view = view;
        refreshUI();
    }

    public void takeString(String input){
        model.getQuestManager().update();
        Command command = parser.getCommand(input);
        GameState state = model.processCommand(command);
        updateState(state);
        refreshUI();
    }

    public void onMovementButton(String direction){
        takeString(String.format("go %s", direction.toLowerCase()));
    }

//    public void onAbilityClick(String abilityName) {
//        if (model.getState() == GameState.FIGHT) {
//            Battle activeBattle = model.getBattle();
//            activeBattle.performTurn(abilityName);
//        }
//
//        refreshUI();
//    }

    public void onAbilityClick(int index){
        if(model.getState() == GameState.FIGHT){
            model.getBattle().performTurn(index);
        } else if (model.getState() == GameState.EXPLORATION) {
            model.getPlayer().setAbilityByIndex(index);
        }
        refreshUI();
    }

    public void updateState(GameState gameState){
        view.updateState(gameState);
        this.state = gameState;
        if (state==GameState.FIGHT){

        }
//        {
//            String[] abilityNames = new String[4];
//            int i = 0;
//            for(Ability ability : model.getPlayer().getActiveAbilities()) {
//                if(ability != null){
//                    abilityNames[i++] = ability.getName();
//                }
//                else{
//                    abilityNames[i++] = "";
//                }
//            }
//            view.setAbilityButtons(abilityNames);
//        }

    }

    public void refreshUI(){
        view.updateHP(model.getPlayer().getHealth());
        view.updateRoom(model.getPlayer().getCurrentRoom().getTitle());
        view.updateLevel(model.getPlayer().getLevel());
        view.updateRoomCharacters(model.getPlayer().getCurrentRoom().getCharacters());
        view.updateRoomItems(model.getPlayer().getCurrentRoom().getInventory().getItems());

        if(state == GameState.FIGHT){
            boolean[] usableAbilites = new boolean[4];
            int i = 0;
            for(int count : model.getPlayer().getUses()){
                usableAbilites[i++] = count > 0;
//                System.out.println(usableAbilites[i-1]);
//                System.out.println(i);
            }
            view.updateEnemyHP(model.getBattle().getNPC().getHealth());
            view.updateAbilityButtons(usableAbilites);
            view.setAbilityButtons(model.getPlayer().getActiveAbilities());
        }else if(state == GameState.EXPLORATION){
            if(model.getArrow().hasTargetRoom()){
                if(model.getArrow().isActivated()) {
                    view.enableArrow();

                    if(model.getArrow().hasReachedTarget()){
                        model.getArrow().deactivate();
                        view.disableArrow();
                        model.getArrow().setTargetRoom(null);
//                        System.out.println("Arrow has reached target room");
                    }else {
                        view.updateArrow(model.getArrow().getAngle());
                    }

                }else{
                    view.disableArrow();
                }
            }
        }
    }

    public void selectAbilityIndexRequest(Ability ability){
        view.showAbilityIndexSelector(ability.getName());
    }

//    public void updateSelectedAbilities(int index){
//        model.getPlayer().setAbilityByIndex(index);
//    }
}