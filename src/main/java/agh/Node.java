package agh;

import java.util.ArrayList;

public class Node {
    private boolean isJungle;
    public ArrayList<Animal> animal;
    public Grass grass;

    public Node(boolean isJungle) {
        this.isJungle=isJungle;
    }
}
