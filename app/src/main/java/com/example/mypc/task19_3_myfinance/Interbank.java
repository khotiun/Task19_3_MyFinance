package com.example.mypc.task19_3_myfinance;

/**
 * Created by MyPc on 06.04.2017.
 */
//        id - id точки,
//        pointDate - дата обновления точки,
//        pdate - дата,
//        bid - продажа,
//        ask - покупка,
//        currency - валюта,
//        status - указание, что точка была удалена. Принимает значение "deleted".
public class Interbank {
    int mId;
    String mPointDate;
    String mPdate;
    double mBid;
    double mAsk;
    String mCurrency;

    public Interbank(int id, String pointDate, String pdate, double bid, double ask, String currency) {
        mId = id;
        mPointDate = pointDate;
        mPdate = pdate;
        mBid = bid;
        mAsk = ask;
        mCurrency = currency;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPointDate() {
        return mPointDate;
    }

    public void setPointDate(String pointDate) {
        mPointDate = pointDate;
    }

    public String getPdate() {
        return mPdate;
    }

    public void setPdate(String pdate) {
        mPdate = pdate;
    }

    public double getBid() {
        return mBid;
    }

    public void setBid(double bid) {
        mBid = bid;
    }

    public double getAsk() {
        return mAsk;
    }

    public void setAsk(double ask) {
        mAsk = ask;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }
}
