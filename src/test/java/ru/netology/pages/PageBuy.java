package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.util.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PageBuy {

    public PageBuy() {
        SelenideElement pageCredit = $$("h3.heading").find(exactText("Оплата по карте"));
        pageCredit.shouldBe(visible);
    }

    private ElementsCollection form = $$(".form-field .input");
    private SelenideElement cardField = form.find(exactText("Номер карты")).$(".input__control");
    private SelenideElement monthField = form.find(exactText("Месяц")).$(".input__control");
    private SelenideElement yearField = form.find(exactText("Год")).$(".input__control");
    private SelenideElement ownerField = form.find(exactText("Владелец")).$(".input__control");
    private SelenideElement codeField = form.find(exactText("CVC/CVV")).$(".input__control");
    private SelenideElement buttonSent = $$("[type='button']").find(exactText("Продолжить"));

    private ElementsCollection formBelow = $$(".form-field .input .input__top");
    private SelenideElement belowFieldCard = formBelow.find(exactText("Номер карты")).parent().$(".input__sub");
    private SelenideElement belowFieldOwner = formBelow.find(exactText("Владелец")).parent().$(".input__sub");
    private SelenideElement belowFieldYear = formBelow.find(exactText("Год")).parent().$(".input__sub");
    private SelenideElement belowFieldMonth = formBelow.find(exactText("Месяц")).parent().$(".input__sub");
    private SelenideElement belowFieldCode = formBelow.find(exactText("CVC/CVV")).parent().$(".input__sub");

    private SelenideElement messageError = $(byText("Ошибка"));
    private SelenideElement messageDecline = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement messageSuccess = $(byText("Успешно"));
    private SelenideElement messageApprove = $(byText("Операция одобрена Банком."));

    public void toSent() {
        buttonSent.click();
    }

    public void fillFormBuy(DataHelper.FormFields info) {
        cardField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvvCode());
    }

    public void declinedMessage() {
        messageError.shouldBe(visible, Duration.ofSeconds(10));
        messageDecline.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void approvedMessage() {
        messageSuccess.shouldBe(visible, Duration.ofSeconds(10));
        messageApprove.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void emptyFieldCardMessage() {
        belowFieldCard.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyFieldMonthMessage() {
        belowFieldMonth.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyFieldYearMessage() {
        belowFieldYear.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyFieldOwnerMessage() {
        belowFieldOwner.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void emptyFieldCodeMessage() {
        belowFieldCode.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    public void wrongFieldCardMessage() {
        belowFieldCard.shouldHave(exactText("Неверный формат"));
    }

    public void wrongFieldMonthMessage() {
        belowFieldMonth.shouldHave(exactText("Неверный формат"));
    }

    public void wrongFieldYearMessage() {
        belowFieldYear.shouldHave(exactText("Неверный формат"));
    }

    public void wrongFieldOwnerMessage() {
        belowFieldOwner.shouldHave(exactText("Неверный формат"));
    }

    public void wrongFieldCodeMessage() {
        belowFieldCode.shouldHave(exactText("Неверный формат"));
    }

    public void validityMonthMessage() {
        belowFieldMonth.shouldHave(exactText("Неверно указан срок действия карты"));
    }

    public void validityYearMessage() {
        belowFieldYear.shouldHave(exactText("Неверно указан срок действия карты"));
    }

    public void expiredCardMessageYear() {
        belowFieldYear.shouldHave(exactText("Истёк срок действия карты"));
    }

    public void expiredCardMessageMonth() {
        belowFieldMonth.shouldHave(exactText("Истёк срок действия карты"));
    }

    public void cleanFieldsForm() {
        cardField.sendKeys(Keys.CONTROL + "A");
        cardField.sendKeys(Keys.DELETE);
        monthField.sendKeys(Keys.CONTROL + "A");
        monthField.sendKeys(Keys.DELETE);
        yearField.sendKeys(Keys.CONTROL + "A");
        yearField.sendKeys(Keys.DELETE);
        ownerField.sendKeys(Keys.CONTROL + "A");
        ownerField.sendKeys(Keys.DELETE);
        codeField.sendKeys(Keys.CONTROL + "A");
        codeField.sendKeys(Keys.DELETE);
    }
}
