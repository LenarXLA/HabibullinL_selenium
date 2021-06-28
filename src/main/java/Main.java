public class Main {
    public static void main(String[] args) {
        ManipulateSite site = new ManipulateSite();
        // точка входа
        site.setup();
        // Выберем в выпадающем списке “категория”  значение оргтехника и расходники
        site.selectCategoryOfTools();
        // В поле поиск по объявлению введем значение “Принтер”
        site.inputFindingTools();
        // Нажмем на поле город
        site.clickToCityButton();
        // Заполним значением “Владивосток” поле город  в открывшемся окне и кликнем по первому предложенному варианту
        // Нажмем на кнопку “Показать объявления”
        site.inputCityAndShowAd();
        // Проверим, активирован ли чекбокс, и если не активирован – активируем и нажмем кнопку “Показать объявления”
        site.checkCheckboxAndShowAd();
        // В выпадающем списке фильтрации выберем фильтрацию по убыванию цены
        site.filterResults();
        // Выведем в консоль название и стоимость 3х самых дорогих принтеров
        site.printResults();

        // Делаю принудительное ожидание чтобы успеть увидеть работу программы
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // закрываем и останавливаем драйвер
        site.quitProgram();
    }
}
