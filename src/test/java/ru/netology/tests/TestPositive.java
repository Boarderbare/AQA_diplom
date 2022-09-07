package ru.netology.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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


