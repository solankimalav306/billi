package org.example;

public class item {
    private int SerialNumber;
    private String ProductName;
    private Float Price;
    private Float GSTApplicable;
    private Float Discount;
    private int LoyalityPoints;
    private boolean StockAvalability;
    public item(int SerialNumber,String ProductName,Float Price,Float GSTApplicable,Float Discount,int LoyalityPoints,boolean StockAvalability) {
        this.SerialNumber = SerialNumber;
        this.ProductName = ProductName;
        this.Price = Price;
        this.GSTApplicable = GSTApplicable;
        this.Discount = Discount;
        this.LoyalityPoints = LoyalityPoints;
        this.StockAvalability = StockAvalability;
    }
    public int getSerialNumber() {
        return SerialNumber;
    }
    public String getProductName() {
        return ProductName;
    }
    public Float getPrice() {
        return Price;
    }
    public Float getGSTApplicable() {
        return GSTApplicable;
    }
    public Float getDiscount() {
        return Discount;
    }
    public int getLoyalityPoints() {
        return LoyalityPoints;
    }
    public boolean isStockAvalability() {
        return StockAvalability;
    }
    public void setSerialNumber(int SerialNumber) {
        this.SerialNumber = SerialNumber;
    }
    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }
    public void setPrice(Float Price) {
        this.Price = Price;
    }
    public void setGSTApplicable(Float GSTApplicable) {
        this.GSTApplicable = GSTApplicable;
    }
    public void setDiscount(Float Discount) {
        this.Discount = Discount;
    }
    public void setLoyalityPoints(int LoyalityPoints) {
        this.LoyalityPoints = LoyalityPoints;
    }
    public void setStockAvalability(boolean StockAvalability) {
        this.StockAvalability = StockAvalability;
    }
}
