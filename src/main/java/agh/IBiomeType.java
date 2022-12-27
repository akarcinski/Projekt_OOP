package agh;

import java.util.ArrayList;
import java.util.HashMap;

public interface IBiomeType {
    public void placeGrass();
    public ArrayList<Grass> getGrassArray();
    public HashMap<Vector2d, Grass> getHashMap();
    public void eatGrass(Vector2d position);
    public void setDeath(Vector2d position);
}
