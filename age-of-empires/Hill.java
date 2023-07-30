package indy;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;

public class Hill extends Biome{

    private Group _imageGroup;

    public Hill(double xLoc, double yLoc) {
        super(xLoc, yLoc, true);
        super.getSquare().setFill(Color.SADDLEBROWN);
        //super.getSquare().setStroke(Color.GRAY);
        super.getSquare().setStrokeWidth(2);
        try {
            createHillImage(xLoc, yLoc);
        }
        catch (IOException ioe){
            System.out.println("Hill IOException!");
        }
    }

    private void createHillImage(double xLoc, double yLoc) throws IOException{
        ImageView imageView = new ImageView(new Image(new FileInputStream("Hill_Top_1.png")));
        imageView.setX(xLoc);
        imageView.setY(yLoc);
        _imageGroup= new Group(imageView);
    }

    @Override
    public Node[] getNodes() {
        return new Node[]{super.getSquare(), _imageGroup};
    }
}
