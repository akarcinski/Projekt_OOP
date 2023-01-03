import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Engine implements Runnable{
    private WorldMap mapa;
    private GameView gameView;
    private long speed;
    public volatile boolean flag;
    public volatile boolean terminate;
    public Engine(WorldMap mapa, GameView gameView, long speed){
        this.mapa=mapa;
        this.gameView = gameView;
        this.speed = speed;
        flag = false;
        terminate = false;
    }


    @Override
    public void run() {
        //Platform.runLater(); TUTAJ RYSOWANIE MAPY
        flag = false;
        //System.out.println("dziala");
        try{
            while (mapa.getAnimalList().size() > 0) {
                while (flag) {
                    if(terminate)
                        break;

                    Thread.sleep(100);
                }

                if(terminate)
                    break;
                //System.out.println("dziala");

                mapa.cleanMap();
                mapa.moveAnimals();
                mapa.consumption();
                mapa.copulation();
                mapa.placeGrass();
                mapa.changeGenesAnimals();
                gameView.updateGrid();
                //System.out.println("liczba fields "+mapa.freeFields());
                Thread.sleep(speed);

            }


            //Platform.runLater(); TUTAJ RÓWNIEŻ RYSOWANIE MAPY
        }catch(InterruptedException | FileNotFoundException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stop(){
        flag = true;
    }
    public void start(){ flag = false;}
    public void kill(){
        terminate = true; flag = false;}
}
