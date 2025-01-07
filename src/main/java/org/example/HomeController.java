package org.example;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public itemlist items;
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
    private Label productnameD;

    @FXML
    private Label GSTD;

    @FXML
    private Label discountD;

    @FXML
    private Label LoyalityD;

    @FXML
    private Label stockD;

    @FXML
    private Label priceD;

    @FXML
    private Label priceTotal;

    @FXML
    private TextField SearchProductD;

    @FXML
    private TextField itemnoB;

    @FXML
    private TextField quantityB;

    @FXML
    private Button SearchD;

    @FXML
    public TableView<billitem> newTableView;
    @FXML
    public TableColumn<billitem, Integer> SerialNumber;
    @FXML
    public TableColumn<billitem, String> ProductName;
    @FXML
    public TableColumn<billitem, Float> Price;
    @FXML
    public TableColumn<billitem, Float> GSTApplicable;
    @FXML
    public TableColumn<billitem, Float> Discount;
    @FXML
    public TableColumn<billitem, Integer> LoyaltyPoints;
    @FXML
    public TableColumn<billitem, Integer> Quantity;

    @FXML
    public Label netTotal;

    @FXML
    public Label nettaxes;

    @FXML
    public Label netDiscount;

    @FXML
    public Label totalLoyalitypts;

    @FXML
    public Label totalquantity;

    public ObservableList<billitem> observablelist = FXCollections.observableArrayList();

    public HomeController() {
        items = org.example.itemlist.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        GSTApplicable.setCellValueFactory(new PropertyValueFactory<>("gstApplicable"));
        Discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        LoyaltyPoints.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        newTableView.setItems(observablelist);

        observablelist.addListener((ListChangeListener<billitem>) change -> calculateTotalPrice());
    }

    public void displayProductDetails(int ProductID) {
        item newitem = items.getItems().get(ProductID - 1);
        productnameD.setText(newitem.getProductName());
        GSTD.setText(newitem.getGSTApplicable() + "");
        discountD.setText(newitem.getDiscount() + "");
        LoyalityD.setText(newitem.getLoyalityPoints() + "");
        priceD.setText(newitem.getPrice() + "");
        if (newitem.isStockAvalability()) {
            stockD.setText("Available");
        } else {
            stockD.setText("Out of Stock");
        }
    }

    @FXML
    public void handleSearchClick(ActionEvent event) {
        int QueryProductID = Integer.parseInt(SearchProductD.getText());
        displayProductDetails(QueryProductID);
    }

    @FXML
    public void handleAddingtoBill(ActionEvent event) {
        try {
            int QueryProductID = Integer.parseInt(itemnoB.getText());
            displayProductDetails(QueryProductID);
            item newitem = items.getItems().get(QueryProductID - 1);
            if (newitem.isStockAvalability()) {
                int quantityToAdd = Integer.parseInt(quantityB.getText());
                billitem existingBillItem = observablelist.stream().filter(item -> item.getSerialNumber() == QueryProductID).findFirst().orElse(null);
                if (existingBillItem != null) {
                    existingBillItem.setQuantity(existingBillItem.getQuantity() + quantityToAdd);
                    System.out.println("Updated quantity of existing item in the bill.");
                } else {
                    billitem newBillItem = new billitem(QueryProductID, newitem.getProductName(), newitem.getPrice(), newitem.getGSTApplicable(), newitem.getDiscount(), newitem.getLoyalityPoints(), quantityToAdd);
                    observablelist.add(newBillItem);
                }
                newTableView.refresh();
                calculateTotalPrice();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    public void handleRemovingingtoBill(ActionEvent event) {
        try {
            int QueryProductID = Integer.parseInt(itemnoB.getText());
            int removalQuantity = Integer.parseInt(quantityB.getText());
            billitem itemToRemove = observablelist.stream().filter(item -> item.getSerialNumber() == QueryProductID).findFirst().orElse(null);
            if (itemToRemove != null) {
                if (itemToRemove.getQuantity() > removalQuantity) {
                    itemToRemove.setQuantity(itemToRemove.getQuantity() - removalQuantity);
                    System.out.println("Reduced quantity of item in the bill.");
                } else {
                    observablelist.remove(itemToRemove);
                    System.out.println("Item removed from the bill.");
                }
                newTableView.refresh();
                calculateTotalPrice();
            } else {
                System.out.println("Item not found in the bill.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

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
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF Invoice");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);
                String logoPath = "D:\\code\\Java Prep\\Projects\\Billi\\dir\\logo.png";
                ImageData imageData = ImageDataFactory.create(logoPath);
                Image logo = new Image(imageData);
                logo.setWidth(100);
                logo.setHeight(100);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                document.add(logo);
                document.add(new Paragraph("Your Organization Name")
                        .setBold()
                        .setFontSize(24)
                        .setTextAlignment(TextAlignment.CENTER));

                document.add(new Paragraph("Billing Invoice")
                        .setBold()
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER));


                document.add(new Paragraph("\n"));
                float[] columnWidths = {60F, 120F, 80F, 80F, 80F, 80F, 60F};
                Table table = new Table(columnWidths);
                table.addCell("S.No");
                table.addCell("Product Name");
                table.addCell("Price");
                table.addCell("GST (%)");
                table.addCell("Discount (%)");
                table.addCell("Loyalty Points");
                table.addCell("Quantity");
                for (billitem item : observablelist) {
                    table.addCell(String.valueOf(item.getSerialNumber()));
                    table.addCell(item.getProductName());
                    table.addCell(String.format("%.2f", item.getPrice()));
                    table.addCell(String.format("%.2f", item.getGstApplicable()));
                    table.addCell(String.format("%.2f", item.getDiscount()));
                    table.addCell(String.valueOf(item.getLoyaltyPoints()));
                    table.addCell(String.valueOf(item.getQuantity()));
                }

                document.add(table);
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Net Total: ₹" + netTotal.getText()));
                document.add(new Paragraph("Total Taxes: ₹" + nettaxes.getText()));
                document.add(new Paragraph("Total Discount: ₹" + netDiscount.getText()));
                document.add(new Paragraph("Total Loyalty Points: " + totalLoyalitypts.getText()));
                document.add(new Paragraph("Total Quantity: " + totalquantity.getText()));
                document.close();
                System.out.println("PDF Invoice generated successfully at: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error generating PDF: " + e.getMessage());
        }
    }


    @FXML
    public void handleemailbillClick() {
        System.out.println("emailbill clicked");
    }

    private void calculateTotalPrice() {
        float netTotalValue = 0;
        float totalTaxes = 0;
        float totalDiscount = 0;
        int totalLoyaltyPoints = 0;
        int totalQuantityValue = 0;

        for (billitem item : observablelist) {
            float itemPrice = item.getPrice();
            int quantity = item.getQuantity();
            netTotalValue += itemPrice * quantity;
            totalTaxes += (itemPrice * item.getGstApplicable() / 100) * quantity;
            totalDiscount += (itemPrice * item.getDiscount() / 100) * quantity;
            totalLoyaltyPoints += item.getLoyaltyPoints() * quantity;
            totalQuantityValue += quantity;
        }

        // Set the calculated values to the labels
        netTotal.setText(String.format("%.2f", netTotalValue));
        nettaxes.setText(String.format("%.2f", totalTaxes));
        netDiscount.setText(String.format("%.2f", totalDiscount));
        totalLoyalitypts.setText(String.valueOf(totalLoyaltyPoints));
        totalquantity.setText(String.valueOf(totalQuantityValue));

        // Update the priceTotal label to show the final amount (net total + taxes - discount)
        float finalTotal = netTotalValue + totalTaxes - totalDiscount;
        priceTotal.setText(String.format("%.2f", finalTotal));
    }

}
