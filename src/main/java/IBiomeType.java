import java.util.ArrayList;
import java.util.HashMap;

public interface IBiomeType {
    public void placeGrass();
    public ArrayList<Grass> getGrassArray();
    public HashMap<Vector2d, Grass> getHashMap();
    public boolean eatGrass(Vector2d position);
    public void setDeath(Vector2d position);
    public int getSize();
    public boolean[][] getPreferedFields();
    public void update();
    public ArrayList<Grass> getNewGrassArray();
}
