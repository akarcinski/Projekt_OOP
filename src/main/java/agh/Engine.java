package agh;

import javafx.application.Platform;

public class Engine implements Runnable{
    private WorldMap mapa;
    public Engine(WorldMap mapa){
        this.mapa=mapa;
    }

    @Override
    public void run() {
        //Platform.runLater(); TUTAJ RYSOWANIE MAPY
        while (mapa.getAnimalList().size()>0){
            mapa.cleanMap();
            mapa.moveAnimals();
            mapa.consumption();
            mapa.copulation();
            mapa.placeGrass();
            mapa.changeGenesAnimals();

            //Platform.runLater(); TUTAJ RÓWNIEŻ RYSOWANIE MAPY
        }
    }
}
