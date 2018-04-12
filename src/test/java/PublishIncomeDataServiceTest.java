import com.sapient.config.AppConfig;
import com.sapient.io.IncomeDetailReaderService;
import com.sapient.models.Currency;
import com.sapient.models.Gender;
import com.sapient.models.CountryGenderWiseIncomeDetail;
import com.sapient.models.incomeDetail;
import com.sapient.repositories.IncomeDetailRepository;
import com.sapient.services.CurrencyConverter;
import com.sapient.services.impl.AvgIncomeHandler;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class PublishIncomeDataServiceTest {

    private static DecimalFormat df = new DecimalFormat(".##");

    @Autowired
    IncomeDetailReaderService txReaderService;

    @Autowired
    CurrencyConverter currencyConverter;

    @Autowired
    IncomeDetailRepository repository;


    @Autowired
    AvgIncomeHandler handler;

    @Test
    public void verifyConversionToUSD() {

        assertThat(currencyConverter.convert(Currency.INR, new BigDecimal(66d)).doubleValue(), is(new BigDecimal(1).doubleValue()));
        assertThat(currencyConverter.convert(Currency.INR, new BigDecimal(132d)).doubleValue(), is(new BigDecimal(2).doubleValue()));
        assertThat(currencyConverter.convert(Currency.SGD, new BigDecimal(1.5d)).doubleValue(), is(new BigDecimal(1).doubleValue()));
        assertThat(currencyConverter.convert(Currency.HKD, new BigDecimal(8d)).doubleValue(), is(new BigDecimal(1).doubleValue()));
        assertThat(currencyConverter.convert(Currency.GBP, new BigDecimal(.67d)).doubleValue(), is(new BigDecimal(1).doubleValue()));

    }

    @Test
    public void checkAvgIncomeAfterMultipleTransaction() {
        incomeDetail t1 = new incomeDetail("Hyderabad", "IND", Gender.M, Currency.INR, new BigDecimal(20000));
        incomeDetail t2 = new incomeDetail("Banglore", "IND", Gender.M, Currency.INR, new BigDecimal(10000));
        incomeDetail t3 = new incomeDetail("New York", "USA", Gender.M, Currency.USD, new BigDecimal(1000));

        handler.handleRequest(t1);
        handler.handleRequest(t2);
        handler.handleRequest(t3);
        List<CountryGenderWiseIncomeDetail> response = handler.getResponse();

        CountryGenderWiseIncomeDetail report = new CountryGenderWiseIncomeDetail("IND", Gender.M.toString(), new BigDecimal(15000).divide(new BigDecimal(66), 3, BigDecimal.ROUND_HALF_UP));

        assertThat("", response.contains(report), is(true));

        report = new CountryGenderWiseIncomeDetail("USA", Gender.M.toString(), new BigDecimal(1000).divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP));
        assertThat("", response.contains(report), is(true));


    }

    @Test
    public void checkNumOfEntriesAfterAvgIncomeCalculation() {

        incomeDetail t1 = new incomeDetail("Hyderabad", "IND", Gender.M, Currency.INR, new BigDecimal(20000));
        incomeDetail t2 = new incomeDetail("Banglore", "IND", Gender.M, Currency.INR, new BigDecimal(10000));
        incomeDetail t3 = new incomeDetail("Banglore", "IND", Gender.F, Currency.INR, new BigDecimal(20000));
        incomeDetail t4 = new incomeDetail("New Delhi", null, Gender.F, Currency.INR, new BigDecimal(10000));
        incomeDetail t5 = new incomeDetail("New York", "USA", Gender.M, Currency.USD, new BigDecimal(1000));

        handler.handleRequest(t1);
        List<CountryGenderWiseIncomeDetail> response = handler.getResponse();
        assertThat("There should be Only one avg income in report as one transaction", response, hasSize(1));

        handler.handleRequest(t2);
        response = handler.getResponse();
        assertThat("There should be Only one avg income in report as country and gender is same for both transactions", response, hasSize(1));


        handler.handleRequest(t3);
        response = handler.getResponse();
        assertThat("There should be 2 avg income as same country but 2 diff gender", response, hasSize(2));

        handler.handleRequest(t4);
        response = handler.getResponse();
        assertThat("when country is missing city is country so 3 income reports should be there", response, hasSize(3));

        handler.handleRequest(t5);
        response = handler.getResponse();
        assertThat("4 should be the avg Num of incomes as 2 diff country with same gender + 1 diff gender + Country is missing ", response, hasSize(4));

    }


    @After
    public void reset() {
        repository.clearTx();
    }

}
