package indy;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Building implements Controllable {

    private ImageView _image;
    private Player _player;

    public Building(double xLoc, double yLoc, Player player) {
        _image = setImage(xLoc,yLoc);
        _player = player;
    }

    @Override
    public Player getPlayer() {
        return _player;
    }

    @Override
    public Node getNode() {
        return _image;
    }

    public ImageView getImage() {
        return _image;
    }

    public boolean actionAvailable() {
        return false;
    };

    public boolean canAttack() {
        return false;
    }

    public abstract ImageView setImage(double xLoc, double yLoc);

    public boolean movable() {
        return false;
    }
}
