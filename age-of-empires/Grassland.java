package indy;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;

public class Grassland extends Biome{

    private Group _imageGroup;

    public Grassland(double xLoc, double yLoc) {
        super(xLoc, yLoc, true);
        super.getSquare().setFill(Color.LIGHTGREEN);
   //     super.getSquare().setStroke(Color.GREEN);
        super.getSquare().setStrokeWidth(2);
        try {
            createGrassImage(xLoc, yLoc);
        }
        catch (IOException ioe){
            System.out.println("Grassland IOException!");
        }
    }

    private void createGrassImage(double xLoc, double yLoc) throws IOException{
        ImageView imageView = new ImageView(new Image(new FileInputStream("Grass_Patch_1.png")));
        imageView.setX(xLoc);
        imageView.setY(yLoc + (Constants.BIOME_SQUARE_SIZE / 2));
        _imageGroup= new Group(imageView);
    }

    @Override
    public Node[] getNodes() {
        return new Node[]{super.getSquare(), _imageGroup};
    }
}
