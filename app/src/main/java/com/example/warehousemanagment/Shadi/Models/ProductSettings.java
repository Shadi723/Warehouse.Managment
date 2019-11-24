package com.example.warehousemanagment.Shadi.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductSettings implements Parcelable {
    private Product product;
    private Trademark trademark;

    public ProductSettings() {
    }

    public ProductSettings(Product product, Trademark trademark) {
        this.product = product;
        this.trademark = trademark;
    }


    protected ProductSettings(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(product, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductSettings> CREATOR = new Creator<ProductSettings>() {
        @Override
        public ProductSettings createFromParcel(Parcel in) {
            return new ProductSettings(in);
        }

        @Override
        public ProductSettings[] newArray(int size) {
            return new ProductSettings[size];
        }
    };

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Trademark getTrademark() {
        return trademark;
    }

    public void setTrademark(Trademark trademark) {
        this.trademark = trademark;
    }
}
