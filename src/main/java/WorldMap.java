import java.util.*;

public class WorldMap {
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

    private ArrayList<Grass> grassList = new ArrayList<>();
    private ArrayList<Animal> animalList = new ArrayList<>();
    private ArrayList<Animal> deadAnimalList= new ArrayList<>();
    private Set<Animal> animalSet = new TreeSet<>(new AnimalComparator());
    private Set<Animal> geneSet = new TreeSet<>(new GeneComparator());
    private int day;
    private Random rand = new Random();
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
        this.day=1;
        this.grassList=biomeType.getGrassArray();
        Random rand = new Random();
        for(int i=0; i<animalNum; i++){
            int x=rand.nextInt(width);
            int y= rand.nextInt(height);

            int[] genes=new int[genomeLength];
            for(int j=0; j<genomeLength; j++){
                genes[j]=rand.nextInt(8);
            }
            Animal animal = new Animal(new Vector2d(x,y), maxEnergy, Direction.values[rand.nextInt(8)], genes,0, day);
            this.animalList.add(animal);
            this.animalSet.add(animal);
            this.geneSet.add(animal);
        }
    }

    // DO SIMULATION ENGINE
    public void cleanMap(){ // czysci plansze z martwych zwierzat
        boolean clean=true;
        while (clean) {
            clean=false;
            for (Animal animal : animalList) {
                if (animal.getEnergy() <= 0) {
                    clean=true;
                    biomeType.setDeath(animal.getPosition());
                    animalList.remove(animal);
                    animalSet.remove(animal);
                    deadAnimalList.add(animal);
                    break;
                }
            }
        }
        biomeType.update();
    }
    public void changeGenesAnimals(){ // kolejnosc wykonywania genow zaleznie od zachowania jakie jest przyjete (używać na samym koncu iteracji)
        for (Animal animal: animalList){
            behaviourType.nextGene(animal);
        }
    }
    public void moveAnimals(){ // przemieszcza zwierzeta w zaleznosci od przyjetej mapy i jej ograniczen
        for (Animal animal: animalList){
            mapType.move(animal);
            updateTreeSet(animal);
        }
    }
    public void consumption(){ // zwierzeta sobie jedza
        boolean zmiana=true;
        while(zmiana) {
            zmiana=false;
            for (Animal animal : animalSet) {
                if (animal.getEnergy() < maxEnergy) {
                    if (biomeType.eatGrass(animal.getPosition())) {
                        zmiana=true;
                        animal.addEnergy(restoreEnergy);
                        animal.addNumGrass();
                        updateTreeSet(animal);
                    }
                }
                if (zmiana) break;
            }
        }
    }

    public void copulation(){
        ArrayList<Animal> childlist = new ArrayList<>();
        boolean change;
        while (true){
            change=false;
            for(Animal animal1: animalList){
                for (Animal animal2: animalList){
                    if (animal1!=animal2 & animal1.getPosition().equals(animal2.getPosition()) & animal1.getEnergy()>=maxEnergy & animal2.getEnergy()>=maxEnergy){
                        childlist.add(child(animal1, animal2));
                        change=true;
                        updateTreeSet(animal1);
                        updateTreeSet(animal2);
                    }
                    if (change) break;
                }
                if (change) break;
            }
            if (!change) break;
        }
        for (Animal animal: childlist){
            animalList.add(animal);
            animalSet.add(animal);
            geneSet.add(animal);
        }
    }
    public void placeGrass(){
        biomeType.placeGrass();
        day+=1;
    }

    // PRZYDATNE GETY
    public ArrayList<Animal> getAnimalList(){
        return animalList;
    }

    public boolean[][] getPreferedFields(){
        return biomeType.getPreferedFields();
    }

    public ArrayList<Grass> getGrassList(){
        return biomeType.getGrassArray();
    }
    public int getDay(){return day;}
    // POMOCNICZE FUNKCJE
    private Animal child(Animal animal1, Animal animal2){
        int mianownik = animal1.getEnergy()+animal2.getEnergy();
        int przeciecie = (int)(animal1.getEnergy()/mianownik);
        int[] genes=new int[genomeLength];
        int[] animal1genes=animal1.getGenes();
        int[] animal2genes=animal2.getGenes();
        if (rand.nextBoolean()){
            for (int i=0; i<przeciecie; i++){
                genes[i]=animal1genes[i];
            }
            for (int i=przeciecie; i<genomeLength;i++){
                genes[i]=animal2genes[i];
            }
        }
        else {
            for (int i=0; i<genomeLength-przeciecie;i++){
                genes[i]=animal2genes[i];
            }
            for (int i=genomeLength-przeciecie; i<genomeLength; i++){
                genes[i]=animal1genes[i];
            }
        }
        int change = rand.nextInt(maxMutation-minMutation+1)+minMutation;
        Animal animal = new Animal(animal1.getPosition(), 2*energyToChild, Direction.values[rand.nextInt(8)], genes, rand.nextInt(8), day);
        if (change>0){
            int changes=0;
            boolean[] changemut = new boolean[genomeLength];
            for (int i=0; i<genomeLength; i++){
                changemut[i]=false;
            }
            int idx;
            while (true){
                idx=rand.nextInt(genomeLength);
                if (!changemut[idx]){
                    mutationType.mutate(animal, idx);
                    changemut[idx]=true;
                    changes+=1;
                }
                if (changes==change) break;
            }
        }
        animal1.addKid();
        animal2.addKid();
        animal1.addEnergy(-energyToChild);
        animal2.addEnergy(-energyToChild);
        return animal;
    }
    private void updateTreeSet(Animal animal){
        animalSet.remove(animal);
        animalSet.add(animal);
    }


    // POBIERANIE RÓŻNYCH STATYSTYK
    public int getLiveAnimalNum(){ // liczba wszystkich zwierzat
        return animalList.size();
    }
    public int getGrassNum(){ // liczba wszystkich roślin
        return biomeType.getSize();
    }
    public int freeFields(){ // liczba wolnych pól
        boolean[][] free = new boolean[width][height];
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                free[i][j]=true;
            }
        }
        for (Animal animal: animalList){
            free[animal.getPosition().getX()][animal.getPosition().getY()]=false;
        }
        for (Grass grass: biomeType.getGrassArray()){
            free[grass.getPosition().getX()][grass.getPosition().getY()]=false;
        }
        int freefields=0;
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (free[i][j]){
                    freefields+=1;
                }
            }
        }
        return freefields;
    }
    public int[] mostPopularGenes(){ // najbardziej popularny genotyp
        int idx=0;
        int maximum=0;
        int temp=1;
        Object[] genelist = geneSet.toArray();
        for (int i=1; i<genelist.length; i++){
            if (Arrays.equals(((Animal) genelist[i - 1]).getGenes(), ((Animal) genelist[i]).getGenes())){
                temp+=1;
            }
            else {
                if (maximum<temp){
                    maximum=temp;
                    idx=i-1;
                    temp=1;
                }
            }
        }
        return ((Animal) genelist[idx]).getGenes();
    }
    public int avgEnergy(){ // średni poziom energii dla żyjących zwierząt
        if (animalList.size()==0){
            return 0;
        }
        int eng=0;
        for (Animal animal: animalList){
            eng+=animal.getEnergy();
        }
        eng=(int)(eng/animalList.size());
        return eng;
    }
    public int avgLive(){ // średnia długość życia zwierząt (dla tych martwych)
        if (deadAnimalList.size()==0){
            return 0;
        }
        int age=0;
        for (Animal animal: deadAnimalList){
            age+=animal.getDeath()-animal.getBorn();
        }
        age=(int)(age/deadAnimalList.size());
        return age;
    }

}
class AnimalComparator implements Comparator<Animal>{
    @Override public int compare(Animal a1, Animal a2){
        if (a1.getEnergy()>a2.getEnergy()){
            return -1;
        } else if (a1.getEnergy()<a2.getEnergy()) {
            return 1;
        }
        else if (a1.getBorn() > a2.getBorn()) {
            return -1;
        } else if (a1.getBorn() < a2.getBorn()) {
            return 1;
        } else {
            if (a1.getKids() > a2.getKids()) {
                return -1;
            } else if (a1.getKids() < a2.getKids()) {
                return 1;
            } else return 0;
        }
    }
}
class GeneComparator implements Comparator<Animal>{
    @Override
    public int compare(Animal a1, Animal a2){
        int[] a1genes=a1.getGenes();
        int[] a2genes=a2.getGenes();
        for (int i=0; i<a1.getGenes().length; i++){
            if (a1genes[i]<a2genes[i]){
                return -1;
            } else if (a1genes[i]>a2genes[i]) {
                return 1;
            }
        }
        return 0;
    }
}