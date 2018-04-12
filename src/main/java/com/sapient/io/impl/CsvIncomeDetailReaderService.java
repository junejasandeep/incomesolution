package com.sapient.io.impl;

import com.sapient.io.IncomeDetailReaderService;
import com.sapient.models.Currency;
import com.sapient.models.Gender;
import com.sapient.models.incomeDetail;
import com.sapient.services.TxHandler;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;

@Service
public class CsvIncomeDetailReaderService implements IncomeDetailReaderService {

    @Autowired
    private TxHandler transactionHandler;

    @Value("${tx.csvreader.filename}")
    private String fileName;

    @Value("${tx.csvreader.delimiter}")
    private String delimiter;


    public void read() throws IOException {

        try (
                BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        ) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = StringUtils.splitPreserveAllTokens(line, delimiter);

                @NotNull
                String city = tokens[0];
                String country = tokens[1];

                @NotNull
                Gender gender = Gender.valueOf(tokens[2]);

                @NotNull
                Currency currency = Currency.valueOf(tokens[3]);

                @NotNull
                BigDecimal income = new BigDecimal(tokens[4]);


                incomeDetail tx = new incomeDetail(city, country, gender, currency, income);
                transactionHandler.handleRequest(tx);

            }
        } catch (FileNotFoundException e) {
            //TODO: Log File not found
            throw e;
        } catch (IOException e) {
            //TODO: Log IOException print and go to next line
            throw e;
        }

    }

}
