package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.pages.PageBuy;
import ru.netology.pages.PageCredit;
import ru.netology.pages.PageMain;
import ru.netology.util.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPositive {
    private PageMain pageMain = new PageMain();

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        DataHelper.cleanData();
        SelenideLogger.removeListener("allure");
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @Test
    @DisplayName("Should successfully buying travel by valid card")
    void shouldBuyTravel() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCardApproved());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.approvedMessage();
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("APPROVED", status.getStatus());
    }

    @Test
    @DisplayName("Should successfully buying travel with credit by valid card")
    void shouldBuyTravelOnCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getCardApproved());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.approvedMessage();
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("APPROVED", status.getStatus());
    }
}



