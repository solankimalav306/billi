package org.example;

public class customer {
    private int id;
    private String name;
    private int points;
    public customer(int id, String name, int points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
