package indy;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;

public class Plain extends Biome{

    private Group _imageGroup;

    public Plain(double xLoc, double yLoc) {
        super(xLoc, yLoc, true);
        super.getSquare().setFill(Color.TAN);
        //  super.getSquare().setStroke(Color.SADDLEBROWN);
        super.getSquare().setStrokeWidth(2);
        try {
            createPlainImage(xLoc, yLoc);
        }
        catch (IOException ioe){
            System.out.println("Plain IOException!");
        }
    }

    private void createPlainImage(double xLoc, double yLoc) throws IOException{
        ImageView imageView = new ImageView(new Image(new FileInputStream("Grass_Patch_2.png")));
        imageView.setX(xLoc + (Constants.BIOME_SQUARE_SIZE / 8));
        imageView.setY(yLoc + (Constants.BIOME_SQUARE_SIZE / 4));
        _imageGroup= new Group(imageView);
    }

    @Override
    public Node[] getNodes() {
        return new Node[]{super.getSquare(), _imageGroup};
    }

}
