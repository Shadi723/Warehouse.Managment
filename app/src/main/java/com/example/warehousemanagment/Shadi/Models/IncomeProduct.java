package com.example.warehousemanagment.Shadi.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class IncomeProduct implements Parcelable {
    private String user_name;
    private int package_number;
    private Date date;
    private String trademark;
    private String Seller;
    private float inside_package_number;

    public IncomeProduct() {
    }

    public IncomeProduct(String user_name, int package_number, Date date, String trademark, String seller, float inside_package_number) {
        this.user_name = user_name;
        this.package_number = package_number;
        this.date = date;
        this.trademark = trademark;
        Seller = seller;
        this.inside_package_number = inside_package_number;
    }

    protected IncomeProduct(Parcel in) {
        user_name = in.readString();
        package_number = in.readInt();
        trademark = in.readString();
        Seller = in.readString();
        inside_package_number = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_name);
        dest.writeInt(package_number);
        dest.writeString(trademark);
        dest.writeString(Seller);
        dest.writeFloat(inside_package_number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IncomeProduct> CREATOR = new Creator<IncomeProduct>() {
        @Override
        public IncomeProduct createFromParcel(Parcel in) {
            return new IncomeProduct(in);
        }

        @Override
        public IncomeProduct[] newArray(int size) {
            return new IncomeProduct[size];
        }
    };

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getPackage_number() {
        return package_number;
    }

    public void setPackage_number(int package_number) {
        this.package_number = package_number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public float getInside_package_number() {
        return inside_package_number;
    }

    public void setInside_package_number(float inside_package_number) {
        this.inside_package_number = inside_package_number;
    }
}
