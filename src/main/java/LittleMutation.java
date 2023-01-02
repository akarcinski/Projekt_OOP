import java.util.Random;

public class LittleMutation implements IMutationType{
    int lengthGene;
    Random rand = new Random();
    public LittleMutation(int lengthGene){
        this.lengthGene=lengthGene;
    }

    @Override
    public void mutate(Animal animal, int index) {
        if(rand.nextInt(2)<1){
            animal.genes[index]=(animal.genes[index]-1)%8;
            if (animal.genes[index]<0){
                animal.genes[index]+=8;
            }
        }
        else animal.genes[index]=(animal.genes[index]+1)%8;
    }
}
