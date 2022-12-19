package agh;

abstract public class WorldMap {
    private int width;
    private int height;
    private Node[][] map;

    private int grassNum;       // startowa liczba roślin
    private int restoreEnergy;  // energia zapewniana przez zjedzenie jednej rośliny
    private int growingRate;    // liczba roślin wyrastająca każdego dnia
    private int animalNum;      // startowa liczba zwierzaków
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
}
