package com.sapient.services;

import com.sapient.models.Currency;

import java.math.BigDecimal;

public interface CurrencyConverter {

   BigDecimal convert(Currency input, BigDecimal amount);



}
