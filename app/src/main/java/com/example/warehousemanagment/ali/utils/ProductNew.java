package com.example.warehousemanagment.ali.utils;

public class ProductNew {

    private int id;
    private String imageurl;
    private String color;
    private String name;
    private String trademark;
    private int type;
    private int depth;
    private int height;
    private int width;
    private int kg;
    private int inner_count;

    public ProductNew() {
    }

    public ProductNew(int id, String imageurl, String color, String name, String trademark, int type, int depth, int height, int width, int kg, int inner_count) {
        this.id = id;
        this.imageurl = imageurl;
        this.color = color;
        this.name = name;
        this.trademark = trademark;
        this.type = type;
        this.depth = depth;
        this.height = height;
        this.width = width;
        this.kg = kg;
        this.inner_count = inner_count;
    }

    public int getId() {
        return id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getTrademark() {
        return trademark;
    }

    public int getType() {
        return type;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getKg() {
        return kg;
    }

    public int getInner_count() {
        return inner_count;
    }
}
