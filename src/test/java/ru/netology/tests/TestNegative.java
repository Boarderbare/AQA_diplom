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

    private final String messageEmpty = "Поле обязательно для заполнения";
    private final String messageWrongFormat = "Неверный формат";
    private final String messageWrongDate = "Неверно указан срок действия карты";
    private final String messageExpiredDate = "Истёк срок действия карты";


    @Test
    @DisplayName("Form Buy.Should be declined operation buying travel by invalid card")
    void shouldDeclineBuyTravelFormBuy() {
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
    @DisplayName("Form Buy. Should be declined operation buying travel by invalid card without write data in DB")
    void shouldDeclineBuyTravelInvalidCardFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getAnyCard());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.declinedMessage();
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in card field")
    void shouldMessageFieldCardWrongFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCardWithZero());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldCardMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in month field. Month - '00' ")
    void shouldMessageFieldMonthWrongFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthWithZeros());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldMonthMessage(messageWrongDate);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in month field. Month - '13' ")
    void shouldMessageFieldMonthWrong2FormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthWrong());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldMonthMessage(messageWrongDate);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in month field. Month - '1' ")
    void shouldMessageFieldMonthWrong3FormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthOneDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldMonthMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in year field. Year - '1' ")
    void shouldMessageFieldYearWrongFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearOneDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldYearMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in year field. Year - value more then 5 year")
    void shouldMessageFieldYearWrong2FormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearWrong());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldYearMessage(messageWrongDate);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in year field. Year - value '00' ")
    void shouldMessageExpiredCardFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearWithZeros());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldYearMessage(messageExpiredDate);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in month field. Year - currently, month - before")
    void shouldMessageExpiredCard2FormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getDateExpired());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldMonthMessage(messageExpiredDate);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in Owner field. Value 'owner' with digits")
    void shouldMessageWrongFieldOwnerFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerDigits());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in Owner field. Value 'owner' on cyrillic")
    void shouldMessageWrongFieldOwner2FormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerCyrillic());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in Owner field. Value 'owner' only first name")
    void shouldMessageWrongFieldOwner3FormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerOnlyFirstName());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in Owner field. Value 'owner' with special characters")
    void shouldMessageWrongFieldOwner4FormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getWrongOwnerWithSpecChar());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about wrong data in 'Code' field. Value 'code' - one digit ")
    void shouldMessageWrongFieldCodeFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCodeOneDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldCodeMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Buy. Should be messages under fields disappear after change values")
    void shouldDisappearMassagesUnderFieldsFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getFormAllFieldsDigit());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldCardMessage(messageWrongFormat);
        pageBuy.messageFieldMonthMessage(messageWrongFormat);
        pageBuy.messageFieldYearMessage(messageWrongFormat);
        pageBuy.messageFieldOwnerMessage(messageWrongFormat);
        pageBuy.messageFieldCodeMessage(messageWrongFormat);
        pageBuy.cleanFieldsForm();
        pageBuy.fillFormBuy(DataHelper.getAnyCard());
        pageBuy.toSent();
        pageBuy.declinedMessage();
    }

    @Test
    @DisplayName("Form Buy. Should be messages about empty field 'number card'")
    void shouldMessageEmptyFieldsCardFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCardEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldCardMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about empty field 'Month'")
    void shouldMessageEmptyFieldsMonthFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getMonthEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldMonthMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about empty field 'year'")
    void shouldMessageEmptyFieldsYearFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getYearEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldYearMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about empty field 'owner")
    void shouldMessageEmptyFieldsOwnerFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getOwnerEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldOwnerMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Buy. Should be messages about empty field 'CVC/CVV")
    void shouldMessageEmptyFieldsCodeFormBuy() {
        pageMain.toBuy()
                .fillFormBuy(DataHelper.getCodeEmpty());
        PageBuy pageBuy = new PageBuy();
        pageBuy.toSent();
        pageBuy.messageFieldCodeMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Credit. Should be declined operation buying travel on credit by invalid card")
    void shouldDeclineBuyTravelWithCredit() {
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
    @DisplayName("Form Credit.  Should be declined operation buying travel by invalid card without write data in DB")
    void shouldDeclineBuyTravelInvalidCardFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getAnyCard());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.declinedMessage();
    }

    @Test
    @DisplayName("Form Credit. Should be messages about wrong data in card field")
    void shouldMessageFieldCardWrongFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getCardWithZero());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldCardMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in month field. Month - '00' ")
    void shouldMessageFieldMonthWrongFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getMonthWithZeros());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldMonthMessage(messageWrongDate);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in month field. Month - '13' ")
    void shouldMessageFieldMonthWrong2FormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getMonthWrong());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldMonthMessage(messageWrongDate);
    }

    @Test
    @DisplayName("Form Credit. Should be messages about wrong data in month field. Month - '1' ")
    void shouldMessageFieldMonthWrong3FormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getMonthOneDigit());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldMonthMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit. Should be messages about wrong data in year field. Year - '1' ")
    void shouldMessageFieldYearWrongFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getYearOneDigit());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldYearMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in year field. Year - value more then 5 year")
    void shouldMessageFieldYearWrong2FormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getYearWrong());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldYearMessage(messageWrongDate);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in year field. Year - value '00' ")
    void shouldMessageExpiredCardFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getYearWithZeros());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldYearMessage(messageExpiredDate);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in month field. Year - currently, month - before")
    void shouldMessageExpiredCard2FormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getDateExpired());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldMonthMessage(messageExpiredDate);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in Owner field. Value 'owner' with digits")
    void shouldMessageWrongFieldOwnerFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getOwnerDigits());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit. Should be messages about wrong data in Owner field. Value 'owner' on cyrillic")
    void shouldMessageWrongFieldOwner2FormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getOwnerCyrillic());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in Owner field. Value 'owner' only first name")
    void shouldMessageWrongFieldOwner3FormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getOwnerOnlyFirstName());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about wrong data in Owner field. Value 'owner' with special characters")
    void shouldMessageWrongFieldOwner4FormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getWrongOwnerWithSpecChar());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldOwnerMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit. Should be messages about wrong data in 'Code' field. Value 'code' - one digit ")
    void shouldMessageWrongFieldCodeFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getCodeOneDigit());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldCodeMessage(messageWrongFormat);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages under fields disappear after change values")
    void shouldDisappearMassagesUnderFieldsFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getFormAllFieldsDigit());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldCardMessage(messageWrongFormat);
        pageCredit.messageFieldMonthMessage(messageWrongFormat);
        pageCredit.messageFieldYearMessage(messageWrongFormat);
        pageCredit.messageFieldOwnerMessage(messageWrongFormat);
        pageCredit.messageFieldCodeMessage(messageWrongFormat);
        pageCredit.cleanFieldsForm();
        pageCredit.fillFormCredit(DataHelper.getAnyCard());
        pageCredit.toSent();
        pageCredit.declinedMessage();
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about empty field 'number card'")
    void shouldMessageEmptyFieldsCardFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getCardEmpty());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldCardMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about empty field 'Month'")
    void shouldMessageEmptyFieldsMonthFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getMonthEmpty());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldMonthMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about empty field 'year'")
    void shouldMessageEmptyFieldsYearFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getYearEmpty());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldYearMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Credit.  Should be messages about empty field 'owner")
    void shouldMessageEmptyFieldsOwnerFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getOwnerEmpty());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldOwnerMessage(messageEmpty);
    }

    @Test
    @DisplayName("Form Credit. Should be messages about empty field 'CVC/CVV")
    void shouldMessageEmptyFieldsCodeFormCredit() {
        pageMain.toCredit()
                .fillFormCredit(DataHelper.getCodeEmpty());
        PageCredit pageCredit = new PageCredit();
        pageCredit.toSent();
        pageCredit.messageFieldCodeMessage(messageEmpty);
    }
}