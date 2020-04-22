package tests;

import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import models.AccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.notNullValue;

    /* Существует веб-сервис, который отображает информацию:
    по счетам:
    GET http://kn-ktapp.herokuapp.com/apitest/accounts
    по отдельному счету:
    GET http://kn-ktapp.herokuapp.com/apitest/accounts/12345678
    Необходимо выполнить GET запрос к каждом из сервисов. Полученный ответ десериализовать в объект класса.
    Проверить, что значение account_id не пустое. */

@Story("Тестовое задание API")
@Tag("api")
public class ApiTests {
    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "http://kn-ktapp.herokuapp.com";
    }

    @Test
    @DisplayName("Проверка, что в запросах к списку счетов account_id не пустой")
    void accountsTest() { // todo move to steps
        AccountData[] accountsDataList = get("/apitest/accounts").as(AccountData[].class);

        for (AccountData accountData: accountsDataList) {
            get("/apitest/accounts/" + accountData.getAccountId())
                    .then()
                    .body("account_id", notNullValue());
        }
    }

}
