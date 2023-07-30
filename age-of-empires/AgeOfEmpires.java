package indy;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class AgeOfEmpires {

    private Map _map;
    private Button[][] _buttons;
    private Player[] _players;
    private Label _description;

    public AgeOfEmpires(Pane mainPane, Pane buttonPane, int mapWidth, int mapLength) {
        _players = new Player[2];
        _players[0] = new Player(Country.SONG_DYNASTY);
        _players[1] = new Player(Country.BYZANTINE_EMPIRE);
        _players[0].setActiveTurn(true);
        createButtons(buttonPane);
        manageButtons();
        createLabel(buttonPane);
        _map = new Map(mainPane, _players, _description);

    }

    private void createButtons(Pane buttonPane) {
        _buttons = new Button[5][2];
        _buttons[0][0] = new Button("Surrender");
        _buttons[0][1] = new Button("Quit");
        _buttons[1][0] = new Button("End Turn");
        _buttons[1][1] = new Button("Attack");
        _buttons[2][0] = new Button("Build Unit");
        _buttons[2][1] = new Button("Build Farm");
        _buttons[3][0] = new Button("Build Barracks");
        _buttons[3][1] = new Button("Build Stable");
        _buttons[4][0] = new Button("Build Range");
        _buttons[4][1] = new Button("Build Tower");
        for (int buttonI = 0; buttonI < _buttons.length; buttonI++) {
            for (int buttonJ = 0; buttonJ < _buttons[0].length; buttonJ++) {
                Button button = _buttons[buttonI][buttonJ];
                button.setPrefSize(120, 60);
                button.setTranslateX(buttonJ * 180);
                button.setTranslateY((buttonI * 120) + 120);
                buttonPane.getChildren().add(button);
                button.setFocusTraversable(true);
            }
        }
    }

    private void manageButtons() {
        _buttons[0][0].setOnAction(new SurrenderHandler()); //surrender button
        _buttons[0][1].setOnAction(new QuitButtonHandler()); //quit button
        _buttons[1][0].setOnAction(new NextTurnHandler()); // next turn button
        _buttons[1][1].setOnAction(new AttackHandler()); // attack button
        _buttons[2][0].setOnAction(new BuildUnitHandler()); // create a unit button
        _buttons[2][1].setOnAction(new BuildFarmHandler()); // create a farm button
        _buttons[3][0].setOnAction(new BuildBarracksHandler()); // create a barracks button
        _buttons[3][1].setOnAction(new BuildStableHandler()); // create a stable button
        _buttons[4][0].setOnAction(new BuildRangeHandler()); // create a range button
        _buttons[4][1].setOnAction(new BuildTowerHandler()); // create a tower button
    }

    private void createLabel(Pane buttonPane) {
        _description = new Label();
        _description.setText("It is " + _players[0] + "'s turn! \n\n" + _players[0] + "'s income: " + _players[0].getIncome());
        _description.setFont(new Font(18));
        buttonPane.getChildren().add(_description);

    }

    private class SurrenderHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _map.startNextTurn(_players);
            _map.createEndGameLabel(getActivePLayer()); // hands victory to the other player
        }
    }
    /*
    Exits the program.
     */
    private class QuitButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }
    }

    private class NextTurnHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _map.startNextTurn(_players);
        }
    }

    private class AttackHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
                _map.manageAttackHandler();
        }
    }

    private class BuildUnitHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Controllable focusedControllable = _map.getFocusedControllable();
            Controllable parameterControllable;
                switch(focusedControllable.getClass().getSimpleName()) {
                    case "Barracks": parameterControllable = new HeavyInfantry(_map.getControllableTile(focusedControllable).getXLoc(), _map.getControllableTile(focusedControllable).getYLoc(), focusedControllable.getPlayer());
                    case "Stable": parameterControllable = new Cavalry(_map.getControllableTile(focusedControllable).getXLoc(), _map.getControllableTile(focusedControllable).getYLoc(), focusedControllable.getPlayer());
                    case "Range": parameterControllable = new LightInfantry(_map.getControllableTile(focusedControllable).getXLoc(), _map.getControllableTile(focusedControllable).getYLoc(), focusedControllable.getPlayer());
                    case "City": parameterControllable = new Villager(_map.getControllableTile(focusedControllable).getXLoc(), _map.getControllableTile(focusedControllable).getYLoc(), focusedControllable.getPlayer());
                    default: parameterControllable = new Villager(_map.getControllableTile(focusedControllable).getXLoc(), _map.getControllableTile(focusedControllable).getYLoc(), focusedControllable.getPlayer());
                }
            _map.manageBuildHandler(parameterControllable);

            }
        }

    private class BuildFarmHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _map.manageBuildHandler(new Farm(_map.getFocusedControllable().getImage().getX(), _map.getFocusedControllable().getImage().getX(), getActivePLayer()));
        }
    }

    private class BuildBarracksHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _map.manageBuildHandler(new Barracks(_map.getFocusedControllable().getImage().getX(), _map.getFocusedControllable().getImage().getX(), getActivePLayer()));
        }
    }

    private class BuildRangeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _map.manageBuildHandler(new Range(_map.getFocusedControllable().getImage().getX(), _map.getFocusedControllable().getImage().getX(), getActivePLayer()));
        }
    }

    private class BuildStableHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _map.manageBuildHandler(new Stable(_map.getFocusedControllable().getImage().getX(), _map.getFocusedControllable().getImage().getX(), getActivePLayer()));
        }
    }

    private class BuildTowerHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            _map.manageBuildHandler(new Farm(_map.getFocusedControllable().getImage().getX(), _map.getFocusedControllable().getImage().getX(), getActivePLayer()));
        }
    }

    public Player getActivePLayer() {
        if (_players[0].isActiveTurn()) return _players[0];
        else return _players[1];
    }



}
