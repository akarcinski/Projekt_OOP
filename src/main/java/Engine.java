import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Engine implements Runnable{
    private WorldMap mapa;
    private GameView gameView;
    private long speed;
    private boolean saveFile=true;
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
        System.out.println("dziala");
        try{
            File file = new File("src/main/resources/agh/statistics.csv");
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            while (mapa.getAnimalList().size() > 0) {
                while (flag) {
                    if(terminate){
                        pw.close();
                        break;
                    }
                    Thread.sleep(100);
                }

                if(terminate){
                    pw.close();
                    break;
                }
                System.out.println("dziala");
                if (saveFile){
                    pw.append(mapa.getLiveAnimalNum() + ", " + mapa.getGrassNum() + ", " + mapa.freeFields() + ", " + Arrays.toString(mapa.mostPopularGenes()) + ", " + mapa.avgEnergy() + ", " + mapa.avgLive());
                    pw.flush();
                }
                mapa.cleanMap();
                mapa.moveAnimals();
                mapa.consumption();
                mapa.copulation();
                mapa.placeGrass();
                mapa.changeGenesAnimals();
                gameView.updateGrid();
//                System.out.println("liczba fields "+mapa.freeFields());
                Thread.sleep(speed);

            }


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
    public void start(){ flag = false;}
    public void kill(){
        terminate = true; flag = false;}
}
