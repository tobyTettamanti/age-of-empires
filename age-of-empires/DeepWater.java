package indy;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class DeepWater extends Biome{

    public DeepWater(double xLoc, double yLoc) {
        super(xLoc, yLoc, false);
        super.getSquare().setFill(Color.BLUE);
   //     super.getSquare().setStroke(Color.DARKBLUE);
        super.getSquare().setStrokeWidth(2);
    }

    @Override
    public Node[] getNodes() {
        return new Node[]{super.getSquare()};
    }
}
