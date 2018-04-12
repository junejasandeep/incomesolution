package com.sapient.models;

import java.math.BigDecimal;

public class CountryGenderWiseIncomeDetail {

    private String country;
    private String gender;
    private BigDecimal avgIncome;

    public CountryGenderWiseIncomeDetail(String country, String gender, BigDecimal avgIncome) {
        this.country = country;
        this.gender = gender;
        this.avgIncome = avgIncome;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BigDecimal getAvgIncome() {
        return avgIncome;
    }

    public void setAvgIncome(BigDecimal avgIncome) {
        this.avgIncome = avgIncome;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryGenderWiseIncomeDetail report = (CountryGenderWiseIncomeDetail) o;

        if (country != null ? !country.equals(report.country) : report.country != null) return false;
        if (gender != null ? !gender.equals(report.gender) : report.gender != null) return false;
        return avgIncome != null ? avgIncome.equals(report.avgIncome) : report.avgIncome == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (avgIncome != null ? avgIncome.hashCode() : 0);
        return result;
    }
}
