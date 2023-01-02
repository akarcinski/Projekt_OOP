import java.io.FileNotFoundException;

public class Engine implements Runnable{
    private WorldMap mapa;
    private GameView gameView;
    public Engine(WorldMap mapa, GameView gameView){
        this.mapa=mapa;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        //Platform.runLater(); TUTAJ RYSOWANIE MAPY
        System.out.println("dziala");
        try{
            while (mapa.getAnimalList().size() > 0) {
                System.out.println("dziala");
                gameView.updateGrid();
                mapa.cleanMap();
                mapa.moveAnimals();
                mapa.consumption();
                mapa.copulation();
                mapa.placeGrass();
                mapa.changeGenesAnimals();
                Thread.sleep(500);

            }

            //Platform.runLater(); TUTAJ RÓWNIEŻ RYSOWANIE MAPY
        }catch(InterruptedException | FileNotFoundException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
