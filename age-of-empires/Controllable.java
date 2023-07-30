package indy;

import javafx.scene.Node;

import javafx.scene.image.ImageView;

public interface Controllable {

    public boolean movable();

    public ImageView getImage();

    public boolean actionAvailable();

    public Player getPlayer();

    public boolean canAttack();

    public Node getNode();

    public ImageView setImage(double xLoc, double yLoc);
}
