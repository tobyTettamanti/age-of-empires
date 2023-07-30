package indy;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;

public class Desert extends Biome{

    private Group _imageGroup;

    public Desert(double xLoc, double yLoc) {
        super(xLoc, yLoc, true);
        super.getSquare().setFill(Color.LIGHTYELLOW);
     //   super.getSquare().setStroke(Color.TAN);
        super.getSquare().setStrokeWidth(2);
        try {
            createDesertImage(xLoc, yLoc);
        }
        catch (IOException ioe){
            System.out.println("Desert IOException!");
        }
    }

    private void createDesertImage(double xLoc, double yLoc) throws IOException{
        ImageView imageView = new ImageView(new Image(new FileInputStream("Desert_Hill_1.png")));
        imageView.setX(xLoc);
        imageView.setY(yLoc + (Constants.BIOME_SQUARE_SIZE / 4));
        _imageGroup= new Group(imageView);
    }

    @Override
    public Node[] getNodes() {
        return new Node[]{super.getSquare(), _imageGroup};
    }
}
