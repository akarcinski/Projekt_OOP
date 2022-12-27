package agh;

public class Animal{
    private Vector2d position;
    private int energy;
    private Direction direction;
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

    public int nextMove(){
//        idxGene++;
//        idxGene %= genes.length;
        return genes[idxGene];
    }


}
