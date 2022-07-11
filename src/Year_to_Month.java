import java.util.ArrayList;

//Класс, содержащий список ХэшТаблиц(строк одного месяца) {{0 | 1}, YearlyReport} = {{доход | расход}, строка годового отчета}
public class Year_to_Month {

    ArrayList<YearlyReport> yearToMonth;

    Year_to_Month(ArrayList<YearlyReport> yearToMonth){
        this.yearToMonth = new ArrayList<>();
    }
}
