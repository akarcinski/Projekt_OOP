import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    private Label gvActEnergy;

    @FXML
    private Label gvActGenome;

    @FXML
    private Label gvActiveGenome;
    @FXML
    private Label gvChildrens;
    @FXML
    private Label gvDeathDay;
    @FXML
    private Label gvEatenGrasses;
    @FXML
    private Label gvAliveDays;
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
    public Button gvStartBT;

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
    private boolean save;
    ArrayList<Animal> animals;
    ArrayList<Grass> grasses;
    boolean[][] pref;
    Thread game;
    Animal selected;
    @FXML
    void gridSelect(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        Integer colIndex;
        Integer rowIndex;
        if (clickedNode != gvMap) {
            Node parent = clickedNode.getParent();
            while (parent != gvMap) {
                clickedNode = parent;
                parent = clickedNode.getParent();
            }
            colIndex = GridPane.getColumnIndex(clickedNode);
            rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);

            for (Animal animal : animals) {
                if (animal.getPosition().getX() == colIndex && animal.getPosition().getY() == rowIndex){
                    System.out.println("znaleziono gada");
                    selected = animal;
                }
            }

        }
    }

    private boolean showing=false;
    private boolean tmp= false;
    @FXML
    void gvShowWithGenome(ActionEvent event) throws FileNotFoundException {
        if (tmp) {
            showing = !showing;
            updateGrid();
        }
    }

    @FXML
    void gvUnselect(ActionEvent event) {
        selected = null;
        gvActGenome.setText("nothing");
        gvActiveGenome.setText("0");
        gvActEnergy.setText("0");
        gvChildrens.setText("0");


        gvDeathDay.setText("0");

        gvEatenGrasses.setText("0");

        gvAliveDays.setText("0");
    }

    @FXML
    void btnClose(ActionEvent event){
        System.out.println("exit");
        engine.kill();
        try {
            Stage s = (Stage)gvStartBT.getScene().getWindow();
            s.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnStart(ActionEvent event) throws FileNotFoundException, InterruptedException {
        tmp = true;
        if (gvStartBT.getText().equals("START")) {
            System.out.println("start");
            selected = null;
            gvStartBT.setText("STOP");
            setGrid();
            //updateGrid();
            gvMap.setGridLinesVisible(true);
            engine = new Engine(map, this, speed, save);
            game = new Thread(this.engine);
            game.start();
        }
        else if(gvStartBT.getText().equals("STOP")) {
            System.out.println("stop");
            gvStartBT.setText("RESUME");
            engine.stop();
        }
        else {
            System.out.println("resume");
            gvStartBT.setText("STOP");
            engine.start();
        }

    }

    public void receiveData(WorldMap map, int width, int height, long speed, boolean save) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.save = save;
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
            if (selected != null){
                gvActGenome.setText(Arrays.toString(selected.getGenes()));
                gvActiveGenome.setText(Integer.toString(selected.nextMove()));
                gvActEnergy.setText(Integer.toString(selected.getEnergy()));
                gvChildrens.setText(Integer.toString(selected.getKids()));

                if (selected.getDeath() == -1)
                    gvDeathDay.setText("Zyje");
                else
                    gvDeathDay.setText(Integer.toString(selected.getDeath()));

                gvEatenGrasses.setText(Integer.toString(selected.getEatenGrass()));
                if (selected.getDeath() == -1)
                    gvAliveDays.setText(Integer.toString(map.getDay() - selected.getBorn()));
                else
                    gvAliveDays.setText(Integer.toString(selected.getDeath()));
            }

            gvLiveAnimalNum.setText(Integer.toString(map.getLiveAnimalNum()));
            gvGrassNum.setText(Integer.toString(map.getGrassNum()));
            gvFreeFields.setText(Integer.toString(map.freeFields()));
            gvAvgEnergy.setText(Integer.toString(map.avgEnergy()));
            gvAvgLive.setText(Integer.toString(map.avgLive()));
            gvMostPopularGenes.setText(Arrays.toString(map.mostPopularGenes()));
            gvDay.setText(Integer.toString(map.getDay()));

            gvMap.getChildren().clear();
            gvMap.setGridLinesVisible(true);
            boolean[][] grid = new boolean[width][height];
            for(int i = 0; i<height;i++)
                for (int j = 0; j < width; j++) {
                    grid[j][i] = false;
                }
            //gvMap.add(new Label("123"),0,0);

            if (selected != null) {
                VBox vBox = animalElement(pref[selected.getPosition().getX()][selected.getPosition().getY()], selected.getDirection(), selected.getEnergy(), true);
                gvMap.add(vBox, selected.getPosition().getX(), selected.getPosition().getY());
                grid[selected.getPosition().getX()][selected.getPosition().getY()] = true;
            }



            for (Animal animal : animals) {
                if (animal==null || grid[animal.getPosition().getX()][animal.getPosition().getY()])
                    continue;
                VBox vBox;
                if (showing) {
                    if (Arrays.equals(animal.getGenes(), map.mostPopularGenes()))
                        vBox = animalElement(pref[animal.getPosition().getX()][animal.getPosition().getY()], animal.getDirection(), animal.getEnergy(), true);
                    else
                        vBox = animalElement(pref[animal.getPosition().getX()][animal.getPosition().getY()], animal.getDirection(), animal.getEnergy(), false);
                }
                else
                    vBox = animalElement(pref[animal.getPosition().getX()][animal.getPosition().getY()], animal.getDirection(), animal.getEnergy(), false);

                gvMap.add(vBox, animal.getPosition().getX(), animal.getPosition().getY());
                grid[animal.getPosition().getX()][animal.getPosition().getY()] = true;
                //gvMap.styleProperty().set("");
            }
            for (Grass grass : grasses) {
                if (grass == null || grid[grass.getPosition().getX()][grass.getPosition().getY()])
                    continue;
                VBox vBox = grassElement(pref[grass.getPosition().getX()][grass.getPosition().getY()]);
                gvMap.add(vBox, grass.getPosition().getX(), grass.getPosition().getY());
                grid[grass.getPosition().getX()][grass.getPosition().getY()]=true;
            }
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if(grid[x][y])
                        continue;

                    VBox vBox = null;
                    try {
                        vBox = groundElement(pref[x][y]);
                    } catch (FileNotFoundException exception) {
                        exception.printStackTrace();
                    }
                    gvMap.add(vBox, x, y);
                }
            }


        });
    }

    public VBox animalElement(Boolean type, Direction dir, int energy, boolean flag) {
        VBox vBox = new VBox();
        Image image = null;
        ImageView imageView;

        try {
            if (type) {
                switch (dir) {
                    case NN -> image = new Image(new FileInputStream("src/main/resources/animal0Jungle.png"));
                    case NE -> image = new Image(new FileInputStream("src/main/resources/animal1Jungle.png"));
                    case EE -> image = new Image(new FileInputStream("src/main/resources/animal2Jungle.png"));
                    case SE -> image = new Image(new FileInputStream("src/main/resources/animal3Jungle.png"));
                    case SS -> image = new Image(new FileInputStream("src/main/resources/animal4Jungle.png"));
                    case SW -> image = new Image(new FileInputStream("src/main/resources/animal5Jungle.png"));
                    case WW -> image = new Image(new FileInputStream("src/main/resources/animal6Jungle.png"));
                    case NW -> image = new Image(new FileInputStream("src/main/resources/animal7Jungle.png"));
                }
            }
            else {
                switch (dir) {
                    case NN -> image = new Image(new FileInputStream("src/main/resources/animal0Steppe.png"));
                    case NE -> image = new Image(new FileInputStream("src/main/resources/animal1Steppe.png"));
                    case EE -> image = new Image(new FileInputStream("src/main/resources/animal2Steppe.png"));
                    case SE -> image = new Image(new FileInputStream("src/main/resources/animal3Steppe.png"));
                    case SS -> image = new Image(new FileInputStream("src/main/resources/animal4Steppe.png"));
                    case SW -> image = new Image(new FileInputStream("src/main/resources/animal5Steppe.png"));
                    case WW -> image = new Image(new FileInputStream("src/main/resources/animal6Steppe.png"));
                    case NW -> image = new Image(new FileInputStream("src/main/resources/animal7Steppe.png"));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return vBox;
        }

        //ProgressBar energyBar = new ProgressBar(0.5);
        //energyBar.setStyle("-fx-accent: green");
        //energyBar.setProgress(0.5);
        Label energyIndicator = new Label(Integer.toString(energy));
        energyIndicator.setPrefSize(cellWidth,10);
        energyIndicator.setMaxSize(cellWidth, 10);
        energyIndicator.setAlignment(Pos.CENTER);
        if (flag) {
            energyIndicator.setPrefSize(cellWidth, 3);
            energyIndicator.setMaxSize(cellWidth, 3);
            energyIndicator.setStyle("-fx-border-color: red;\n" +
                    //"-fx-border-insets: 5;\n" +
                    "-fx-border-width: 2;\n" +
                    "-fx-border-style: solid;\n");
        }

        imageView = new ImageView(image);
        imageView.setFitHeight(cellHeight-17);
        imageView.setFitWidth(cellWidth);
        //vBox.setSpacing(5);
        vBox.getChildren().addAll(imageView, energyIndicator);
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
