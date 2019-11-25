package com.example.warehousemanagment.Shadi.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int id;
    private String name;
    private String color;
    private String category;
    private String type;
    private String imgUrl;
    private float width;
    private float height;
    private float depth;
    private float kg;
    private int inner_count;
    private String  unite;
    private String trademark;

    public Product() {
    }

    public Product(int id, String name, String color, String category, String type, String imgUrl, float width, float height, float depth, float kg, int inner_count, String unite, String trademark) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.category = category;
        this.type = type;
        this.imgUrl = imgUrl;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.kg = kg;
        this.inner_count = inner_count;
        this.unite = unite;
        this.trademark = trademark;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        color = in.readString();
        category = in.readString();
        type = in.readString();
        imgUrl = in.readString();
        width = in.readFloat();
        height = in.readFloat();
        depth = in.readFloat();
        kg = in.readFloat();
        inner_count = in.readInt();
    }


    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getKg() {
        return kg;
    }

    public void setKg(float kg) {
        this.kg = kg;
    }

    public int getInner_count() {
        return inner_count;
    }

    public void setInner_count(int inner_count) {
        this.inner_count = inner_count;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(color);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeString(imgUrl);
        dest.writeFloat(width);
        dest.writeFloat(height);
        dest.writeFloat(depth);
        dest.writeFloat(kg);
        dest.writeInt(inner_count);
        dest.writeString(unite);
        dest.writeString(trademark);
    }
}
