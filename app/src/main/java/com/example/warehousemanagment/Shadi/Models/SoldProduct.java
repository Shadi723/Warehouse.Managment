package com.example.warehousemanagment.Shadi.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class SoldProduct implements Parcelable {
    private String user_name;
    private int package_count;
    private Date date;
    private String client;
    private String trademaek;
    private float total_quantity;

    public SoldProduct() {
    }

    public SoldProduct(String user_name, int package_count, Date date, String client, String trademaek, float total_quantity) {
        this.user_name = user_name;
        this.package_count = package_count;
        this.date = date;
        this.client = client;
        this.trademaek = trademaek;
        this.total_quantity = total_quantity;
    }

    protected SoldProduct(Parcel in) {
        user_name = in.readString();
        package_count = in.readInt();
        client = in.readString();
        trademaek = in.readString();
        total_quantity = in.readFloat();
    }

    public static final Creator<SoldProduct> CREATOR = new Creator<SoldProduct>() {
        @Override
        public SoldProduct createFromParcel(Parcel in) {
            return new SoldProduct(in);
        }

        @Override
        public SoldProduct[] newArray(int size) {
            return new SoldProduct[size];
        }
    };

    public float getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(float total_quantity) {
        this.total_quantity = total_quantity;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getPackage_count() {
        return package_count;
    }

    public void setPackage_count(int package_count) {
        this.package_count = package_count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTrademaek() {
        return trademaek;
    }

    public void setTrademaek(String trademaek) {
        this.trademaek = trademaek;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_name);
        dest.writeInt(package_count);
        dest.writeString(client);
        dest.writeString(trademaek);
        dest.writeFloat(total_quantity);
    }
}
