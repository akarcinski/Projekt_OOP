package agh;

public class Animal{
    protected Vector2d position;
    private int energy;
    protected Direction direction;
    protected int[] genes;
    protected int idxGene;
    private final int born;
    private int kids;
    private int death=-1;
    public Animal(Vector2d position, int energy, Direction direction, int[] genes, int idxGene, int born) {
        this.position = position;
        this.energy = energy;
        this.direction = direction;
        this.genes = genes;
        this.born=born;
        this.idxGene=idxGene;
        this.kids=0;
    }

    public int getDeath() {
        return death;
    }
    public void setDeath(int death){
        this.death=death;
    }

    public int getKids() {
        return kids;
    }
    public void addKid(){
        kids+=1;
    }

    public int getBorn() {
        return born;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public void move(Vector2d position, Direction direction){
        this.position=position;
        this.direction=direction;
    }
    public int nextMove(){
//        idxGene++;
//        idxGene %= genes.length;
        return genes[idxGene];
    }
    public void setDirection(Direction direction){
        this.direction=direction;
    }

    public int[] getGenes() {
        return genes;
    }
}
