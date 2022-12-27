package agh;

import java.util.*;

public class ToxicBiome implements IBiomeType{
    private int height;
    private int width;
    private Integer[][] deathArray;
    private ArrayList<Grass> grassArray = new ArrayList<>();
    private Map<Vector2d, Grass> haszmapa=new HashMap<>();
    public ToxicBiome(int width, int height){
        this.width=width;
        this.height=height;
        deathArray = new Integer[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                deathArray[i][j]=0;
            }
        }
    }
    private Integer getMinDeath(){
        Integer minimum=deathArray[0][0];
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (minimum>deathArray[i][j]){
                    minimum=deathArray[i][j];
                }
            }
        }
        return minimum;
    }
    @Override
    public void placeGrass() {
        Random rand = new Random();
        Integer minimum=getMinDeath();
        for (int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                if (deathArray[i][j].equals(minimum)){
                    if (rand.nextInt(10)<8){
                        Vector2d pozycja = new Vector2d(i,j);
                        if (!haszmapa.containsKey(pozycja)){
                            Grass trawa = new Grass(pozycja);
                            haszmapa.put(pozycja, trawa);
                            grassArray.add(trawa);
                        }
                    }
                }
                else {
                    if (rand.nextInt(10)<2){
                        Vector2d pozycja = new Vector2d(i,j);
                        if (!haszmapa.containsKey(pozycja)){
                            Grass trawa = new Grass(pozycja);
                            haszmapa.put(pozycja, trawa);
                            grassArray.add(trawa);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setDeath(Vector2d position) {
        deathArray[position.getX()][position.getY()]+=1;
    }
    @Override
    public ArrayList<Grass> getGrassArray() {
        return new ArrayList<>(grassArray);
    }

    @Override
    public HashMap<Vector2d, Grass> getHashMap() {
        return new HashMap<>(haszmapa);
    }

    @Override
    public void eatGrass(Vector2d position) {
        grassArray.remove(haszmapa.get(position));
        haszmapa.remove(position);
    }
}
