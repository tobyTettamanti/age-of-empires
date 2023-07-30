package indy;

import javafx.scene.layout.Pane;
public interface Attacking {

    public void attack(Tile initialAttackingUnitTile, Tile initialDefendingUnitTile, Pane mapPane);
    public void counterattack(Tile initialAttackingUnitTile, Pane mapPane);
    public int loseMorale(int moraleDamage);
    public int getMorale();
    public int getRange();
    public int getAttack();
    public int getDefense();
    public void permitActions();
    public void prohibitActions();
}
