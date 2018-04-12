package com.sapient.services.impl;

import com.sapient.models.Currency;
import com.sapient.services.CurrencyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class USDCurrencyConverter implements CurrencyConverter {


    @Value("${conversion.inr.usd.multiplier}")
    private String inrToUsdMultiplier;

    @Value("${conversion.gbp.usd.multiplier}")
    private String gbpToUsdMultiplier;

    @Value("${conversion.sgd.usd.multiplier}")
    private String sgdToUsdMultiplier;

    @Value("${conversion.hkd.usd.multiplier}")
    private String hkdToUsdMultiplier;

    @Value("${conversion.usd.scale}")
    private int scale;

    private Map<Currency, Function<BigDecimal, BigDecimal>> usdConverter = new HashMap<>();

    @PostConstruct
    private void loadMap() {
        usdConverter.put(Currency.INR, amount -> amount.divide(new BigDecimal(inrToUsdMultiplier), scale, BigDecimal.ROUND_HALF_UP));
        usdConverter.put(Currency.GBP, amount -> amount.divide(new BigDecimal(gbpToUsdMultiplier), scale, BigDecimal.ROUND_HALF_UP));
        usdConverter.put(Currency.SGD, amount -> amount.divide(new BigDecimal(sgdToUsdMultiplier), scale, BigDecimal.ROUND_HALF_UP));
        usdConverter.put(Currency.HKD, amount -> amount.divide(new BigDecimal(hkdToUsdMultiplier), scale, BigDecimal.ROUND_HALF_UP));
        usdConverter.put(Currency.USD, amount -> amount.divide(new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP));
    }

    @Override
    public BigDecimal convert(Currency input, BigDecimal amount) {
        return usdConverter.get(input).apply(amount);
    }
}

