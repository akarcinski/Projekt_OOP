package agh;

import java.util.*;

public class ForestedBiome implements IBiomeType{
    private int width;
    private int height;
    private int low;
    private int high;
    private ArrayList<Grass> grassArray = new ArrayList<>();
    private Map<Vector2d, Grass> hashmapa = new HashMap<>();
    public ForestedBiome(int width, int height){
        this.width=width;
        this.height=height;
        this.low=(int) Math.round(height*0.4);
        this.high=(int) Math.round(height*0.6);
    }

    @Override
    public void placeGrass() {
        Random rand = new Random();
        for(int i=0; i<high-low+1; i++) {
            for (int j = 0; j < width; j++) {
                if (rand.nextInt(10) < 2) {
                    Vector2d pozycja = new Vector2d(j, i);
                    if (!hashmapa.containsKey(pozycja)) {
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        hashmapa.put(pozycja, trawa);
                    }
                }
            }
        }
        for(int i=low; i<=high; i++){
            for(int j=0; j<width; j++){
                if (rand.nextInt(10)<8){
                    Vector2d pozycja = new Vector2d(j,i);
                    if (!hashmapa.containsKey(pozycja)){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        hashmapa.put(pozycja, trawa);
                    }
                }
            }
        }
        for(int i=high+1; i<height; i++){
            for(int j=0; j<width; j++){
                if (rand.nextInt(10)<2){
                    Vector2d pozycja = new Vector2d(j,i);
                    if (!hashmapa.containsKey(pozycja)){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        hashmapa.put(pozycja, trawa);
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<Grass> getGrassArray() {
        return new ArrayList<>(grassArray);
    }

    @Override
    public HashMap<Vector2d, Grass> getHashMap() {
        return new HashMap<>(hashmapa);
    }

    @Override
    public void eatGrass(Vector2d position) {
        grassArray.remove(hashmapa.get(position));
        hashmapa.remove(position);
    }

    @Override
    public void setDeath(Vector2d position) {

    }
}
