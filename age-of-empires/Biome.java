package indy;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public abstract class Biome {

    private boolean _isCrossable;
    private Rectangle _square;

    public Biome(double xLoc, double yLoc, boolean isCrossable) {
        _square = new Rectangle(xLoc, yLoc, Constants.BIOME_SQUARE_SIZE, Constants.BIOME_SQUARE_SIZE);
        _isCrossable = isCrossable;
    }

    public Rectangle getSquare() {
        return _square;
    }

    public boolean isCrossable() {
        return _isCrossable;
    }

    public abstract Node[] getNodes();
}
