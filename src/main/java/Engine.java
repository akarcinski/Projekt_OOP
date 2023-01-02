import java.io.FileNotFoundException;

public class Engine implements Runnable{
    private WorldMap mapa;
    private GameView gameView;
    private long speed;
    public volatile boolean flag;
    public Engine(WorldMap mapa, GameView gameView, long speed){
        this.mapa=mapa;
        this.gameView = gameView;
        this.speed = speed;
        flag = false;
    }

    @Override
    public void run() {
        //Platform.runLater(); TUTAJ RYSOWANIE MAPY
        flag = false;
        System.out.println("dziala");
        try{
            while (mapa.getAnimalList().size() > 0) {
                if(flag)
                    break;
                System.out.println("dziala");
                gameView.updateGrid();
                mapa.cleanMap();
                mapa.moveAnimals();
                mapa.consumption();
                mapa.copulation();
                mapa.placeGrass();
                mapa.changeGenesAnimals();

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
}
