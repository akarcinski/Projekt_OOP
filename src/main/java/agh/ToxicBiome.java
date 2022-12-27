package agh;

import java.util.*;

public class ToxicBiome implements IBiomeType{
    private int height;
    private int width;
    private Integer[][] deathArray;
    private ArrayList<Grass> grassArray = new ArrayList<>();
    private Map<Vector2d, Grass> haszmapa=new HashMap<>();
    private Random rand = new Random();
    private int stage_num;
    public ToxicBiome(int width, int height, int start_num, int stage_num){
        this.width=width;
        this.height=height;
        this.stage_num=stage_num;
        deathArray = new Integer[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                deathArray[i][j]=0;
            }
        }
        int placed_grass=0;
        int x,y;
        while (true){
            x=rand.nextInt(width);
            y=rand.nextInt(height);
            Vector2d pozycja = new Vector2d(x,y);
            if (!haszmapa.containsKey(pozycja)){
                if (rand.nextInt(10)<8){
                    Grass trawa = new Grass(pozycja);
                    grassArray.add(trawa);
                    haszmapa.put(pozycja, trawa);
                    placed_grass+=1;
                }
            }
            if (placed_grass>=stage_num | grassArray.size()==width*height) break;
        }
    }
    private int getMinDeath(){
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
        int placed_grass=0;
        int x,y;
        int minimum=getMinDeath();
        while (true){
            x=rand.nextInt(width);
            y=rand.nextInt(height);
            Vector2d pozycja = new Vector2d(x,y);
            if (!haszmapa.containsKey(pozycja)){
                if (deathArray[x][y]==minimum){
                    if (rand.nextInt(10)<8){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        haszmapa.put(pozycja, trawa);
                        placed_grass+=1;
                    }
                }
                else {
                    if (rand.nextInt(10)<2){
                        Grass trawa = new Grass(pozycja);
                        grassArray.add(trawa);
                        haszmapa.put(pozycja, trawa);
                        placed_grass+=1;
                    }
                }
            }
            if (placed_grass>=stage_num | grassArray.size()==width*height) break;
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
