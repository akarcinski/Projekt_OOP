import javax.swing.*;
import java.util.Arrays;

public class Earth implements IMapType{
    int width;
    int height;
    public Earth(int width, int height){
        this.width=width;
        this.height=height;
    }

    @Override
    public void move(Animal animal) {
//        System.out.println(Arrays.toString(animal.getGenes()));
//        System.out.println(animal.getDirection().ordinal());
//        System.out.println(Direction.values[(animal.getDirection().ordinal())]);
//        System.out.println(animal.nextMove());
//        System.out.println(Direction.values[(animal.getDirection().ordinal()+animal.nextMove())%8]);
        Direction direction = Direction.values[(animal.getDirection().ordinal()+animal.nextMove())%8];
        Vector2d nextposition = animal.getPosition().add(Direction.toVector2d(direction));
        if (nextposition.getX()==-1) {
            nextposition=nextposition.add(new Vector2d(width,0));
        } else if (nextposition.getX()==width) {
            nextposition=nextposition.add(new Vector2d(-width,0));
        }
        if (nextposition.getY()==height){
            animal.setDirection(Direction.values[(animal.direction.ordinal()+4)%8]);
        } else if (nextposition.getY()==-1) {
            animal.setDirection(Direction.values[(animal.direction.ordinal()+4)%8]);
        }
        else {
            animal.move(nextposition, direction, 1);
        }
    }
}
