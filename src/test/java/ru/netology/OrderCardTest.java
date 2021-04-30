package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

public class OrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
            //System.setProperty("webdriver.gecko.driver", "./driver/firefox/geckodriver.exe");
        WebDriverManager.firefoxdriver().setup();

    }

    @BeforeEach
    void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        //options.addArguments("--marionette");
        //options.addArguments("--marionette");
        //options.addArguments("--disable-dev-shm-usage");
        //options.addArguments("--no-sandbox");
        //options.addArguments("--headless");
        //options.addArguments("-foreground");
        //options.addArguments("-no-remote");
        //options.addArguments("-profile");
        //options.setHeadless(true);
        driver = new FirefoxDriver(options);
    }

    @AfterEach
    void  tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTest() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванова Анна");
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage.trim(), actualMessage.trim());
    }



}
