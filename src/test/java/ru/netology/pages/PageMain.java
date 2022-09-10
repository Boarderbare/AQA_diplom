package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class PageMain {
    public PageBuy toBuy() {
        SelenideElement buttonBuy = $$("[type='button']").find(exactText("Купить"));
        buttonBuy.click();
        return new PageBuy();
    }

    public PageCredit toCredit() {
        SelenideElement buttonCredit = $$("[type='button']").find(exactText("Купить в кредит"));
        buttonCredit.click();
        return new PageCredit();
    }
}
