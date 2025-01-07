package org.example;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.layout.element.*;
import javafx.scene.control.*;


public class HomeController implements Initializable {
    public itemlist items;
    public Organisation organisation;
    int customerID;
    int BillNumber;
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
    private TextField CustID;

    @FXML
    private TextField itemnoB;

    @FXML
    private TextField quantityB;

    @FXML
    private Button SearchD;

    @FXML
    private Button SearchC;

    @FXML
    public Label CustName;

    @FXML
    public Label CustPts;

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
        organisation = org.example.Organisation.getInstance();
        customerID = 0;
        BillNumber = getLastBillNumber();
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

    public void displayCustomerDetails(int CustomerID) {
        customer newCustomer = organisation.getCustomers().get(CustomerID - 1);
        customerID = CustomerID;
        CustName.setText(newCustomer.getName());
        CustPts.setText(newCustomer.getPoints()+"");
    }

    @FXML
    public void handleSearchClick(ActionEvent event) {
        int QueryProductID = Integer.parseInt(SearchProductD.getText());
        displayProductDetails(QueryProductID);
    }

    @FXML
    public void handleSearchCClick(ActionEvent event) {
        int CustomerID = Integer.parseInt(CustID.getText());
        displayCustomerDetails(CustomerID);
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
        System.out.println("NewBill");
        BillNumber++;
    }

    private int getLastBillNumber() {
        File directory = new File("D:/code/Java Prep/Projects/Billi/bill_pdfs");
        if (!directory.exists()) {
            directory.mkdirs();
            return 0;
        }
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".pdf"));
        if (files == null || files.length == 0) {
            return 0;
        }
        return FXCollections.observableArrayList(files).stream()
                .map(file -> Integer.parseInt(file.getName().replace(".pdf", "")))
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    @FXML
    public void handleprintbillClick() {
        try {
            File directory = new File("D:/code/Java Prep/Projects/Billi/bill_pdfs");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String filePath = directory.getAbsolutePath() + "/" + BillNumber + ".pdf";
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            String logoPath = "D:/code/Java Prep/Projects/Billi/dir/logo.png";
            ImageData imageData = ImageDataFactory.create(logoPath);
            Image logo = new Image(imageData);
            logo.setWidth(100);
            logo.setHeight(100);
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(logo);
            document.add(new Paragraph(organisation.getName()).setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Billing Invoice #"+BillNumber).setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));

            float[] columnWidths = {60F, 120F, 80F, 80F, 80F, 80F, 60F};
            Table table = new Table(columnWidths);
            table.addCell("S.No").setBold();
            table.addCell("Product Name").setBold();
            table.addCell("Price").setBold();
            table.addCell("GST (%)").setBold();
            table.addCell("Discount (%)").setBold();
            table.addCell("Loyalty Points").setBold();
            table.addCell("Quantity").setBold();
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

            Table detailsTable = new Table(UnitValue.createPercentArray(new float[]{50, 50})).useAllAvailableWidth();
            detailsTable.addCell(new Paragraph("Net Total: ₹" + netTotal.getText()));
            detailsTable.addCell(new Paragraph("Total Taxes: ₹" + nettaxes.getText()));
            detailsTable.addCell(new Paragraph("Total Discount: ₹" + netDiscount.getText()));
            detailsTable.addCell(new Paragraph("Total Loyalty Points: " + totalLoyalitypts.getText()));
            detailsTable.addCell(new Paragraph("Total Quantity: " + totalquantity.getText()));
            document.add(detailsTable);

            organisation.getCustomers().get(customerID - 1).setPoints(organisation.getCustomers().get(customerID - 1).getPoints() + Integer.parseInt(totalLoyalitypts.getText()));
            document.add(new Paragraph("Total Amount: ₹" + priceTotal.getText()).setBold().setFontSize(18).setTextAlignment(TextAlignment.LEFT));
            Paragraph footer = new Paragraph("Generated with Love❤️, Billi").setFontSize(12).setTextAlignment(TextAlignment.CENTER);
            footer.setFontColor(DeviceGray.GRAY);
            document.add(footer);

            document.close();
            System.out.println("PDF Invoice generated successfully at: " + filePath);
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
