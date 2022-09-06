package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.util.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PageMain {
    private ElementsCollection form = $$(".form-field .input");
    private SelenideElement cardField = form.find(exactText("Номер карты")).$(".input__control");
    private SelenideElement monthField = form.find(exactText("Месяц")).$(".input__control");
    private SelenideElement yearField = form.find(exactText("Год")).$(".input__control");
    private SelenideElement ownerField = form.find(exactText("Владелец")).$(".input__control");
    private SelenideElement codeField = form.find(exactText("CVC/CVV")).$(".input__control");
    private SelenideElement buttonBuy = $$("[type='button']").find(exactText("Купить"));
    private SelenideElement buttonCredit = $$("[type='button']").find(exactText("Купить в кредит"));
    private SelenideElement buttonSent = $$("[type='button']").find(exactText("Продолжить"));
    private SelenideElement pageBuy = $$("h3.heading").find(exactText("Оплата по карте"));
    private SelenideElement pageCredit = $$("h3.heading").find(exactText("Кредит по данным карты"));

    public void toBuy() {
        buttonBuy.click();
    }

    public void toCredit() {
        buttonCredit.click();
    }

    public void toSent() {
        buttonSent.click();
    }

    public void pageBuy() {
        pageBuy.shouldBe(visible);
    }

    public void pageCredit() {
        pageCredit.shouldBe(visible);
    }

    public void fillFormBuy(DataHelper.FormFields info) {
        cardField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getOwner());
        codeField.setValue(info.getCvvCode());
    }

    public void declinedMessage() {
        SelenideElement message= $(byText("Ошибка")).shouldBe(visible, Duration.ofSeconds(10));
        SelenideElement messageSecond= $(byText("Ошибка! Банк отказал в проведении операции.")).shouldBe(visible,Duration.ofSeconds(10));
    }
    public void approvedMessage() {
        SelenideElement message = $(byText("Успешно")).shouldBe(visible, Duration.ofSeconds(10));
        SelenideElement messageSecond = $(byText("Операция одобрена Банком.")).shouldBe(visible,Duration.ofSeconds(10));
    }

    public void emptyFieldCardMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Номер карты"));
        first.parent().$(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    public void emptyFieldMonthMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Месяц"));
        first.parent().$(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    public void emptyFieldYearMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Год"));
        first.parent().$(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    public void emptyFieldOwnerMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Владелец"));
        first.parent().$(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    public void emptyFieldCodeMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("CVC/CVV"));
        first.parent().$(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    public void wrongFieldCardMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Номер карты"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверный формат"));
    }
    public void wrongFieldMonthMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Месяц"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверный формат"));
    }
    public void wrongFieldYearMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Год"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверный формат"));
    }
    public void wrongFieldOwnerMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Владелец"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверный формат"));
    }
    public void wrongFieldCodeMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("CVC/CVV"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверный формат"));
    }
    public void validityMonthMessage() {
        SelenideElement first =$$(".form-field .input .input__top").find(exactText("Месяц"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверно указан срок действия карты"));
    }
    public void validityYearMessage() {
        SelenideElement first = $$(".form-field .input .input__top").find(exactText("Год"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверно указан срок действия карты"));
    }
    public void expiredCardMessageYear() {
        SelenideElement first = $$(".form-field .input .input__top").find(exactText("Год"));
        first.parent().$(".input__sub").shouldHave(exactText("Истёк срок действия карты"));
    }
    public void expiredCardMessageMonth() {
        SelenideElement first = $$(".form-field .input .input__top").find(exactText("Месяц"));
        first.parent().$(".input__sub").shouldHave(exactText("Неверно указан срок действия карты"));
    }

//    public void errorMessageClose() {
//        SelenideElement buttonClose = $(".notification button");
//        buttonClose.click();
//    }

    public void cleanForm() {
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
