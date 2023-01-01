import java.util.Random;

public class CrazyBehaviour implements IBehaviourType{
    public CrazyBehaviour(){}
    @Override
    public void nextGene(Animal animal) {
        Random rand=new Random();
        if (rand.nextInt(10)<8){
            animal.idxGene=(animal.idxGene+1)%8;
        }
        else animal.idxGene=rand.nextInt(8);
    }
}
