import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import agh.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button btnMainMenu;
    @FXML
    private TextField ftAnimalNum;

    @FXML
    private ChoiceBox<String> ftBehaviourType;

    @FXML
    private ChoiceBox<String> ftBiomeType;

    @FXML
    private TextField ftEnergyToChild;

    @FXML
    private TextField ftGenomeLength;

    @FXML
    private TextField ftGrassNum;

    @FXML
    private TextField ftGrowingRate;

    @FXML
    private TextField ftHeight;

    @FXML
    private ChoiceBox<String> ftMapType;

    @FXML
    private TextField ftMaxEnergy;

    @FXML
    private TextField ftMaxMutation;

    @FXML
    private TextField ftMinMutation;

    @FXML
    private ChoiceBox<String> ftMutationType;

    @FXML
    private TextField ftRestoreEnergy;

    @FXML
    private TextField ftStartEnergy;

    @FXML
    private TextField ftWidth;

    private String[] mutations = {"lekka", "calkowita"};
    private String[] maps = {"ziemia", "pieklo"};
    private String[] biomes = {"jungle", "trupy"};
    private String[] behaviours = {"normalne", "szalone"};

    @FXML
    void btnOnClicked(ActionEvent event) throws IOException {
        int width;
        int height;
        int grassNum;       // startowa liczba roślin
        int restoreEnergy;  // energia zapewniana przez zjedzenie jednej rośliny
        int growingRate;    // liczba roślin wyrastająca każdego dnia
        int animalNum;      // startowa liczba zwierzaków
        int startEnergy;    // startowa energia zwierzaków
        int maxEnergy;      // energia konieczna, by uznać zwierzaka za najedzonego
        int energyToChild;   // energia rodziców zużywana by stworzyć potomka
        int minMutation;     // minimalna liczba mutacji u potomków
        int maxMutation;     // maksymalna liczba mutacji u potomków
        int genomeLength;    // długość genomu zwierzaków

        IMapType mapType;            // typ mapy:        kula ziemska, piekielny portal
        IBiomeType biomeType;        // typ zalesienia:  zalesione równiki, toksyczne trupy
        IMutationType mutationType;  // typ mutacji:     pełna losowość, korekta
        IBehaviourType behaviourType;// typ zachowania:  pełna predestynacja, nieco szaleństwa

        width = Integer.parseInt(ftWidth.getText());
        height = Integer.parseInt(ftHeight.getText());
        grassNum = Integer.parseInt(ftGrassNum.getText());
        restoreEnergy = Integer.parseInt(ftRestoreEnergy.getText());
        growingRate = Integer.parseInt(ftGrowingRate.getText());
        animalNum = Integer.parseInt(ftAnimalNum.getText());
        startEnergy = Integer.parseInt(ftStartEnergy.getText());
        maxEnergy = Integer.parseInt(ftMaxEnergy.getText());
        energyToChild = Integer.parseInt(ftEnergyToChild.getText());
        minMutation = Integer.parseInt(ftMinMutation.getText());
        maxMutation = Integer.parseInt(ftMaxMutation.getText());
        genomeLength = Integer.parseInt(ftGenomeLength.getText());

        mapType = (ftMapType.getValue().equals(maps[0]) ? new Earth(width, height) : new SathanPortal(width, height, 3));
        biomeType = (ftBiomeType.getValue().equals(biomes[0]) ? new ForestedBiome(width, height, grassNum, growingRate) :
                new ToxicBiome(width, height, grassNum, growingRate));
        mutationType = (ftMutationType.getValue().equals(mutations[0]) ? new LittleMutation(genomeLength) :
                new FullMutation(genomeLength));
        behaviourType = (ftBehaviourType.getValue().equals(behaviours[0]) ? new NormalBehaviour() : new CrazyBehaviour());

        WorldMap worldMap = new WorldMap(width,height,grassNum,restoreEnergy,growingRate,animalNum,startEnergy,maxEnergy,
                energyToChild,minMutation, maxMutation,genomeLength,mapType,biomeType,mutationType,behaviourType);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameView.fxml"));
        Parent root1 = fxmlLoader.load();

        GameView gameViewController = fxmlLoader.getController();
        gameViewController.receiveData(worldMap, width, height);

        Stage gameView = new Stage();
        gameView.setTitle("Symulacja");
        gameView.setScene(new Scene(root1));
        gameView.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ftMutationType.getItems().addAll(mutations);
        ftMapType.getItems().addAll(maps);
        ftBiomeType.getItems().addAll(biomes);
        ftBehaviourType.getItems().addAll(behaviours);
    }
}
