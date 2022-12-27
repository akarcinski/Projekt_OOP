package agh;

import java.util.*;

public class ForestedBiome implements IBiomeType{
    private int width;
    private int height;
    private int low;
    private int high;
    private int stage_num;
    private final Random rand=new Random();
    private ArrayList<Grass> grassArray = new ArrayList<>();
    private Map<Vector2d, Grass> hashmapa = new HashMap<>();
    public ForestedBiome(int width, int height, int start_num, int stage_num){
        this.width=width;
        this.height=height;
        this.low=(int) Math.round(height*0.4);
        this.high=(int) Math.round(height*0.6);
        this.stage_num=stage_num;
        int placed_grass=0;
        int x,y;
        while (true){
            x=rand.nextInt(width);
            y=rand.nextInt(height);
            Vector2d pozycja = new Vector2d(x,y);
            if (!hashmapa.containsKey(pozycja)){
                if (y<low | y>high){
                    if (rand.nextInt(10)<8){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        hashmapa.put(pozycja, trawa);
                        placed_grass+=1;
                    }
                }
                else {
                    if (rand.nextInt(10)<2){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        hashmapa.put(pozycja, trawa);
                        placed_grass+=1;
                    }
                }
            }
            if (placed_grass>=start_num | grassArray.size()==width*height) break;
        }
    }

    @Override
    public void placeGrass() {
        int placed_grass=0;
        int x,y;
        while (true){
            x=rand.nextInt(width);
            y=rand.nextInt(height);
            Vector2d pozycja = new Vector2d(x,y);
            if (!hashmapa.containsKey(pozycja)){
                if (y<low | y>high){
                    if (rand.nextInt(10)<8){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        hashmapa.put(pozycja, trawa);
                        placed_grass+=1;
                    }
                }
                else {
                    if (rand.nextInt(10)<2){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        hashmapa.put(pozycja, trawa);
                        placed_grass+=1;
                    }
                }
            }
            if (placed_grass>=stage_num | grassArray.size()==width*height) break;
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
