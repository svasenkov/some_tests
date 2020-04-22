package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.logging.Level;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static helpers.Attachments.*;

//    Написать автотест, который выполнит поиск на yandex.ru
//    любой подстроки и проверит, что общее количество результатов больше 1000.

@Story("Тестовое задание GUI")
@Tag("ui")
class UiTests {
    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
        Configuration.remote = "http://" + System.getProperty("selenoid_url") + ":4444/wd/hub/";
    }

    @AfterEach
    public void afterEach(){
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachBrowserConsoleLogs();
        attachVideo();

        closeWebDriver();
    }

    @Test
    @DisplayName("Проверка, что результатов поиска больше 1000")
    void searchTest() {
        open("http://yandex.ru");

        $("#text").val("text").pressEnter();

        $(".serp-adv__found").shouldHave(text("млн результатов"));
    }
}
