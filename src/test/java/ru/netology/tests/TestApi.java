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
    public static void cleanData() {
        DataHelper.cleanData();
    }
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    public void shouldSuccessfulBuying() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardApproved();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormBuy(info);
        assertEquals("APPROVED", actual);
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    public void shouldSuccessfulBuyingOnCredit() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardApproved();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormCredit(info);
        assertEquals("APPROVED", actual);
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    public void shouldDeclineBuying() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardDeclined();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormBuy(info);
        assertEquals("DECLINED", actual);
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    public void shouldDeclineBuyingOnCredit() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardDeclined();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormCredit(info);
        assertEquals("DECLINED", actual);
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("DECLINED", status.getStatus());
    }
}
