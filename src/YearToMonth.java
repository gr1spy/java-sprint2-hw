import java.util.ArrayList;

/**
 * Класс, содержащий коллекицю "месяцев" в годовом отчете
 */
public class YearToMonth {

    /**
     * @yearToMonth объект "месяц" в годовом отчете с распарсенными строками
     */
    ArrayList<YearlyReport> yearToMonth;

    /**
     * @param yearToMonth список объектов "месяц" в годовом отчете.
     *                    (объект "месяц" осодержит распарсенные строки)
     */
    YearToMonth(ArrayList<YearlyReport> yearToMonth){
        this.yearToMonth = new ArrayList<>();
    }
}
