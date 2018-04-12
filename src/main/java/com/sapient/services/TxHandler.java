package com.sapient.services;

import com.sapient.models.CountryGenderWiseIncomeDetail;
import com.sapient.models.incomeDetail;

import java.util.List;


public interface TxHandler {

    void handleRequest(incomeDetail tx);
    List<CountryGenderWiseIncomeDetail> getResponse();
}
