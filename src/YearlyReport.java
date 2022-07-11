public class YearlyReport {

    short month; //месяц (число: 01, 02 ..)
    int amount; //сумма
    boolean is_expense; // false - доход, true - расход

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
