package agh;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

abstract public class WorldMap {
    private int width;
    private int height;
    private Node[][] map;

    private int grassNum;       // startowa liczba roślin
    private int restoreEnergy;  // energia zapewniana przez zjedzenie jednej rośliny
    private int growingRate;    // liczba roślin wyrastająca każdego dnia
    private int animalNum=0;      // startowa liczba zwierzaków
    private int startEnergy;    // startowa energia zwierzaków
    private int maxEnergy;      // energia konieczna, by uznać zwierzaka za najedzonego
    private int energyToChild;   // energia rodziców zużywana by stworzyć potomka
    private int minMutation;     // minimalna liczba mutacji u potomków
    private int maxMutation;     // maksymalna liczba mutacji u potomków
    private int genomeLength;    // długość genomu zwierzaków

    private IMapType mapType;            // typ mapy:        kula ziemska, piekielny portal
    private IBiomeType biomeType;        // typ zalesienia:  zalesione równiki, toksyczne trupy
    private IMutationType mutationType;  // typ mutacji:     pełna losowość, korekta
    private IBehaviourType behaviourType;// typ zachowania:  pełna predestynacja, nieco szaleństwa

    private ArrayList<Grass> grassList = new ArrayList<>();
    private ArrayList<Animal> animalList = new ArrayList<>();
    public WorldMap(int width, int height, int grassNum, int restoreEnergy, int growingRate, int animalNum, int startEnergy, int maxEnergy
            , int energyToChild, int minMutation, int maxMutation, int genomeLength,
                    IMapType mapType, IBiomeType biomeType, IMutationType mutationType, IBehaviourType behaviourType){
        this.width=width;
        this.height=height;
        this.mapType=mapType;
        this.biomeType=biomeType;
        this.mutationType=mutationType;
        this.behaviourType=behaviourType;
        this.restoreEnergy=restoreEnergy;
        this.growingRate=growingRate;
        this.startEnergy=startEnergy;
        this.maxEnergy=maxEnergy;
        this.energyToChild=energyToChild;
        this.minMutation=minMutation;
        this.maxMutation=maxMutation;
        this.genomeLength=genomeLength;
        this.grassNum=grassNum;
        Random rand = new Random();
        for(int i=0; i<animalNum; i++){
            int x=rand.nextInt(width);
            int y= rand.nextInt(height);

            int[] genes=new int[genomeLength];
            for(int j=0; j<genomeLength; j++){
                genes[j]=rand.nextInt()%8;
            }
            this.animalList.add(new Animal(new Vector2d(x,y), maxEnergy, Direction.values[rand.nextInt(8)], genes));
        }
    }
    public void cleanMap(){ // czysci plansze z martwych zwierzat
        for(Animal animal: animalList){
            if (animal.getEnergy()<=0){
                biomeType.setDeath(animal.getPosition());
                animalList.remove(animal);
            }
        }
    }
    public void changeGenesAnimals(){ // kolejnosc wykonywania genow zaleznie od zachowania jakie jest przyjete
        for (Animal animal: animalList){
            behaviourType.nextGene(animal);
        }
    }
    public void mutateAnimals(){ // mutuje geny w zaleznosci od przyjetej mutacji
        for (Animal animal: animalList){
            mutationType.mutate(animal);
        }
    }
    public void moveAnimals(){ // przemieszcza zwierzeta w zaleznosci od przyjetej mapy i jej ograniczen
        for (Animal animal: animalList){
            mapType.move(animal);
        }
    }
    public void consumption(){}

    public void copulation(){

    }
    public void placeGrass(){
        biomeType.placeGrass();
    }
}