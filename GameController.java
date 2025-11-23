public class GameController{
    private ZorkGame model;
    private GUI view;
    private Parser parser;

    public GameController(){
        this.parser = new Parser();

    }

    public void setModel(ZorkGame model){
        this.model = model;
    }
    public void setView(GUI view){
        this.view = view;
    }

    public void takeString(String input){
        Command command = parser.getCommand(input);
        GameState state = model.processCommand(command);
        updateState(state);
    }

    public void onAbilityClick(String abilityName){
        if (model.getState() == GameState.FIGHT) {
            Battle activeBattle = model.getBattle();
            activeBattle.performTurn(abilityName);
        }
    }

    public void updateState(GameState gameState){
        view.updateState(gameState);
        if (gameState==GameState.FIGHT){
            String[] abilityNames = new String[4];
            int i=0;
            for(Ability ability : model.getPlayer().getActiveAbilities()){
                abilityNames[i++] = ability.getName();
            }
            view.setAbilityButtons(abilityNames);
        }
    }
}