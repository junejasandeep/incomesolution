package com.sapient.models;

import java.math.BigDecimal;

public class incomeDetail {

    private String city;
    private String country;
    private Gender gender;
    private Currency currency;
    private BigDecimal income;

    public incomeDetail(String city, String country, Gender gender, Currency currency, BigDecimal income) {
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.currency = currency;
        this.income = income;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
