package netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BankCardTest {
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
    public void shouldNotOrderCardIfNameWithSpaceBar() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("  ");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79253456565");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void shouldNotOrderCardIfPhoneIsEmptyLine() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void shouldNotOrderCardIfNameIsEmptyLine() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79456435353");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void shouldNotOrderCardIfNameAndPhoneIsEmptyLines() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.findElement(By.cssSelector(".button")).click();

        String nameNotFilled = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", nameNotFilled.trim());
        String phoneNotFilled = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", phoneNotFilled.trim());
    }
    @Test
    public void shouldNotOrderCardIfPhoneWithSpaceBar() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("  ");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void shouldOrderCardWithCorrectValues() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79202360185");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldOrderCardIfNameWithHyphen() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Анна-Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7202360185");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }


    @Test
    public void shouldOrderCardIfNameWithJ() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаев Алексей");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79202360185");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameInEnglish() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Polezhaeva Maria");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79202360185");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithYo() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванова Алёна");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79202360184");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithHieroglyphs() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("愛愛愛 愛愛愛");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7202368372");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithArabicSymbols() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("العوالجهل ظ");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79356437856");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithSpecialSymbols() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева М%а*рия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79256784534");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNameWithNumbers() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева 2345");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79253456565");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }


    @Test
    public void shouldNotOrderCardIfPhone10Numbers() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7920236018");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }


    @Test
    public void shouldNotOrderCardIfPhoneWithLetters() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Лето");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }



    @Test
    public void shouldNotOrderCardIfPhoneWithHieroglyphs() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("愛愛");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithArabianSymbols() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("النور والجهل ظلام");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfPhoneWithSpecialSymbols() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7920%35363");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();

        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldNotOrderCardIfNoClickAgreement() {

        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Полежаева Мария");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79202360185");

        form.findElement(By.cssSelector(".button")).click();
        WebElement agree = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid"));
        assertTrue(agree.isDisplayed(), "Для отправки заявки требуется соглашение на обработку данных");
    }
}