package com.sapient.repositories;

import com.sapient.models.incomeDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IncomeDetailRepository {

    void addTx(incomeDetail tx);

    List<incomeDetail> getAllTxs();

    Map<String, BigDecimal> getAvgIncome();

    void addAvgIncome(String key, BigDecimal income);

    void clearTx();

}
