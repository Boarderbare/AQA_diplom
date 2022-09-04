package ru.netology.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pages.PageMain;
import ru.netology.util.DataHelper;
import ru.netology.util.RestHelper;

import static com.codeborne.selenide.Selenide.open;

public class TestPositive {
//    @BeforeEach
//    public void setUp() {
//        open("http://localhost:8080/");
//    }
//
//    @AfterAll
//    public static void cleanData() {
//        DataHelper.cleanData();
//    }
//
    @Test
    void shouldBuyTravel(){
        open("http://localhost:8080/");
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getCardApproved());
        page.toSent();
        page.approvedMessage();

    }
    @Test
    void shouldBuyTravelOnCredit(){
        open("http://localhost:8080/");
        PageMain page = new PageMain();
        page.toCredit();
        page.pageCredit();
        page.fillFormBuy(DataHelper.getCardDeclined());
        page.toSent();
        page.declinedMessage();
    }

    @Test
    void should(){

    }
    @Test
    void date(){

    }
    @Test
    void dat(){
        System.out.println(DataHelper.getOwnerCyrillic());
    }
}


