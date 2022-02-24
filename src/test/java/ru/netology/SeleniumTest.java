package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SeleniumTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {

        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void positiveTest() {

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Гарри Эванс-Поттер");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actualMessage = driver.findElement(By.cssSelector(".paragraph_theme_alfa-on-white")).getText();
        assertEquals(expectedMessage, actualMessage.trim());
    }

    @Test
    public void negativeBecauseOfTranslitName() {

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Harry Поттер");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals(expectedMessage, actualMessage.trim());
    }

    @Test
    public void negativeBecauseOfEmptyNameField() {

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedMessage = "Поле обязательно для заполнения";
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals(expectedMessage, actualMessage.trim());
    }

    @Test
    public void negativeBecauseOfIncorrectPhoneNumber() {

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Гарри Поттер");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("8005553535");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals(expectedMessage, actualMessage.trim());
    }

    @Test
    public void negativeBecauseOfEmptyPhoneNumber() {

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Гарри Поттер");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expectedMessage = "Поле обязательно для заполнения";
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals(expectedMessage, actualMessage.trim());
    }

    @Test
    public void negativeBecauseOfEmptyCheckBox() {

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Гарри Поттер");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);
    }
}