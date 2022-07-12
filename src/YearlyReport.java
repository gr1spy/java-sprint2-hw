/**
 * Класс хранит распарсенные объекты обной строки годового отчета
 */
public class YearlyReport {

    short month; //
    int amount;
    boolean is_expense;

    /**
     * @param numMonth месяц (число: 01, 02 ..)
     * @param amountSum сумма
     * @param isExpense false - доход, true - расход
     */
    YearlyReport(
            short numMonth,
            int amountSum,
            boolean isExpense
    ){
        month = numMonth;
        amount = amountSum;
        is_expense = isExpense;
    }

}
