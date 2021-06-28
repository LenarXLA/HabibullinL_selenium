import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import typeReg.ExpensiveCategory;
import typeReg.MainCategory;

import java.util.concurrent.TimeUnit;

public class StepDefs {
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
    public void getPage() {
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("page"));
    }

    @И("в выпадающием списке категорий выбрана {mainCategory}")
    public void selectCategoryOfTools(MainCategory mainCategory) {
        locatorPage.selectCategoryTool(mainCategory.value);
    }

    @И("в поле поиска введено значение {word}")
    public void inputFindingTools(String findStr) {
        locatorPage.inputGoods(findStr);
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public void clickToCityButton() {
        locatorPage.clickCityBtn();
    }

    @Тогда("в поле региона введено значение {word}")
    public void inputCityAndShowAd(String city) {
        locatorPage.inputCity(city);
        locatorPage.clickBtnFindingCity(city);
    }

    @И("нажата кнопка показать объявления")
    public void showAd() {
        locatorPage.clickShowAdBtn();
    }

    @Тогда("открыласть страница результатов по запросу {word}")
    public void isNeedPage(String printer) {
        String str = driver.findElement(By.xpath("//h1[@data-marker='page-title/text']")).getText();
        if (str.contains(printer)) {
            System.out.println("open page contain printer");
        } else {
            System.out.println("open page NOT contain printer");
        }
    }

    @И("активирован чекбокс только с доставкой")
    public void checkCheckboxAndShowAd() {
        locatorPage.clickCheckBoxDelivery();
        locatorPage.clickFilterDeliverySubmitBtn();
    }

    @И("в выпадающем списке сортировка выбрано значение {expensiveCategory}")
    public void filterResults(ExpensiveCategory exp) {
        locatorPage.selectFilter(exp.value);
    }

    @И("в консоль выведены названия и цены {int} первых товаров")
    public void printResults(int number) {
        locatorPage.printTools(number);
    }

    @After
    public void quitProgram() {
        driver.quit();
    }
}
