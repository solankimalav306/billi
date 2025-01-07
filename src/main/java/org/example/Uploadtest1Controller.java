package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Uploadtest1Controller {
    @FXML
    private TextField OrgName;
    public Organisation organisation;
    @FXML
    public void handleSubmitButtonClick(ActionEvent event) throws Exception {
        organisation = Organisation.getInstance();
        organisation.setName(OrgName.getText());
        Loader(event);
    }
    public void Loader(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/uploadtest.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }
}
