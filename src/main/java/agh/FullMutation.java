package agh;

import java.util.Random;

public class FullMutation implements IMutationType{
    int lengthGene;
    Random rand = new Random();
    public FullMutation(int lengthGene){
        this.lengthGene=lengthGene;
    }

    @Override
    public void mutate(Animal animal, int index) {
        animal.genes[index]= rand.nextInt(8);
    }
}
