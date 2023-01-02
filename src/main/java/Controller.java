import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button btnMainMenu;
    @FXML
    private Button ftbtnFileChooser;
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
    @FXML
    private Slider ftSimulationSpeed;

    private String[] mutations = {"lekka", "calkowita"};
    private String[] maps = {"ziemia", "pieklo"};
    private String[] biomes = {"jungle", "trupy"};
    private String[] behaviours = {"normalne", "szalone"};
    private File f;
    @FXML
    void ftLoadData(ActionEvent event) {
        if (f!=null) {
            System.out.println(":(((");
            String fileContent;
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))) {

                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null)
                {
                    contentBuilder.append(sCurrentLine).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileContent = contentBuilder.toString();
            //System.out.println(fileContent);
            String[] lines = fileContent.split("\n");
            ftWidth.setText((lines[0].split(":"))[1]);
            ftHeight.setText((lines[1].split(":"))[1]);
            ftGrassNum.setText((lines[2].split(":"))[1]);
            ftRestoreEnergy.setText((lines[3].split(":"))[1]);
            ftGrowingRate.setText((lines[4].split(":"))[1]);
            ftAnimalNum.setText((lines[5].split(":"))[1]);
            ftStartEnergy.setText((lines[6].split(":"))[1]);
            ftMaxEnergy.setText((lines[7].split(":"))[1]);
            ftEnergyToChild.setText((lines[8].split(":"))[1]);
            ftMinMutation.setText((lines[9].split(":"))[1]);
            ftMaxMutation.setText((lines[10].split(":"))[1]);
            ftGenomeLength.setText((lines[11].split(":"))[1]);
            ftMapType.setValue(maps[Integer.parseInt((lines[12].split(":"))[1])]);
            ftBiomeType.setValue(biomes[Integer.parseInt((lines[13].split(":"))[1])]);
            ftMutationType.setValue(mutations[Integer.parseInt((lines[14].split(":"))[1])]);
            ftBehaviourType.setValue(behaviours[Integer.parseInt((lines[15].split(":"))[1])]);
            ftSimulationSpeed.setValue(Double.parseDouble((lines[16].split(":"))[1]));
        }
    }

    @FXML
    void singleFileChoser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        f = fileChooser.showOpenDialog(null);

        if (f!=null) {
            ftbtnFileChooser.setText(f.getPath());
        }
    }


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


            WorldMap worldMap = new WorldMap(width, height, grassNum, restoreEnergy, growingRate, animalNum, startEnergy, maxEnergy,
                    energyToChild, minMutation, maxMutation, genomeLength, mapType, biomeType, mutationType, behaviourType);

            long simulationSpeed = (long) ftSimulationSpeed.getValue();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameView.fxml"));
        Parent root1 = fxmlLoader.load();

        GameView gameViewController = fxmlLoader.getController();
        gameViewController.receiveData(worldMap, width, height, simulationSpeed);

        Stage gameView = new Stage();
        gameView.setTitle("Symulacja");
        gameView.setScene(new Scene(root1));
        gameView.show();
        //gameView.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, gameView.getOnCloseRequest());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ftMutationType.getItems().addAll(mutations);
        ftMutationType.setValue(mutations[0]);
        ftMapType.getItems().addAll(maps);
        ftMapType.setValue((maps[0]));
        ftBiomeType.getItems().addAll(biomes);
        ftBiomeType.setValue(biomes[0]);
        ftBehaviourType.getItems().addAll(behaviours);
        ftBehaviourType.setValue(behaviours[0]);
    }
}
