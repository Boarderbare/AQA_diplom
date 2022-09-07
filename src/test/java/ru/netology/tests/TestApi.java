package ru.netology.tests;

import org.junit.jupiter.api.AfterAll;
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

    @Test
    public void shouldSuccessfulBuying() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardApproved();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormBuy(info);
        assertEquals("APPROVED", actual);
    }

    @Test
    public void shouldSuccessfulBuyingOnCredit() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardApproved();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormCredit(info);
        assertEquals("APPROVED", actual);
    }

    @Test
    public void shouldDeclineBuying() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardDeclined();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormBuy(info);
        assertEquals("DECLINED", actual);
    }

    @Test
    public void shouldDeclineBuyingOnCredit() {
        PageMain page = new PageMain();
        var info = DataHelper.getCardDeclined();
        RestHelper rest = new RestHelper();
        var actual = rest.sentFormCredit(info);
        assertEquals("DECLINED", actual);
    }
}
