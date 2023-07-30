package indy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HeavyInfantry extends Unit implements Attacking {

    public HeavyInfantry(double xLoc, double yLoc, Player player) {
        super(4, 40, 20, 1, xLoc, yLoc, player);

    }
    public HeavyInfantry (HeavyInfantry heavyInfantry) {
        super(heavyInfantry.getMorale(), 4, 40, 20, 1, heavyInfantry.getXLoc(), heavyInfantry.getYLoc(), heavyInfantry.getPlayer());
    }

    @Override
    public void attack(Tile initialAttackingUnitTile, Tile initialDefendingUnitTile, Pane mapPane) { // attack damage based on terrain, units morale, enemy morale
        if (initialDefendingUnitTile.getControllable().canAttack()) { // Controllable implements Attacking
            Attacking defendingControllable = ((Attacking) initialDefendingUnitTile.getControllable());

            double damageVal = this.getAttack() - defendingControllable.getDefense(); // always at least greater than 0
            System.out.println("Initial damage: " + damageVal);
            damageVal *= additionalFactors(initialDefendingUnitTile);
            damageVal = Math.pow(damageVal,1.25); // attack is a parabola of attack - defense - the larger the value, the more morale damage grows exponentially
            System.out.println("Attacking unit damage: " + damageVal);
            if (defendingControllable.loseMorale((int)damageVal) <= 0) initialDefendingUnitTile.removeControllable(mapPane);
            else if (defendingControllable.getRange() >= this.getRange()) defendingControllable.counterattack(initialAttackingUnitTile, mapPane);
            prohibitActions();
        }
        else { // Controllable does not implement Attacking, gets removed
            initialDefendingUnitTile.removeControllable(mapPane);
        }

    }

    private double additionalFactors(Tile defendingTile) {
        double damageModifier = 1;
        switch (defendingTile.getBiome().getClass().getSimpleName()) {
            case "Grassland" : damageModifier *= 1;
            case "Plain": damageModifier *= 1;
            case "Desert": damageModifier *= .9;
            case "Hill": damageModifier *= .6;
            case "Mountain": damageModifier *= .3;
            case "Forest": damageModifier *= .6;
            case "ShallowWater": damageModifier *= 1.3;
        }
        damageModifier *= ((this.getMorale() / 200) + .5); // a unit at half morale deals 75% damage, a unit at 1 morale deals
        return damageModifier;
    }

    @Override
    public void counterattack(Tile initialAttackingUnitTile, Pane mapPane) {
        Attacking defendingControllable = ((Attacking) initialAttackingUnitTile.getControllable());

        double damageVal = this.getAttack() - defendingControllable.getDefense(); // always at least greater than 0
        damageVal *= .5; // counter-attack does half morale damage
        damageVal *= additionalFactors(initialAttackingUnitTile);
        System.out.println("Counterattacking unit damage: " + damageVal);
        damageVal = Math.pow(damageVal,1.25); // attack is a parabola of attack - defense - the larger the value, the more morale damage grows exponentially
        if (defendingControllable.loseMorale((int)damageVal) <= 0) initialAttackingUnitTile.removeControllable(mapPane);
    }

    @Override
    public int getRange() {
        return super.getRange();
    }

    @Override
    public int getAttack() {
        return 40;
    }

    @Override
    public int getDefense() {
        return 20;
    }

    @Override
    public ImageView setImage(double xLoc, double yLoc) {
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("Heavy_Infantry.png")));
            imageView.setX(xLoc);
            imageView.setY(yLoc);
            return imageView;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
