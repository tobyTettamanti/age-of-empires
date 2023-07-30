package indy;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class ShallowWater extends Biome{

    public ShallowWater(double xLoc, double yLoc) {
        super(xLoc, yLoc, true);
        super.getSquare().setFill(Color.LIGHTBLUE);
    //    super.getSquare().setStroke(Color.BLUE);
        super.getSquare().setStrokeWidth(2);
    }

    @Override
    public Node[] getNodes() {
        return new Node[]{super.getSquare()};
    }
}
