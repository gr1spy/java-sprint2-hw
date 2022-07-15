import java.util.ArrayList;
import java.util.List;

/**
 * Класс, в котором содержатся строки одного месячного отчета
 */
public class Month {


    /**
     * @month - объект "месяц" с распарсенными строками из месячного отчета
     */
    List<MonthlyReport> month;

    /**
     * @param month содержит все распарсенные строки одного месячного отчета
     */
    Month(List<MonthlyReport> month){
        this.month = new ArrayList<>();
    }

}
