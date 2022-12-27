package agh;

import java.util.Random;

public class FullMutation implements IMutationType{
    int lengthGene;
    Random rand = new Random();
    public FullMutation(int lengthGene){
        this.lengthGene=lengthGene;
    }

    @Override
    public void mutate(Animal animal) {
        for(int i=0; i<lengthGene; i++){
            animal.genes[i]= rand.nextInt(8);
        }
    }
}
