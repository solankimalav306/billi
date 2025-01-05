package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UploadtestController {

    @FXML
    private Button uploadButton;

    @FXML
    private Button submitButton;

    @FXML
    private Label statusLabel;

    // Internal directory to save files
    private static final String INTERNAL_DIR = "D:\\code\\Java Prep\\Projects\\Billi\\dir";

    // Store selected file temporarily
    private File selectedFile;

    @FXML
    public void handleUploadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            statusLabel.setText(selectedFile.getName());
        } else {
            statusLabel.setText("No file selected.");
        }
    }

    @FXML
    public void handleSubmitButtonClick(ActionEvent event) throws Exception {
        if (selectedFile != null) {
            try {
                saveFileToInternalDirectory(selectedFile);
//                statusLabel.setText("File uploaded to: " + INTERNAL_DIR);
                selectedFile = null;  // Clear the selected file
            } catch (IOException e) {
                statusLabel.setText("Error: " + e.getMessage());
                e.printStackTrace();
            }
            Loader(event);
        } else {
            statusLabel.setText("No file selected to upload.");
        }

    }
    public void Loader(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/uploadcsv.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    private void saveFileToInternalDirectory(File file) throws IOException {
        Path internalDirPath = Path.of(INTERNAL_DIR);
        if (!Files.exists(internalDirPath)) {
            Files.createDirectories(internalDirPath);
        }

        Path destinationPath = internalDirPath.resolve(file.getName());
        Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
