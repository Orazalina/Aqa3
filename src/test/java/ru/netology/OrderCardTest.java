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
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void correctFormCompletion() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванова Анна");
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, actualMessage.trim());
    }

    @Test
    void withLatinLetters() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Ivanova Anna");
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.className("input__sub")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, actualMessage.trim());
    }

    @Test
    void withIncorrectNumber() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванова Анна");
        textFields.get(1).sendKeys("+77777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        textFields = driver.findElements(By.className("input__sub"));
        String message = textFields.get(1).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, message.trim());
    }

    @Test
    void withNoName() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0);
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        textFields = driver.findElements(By.className("input__sub"));
        String message = textFields.get(0).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, message.trim());
    }

    @Test
    void withNoTel() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванова Анна");
        textFields.get(1);
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        textFields = driver.findElements(By.className("input__sub"));
        String message = textFields.get(1).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, message.trim());
    }

    @Test
    void withNoClickCheckbox() {
        driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иванова Анна");
        textFields.get(1).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box"));
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id=agreement]")).getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMessage, actualMessage.trim());
    }
}