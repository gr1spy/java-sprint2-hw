import java.util.HashMap;

public class MonthlyReport {

    String item_name; //название товара
    boolean is_expense; //одно из двух значений:TRUE или FALSE. Обозначает, является ли запись тратой (TRUE) или доходом (FALSE)
    int quantity; //количество закупленного или проданного товара
    int sum_of_one; //стоимость одной единицы товара. Целое число

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
