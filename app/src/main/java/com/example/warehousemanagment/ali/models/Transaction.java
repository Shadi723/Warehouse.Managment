package com.example.warehousemanagment.ali.models;

public class Transaction  {

    private int id;
    private String transactionDate;
    private String transactionCode;
    private String transactionDesc;



    public Transaction(int id, String transactionDate, String transactionCode, String transactionDesc) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionCode = transactionCode;
        this.transactionDesc = transactionDesc;
    }

    public int getId() {
        return id;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }
}
