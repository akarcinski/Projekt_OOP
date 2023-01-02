import java.util.*;

public class ForestedBiome implements IBiomeType{
    private int width;
    private int height;
    private int low;
    private int high;
    private int stage_num;
    private boolean[][] preferedFields;
    private final Random rand=new Random();
    private ArrayList<Grass> grassArray = new ArrayList<>();
    private Map<Vector2d, Grass> hashmapa = new HashMap<>();
    public ForestedBiome(int width, int height, int start_num_grass, int stage_num_grass){
        this.width=width;
        this.height=height;
        this.low=(int) Math.round(height*0.4);
        this.high=(int) Math.round(height*0.6);
        this.preferedFields = new boolean[width][height];
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (j<low | j>high){
                    preferedFields[i][j]=false;
                }
                else {
                    preferedFields[i][j]=true;
                }
            }
        }
        this.stage_num=stage_num_grass;
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
            if (placed_grass>=start_num_grass | grassArray.size()==width*height) break;
        }
    }

    @Override
    public boolean[][] getPreferedFields() {
        boolean[][] copy = new boolean[width][];
        for (int i=0; i<width; i++){
            copy[i]=preferedFields[i].clone();
        }
        return copy;
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
        return grassArray;
    }

    @Override
    public HashMap<Vector2d, Grass> getHashMap() {
        return new HashMap<>(hashmapa);
    }

    @Override
    public boolean eatGrass(Vector2d position) {
        if (hashmapa.containsKey(position)) {
            grassArray.remove(hashmapa.get(position));
            hashmapa.remove(position);
            return true;
        }
        return false;
    }

    @Override
    public void setDeath(Vector2d position) {

    }

    @Override
    public int getSize() {
        return grassArray.size();
    }

    @Override
    public void update() {

    }
}
