package com.sapient.repositories.impl;

import com.sapient.models.incomeDetail;
import com.sapient.repositories.IncomeDetailRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryIncomeDetailRepository implements IncomeDetailRepository {

    private static List<incomeDetail> txs = new LinkedList<>();

    private static Map<String, BigDecimal> CountryGenderWiseAvgIncome = new HashMap<>();

    @Override
    public void addTx(incomeDetail tx) {
        txs.add(tx);

    }

    @Override
    public List<incomeDetail> getAllTxs() {
        return txs;
    }

    @Override
    public Map<String, BigDecimal> getAvgIncome() {
        return CountryGenderWiseAvgIncome;
    }

    @Override
    public void addAvgIncome(String key, BigDecimal income) {
        BigDecimal v = CountryGenderWiseAvgIncome.get(key);
        if (v != null)
            v = v.add(income).divide(new BigDecimal(2), 3, BigDecimal.ROUND_HALF_UP);
        else
            v = income;

        CountryGenderWiseAvgIncome.put(key, v);

    }

    @Override
    public void clearTx() {
        txs = new LinkedList<>();
        CountryGenderWiseAvgIncome = new HashMap<>();
    }


}
