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

    private ArrayList<Grass> grassList;
    private ArrayList<Animal> animalList;
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
        placeGrass();
        Random rand = new Random();
        for(int i=0; i<animalNum; i++){
            int x=rand.nextInt(width);
            int y= rand.nextInt(height);
            this.animalList.add(new Animal(new Vector2d(x,y), ));
        }
    }
}