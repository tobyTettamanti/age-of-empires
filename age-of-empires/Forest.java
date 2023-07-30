package indy;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.Node;

public class Forest extends Biome{

    private Group _imageGroup;

    public Forest(double xLoc, double yLoc) {
        super(xLoc, yLoc, true);
        super.getSquare().setFill(Color.GREEN);
   //     super.getSquare().setStroke(Color.LIGHTGREEN);
        super.getSquare().setStrokeWidth(2);
        try {
            createForestImage(xLoc, yLoc);
        }
        catch (IOException ioe){
            System.out.println("Forest IOException!");
        }
    }

    private void createForestImage(double xLoc, double yLoc) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("Oak_Tree_1.png");
        int randomVal = (int)(Math.random() * 4);
        switch (randomVal) {
            case 0: {
                fileInputStream = new FileInputStream("Pine_Tree_1.png");
                break;
            }
            case 1: {
                fileInputStream = new FileInputStream("Pine_Tree_2.png");
                break;
            }
            case 2: {
                fileInputStream = new FileInputStream("Oak_Tree_2.png");
                break;
            }
        }
        ImageView imageView = new ImageView(new Image(fileInputStream));
        imageView.setX(xLoc + (Constants.BIOME_SQUARE_SIZE / 5));
        imageView.setY(yLoc + (Constants.BIOME_SQUARE_SIZE / 10));
        _imageGroup= new Group(imageView);
    }

     @Override public Node[] getNodes() {
         return new Node[]{super.getSquare(), _imageGroup};
     }


}
