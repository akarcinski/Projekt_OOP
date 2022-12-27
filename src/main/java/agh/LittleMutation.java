package agh;

import java.util.Random;

public class LittleMutation implements IMutationType{
    int lengthGene;
    Random rand = new Random();
    public LittleMutation(int lengthGene){
        this.lengthGene=lengthGene;
    }

    @Override
    public void mutate(Animal animal) {
        for (int i=0; i<lengthGene; i++){
            if(rand.nextInt(2)<1){
                animal.genes[i]=(animal.genes[i]-1)%8;
            }
            else animal.genes[i]=(animal.genes[i]+1)%8;
        }
    }
}
