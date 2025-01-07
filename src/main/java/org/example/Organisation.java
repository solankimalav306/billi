package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Organisation {
    private static Organisation instance;
    private String name;
    private ArrayList<customer> customers;

    public Organisation() {
        this.name = "Your Organisation";
        customers = new ArrayList<>();
    }

    public static Organisation getInstance() {
        if (instance == null) {
            instance = new Organisation();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(customer customer) {
        customers.remove(customer);
    }

    public void loadData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    try {
                        int id = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        int points = Integer.parseInt(data[2].trim());
                        customer cust = new customer(id, name, points);
                        addCustomer(cust);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing number: " + e.getMessage());
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
