import java.io.*;
import java.util.Arrays;

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
        try {
            File file = new File("statistics.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try{
            while (mapa.getAnimalList().size() > 0) {
                pw.append(mapa.getLiveAnimalNum() + ", " + mapa.getGrassNum() + ", " + mapa.freeFields() + ", " + Arrays.toString(mapa.mostPopularGenes()) + ", " + mapa.avgEnergy() + ", " + mapa.avgLive());
                pw.flush();
                if(flag)
                    break;
                System.out.println("dziala");
                mapa.cleanMap();
                mapa.moveAnimals();
                mapa.consumption();
                mapa.copulation();
                mapa.placeGrass();
                mapa.changeGenesAnimals();
                gameView.updateGrid();
                Thread.sleep(speed);

            }
            pw.close();


            //Platform.runLater(); TUTAJ RÓWNIEŻ RYSOWANIE MAPY
        }catch(InterruptedException | FileNotFoundException interruptedException) {
            interruptedException.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop(){
        flag = true;
    }
}
