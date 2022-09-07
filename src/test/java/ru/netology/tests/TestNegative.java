package ru.netology.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.pages.PageMain;
import ru.netology.util.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNegative {

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    public static void cleanData() {
        DataHelper.cleanData();
    }

    @Test
    @DisplayName("Should be declined operation buying travel with credit by invalid card")
    void shouldNoBuyTravelWithCredit() {
        PageMain page = new PageMain();
        page.toCredit();
        page.pageCredit();
        page.fillFormBuy(DataHelper.getCardDeclined());
        page.toSent();
        page.declinedMessage();
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    @DisplayName("Should be declined operation buying travel by invalid card")
    void shouldNoBuyTravel() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getCardDeclined());
        page.toSent();
        page.declinedMessage();
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    @DisplayName("Should be declined operation buying travel by invalid card. without write data in DB")
    void shouldNoBuyTravelInvalidCard() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.toSent();
        page.declinedMessage();
    }

    @Test
    @DisplayName("Should be messages about filling form's fields")
    void shouldMessageFillFieldsAll() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.toSent();
        page.emptyFieldCardMessage();
        page.emptyFieldMonthMessage();
        page.emptyFieldYearMessage();
        page.emptyFieldOwnerMessage();
        page.emptyFieldCodeMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in card field")
    void shouldMessageFieldCardWrong() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getCardWithZero());
        page.toSent();
        page.wrongFieldCardMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in month field. Month - '00' ")
    void shouldMessageFieldMonthWrong() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getMonthWithZeros());
        page.toSent();
        page.validityMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in month field. Month - '13' ")
    void shouldMessageFieldMonthWrong2() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getMonthWrong());
        page.toSent();
        page.validityMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in month field. Month - '1' ")
    void shouldMessageFieldMonthWrong3() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getMonthOneDigit());
        page.toSent();
        page.wrongFieldMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - '1' ")
    void shouldMessageFieldYearWrong() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getYearOneDigit());
        page.toSent();
        page.wrongFieldYearMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - value more then 5 year")
    void shouldMessageFieldYearWrong2() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getYearWrong());
        page.toSent();
        page.validityYearMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - value '00' ")
    void shouldMessageExpiredCard() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getYearWithZeros());
        page.toSent();
        page.expiredCardMessageYear();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - currently, month - before")
    void shouldMessageExpiredCard2() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getDateExpired());
        page.toSent();
        page.expiredCardMessageMonth();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' with digits")
    void shouldMessageWrongFieldOwner() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getOwnerDigits());
        page.toSent();
        page.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' on cyrillic")
    void shouldMessageWrongFieldOwner2() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getOwnerCyrillic());
        page.toSent();
        page.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' only first name")
    void shouldMessageWrongFieldOwner3() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getOwnerOnlyFirstName());
        page.toSent();
        page.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' with special characters")
    void shouldMessageWrongFieldOwner4() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getWrongOwnerWithSpecChar());
        page.toSent();
        page.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in 'Code' field. Value 'code' - digit ")
    void shouldMessageWrongFieldCode() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getCodeWrong());
        page.toSent();
        page.wrongFieldCodeMessage();
    }

    @Test
    @DisplayName("Should be messages under fields disappear after change values")
    void shouldDisappearMassagesUnderFields() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getFormAllFieldsDigit());
        page.toSent();
        page.wrongFieldCardMessage();
        page.wrongFieldMonthMessage();
        page.wrongFieldYearMessage();
        page.wrongFieldOwnerMessage();
        page.wrongFieldCodeMessage();
        page.cleanForm();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.toSent();
        page.declinedMessage();
    }
}