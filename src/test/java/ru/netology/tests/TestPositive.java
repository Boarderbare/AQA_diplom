package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.pages.PageMain;
import ru.netology.util.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPositive {
    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }
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
    @DisplayName("Should successfully buying travel by valid card")
    void shouldBuyTravel() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getCardApproved());
        page.toSent();
        page.approvedMessage();
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    @DisplayName("Should successfully buying travel with credit by valid card")
    void shouldBuyTravelOnCredit() {
        PageMain page = new PageMain();
        page.toCredit();
        page.pageCredit();
        page.fillFormBuy(DataHelper.getCardApproved());
        page.toSent();
        page.approvedMessage();
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("APPROVED", status.getStatus());
    }
}


