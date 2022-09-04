package ru.netology.util;

import com.github.javafaker.Faker;
import data.StatusOperation;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.text.DecimalFormat;
import java.util.Date;
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

    public static FormFields geAnyCard() {
        var date = getDate();
        return new FormFields(getNumberCard(), date.getMonth(), date.getYear(), getOwner(), getCode());
    }

    public static String getNumberCard() {
        return faker.finance().creditCard();
    }

    public static int getDigit() {
        return new Random().nextInt(10);
    }

    @Value
    public static class DateMonthYear {
        String month;
        String year;
    }

    public static DateMonthYear getDate() {
        var date = faker.date().future(365 * 5, TimeUnit.DAYS);
        var month = new DecimalFormat("00").format(date.getMonth() + 1);
        var year = String.valueOf(date.getYear() - 100);
        return new DateMonthYear(month, year);
    }

    public static DateMonthYear getDateInPastThisYear() {
        //ToDo
        var date = faker.date().future(365 * 5, TimeUnit.DAYS);
        var month = new DecimalFormat("00").format(date.getMonth() + 1);
        var year = String.valueOf(date.getYear() - 100);
        return new DateMonthYear(month, year);
    }

    public static String getOwner() {
        return faker.name().fullName();
    }
    public static String getWrongOwnerW() {
        return faker.name().fullName()+"#@";
    }

    public static String getOwnerOnlyFirstName() {
        return faker.name().firstName();
    }

    public static String getOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getCode() {
        return faker.number().digits(3);
    }

    @SneakyThrows

    public static String getIdOperationBuying() {
        var runner = new QueryRunner();
        var getId = "select payment_id from order_entity order by created desc limit 1";
        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getId, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static StatusOperation getStatusOperationBuying() {
        var runner = new QueryRunner();
        var getStatus = "select status, transaction_id  from payment_entity order by created desc limit 1";

        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getStatus, new BeanHandler<>(StatusOperation.class));
        }
    }

    @SneakyThrows
    public static String getIdOperationCredit() {
        var runner = new QueryRunner();
        var getId = "select credit_id from order_entity order by created desc limit 1";
        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getId, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static StatusOperation getStatusOperationCredit() {
        var runner = new QueryRunner();
        var getStatus = "select status, bank_id _id  from credit_request_entity  order by created desc limit 1";

        try (var conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db", "postgresdb", "pass");) {
            return runner.query(conn, getStatus, new BeanHandler<>(StatusOperation.class));
        }
    }
}