package org.example;

import java.util.Objects;

public class billitem {
    private int serialNumber;
    private String productName;
    private Float price;
    private Float gstApplicable;
    private Float discount;
    private int loyaltyPoints;
    private int quantity;

    // Constructor
    public billitem(int serialNumber, String productName, Float price, Float gstApplicable, Float discount, int loyaltyPoints, int quantity) {
        this.serialNumber = serialNumber;
        this.productName = productName;
        this.price = price;
        this.gstApplicable = gstApplicable;
        this.discount = discount;
        this.loyaltyPoints = loyaltyPoints;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getSerialNumber() {
        return serialNumber;
    }

    public String getProductName() {
        return productName;
    }

    public Float getPrice() {
        return price;
    }

    public Float getGstApplicable() {
        return gstApplicable;
    }

    public Float getDiscount() {
        return discount;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Equals and HashCode for comparing objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        billitem billItem = (billitem) o;
        return serialNumber == billItem.serialNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }
}
