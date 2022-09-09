package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
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
    static void tearDownAll() {
        DataHelper.cleanData();
        SelenideLogger.removeListener("allure");
    }
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
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
        page.fillFormBuy(DataHelper.getCodeOneDigit());
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
        page.cleanFieldCard();
        page.cleanFieldMonth();
        page.cleanFieldYear();
        page.cleanFieldOwner();
        page.cleanFieldCode();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.toSent();
        page.declinedMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'number card'")
    void shouldMessageEmptyFieldsCard() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.cleanFieldCard();
        page.toSent();
        page.emptyFieldCardMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'Month'")
    void shouldMessageEmptyFieldsMonth() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.cleanFieldMonth();
        page.toSent();
        page.emptyFieldMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'year'")
    void shouldMessageEmptyFieldsYear() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.cleanFieldYear();
        page.toSent();
        page.emptyFieldYearMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'owner")
    void shouldMessageEmptyFieldsOwner() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.cleanFieldOwner();
        page.toSent();
        page.emptyFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'CVC/CVV")
    void shouldMessageEmptyFieldsCode() {
        PageMain page = new PageMain();
        page.toBuy();
        page.pageBuy();
        page.fillFormBuy(DataHelper.getAnyCard());
        page.cleanFieldCode();
        page.toSent();
        page.emptyFieldCodeMessage();
    }
}