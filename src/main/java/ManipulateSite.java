import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class ManipulateSite {
    public static LocatorPage locatorPage;
    public static WebDriver driver;

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
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("page"));
    }

    // Выберем в выпадающем списке “категория”  значение оргтехника и расходники
    public void selectCategoryOfTools() {
        locatorPage.selectCategoryTool("Оргтехника и расходники");
    }

    // В поле поиск по объявлению введем значение “Принтер”
    public void inputFindingTools() {
        locatorPage.inputGoods("принтер");
    }

    // Нажмем на поле город
    public void clickToCityButton() {
        locatorPage.clickCityBtn();
    }

    // Заполним значением “Владивосток” поле город  в открывшемся окне и кликнем по первому предложенному варианту
    // Нажмем на кнопку “Показать объявления”
    public void inputCityAndShowAd() {
        locatorPage.inputCity("Владивосток");
        locatorPage.clickBtnFindingCity("Владивосток");
        locatorPage.clickShowAdBtn();
    }

    // Проверим, активирован ли чекбокс, и если не активирован – активируем и нажмем кнопку “Показать объявления”
    public void checkCheckboxAndShowAd() {
        locatorPage.clickCheckBoxDelivery();
        locatorPage.clickFilterDeliverySubmitBtn();
    }

    // В выпадающем списке фильтрации выберем фильтрацию по убыванию цены.
    public void filterResults() {
        locatorPage.selectFilter("Дороже");
    }

    // Выведем в консоль название и стоимость 3х самых дорогих принтеров
    public void printResults() {
        locatorPage.printTools(3);
    }

    public void quitProgram() {
        driver.quit();
    }
}
