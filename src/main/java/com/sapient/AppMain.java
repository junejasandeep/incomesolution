package com.sapient;

import com.sapient.config.AppConfig;
import com.sapient.services.impl.PublishIncomeDataService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppMain {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        PublishIncomeDataService publishIncomeDataService = ctx.getBean(PublishIncomeDataService.class);

        publishIncomeDataService.process();
    }
}
