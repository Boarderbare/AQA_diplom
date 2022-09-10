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

public class TestNegative {
    private PageMain pageMain = new PageMain();

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
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCardDeclined());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.declinedMessage();
        var id = DataHelper.getIdOperationBuying();
        var status = DataHelper.getStatusOperationBuying();
        assertEquals(id, status.getTransaction_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    @DisplayName("Should be declined operation buying travel on credit by invalid card")
    void shouldNoBuyTravelWithCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getCardDeclined());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.declinedMessage();
        var id = DataHelper.getIdOperationCredit();
        var status = DataHelper.getStatusOperationCredit();
        assertEquals(id, status.getBank_id());
        assertEquals("DECLINED", status.getStatus());
    }

    @Test
    @DisplayName("Should be declined operation buying travel by invalid card without write data in DB")
    void shouldNoBuyTravelInvalidCard() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getAnyCard());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.declinedMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in card field")
    void shouldMessageFieldCardWrong() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCardWithZero());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldCardMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in month field. Month - '00' ")
    void shouldMessageFieldMonthWrong() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthWithZeros());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.validityMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in month field. Month - '13' ")
    void shouldMessageFieldMonthWrong2() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthWrong());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.validityMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in month field. Month - '1' ")
    void shouldMessageFieldMonthWrong3() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthOneDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - '1' ")
    void shouldMessageFieldYearWrong() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearOneDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldYearMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - value more then 5 year")
    void shouldMessageFieldYearWrong2() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearWrong());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.validityYearMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - value '00' ")
    void shouldMessageExpiredCard() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearWithZeros());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.expiredCardMessageYear();
    }

    @Test
    @DisplayName("Should be messages about wrong data in year field. Year - currently, month - before")
    void shouldMessageExpiredCard2() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getDateExpired());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.expiredCardMessageMonth();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' with digits")
    void shouldMessageWrongFieldOwner() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerDigits());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' on cyrillic")
    void shouldMessageWrongFieldOwner2() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerCyrillic());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' only first name")
    void shouldMessageWrongFieldOwner3() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerOnlyFirstName());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in Owner field. Value 'owner' with special characters")
    void shouldMessageWrongFieldOwner4() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getWrongOwnerWithSpecChar());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about wrong data in 'Code' field. Value 'code' - digit ")
    void shouldMessageWrongFieldCode() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCodeOneDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldCodeMessage();
    }

    @Test
    @DisplayName("Should be messages under fields disappear after change values")
    void shouldDisappearMassagesUnderFields() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getFormAllFieldsDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.wrongFieldCardMessage();
        pageBuy.wrongFieldMonthMessage();
        pageBuy.wrongFieldYearMessage();
        pageBuy.wrongFieldOwnerMessage();
        pageBuy.wrongFieldCodeMessage();
        pageBuy.cleanFieldsForm();
        pageBuy.fillFormBuy(DataHelper.getAnyCard());
        pageBuy.toSent();
        pageBuy.declinedMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'number card'")
    void shouldMessageEmptyFieldsCard() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCardEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.emptyFieldCardMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'Month'")
    void shouldMessageEmptyFieldsMonth() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.emptyFieldMonthMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'year'")
    void shouldMessageEmptyFieldsYear() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.emptyFieldYearMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'owner")
    void shouldMessageEmptyFieldsOwner() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.emptyFieldOwnerMessage();
    }

    @Test
    @DisplayName("Should be messages about empty field 'CVC/CVV")
    void shouldMessageEmptyFieldsCode() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCodeEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.emptyFieldCodeMessage();
    }
}