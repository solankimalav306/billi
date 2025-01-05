package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomeController {
    @FXML
    private Button Dashboard;
    @FXML
    private Button itemList;
    @FXML
    private Button AddItems;
    @FXML
    private Button Customers;
    @FXML
    private Button NewBilling;
    @FXML
    private Button printbill;
    @FXML
    private Button emailbill;

    @FXML
    public void handleDashboardClick() {
        System.out.println("Dashboard clicked");
    }

    @FXML
    public void handleitemListClick() {
        System.out.println("itemList clicked");
    }

    @FXML
    public void handleAddItemsClick() {
        System.out.println("AddItems clicked");
    }

    @FXML
    public void handleCustomersClick() {
        System.out.println("Customers clicked");
    }

    @FXML
    public void handleNewBillingClick() {
        System.out.println("NewBilling clicked");
    }

    @FXML
    public void handleprintbillClick() {
        System.out.println("printbill clicked");
    }
    @FXML
    public void handleemailbillClick() {
        System.out.println("emailbill clicked");
    }
}
