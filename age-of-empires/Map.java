package indy;


import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Map {

    private Tile[][] _map;
    private Controllable _focusedControllable;
    private Pane _mapPane;
    private Label _description;

    public Map(Pane mainPane, Player[] players, Label description) {
        _description = description;
        _map = Constants.CANADA_MAP;
        _mapPane = mainPane;
        for (Tile[] tiles : _map) {
            for (Tile tile : tiles) {
                mainPane.getChildren().addAll(tile.getBiomeNodes());
            }
        }
        _map[2][8].addControllable(new City(_map[2][8].getXLoc(), _map[2][8].getYLoc(), players[0]));
        _map[2][10].addControllable(new Castle(_map[2][10].getXLoc(), _map[2][10].getYLoc(), players[0]));
        _map[4][8].addControllable(new Tower(_map[4][8].getXLoc(), _map[4][8].getYLoc(), players[0]));
        _map[0][8].addControllable(new Tower(_map[0][8].getXLoc(), _map[0][8].getYLoc(), players[0]));
        mainPane.getChildren().add(_map[2][8].getControllableNode());
        mainPane.getChildren().add(_map[2][10].getControllableNode());
        mainPane.getChildren().add(_map[4][8].getControllableNode());
        mainPane.getChildren().add(_map[0][8].getControllableNode());
        _map[2][9].addControllable(new Cavalry(_map[2][9].getXLoc(), _map[2][9].getYLoc(), players[0]));
        _map[2][7].addControllable(new Villager(_map[2][7].getXLoc(), _map[2][7].getYLoc(), players[0]));
        mainPane.getChildren().add(_map[2][9].getControllableNode());
        mainPane.getChildren().add(_map[2][7].getControllableNode());
        _map[0][10].addControllable(new Farm(_map[0][10].getXLoc(), _map[0][10].getYLoc(), players[0]));
        _map[4][10].addControllable(new Farm(_map[4][10].getXLoc(), _map[4][10].getYLoc(), players[0]));
        mainPane.getChildren().add(_map[0][10].getControllableNode());
        mainPane.getChildren().add(_map[4][10].getControllableNode());

        _map[2][17].addControllable(new City(_map[2][17].getXLoc(), _map[2][17].getYLoc(), players[1]));
        _map[2][15].addControllable(new Castle(_map[2][15].getXLoc(), _map[2][15].getYLoc(), players[1]));
        _map[4][17].addControllable(new Tower(_map[4][17].getXLoc(), _map[4][17].getYLoc(), players[1]));
        _map[0][17].addControllable(new Tower(_map[0][17].getXLoc(), _map[0][17].getYLoc(), players[1]));
        _map[1][15].addControllable(new Farm(_map[1][15].getXLoc(), _map[1][15].getYLoc(), players[1]));
        _map[3][15].addControllable(new Farm(_map[3][15].getXLoc(), _map[3][15].getYLoc(), players[1]));
        mainPane.getChildren().add(_map[1][15].getControllableNode());
        mainPane.getChildren().add(_map[3][15].getControllableNode());


        mainPane.getChildren().add(_map[2][17].getControllableNode());
        mainPane.getChildren().add(_map[2][15].getControllableNode());
        mainPane.getChildren().add(_map[4][17].getControllableNode());
        mainPane.getChildren().add(_map[0][17].getControllableNode());

        _map[2][18].addControllable(new Villager(_map[2][18].getXLoc(), _map[2][18].getYLoc(), players[1]));
        _map[2][16].addControllable(new Cavalry(_map[2][16].getXLoc(), _map[2][16].getYLoc(), players[1]));
        mainPane.getChildren().add(_map[2][18].getControllableNode());
        mainPane.getChildren().add(_map[2][16].getControllableNode());
        resetToNeutral();
    }

    private void resetToNeutral() {

        _focusedControllable = null;
        removeHighlights();
        for (Tile[] tiles : _map) {
            for (Tile tile : tiles) {
                manageFocusHandler(tile);
                tile.getBiome().getNodes()[0].removeEventFilter(MouseEvent.MOUSE_CLICKED, new HighlightEventHandler()); // make it reset every round, only to current player

            }
        }
    }

    public void startNextTurn(Player[] players) {
        for (Tile[] tiles : _map) {
            for (Tile tile : tiles) {
                if (tile.getControllable() != null) {
                    if (tile.getControllable().movable()) ((Unit)tile.getControllable()).permitMovement(); // is a unit
                    if (tile.getControllable().canAttack()) ((Attacking)tile.getControllable()).permitActions(); // is an Attacking
                    if (tile.getControllable().getClass().getSimpleName().equals("Farm")) {
                        if (tile.getControllable().getPlayer().getCountry().equals(Country.SONG_DYNASTY)) tile.getControllable().getPlayer().changeIncome(Constants.FARM_INCOME + 2); // Song Dynasty get an extra two income from farming
                            else tile.getControllable().getPlayer().changeIncome(Constants.FARM_INCOME);
                    }
                }
            }
        }

        int activePlayer;
        if (players[0].isActiveTurn()) activePlayer = 1;
            else activePlayer = 0;
        players[activePlayer].setActiveTurn(true);
        players[1 - activePlayer].setActiveTurn(false);
        _description.setText("It is " + players[activePlayer] + "'s turn! \n\n" + players[activePlayer] + "'s income: " + players[activePlayer].getIncome());
        resetToNeutral();
        if (oneCityRemaining()) createEndGameLabel(getLastCity());

    }

    public void createEndGameLabel(Player victor) {
       _description.setText("" + victor + " has won the game! \nCongratulations!");
       _description.setTextFill(Color.RED);
    }

    private Player getLastCity() {
        for (Tile[] tiles : _map) {
            for (Tile tile : tiles) {
                if (tile.getControllable() != null && tile.getControllable().getClass().getSimpleName().equals("City")) return tile.getControllable().getPlayer();
            }
        }
        return null;
    }

    private boolean oneCityRemaining() {
        boolean oneCity = false;
        for (Tile[] tiles : _map) {
            for (Tile tile : tiles) {
                if (tile.getControllable() != null && tile.getControllable().getClass().getSimpleName().equals("City")) {
                    if (oneCity) return false;
                    else oneCity = true;
                }
            }
        }
        return oneCity;
    }

    private void manageFocusHandler(Tile tile) {
        if (tile.getControllable() != null) {
            tile.getControllable().getNode().setPickOnBounds(true);
            tile.getControllable().getNode().addEventFilter(MouseEvent.MOUSE_CLICKED, new HighlightEventHandler()); // make it reset every round, only to current player
        }

    }

    private void manageMoveHandler(Tile originTile) {
        if (_focusedControllable.movable()) {
            if (((Unit)_focusedControllable).movementLeft()) {
                for (Tile[] tiles : _map) {
                    for (Tile tile : tiles) {
                        tile.getBiome().getNodes()[0].setPickOnBounds(true);
                        tile.getBiome().getNodes()[0].addEventFilter(MouseEvent.MOUSE_CLICKED, new MoveEventHandler());
                    }
                }
            }
            else {
                originTile.highlightTile();
            }
        }
        else resetToNeutral();


    }

    public void manageAttackHandler() {
        EventHandler<MouseEvent> attackEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent e) {
                ((Attacking)_focusedControllable).attack(getControllableTile(_focusedControllable),
                   getControllableTile((ImageView) e.getSource()), _mapPane);
                ((ImageView) e.getSource()).removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
            }
        };
        removeHighlights();
        if (getFocusedControllable() != null && getFocusedControllable().actionAvailable()) {
            ArrayList<Tile> tiles = attackableControllableTiles(_map[(int) (_focusedControllable.getImage().getY() / Constants.BIOME_SQUARE_SIZE)][(int) (_focusedControllable.getImage().getX() / Constants.BIOME_SQUARE_SIZE)],
                    ((Attacking) _focusedControllable).getRange());
            for (Tile tile : tiles) {
                tile.highlightTile();
                tile.getBiome().getNodes()[0].setPickOnBounds(true);
                tile.getBiome().getNodes()[0].removeEventFilter(MouseEvent.MOUSE_CLICKED, new HighlightEventHandler());
                tile.getControllableNode().addEventFilter(MouseEvent.MOUSE_CLICKED, attackEventHandler);
            }
        }
    }


    public void manageBuildHandler(Controllable controllableToBuild) {
        EventHandler<MouseEvent> buildEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent e) {
                Tile buildTile = _map[((int) (((Rectangle) e.getSource()).getY() / Constants.BIOME_SQUARE_SIZE))][((int) (((Rectangle) e.getSource()).getX() / Constants.BIOME_SQUARE_SIZE))];
         {
//                if (controllableToBuild.movable()) {
//                    Unit finalUnit;
//                    switch (controllableToBuild.getClass().getSimpleName()) {
//                        case "Villager": {
//                            finalUnit = new Villager(buildTile.getXLoc(), buildTile.getYLoc(), controllableToBuild.getPlayer());
//                            break;
//                        }
//                        case "HeavyInfantry": {
//                            finalUnit = new HeavyInfantry(buildTile.getXLoc(), buildTile.getYLoc(), controllableToBuild.getPlayer());
//                            break;
//                        }
//                        case "LightInfantry": {
//                            finalUnit = new LightInfantry(buildTile.getXLoc(), buildTile.getYLoc(), controllableToBuild.getPlayer());
//                            break;
//                        }
//                        case "Cavalry": {
//                            finalUnit = new Cavalry(buildTile.getXLoc(), buildTile.getYLoc(), controllableToBuild.getPlayer());
//                            break;
//                        }
//                        default: finalUnit = null;
//                    }
                    ((Creating)_focusedControllable).build(buildTile, controllableToBuild,_mapPane);
                }
//                else {
//                    ((Creating)_focusedControllable).build(buildTile, controllableToBuild,_mapPane);
//                }
                ((Rectangle) e.getSource()).removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
                for (Tile tile : highlightOneTileAround(getControllableTile(_focusedControllable))) {
                    tile.unHighlightTile();
                    tile.getBiome().getNodes()[0].removeEventFilter(MouseEvent.MOUSE_CLICKED,this);
                }
                resetToNeutral();
            }
        };
        for (Tile tile : highlightOneTileAround(getControllableTile(_focusedControllable))) {
            tile.getBiome().getNodes()[0].setPickOnBounds(true);
            tile.getBiome().getNodes()[0].addEventFilter(MouseEvent.MOUSE_CLICKED,buildEventHandler);
        }
    }

    private ArrayList<Tile> attackableControllableTiles(Tile originTile, int attackingRange) {
        ArrayList<Tile> returnControllableTiles = new ArrayList<>();
            for (int indexI = -attackingRange; indexI <= attackingRange; indexI++) {
                for (int indexJ = -attackingRange; indexJ <= attackingRange; indexJ++) {
                    try {
                        if (_map[indexI + originTile.getTileYPosition()][indexJ + originTile.getTileXPosition()].getControllable() != null &&
                                !(_map[indexI + originTile.getTileYPosition()][indexJ + originTile.getTileXPosition()].getControllable().getPlayer().isActiveTurn())) // searches for other controllables not controlled by the player
                            returnControllableTiles.add(_map[indexI + originTile.getTileYPosition()][indexJ + originTile.getTileXPosition()]);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {} // does not include out of bounds tiles
                }
            }
        return returnControllableTiles;
    }

    private void removeHighlights() {
        for (Tile tile : getHighlightedTiles()) {
            tile.unHighlightTile();
        }
    }
    private ArrayList<Tile> getHighlightedTiles() {
        ArrayList<Tile> returnTiles = new ArrayList<Tile>();
        for (Tile[] tiles : _map) {
            for (Tile tile : tiles) {
                if (tile.hasRedOutline()) returnTiles.add(tile);
            }
        }
        return returnTiles;
    }

    public ArrayList<Tile> highlightOneTileAround(Tile clickedTile) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int indexI = -1; indexI <= 1; indexI++) {
            for (int indexJ = -1; indexJ <= 1; indexJ++) {
                try {
                    Tile possibleHighlight = _map[(int)(clickedTile.getYLoc() / Constants.BIOME_SQUARE_SIZE) + indexI][(int)(clickedTile.getXLoc() / Constants.BIOME_SQUARE_SIZE) + indexJ];
                    if (possibleHighlight.getBiome().isCrossable() && !possibleHighlight.hasRedOutline() && possibleHighlight.getControllable() == null) {
                        possibleHighlight.highlightTile();
                        tiles.add(possibleHighlight);
                    }
                } catch(ArrayIndexOutOfBoundsException exception) {} // does not add outOfBounds tile to tiles
            }
        }
        return tiles;
    }

    public void highlightMovableSpaces (Unit unit, Tile tile, int movementLeft) {
        if (movementLeft > 0) {
            for (Tile destinationTile : highlightOneTileAround(tile)) {
                highlightMovableSpaces(unit, destinationTile, movementLeft - (unit.getMovementCost(destinationTile.getBiome())));
            }
        }
    }

    public Controllable getFocusedControllable() {
        return _focusedControllable;
    }

    private class MoveEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            Unit focusUnit;
            if (_focusedControllable != null && _focusedControllable.movable()) {
                focusUnit = ((Unit) _focusedControllable);
                Tile originTile = _map[(int) (focusUnit.getYLoc() / Constants.BIOME_SQUARE_SIZE)][(int) (focusUnit.getXLoc() / Constants.BIOME_SQUARE_SIZE)];
                Tile destinationTile = _map[((int) (((Rectangle) e.getSource()).getY() / Constants.BIOME_SQUARE_SIZE))][((int) (((Rectangle) e.getSource()).getX() / Constants.BIOME_SQUARE_SIZE))];
                if (destinationTile.hasRedOutline()) focusUnit.move(destinationTile, originTile, _mapPane);
            }
            for (Tile[] tiles : _map) {
                for (Tile tile : tiles) {
                    tile.getBiome().getNodes()[0].removeEventFilter(MouseEvent.MOUSE_CLICKED, new MoveEventHandler());
                }
            }
            resetToNeutral();
            e.consume();

        }
    }


    private class HighlightEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            Tile tile = getControllableTile((ImageView) e.getSource());
            if (tile.getControllable() != null && tile.getControllable().getPlayer().isActiveTurn()) {

                _focusedControllable = tile.getControllable();
                if (_focusedControllable.movable()) { // is unit
                    if (((Unit) _focusedControllable).movementLeft() ) { // has movement left, it's current player's turn
                        highlightMovableSpaces((Unit) tile.getControllable(), tile, ((Unit) tile.getControllable()).getMovement());
                        manageMoveHandler(tile);
                    }
                    else tile.highlightTile();
                }
                else {
                    removeHighlights();
                    tile.highlightTile();
                }
            }

            tile.getControllable().getNode().removeEventFilter(MouseEvent.MOUSE_CLICKED, this); // make it reset every round, only to current player
            e.consume();
        }
    };

    public Tile getControllableTile(Controllable controllable) {
        return _map[(int)(controllable.getImage().getY() / Constants.BIOME_SQUARE_SIZE)][(int)(controllable.getImage().getX() / Constants.BIOME_SQUARE_SIZE)];
    }

    public Tile getControllableTile(ImageView image) {
        return _map[((int) ((image.getY() / Constants.BIOME_SQUARE_SIZE)))][((int) ((image.getX() / Constants.BIOME_SQUARE_SIZE)))];
    }

}
