package agh;

public class NormalBehaviour implements IBehaviourType{
    public NormalBehaviour(){}
    @Override
    public void nextGene(Animal animal) {
        animal.idxGene= (animal.idxGene+1)%8;
    }
}
