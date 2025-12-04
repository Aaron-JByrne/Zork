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
        if(model.isWaitingForRespawn()){
            if(input.isBlank()) {
                model.respawn();
                view.setMovementButtonsEnabled(true);
                return;
            }else{
                Console.print("you're dead.");
            }
        }else {
            model.getQuestManager().update();
            Command command = parser.getCommand(input);
            GameState state = model.processCommand(command);
            updateState(state);
            refreshUI();
        }
    }

    public void onMovementButton(String direction){
        takeString(String.format("go %s", direction.toLowerCase()));
    }

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
    }

    public void refreshUI(){
        view.updateHP(model.getPlayer().getHealth());
        view.updateRoom(model.getPlayer().getCurrentRoom().getTitle());
        view.updateLevel(model.getPlayer().getLevel(), model.getPlayer().getXP());
        view.updateRoomCharacters(model.getPlayer().getCurrentRoom().getCharacters());
        view.updateRoomItems(model.getPlayer().getCurrentRoom().getInventory().getItems());
        view.updateInventory(model.getPlayer().getInventory().getItems());
        view.setAbilityButtons(model.getPlayer().getActiveAbilities());

        if(model.isWaitingForRespawn()){
            view.setMovementButtonsEnabled(false);
        }

        if(state == GameState.FIGHT){
            boolean[] usableAbilities = new boolean[4];
            int i = 0;
            for(int count : model.getPlayer().getUses()){
                usableAbilities[i++] = count > 0;
            }
            view.updateEnemyLabel(model.getBattle().getNPC().getName(), model.getBattle().getNPC().getHealth(), model.getBattle().getNPC().getLevel());
            view.updateAbilityButtons(usableAbilities);
            if(model.getBattle().getPlayerBC().canStruggle()){
                view.showStruggleButton();
            }

        }else if(state == GameState.EXPLORATION){
            if(model.getArrow().hasTargetRoom()){
                if(model.getArrow().isActivated()) {
                    view.enableArrow();

                    if(model.getArrow().hasReachedTarget()){
                        model.getArrow().deactivate();
                        view.disableArrow();
                        model.getArrow().targetReached();
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
}