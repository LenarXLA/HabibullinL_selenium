import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import typeReg.ExpensiveCategory;
import typeReg.MainCategory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Epic("Test avito site")
public class StepDefsTest {
    public static LocatorPage locatorPage;
    public static WebDriver driver;

    @ParameterType(".*")
    public MainCategory mainCategory(String categ) {
        return MainCategory.valueOf(categ);
    }

    @ParameterType(".*")
    public ExpensiveCategory expensiveCategory(String valueCat) {
        return ExpensiveCategory.valueOf(valueCat);
    }

    @Before
    @BeforeTest
    public void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        driver = new ChromeDriver();
        locatorPage = new LocatorPage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Пусть("открыт ресурс Авито")
    @Test(priority = 1, description = "Старт приложения")
    public void getPage() {
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("page"));
    }

    @И("в выпадающием списке категорий выбрана {mainCategory}")
    @Parameters("mainCategory")
    @Test(priority = 2, description = "в выпадающием списке категорий выбрана категория")
    public void selectCategoryOfTools(@Optional("Оргтехника_и_расходники") MainCategory mainCategory) {
        locatorPage.selectCategoryTool(mainCategory.value);
    }

    @И("в поле поиска введено значение {word}")
    @Parameters("findStr")
    @Test(priority = 3, description = "в поле поиска введено значение")
    public void inputFindingTools(@Optional("принтер") String findStr) {
        locatorPage.inputGoods(findStr);
    }

    @Тогда("кликнуть по выпадающему списку региона")
    @Test(priority = 4, description = "клик по выпадающему списку региона")
    public void clickToCityButton() {
        locatorPage.clickCityBtn();
    }

    @Тогда("в поле региона введено значение {word}")
    @Parameters("city")
    @Test(priority = 5, description = "в поле региона введено значение")
    public void inputCityAndShowAd(@Optional("Владивосток") String city) {
        locatorPage.inputCity(city);
        locatorPage.clickBtnFindingCity(city);
    }

    @И("нажата кнопка показать объявления")
    @Test(priority = 6, description = "нажата кнопка показать объявления")
    public void showAd() {
        locatorPage.clickShowAdBtn();
    }

    @Тогда("открыласть страница результатов по запросу {word}")
    @Parameters("printer")
    @Test(priority = 7, description = "открыласть страница результатов по запросу")
    public void isNeedPage(@Optional("принтер") String query) {
        String str = driver.findElement(By.xpath("//h1[@data-marker='page-title/text']")).getText();
        if (str.contains(query)) {
            System.out.printf("open page contain %s%n", query);
        } else {
            System.out.printf("open page NOT contain %s%n", query);
        }
    }

    @И("активирован чекбокс только с доставкой")
    @Test(priority = 8, description = "активирован чекбокс только с доставкой")
    public void checkCheckboxAndShowAd() {
        locatorPage.clickCheckBoxDelivery();
        locatorPage.clickFilterDeliverySubmitBtn();
    }

    @И("в выпадающем списке сортировка выбрано значение {expensiveCategory}")
    @Parameters("expensiveCategory")
    @Test(priority = 9, description = "в выпадающем списке сортировка выбрано значение Дороже")
    public void filterResults(@Optional("Дороже") ExpensiveCategory exp) {
        locatorPage.selectFilter(exp.value);
    }

    @И("в консоль выведены названия и цены {int} первых товаров")
    @Parameters("number")
    @Test(priority = 10, description = "в консоль выведены названия и цены первых товаров")
    public void printResults(@Optional("3") int number) {
        locatorPage.printTools(number);
    }

    @AfterMethod
    public void printResult() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Path content = Paths.get(scrFile.getPath());
        try (InputStream is = Files.newInputStream(content)) {
            Allure.addAttachment("Screen", is);
        }
    }

    @After
    @AfterTest
    public void quitProgram() {
        driver.quit();
    }
}
