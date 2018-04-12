package com.sapient.io.impl;

import com.sapient.io.IncomeDetailWriterService;
import com.sapient.models.CountryGenderWiseIncomeDetail;
import com.sapient.services.TxHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class CsvIncomeDetailWriterService implements IncomeDetailWriterService {

    @Autowired
    private TxHandler transactionHandler;

    private static DecimalFormat df = new DecimalFormat(".##");


    @Value("${tx.csvwriter.filename}")
    private String fileName;

    @Value("${tx.csvwriter.delimiter}")
    private String delimiter;

    @Override
    public void write() throws IOException {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
        ) {
            List<CountryGenderWiseIncomeDetail> response = transactionHandler.getResponse();

            writer.write("Country/City, Gender, Avg Income\n");
            response.stream().forEach(incomeReport -> {
                StringBuilder builder = new StringBuilder();
                builder.append(incomeReport.getCountry()).append(delimiter)
                        .append(incomeReport.getGender()).append(delimiter)
                        .append(df.format(incomeReport.getAvgIncome())).append("\n");
                try {
                    writer.write(builder.toString());
                } catch (IOException e) {
                    //Log
                }
            });


        } catch (IOException e) {
            //Log And print
            throw e;

        }
    }

}
