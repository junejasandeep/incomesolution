package com.sapient.services.impl;

import com.sapient.models.CountryGenderWiseIncomeDetail;
import com.sapient.models.incomeDetail;
import com.sapient.repositories.impl.InMemoryIncomeDetailRepository;
import com.sapient.services.CurrencyConverter;
import com.sapient.services.TxHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class AvgIncomeHandler implements TxHandler {

    private static String GROUP_KEY = "%s_%s";

    @Autowired
    private InMemoryIncomeDetailRepository txRepository;

    @Autowired
    private CurrencyConverter currencyConverter;


    @Override
    public void handleRequest(incomeDetail tx) {

        txRepository.addTx(tx);

        String country = tx.getCountry();
        if (StringUtils.isBlank(country)) {
            country = tx.getCity();
        }
        String key = String.format(GROUP_KEY, country, tx.getGender().toString());

        txRepository.addAvgIncome(key, currencyConverter.convert(tx.getCurrency(), tx.getIncome()));

    }


    @Override
    public List<CountryGenderWiseIncomeDetail> getResponse() {

        List<CountryGenderWiseIncomeDetail> list = new LinkedList<>();

        txRepository.getAvgIncome().forEach((key, value) -> {

            String[] split = key.split("_");
            list.add(new CountryGenderWiseIncomeDetail(split[0], split[1], value));
        });

        return list;
    }
}
