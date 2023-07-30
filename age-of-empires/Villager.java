package indy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.layout.Pane;

public class Villager extends Unit implements Creating {

    public Villager(double xLoc, double yLoc, Player player) {
        super(4, 0, 0, 0, xLoc, yLoc, player);
    }

    public Villager (Villager villager) {
        super(villager.getMorale(), 4, 0, 0, 0, villager.getXLoc(), villager.getYLoc(), villager.getPlayer());
    }

    @Override
    public boolean canAttack() {
        return false;
    }

    @Override
    public ImageView setImage(double xLoc, double yLoc) {
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("Villager.png")));
            imageView.setX(xLoc);
            imageView.setY(yLoc);
            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean actionAvailable() {
         return false;
    }

    @Override
    public void build(Tile buildingTile, Controllable product, Pane mapPane) {
        int productionCost = 5;
        switch (product.getClass().getSimpleName()) {
            case "Stable": productionCost = 10;
            case "Farm": productionCost = 5;
            case "Range": productionCost = 7;
            case "Barracks": productionCost = 7;
            case "Tower": productionCost = 10;
            default: productionCost = 5;
        }
        if (product.getPlayer().getIncome() >= productionCost) {
            buildingTile.addControllable(product, mapPane);
            product.getPlayer().changeIncome(-productionCost);
        }
    }
}
