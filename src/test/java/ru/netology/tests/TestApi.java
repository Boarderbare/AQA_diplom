package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.pages.PageMain;
import ru.netology.util.DataHelper;
import ru.netology.util.RestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestApi {

    @AfterAll
    static void tearDownAll() {
        DataHelper.cleanData();
        SelenideLogger.removeListener("allure");
    }
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    public PageMain page = new PageMain();
    public RestHelper rest = new RestHelper();

    @Test
    public void shouldSuccessfulBuying() {
        var info = DataHelper.getCardApproved();
        var actual = rest.sentFormBuy(info);
        assertEquals("APPROVED", actual);
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    public void shouldSuccessfulBuyingOnCredit() {
        var info = DataHelper.getCardApproved();
        var actual = rest.sentFormCredit(info);
        assertEquals("APPROVED", actual);
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    public void shouldDeclineBuying() {
        var info = DataHelper.getCardDeclined();
        var actual = rest.sentFormBuy(info);
        assertEquals("DECLINED", actual);
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    public void shouldDeclineBuyingOnCredit() {
        var info = DataHelper.getCardDeclined();
        var actual = rest.sentFormCredit(info);
        assertEquals("DECLINED", actual);
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("DECLINED", status.getStatus());
    }
    @Test
    public void shouldErrorSentCardEmptyFormBuy() {
        var info = DataHelper.getCardEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentCardOneDigitFormBuy() {
        var info = DataHelper.getCardOneDigit();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentMonthEmptyFormBuy() {
        var info = DataHelper.getMonthEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentMonthOneDigitFormBuy() {
        var info = DataHelper.getMonthOneDigit();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentYearEmptyFormBuy() {
        var info = DataHelper.getYearEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentYearOneDigitFormBuy() {
        var info = DataHelper.getYearOneDigit();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentOwnerEmptyFormBuy() {
        var info = DataHelper.getOwnerEmpty();
        rest.sentInvalidFormToBuy(info);
    }
    @Test
    public void shouldErrorSentOwnerOneCharFormBuy() {
        var info = DataHelper.getOwnerOneChar();
        rest.sentInvalidFormToBuy(info);
    }
    @Test
    public void shouldErrorSentCodeEmptyFormBuy() {
        var info = DataHelper.getCodeEmpty();
        rest.sentInvalidFormToBuy(info);
    }
    @Test
    public void shouldErrorSentCodeOneDigitFormBuy() {
        var info = DataHelper.getCodeOneDigit();
        rest.sentInvalidFormToBuy(info);
    }

    @Test
    public void shouldErrorSentCardEmptyFormCredit() {
        var info = DataHelper.getCardEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentCardOneDigitFormCredit() {
        var info = DataHelper.getCardOneDigit();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentMonthEmptyFormCredit() {
        var info = DataHelper.getMonthEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentMonthOneDigitFormCredit() {
        var info = DataHelper.getMonthOneDigit();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentYearEmptyFormCredit() {
        var info = DataHelper.getYearEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentYearOneDigitFormCredit() {
        var info = DataHelper.getYearOneDigit();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentOwnerEmptyFormCredit() {
        var info = DataHelper.getOwnerEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentOwnerOneCharFormCredit() {
        var info = DataHelper.getOwnerOneChar();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentCodeEmptyFormCredit() {
        var info = DataHelper.getCodeEmpty();
        rest.sentInvalidFormToCredit(info);
    }
    @Test
    public void shouldErrorSentCodeOneDigitFormCredit() {
        var info = DataHelper.getCodeOneDigit();
        rest.sentInvalidFormToCredit(info);
    }
}
