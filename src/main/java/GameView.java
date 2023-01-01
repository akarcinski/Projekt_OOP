import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import agh.*;

import java.awt.event.MouseEvent;

public class GameView {

    @FXML
    private GridPane gvMap;

    @FXML
    private Button gvStartBT;

    public WorldMap map;
    private int width;
    private int height;

    // trzeba zmienić na prefHeight i prefWidth
    double gridHeight = 500.0;
    double gridWidth = 700.0;

    @FXML
    void btnStart(ActionEvent event) {
        gvMap.getChildren().clear();
        //double cellWidth =
        for (int i = 0; i<width; i++) {
            gvMap.getRowConstraints().add(new RowConstraints());
            gvMap.getColumnConstraints().add(new ColumnConstraints());
        }
        Stage mainWindow = (Stage) gvMap.getScene().getWindow();
        //String title = ftWidth.getText();
        mainWindow.setTitle(Integer.toString(width));
        //receiveData(event);
    }

    public void receiveData(WorldMap map, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;
    }
}
