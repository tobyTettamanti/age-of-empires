package indy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Farm extends Building {

    public Farm(double xLoc, double yLoc, Player player) {
        super(xLoc, yLoc, player);
    }

    @Override
    public ImageView setImage(double xLoc, double yLoc) {
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("Farm.png")));
            imageView.setX(xLoc);
            imageView.setY(yLoc);

            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
