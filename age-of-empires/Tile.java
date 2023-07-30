package indy;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

public class Tile {

    private Biome _biome;
    private Controllable _controllable;

    public Tile(double xLoc, double yLoc) {
        _controllable = null;
        _biome = randomBiome(xLoc, yLoc);
    }

    public Tile(Biome biome) {
        _controllable = null;
        _biome = biome;
    }

    private Biome randomBiome(double xLoc, double yLoc) {
        int randomVal = (int) (Math.random() * 8);
        switch(randomVal) {
            case 0: return new Grassland(xLoc, yLoc);
            case 1: return new Plain(xLoc, yLoc);
            case 2: return new Hill(xLoc, yLoc);
            case 3: return new Desert(xLoc, yLoc);
            case 4: return new Mountain(xLoc, yLoc);
            case 5: return new ShallowWater(xLoc, yLoc);
            case 6: return new DeepWater(xLoc, yLoc);
            case 7: return new Forest(xLoc, yLoc);
        }
        return null;
    }

    public int getTileXPosition() {
        return (int)((_biome.getSquare().getX() / Constants.BIOME_SQUARE_SIZE));
    }

    public int getTileYPosition() {
        return (int)((_biome.getSquare().getY() / Constants.BIOME_SQUARE_SIZE));
    }

    public Biome getBiome() {
        return _biome;
    }

    public Controllable getControllable() {
        return _controllable;
    }

    public Node getControllableNode() {
        return _controllable.getNode();
    }

    public boolean hasRedOutline() {
        try {
            return _biome.getSquare().getStroke().equals(Color.RED);
        }
        catch(NullPointerException e) {
            return false;
        }
    }

    public double getXLoc() {
        return _biome.getSquare().getX();
    }

    public double getYLoc() {
        return _biome.getSquare().getY();
    }

    public void highlightTile() {
        getBiome().getSquare().setStroke(Color.RED);
        getBiome().getSquare().setStrokeWidth(4);
    }

    public void unHighlightTile() {
        getBiome().getSquare().setStroke(getBiome().getSquare().getFill());
        getBiome().getSquare().setStrokeWidth(0);
    }

    public Node[] getBiomeNodes() {
        return _biome.getNodes();
    }

    public void addControllable(Controllable controllable) {
        _controllable = controllable;
    }

    public void addControllable(Controllable controllable, Pane mapPane) {
        mapPane.getChildren().add(controllable.getNode());
        _controllable = controllable;
        controllable.setImage(this.getXLoc(), this.getYLoc());
    }

    public void removeControllable(Pane mapPane) {
        mapPane.getChildren().remove(_controllable.getNode());
        _controllable = null;
    }

}
