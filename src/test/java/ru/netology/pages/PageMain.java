package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.util.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PageMain {
    private ElementsCollection form = $$(".form-field .input");
    private SelenideElement cardField = form.find(exactText("Номер карты")).$(".input__control");
    private SelenideElement monthField = form.find(exactText("Месяц")).$(".input__control");
    private SelenideElement yaerField = form.find(exactText("Год")).$(".input__control");
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
        yaerField.setValue(info.getYear());
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

//    public void errorMessageClose() {
//        SelenideElement buttonClose = $(".notification button");
//        buttonClose.click();
//    }

    public void clenForm() {
        cardField.sendKeys(Keys.CONTROL + "A");
        cardField.sendKeys(Keys.DELETE);
        monthField.sendKeys(Keys.CONTROL + "A");
        monthField.sendKeys(Keys.DELETE);
        yaerField.sendKeys(Keys.CONTROL + "A");
        yaerField.sendKeys(Keys.DELETE);
        ownerField.sendKeys(Keys.CONTROL + "A");
        ownerField.sendKeys(Keys.DELETE);
        cardField.sendKeys(Keys.CONTROL + "A");
        codeField.sendKeys(Keys.DELETE);
    }

}
