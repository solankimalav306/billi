package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class itemlist {
    private static itemlist instance;
    private List<item> items;

    private itemlist() {
        items = new ArrayList<>();
    }

    public static itemlist getInstance() {
        if (instance == null) {
            instance = new itemlist();
        }
        return instance;
    }

    public List<item> getItems() {
        return items;
    }

    public void loadDataFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int serialNumber = Integer.parseInt(values[0]);
                String productName = values[1];
                Float price = Float.parseFloat(values[2]);
                Float gstApplicable = Float.parseFloat(values[3]);
                Float discount = Float.parseFloat(values[4]);
                int loyaltyPoints = Integer.parseInt(values[5]);
                boolean stockAvailability = values[6].equals("1");

                item newItem = new item(serialNumber, productName, price, gstApplicable, discount, loyaltyPoints, stockAvailability);
                items.add(newItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayItems() {
        for (item i : items) {
            System.out.println("Serial Number: " + i.getSerialNumber());
            System.out.println("Product Name: " + i.getProductName());
            System.out.println("Price: " + i.getPrice());
            System.out.println("GST Applicable: " + i.getGSTApplicable());
            System.out.println("Discount: " + i.getDiscount());
            System.out.println("Loyalty Points: " + i.getLoyalityPoints());
            System.out.println("Stock Availability: " + i.isStockAvalability());
            System.out.println("-------------------------");
        }
    }
}
