/**
 * Класс хранит распарсенные объекты обной строки месячного отчета
 */
public class MonthlyReport {

    String item_name;
    boolean is_expense;
    int quantity;
    int sum_of_one;

    /**
     * @param itemName название товара
     * @param isExpense одно из двух значений:TRUE или FALSE. Обозначает, является ли запись тратой (TRUE) или доходом (FALSE)
     * @param countItem количество закупленного или проданного товара
     * @param sumOfOne стоимость одной единицы товара. Целое число
     */
    MonthlyReport(
            String itemName,
            boolean isExpense,
            int countItem,
            int sumOfOne
    ) {
        item_name = itemName;
        is_expense = isExpense;
        quantity = countItem;
        sum_of_one = sumOfOne;
    }
}
