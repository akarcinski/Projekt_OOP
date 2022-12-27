package agh;

import java.util.Random;

public class SathanPortal implements IMapType{
    int width;
    int height;
    int portalEnergy;
    Random rand = new Random();
    public SathanPortal(int width, int height, int portalEnergy){
        this.width=width;
        this.height=height;
        this.portalEnergy=portalEnergy;
    }

    @Override
    public void move(Animal animal) {
        Vector2d nextposition = animal.getPosition().add(Direction.toVector2d(Direction.values[((animal.getDirection().ordinal()+animal.nextMove())%8)]));
        if (nextposition.getX()<0 | nextposition.getX()>=width | nextposition.getY()<0 | nextposition.getY()>=height){
            nextposition=new Vector2d(rand.nextInt(width),rand.nextInt(height));
            Direction direction = Direction.values[rand.nextInt(8)];
            animal.move(nextposition, direction);
            animal.addEnergy(-portalEnergy);
        }
    }
}
