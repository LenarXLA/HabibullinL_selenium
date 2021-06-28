import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class LocatorPage {
    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public WebDriver driver;

    public LocatorPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * определение локатора селектора категории товаров
     */
    @FindBy(xpath = "//select[@name='category_id']")
    private WebElement selectTool;
    /**
     * определение локатора поля для ввода товара
     */
    @FindBy(xpath = "//input[@data-marker='search-form/suggest']")
    private WebElement findTool;
    /**
     * определение локатора кнопки выбора города
     */
    @FindBy(xpath = "//div[@data-marker='search-form/region']")
    private WebElement cityButton;
    /**
     * определение локатора поля для ввода города
     */
    @FindBy(xpath = "//input[@data-marker='popup-location/region/input']")
    private WebElement findCity;
    /**
     * определение локатора кнопки города из выпадающего списка
     */
    @FindBy(xpath = "//li[@data-marker='suggest(0)']")
    private WebElement btnFindingCity;
    /**
     * определение локатора кнопки “Показать объявления”
     */
    @FindBy(xpath = "//button[@data-marker='popup-location/save-button']")
    private WebElement showAdBtn;
    /**
     * определение локатора чекбокса доставки
     */
    @FindBy(xpath = "//div[@data-marker='delivery-filter/container']")
    private WebElement checkBoxDelivery;
    /**
     * определение локатора кнопки “Показать объявления” при заданном фильтре
     */
    @FindBy(xpath = "//button[@data-marker='search-filters/submit-button']")
    private WebElement filterDeliverySubmitBtn;
    /**
     * определение локатора селектора категории ценности
     */
    @FindBy(xpath = "//option[text()='По умолчанию']/..")
    private WebElement selectFilter;
    /**
     * определение локатора селектора категории ценности
     */
    @FindBy(xpath = "//div[@data-marker='catalog-serp']/div[@data-marker='item']")
    private List<WebElement> printers;


    /**
     * метод для выбора селектора вида товаров
     */
    public void selectCategoryTool(String select) {
        new Select(selectTool).selectByVisibleText(select);
    }
    /**
     * метод для ввода товара
     */
    public void inputGoods(String goods) {
        findTool.sendKeys(goods);
    }
    /**
     * метод для осуществления нажатия кнопки города
     */
    public void clickCityBtn() {
        cityButton.click();
    }
    /**
     * метод для ввода города
     */
    public void inputCity(String city) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-marker='popup-location/region/input']")));
        findCity.sendKeys(city);
    }
    /**
     * метод для осуществления нажатия кнопки найденного города в выпадающем списке
     */
    public void clickBtnFindingCity(String city) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driverWait.until(ExpectedConditions.textToBe(By.xpath("//li[@data-marker='suggest(0)']/span/span/span/strong"), city));
        btnFindingCity.click();
    }
    /**
     * метод для осуществления нажатия кнопки “Показать объявления”
     */
    public void clickShowAdBtn() {
        showAdBtn.click();
    }
    /**
     * метод для осуществления нажатия чекбокса доставки
     */
    public void clickCheckBoxDelivery() {
        if (!checkBoxDelivery.isSelected()) {
            checkBoxDelivery.click();
        }
    }
    /**
     * метод для осуществления нажатия кнопки “Показать объявления” при выбранном фильтре доставки
     */
    public void clickFilterDeliverySubmitBtn() {
        filterDeliverySubmitBtn.click();
    }
    /**
     * метод для выбора селектора фильтра "дороже-дешевле"
     */
    public void selectFilter(String select) {
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[text()='По умолчанию']/..")));
        new Select(selectFilter).selectByVisibleText(select);
    }
    /**
     * метод для вывода в консоль название и стоимость самых дорогих принтеров
     */
    public void printTools(int quantityOfTool) {
        for (int i = 0; i < quantityOfTool; i++) {
            System.out.println(printers.get(i).findElement(By.xpath(".//h3[@itemprop='name']")).getText() +
                    " : " + printers.get(i).findElement(By.xpath(".//meta[@itemprop='price']")).getAttribute("content") + " руб.");
        }
    }
}
