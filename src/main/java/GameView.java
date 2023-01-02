import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GameView {
    @FXML
    private Label gvDay;

    @FXML
    private Label gvAvgEnergy;

    @FXML
    private Label gvAvgLive;

    @FXML
    private Label gvFreeFields;

    @FXML
    private Label gvGrassNum;

    @FXML
    private Label gvLiveAnimalNum;

    @FXML
    private Label gvMostPopularGenes;

    @FXML
    private GridPane gvMap;

    @FXML
    private Button gvStartBT;

    private WorldMap map;
    private int width;
    private int height;
    private long speed;
    private Engine engine;
    // trzeba zmienić na prefHeight i prefWidth
    private final double gridHeight = 500.0;
    private final double gridWidth = 700.0;
    private double cellWidth;
    private double cellHeight;
    ArrayList<Animal> animals;
    ArrayList<Grass> grasses;
    boolean[][] pref;
    Thread game;
    @FXML
    void btnStart(ActionEvent event) throws FileNotFoundException, InterruptedException {
        if (gvStartBT.getText().equals("START")) {
            gvStartBT.setText("STOP");
            setGrid();
            //updateGrid();
            gvMap.setGridLinesVisible(true);
            engine = new Engine(map, this, speed);
            game = new Thread(this.engine);
            game.start();
        }
        else if(gvStartBT.getText().equals("STOP")) {
            System.out.println("stop");
            gvStartBT.setText("RESUME");
            engine.stop();
//            synchronized (game){
//                try {
//                    game.wait();
//                } catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
            //game.stop();
        }
        else {
            gvStartBT.setText("STOP");
            engine.run();
//            synchronized (game){
//
//                game.notifyAll();
//            }
            //game.notifyAll();
        }

    }

    public void closeWindowEvent(){
        System.out.println("closing");
    }

    public void receiveData(WorldMap map, int width, int height, long speed) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.speed = speed;

        animals = this.map.getAnimalList();
        grasses = this.map.getGrassList();
        pref = this.map.getPreferedFields();
    }
    private void setGrid() {
        gvMap.getChildren().clear();
        cellWidth = gridWidth/width;
        cellHeight = gridHeight/height;

        for (int i = 0; i<height; i++)
            gvMap.getRowConstraints().add(new RowConstraints(cellHeight));
        for (int i = 0; i < width; i++)
            gvMap.getColumnConstraints().add(new ColumnConstraints(cellWidth));
    }

    public void updateGrid() throws FileNotFoundException {
        Platform.runLater(() -> {
            gvLiveAnimalNum.setText(Integer.toString(map.getLiveAnimalNum()));
            gvGrassNum.setText(Integer.toString(map.getGrassNum()));
            gvFreeFields.setText(Integer.toString(map.freeFields()));
            gvAvgEnergy.setText(Integer.toString(map.avgEnergy()));
            gvAvgLive.setText(Integer.toString(map.avgLive()));
            gvMostPopularGenes.setText(Arrays.toString(map.mostPopularGenes()));
            gvDay.setText(Integer.toString(map.getDay()));

            gvMap.getChildren().clear();
            //gvMap.add(new Label("123"),0,0);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    VBox vBox = null;
                    try {
                        vBox = groundElement(pref[x][y]);
                    } catch (FileNotFoundException exception) {
                        exception.printStackTrace();
                    }
                    gvMap.add(vBox, x, y);
                }
            }

            for (Grass grass : grasses) {
                VBox vBox = grassElement(pref[grass.getPosition().getX()][grass.getPosition().getY()]);
                gvMap.add(vBox, grass.getPosition().getX(), grass.getPosition().getY());
            }

            for (Animal animal : animals) {
                VBox vBox = animalElement(pref[animal.getPosition().getX()][animal.getPosition().getY()], animal.getDirection());
                gvMap.add(vBox, animal.getPosition().getX(), animal.getPosition().getY());
                gvMap.styleProperty().set("");
            }
        });
    }

    public VBox animalElement(Boolean type, Direction dir) {
        VBox vBox = new VBox();
        Image image = null;
        ImageView imageView;

        try {
            if (type) {
                switch (dir) {
                    case NN -> {
                        image = new Image(new FileInputStream("src/main/resources/animal0Jungle.png"));}
                    case NE -> {
                        image = new Image(new FileInputStream("src/main/resources/animal1Jungle.png"));}
                    case EE -> {
                        image = new Image(new FileInputStream("src/main/resources/animal2Jungle.png"));}
                    case SE -> {
                        image = new Image(new FileInputStream("src/main/resources/animal3Jungle.png"));}
                    case SS -> {
                        image = new Image(new FileInputStream("src/main/resources/animal4Jungle.png"));}
                    case SW -> {
                        image = new Image(new FileInputStream("src/main/resources/animal5Jungle.png"));}
                    case WW -> {
                        image = new Image(new FileInputStream("src/main/resources/animal6Jungle.png"));}
                    case NW -> {
                        image = new Image(new FileInputStream("src/main/resources/animal7Jungle.png"));}
                }
            }
            else {
                switch (dir) {
                    case NN -> {
                        image = new Image(new FileInputStream("src/main/resources/animal0Steppe.png"));}
                    case NE -> {
                        image = new Image(new FileInputStream("src/main/resources/animal1Steppe.png"));}
                    case EE -> {
                        image = new Image(new FileInputStream("src/main/resources/animal2Steppe.png"));}
                    case SE -> {
                        image = new Image(new FileInputStream("src/main/resources/animal3Steppe.png"));}
                    case SS -> {
                        image = new Image(new FileInputStream("src/main/resources/animal4Steppe.png"));}
                    case SW -> {
                        image = new Image(new FileInputStream("src/main/resources/animal5Steppe.png"));}
                    case WW -> {
                        image = new Image(new FileInputStream("src/main/resources/animal6Steppe.png"));}
                    case NW -> {
                        image = new Image(new FileInputStream("src/main/resources/animal7Steppe.png"));}
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return vBox;
        }

        imageView = new ImageView(image);
        imageView.setFitHeight(cellHeight);
        imageView.setFitWidth(cellWidth);
        vBox.getChildren().add(imageView);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public VBox grassElement(Boolean type) {
        VBox vBox = new VBox();
        Image image;
        ImageView imageView;

        try {
            if (type)
                image = new Image(new FileInputStream("src/main/resources/grassJungle.png"));
            else
                image = new Image(new FileInputStream("src/main/resources/grassSteppe.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return vBox;
        }

        imageView = new ImageView(image);
        imageView.setFitHeight(cellHeight);
        imageView.setFitWidth(cellWidth);
        vBox.getChildren().add(imageView);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public VBox groundElement(Boolean type) throws FileNotFoundException {
        VBox vBox = new VBox();
        Image image;
        ImageView imageView;

        try {
            if (type)
                image = new Image(new FileInputStream("src/main/resources/jungle.png"));
            else
                image = new Image(new FileInputStream("src/main/resources/steppe.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return vBox;
        }

        imageView = new ImageView(image);
        imageView.setFitHeight(cellHeight);
        imageView.setFitWidth(cellWidth);
        vBox.getChildren().add(imageView);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
