import java.util.ArrayList;

/**
 * Класс, в котором содержатся строки одного месячного отчета
 */
public class Month {


    /**
     * @month - объект "месяц" с распарсенными строками из месячного отчета
     */
    ArrayList<MonthlyReport> month;

    /**
     * @param month содержит все распарсенные строки одного месячного отчета
     */
    Month(ArrayList<MonthlyReport> month){
        this.month = new ArrayList<>();
    }

}
