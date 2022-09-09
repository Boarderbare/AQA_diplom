package ru.netology.util;

import com.github.javafaker.Faker;
import ru.netology.data.StatusOperationBuying;
import ru.netology.data.StatusOperationCredit;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataHelper {
    public DataHelper() {
    }

    public static Faker faker = new Faker(new Locale("en"));

    @Value
    public static class FormFields {
        private String number;
        private String month;
        private String year;
        private String owner;
        private String cvvCode;
    }

    public static FormFields getCardApproved() {
        var number = "1111 2222 3333 4444";
        var date = getDate();
        return new FormFields(number, date.getMonth(), date.getYear(), getOwner(), getCode());
    }

    public static FormFields getCardDeclined() {
        var number = "5555 6666 7777 8888";
        var date = getDate();
        return new FormFields(number, date.getMonth(), date.getYear(), getOwner(), getCode());
    }

    public static FormFields getAnyCard() {
        var date = getDate();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getOwner(), getCode());
    }

    public static FormFields getCardWithZero() {
        var date = getDate();
        return new FormFields(getZero(), date.getMonth(), date.getYear(), getOwner(), getCode());
    }

    public static FormFields getCardEmpty() {
        var date = getDate();
        return new FormFields(getEmpty(), date.getMonth(), date.getYear(), getOwner(), getCode());
    }
    public static FormFields getCardOneDigit() {
        var date = getDate();
        return new FormFields(getDigit(), date.getMonth(), date.getYear(), getOwner(), getCode());
    }

    public static String getNumberCard() {
        return faker.business().creditCardNumber();
    }

    public static String getDigit() {
        return String.valueOf(new Random().nextInt(9) + 1);
    }

    public static String getZero() {
        return "0";
    }

    public static String getEmpty() {
        return "";
    }
    public static String getOneChar() {
        return faker.letterify("?");
    }

    @Value
    public static class DateMonthYear {
        String month;
        String year;
    }

    public static FormFields getMonthWithZeros() {
        var date = getDate();
        return new FormFields(getNumberCard(), "00", date.getYear(), getOwner(), getCode());
    }

    public static FormFields getMonthWrong() {
        return new FormFields(getNumberCard(), "13", getDate().getYear(), getOwner(), getCode());
    }

    public static FormFields getMonthOneDigit() {
        return new FormFields(getNumberCard(), getDigit(), getDate().getYear(), getOwner(), getCode());
    }

    public static FormFields getMonthEmpty() {
        return new FormFields(getNumberCard(), getEmpty(), getDate().getYear(), getOwner(), getCode());
    }

    public static FormFields getYearOneDigit() {
        return new FormFields(getNumberCard(), getDate().getMonth(), getDigit(), getOwner(), getCode());
    }

    public static FormFields getYearEmpty() {
        return new FormFields(getNumberCard(), getDate().getMonth(), getEmpty(), getOwner(), getCode());
    }

    public static FormFields getYearWithZeros() {
        return new FormFields(getNumberCard(), getDate().getMonth(), "00", getOwner(), getCode());
    }

    public static FormFields getYearWrong() {
        var date = getDate();
        var dateWrong = faker.date().future(365 * 10, 365 * 5, TimeUnit.DAYS);
        var year = String.valueOf(dateWrong.getYear() - 100);
        return new FormFields(getNumberCard(), date.getMonth(), year, getOwner(), getCode());
    }

    public static DateMonthYear getDate() {
        var date = faker.date().future(365 * 5, TimeUnit.DAYS);
        var month = new DecimalFormat("00").format(date.getMonth() + 1);
        var year = String.valueOf(date.getYear() - 100);
        return new DateMonthYear(month, year);
    }

    public static DateMonthYear getDateInPastThisYear() {
        LocalDate date = LocalDate.now();
        var year = new DecimalFormat("00").format(date.getYear() - 2000);
        int month = date.getMonthValue();
        var valueMonth = String.valueOf(new DecimalFormat("00").format(new Random().nextInt(month - 1) + 1));
        return new DateMonthYear(valueMonth, year);
    }

    public static FormFields getDateExpired() {
        var date = getDateInPastThisYear();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getOwner(), getCode());
    }

    public static String getOwner() {
        return faker.name().fullName();
    }

    public static FormFields getOwnerDigits() {
        var date = getDateInPastThisYear();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getDigit(), getCode());
    }

    public static FormFields getOwnerEmpty() {
        var date = getDateInPastThisYear();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getEmpty(), getCode());
    }
    public static FormFields getOwnerOneChar() {
        var date = getDateInPastThisYear();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getOneChar(), getCode());
    }

    public static FormFields getWrongOwnerWithSpecChar() {
        var date = getDateInPastThisYear();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), faker.name().fullName() + "#@", getCode());
    }

    public static FormFields getOwnerOnlyFirstName() {
        var date = getDateInPastThisYear();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), faker.name().firstName(), getCode());
    }

    public static FormFields getOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        var nameCyrillic = faker.name().fullName();
        var date = getDateInPastThisYear();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), nameCyrillic, getCode());
    }

    public static String getCode() {
        return faker.number().digits(3);
    }

    public static FormFields getCodeOneDigit() {
        var date = getDate();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getOwner(), getDigit());
    }

    public static FormFields getCodeEmpty() {
        var date = getDate();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getOwner(), getEmpty());
    }

    public static FormFields getFormAllFieldsDigit() {
        return new FormFields(getDigit(), getDigit(), getDigit(), getDigit(), getDigit());
    }

    @SneakyThrows
    public static String getIdOperationBuying() {
        var runner = new QueryRunner();
        var getId = "SELECT payment_id FROM order_entity ORDER BY created DESC LIMIT 1";
        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getId, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static StatusOperationBuying getStatusOperationBuying() {
        var runner = new QueryRunner();
        var getStatus = "SELECT status, transaction_id  FROM payment_entity ORDER BY created DESC LIMIT 1";

        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getStatus, new BeanHandler<>(StatusOperationBuying.class));
        }
    }

    @SneakyThrows
    public static String getIdOperationCredit() {
        var runner = new QueryRunner();
        var getId = "SELECT credit_id FROM order_entity ORDER BY created DESC LIMIT 1";
        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getId, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static StatusOperationCredit getStatusOperationCredit() {
        var runner = new QueryRunner();
        var getStatus = "SELECT status, bank_id  FROM credit_request_entity  ORDER BY created DESC LIMIT 1";

        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getStatus, new BeanHandler<>(StatusOperationCredit.class));
        }
    }

    @SneakyThrows
    public static void cleanData() {
        var runner = new QueryRunner();
        var deleteDate1 = "delete  from payment_entity";
        var deleteDate2 = "delete  from order_entity";
        var deleteDate3 = "delete  from credit_request_entity";

        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            runner.update(conn, deleteDate1);
            runner.update(conn, deleteDate2);
            runner.update(conn, deleteDate3);
        }
    }
}

