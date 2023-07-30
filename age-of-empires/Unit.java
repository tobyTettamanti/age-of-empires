package indy;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Unit implements Controllable {

    private int _morale;
    private int _mobility;
    private boolean _movementAvailable;
    private boolean _actionAvailable;
    private int _attack;
    private int _defense;
    private int _range;
    private ImageView _image;
    private Player _player;

    public Unit(int mobility, int attack, int defense, int range, double xLoc, double yLoc, Player player) {
        _morale = 100;
        _mobility = mobility;
        _movementAvailable = true;
        _actionAvailable = true;
        _attack = attack;
        _defense = defense;
        _range = range;
        _image = setImage(xLoc, yLoc);
        _player = player;
    }

    public Unit(int morale, int mobility, int attack, int defense, int range, double xLoc, double yLoc, Player player) {
        _morale = morale;
        _mobility = mobility;
        _movementAvailable = true;
        _actionAvailable = true;
        _attack = attack;
        _defense = defense;
        _range = range;
        _image = setImage(xLoc, yLoc);
        _player = player;
    }

    public int getMorale() {
        return _morale;
    }

    @Override
    public Player getPlayer() {
        return _player;
    }

    public int loseMorale(int moraleDamage) {
        System.out.println(_morale -= moraleDamage);
        return _morale -= moraleDamage;
    }

    public int getAttack() {
        return _attack;
    }

    public int getDefense() {
        return _defense;
    }

    public int getRange() {
        return _range;
    }

    public double getXLoc() {
        return _image.getX();
    }

    public double getYLoc() {
        return _image.getY();
    }

    public void setXLoc(double xLoc) {
        _image.setX(xLoc);
    }

    public void setYLoc(double yLoc) {
        _image.setY(yLoc);
    }

    public boolean canAttack() {
        return true;
    }

    public boolean actionAvailable() {
        return _actionAvailable;
    }

    public abstract ImageView setImage(double xLoc, double yLoc);

    public ImageView getImage() {
        return _image;
    }

    public Node getNode() {
        return getImage();
    }

    public int getMovement() {
        return _mobility;
    }
    public boolean movable() {
        return true;
    }

    public int getMovementCost(Biome destinationBiome) {
        Paint destinationColor = destinationBiome.getSquare().getFill();
        if (destinationColor.equals(Color.TAN) || destinationColor.equals(Color.LIGHTGREEN)) return 1; // Plain or Grassland
        else if (destinationColor.equals(Color.LIGHTYELLOW) || destinationColor.equals(Color.GREEN) || destinationColor.equals(Color.SADDLEBROWN)) return 2; // Desert, Forest, or Hill
        else if (destinationColor.equals(Color.GREY) || destinationColor.equals(Color.LIGHTBLUE)) return 4; // Mountain or ShallowWater
        else return 8; // deepWater
    }


    public void move(Tile destinationTile, Tile originTile, Pane mapPane) {
          Unit duplicateUnit = null;
//        String className = (getClass().getSimpleName());
//        if (className.equals("Villager")) duplicateUnit = new Villager((Villager)this);
//        if (className.equals("HeavyInfantry")) duplicateUnit = new HeavyInfantry((HeavyInfantry)this);
//        if (className.equals("LightInfantry")) duplicateUnit = new LightInfantry((LightInfantry)this);
//        if (className.equals("Cavalry")) duplicateUnit = new Cavalry((Cavalry)this);

        switch (getClass().getSimpleName()) {
            case "Villager": {
                duplicateUnit = new Villager((Villager) this);
                break;
            }
            case "HeavyInfantry": {
                duplicateUnit = new HeavyInfantry((HeavyInfantry) this);
                break;
            }
            case "LightInfantry": {
                duplicateUnit = new LightInfantry((LightInfantry) this);
                break;
            }
            case "Cavalry": {
                duplicateUnit = new Cavalry((Cavalry) this);
                break;
            }
            default: duplicateUnit = null;
        }

        duplicateUnit.setXLoc(destinationTile.getXLoc());
        duplicateUnit.setYLoc(destinationTile.getYLoc());

        originTile.removeControllable(mapPane);
        destinationTile.addControllable(duplicateUnit, mapPane);

        duplicateUnit.prohibitMovement();
    }

    public boolean movementLeft() {
        return _movementAvailable;
    }

    public void prohibitMovement() {
        _movementAvailable = false;
    }

    public void permitMovement() {
        _movementAvailable = true;
    }

    public void permitActions() {
        _actionAvailable = true;
    }

    public void prohibitActions() {
        _actionAvailable = false;
    }

}
