package com.sapient.services.impl;

import com.sapient.io.IncomeDetailReaderService;
import com.sapient.io.IncomeDetailWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PublishIncomeDataService {

    @Autowired
    IncomeDetailReaderService readerService;

    @Autowired
    IncomeDetailWriterService writerService;


    public void process() {
        try {
            readerService.read();

            writerService.write();
        } catch (IOException e) {
            //Log ANd Print
        }

    }
}
