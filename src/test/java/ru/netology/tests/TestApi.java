package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    public String pathBuy = "/api/v1/pay";
    public String pathCredit = "/api/v1/credit";

    @Test
    public void shouldSuccessfulBuying() {
        var info = DataHelper.getCardApproved();
        var actual = RestHelper.sentForm(info, pathBuy);
        assertEquals("APPROVED", actual);
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    public void shouldSuccessfulBuyingOnCredit() {
        var info = DataHelper.getCardApproved();
        var actual = RestHelper.sentForm(info,pathCredit);
        assertEquals("APPROVED", actual);
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    public void shouldDeclineBuying() {
        var info = DataHelper.getCardDeclined();
        var actual = RestHelper.sentForm(info, pathBuy);
        assertEquals("DECLINED", actual);
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    public void shouldDeclineBuyingOnCredit() {
        var info = DataHelper.getCardDeclined();
        var actual = RestHelper.sentForm(info,pathCredit);
        assertEquals("DECLINED", actual);
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    public void shouldErrorSentCardEmptyFormBuy() {
        var info = DataHelper.getCardEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentCardOneDigitFormBuy() {
        var info = DataHelper.getCardOneDigit();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentMonthEmptyFormBuy() {
        var info = DataHelper.getMonthEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentMonthOneDigitFormBuy() {
        var info = DataHelper.getMonthOneDigit();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentYearEmptyFormBuy() {
        var info = DataHelper.getYearEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentYearOneDigitFormBuy() {
        var info = DataHelper.getYearOneDigit();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentOwnerEmptyFormBuy() {
        var info = DataHelper.getOwnerEmpty();
        RestHelper.sentInvalidForm(info, pathBuy);
    }

    @Test
    public void shouldErrorSentOwnerOneCharFormBuy() {
        var info = DataHelper.getOwnerOneChar();
        RestHelper.sentInvalidForm(info, pathBuy);
    }

    @Test
    public void shouldErrorSentCodeEmptyFormBuy() {
        var info = DataHelper.getCodeEmpty();
        RestHelper.sentInvalidForm(info, pathBuy);
    }

    @Test
    public void shouldErrorSentCodeOneDigitFormBuy() {
        var info = DataHelper.getCodeOneDigit();
        RestHelper.sentInvalidForm(info, pathBuy);
    }

    @Test
    public void shouldErrorSentCardEmptyFormCredit() {
        var info = DataHelper.getCardEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentCardOneDigitFormCredit() {
        var info = DataHelper.getCardOneDigit();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentMonthEmptyFormCredit() {
        var info = DataHelper.getMonthEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentMonthOneDigitFormCredit() {
        var info = DataHelper.getMonthOneDigit();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentYearEmptyFormCredit() {
        var info = DataHelper.getYearEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentYearOneDigitFormCredit() {
        var info = DataHelper.getYearOneDigit();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentOwnerEmptyFormCredit() {
        var info = DataHelper.getOwnerEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentOwnerOneCharFormCredit() {
        var info = DataHelper.getOwnerOneChar();
        RestHelper.sentInvalidForm(info, pathCredit);
    }

    @Test
    public void shouldErrorSentCodeEmptyFormCredit() {
        var info = DataHelper.getCodeEmpty();
        RestHelper.sentInvalidForm(info,pathCredit);
    }

    @Test
    public void shouldErrorSentCodeOneDigitFormCredit() {
        var info = DataHelper.getCodeOneDigit();
        RestHelper.sentInvalidForm(info,pathCredit);
    }
}
