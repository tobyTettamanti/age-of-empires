package indy;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;

public class Mountain extends Biome{

    private ImageView _imageView;

    public Mountain(double xLoc, double yLoc) {
        super(xLoc, yLoc, false);
        super.getSquare().setFill(Color.GRAY);
        super.getSquare().setStrokeWidth(2);
        try {
            createMountainImage(xLoc, yLoc);
        }
        catch (IOException ioe){
            System.out.println("Mountain IOException!");
        }
    }

    private void createMountainImage(double xLoc, double yLoc) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("Mountain_Top_3.png");;
        int randomVal = (int)(Math.random() * 3);
        switch (randomVal) {
            case 0: {
                fileInputStream = new FileInputStream("Mountain_Top_1.png");
                break;
            }
            case 1: {
                fileInputStream = new FileInputStream("Mountain_Top_2.png");
            }
        }
        _imageView = new ImageView(new Image(fileInputStream));
        _imageView.setX(xLoc);
        _imageView.setY(yLoc);
    }

    @Override public Node[] getNodes() {
        return new Node[]{super.getSquare(), _imageView};
    }

}
