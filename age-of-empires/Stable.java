package indy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Stable extends Building implements Creating{

    public Stable(double xLoc, double yLoc, Player player) {
        super(xLoc, yLoc, player);
    }
    @Override
    public ImageView setImage(double xLoc, double yLoc) {
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("Stable.png")));
            imageView.setX(xLoc);
            imageView.setY(yLoc);

            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void build(Tile buildingTile, Controllable product, Pane mapPane) {
        int productionCost = 7;
        if (product.getPlayer().getIncome() >= productionCost) {
            buildingTile.addControllable(product, mapPane);
            product.getPlayer().changeIncome(-productionCost);
        }
    }
}
