package agh;

public class Animal{
    protected Vector2d position;
    private int energy;
    protected Direction direction;
    protected int[] genes;
    protected int idxGene = -1;
    public Animal(Vector2d position, int energy, Direction direction, int[] genes) {
        this.position = position;
        this.energy = energy;
        this.direction = direction;
        this.genes = genes;
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


}
