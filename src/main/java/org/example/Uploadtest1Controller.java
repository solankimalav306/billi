package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Uploadtest1Controller {
    @FXML
    public void handleSubmitButtonClick(ActionEvent event) throws Exception {
        Loader(event);
    }
    public void Loader(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/upload.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }
}
