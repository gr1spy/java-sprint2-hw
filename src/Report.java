import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Report {

    /**
     * months - список HashMap`ов, содержащих номер строки (Integer) в файле месячного отчета + набор полей (MonthlyReport),
     * HashMap - один файл месячного отчета
     */
    ArrayList<HashMap<Integer, MonthlyReport>> months = new ArrayList<>();
    HashMap<Integer, YearlyReport> year_2021 = new HashMap<>();


    void read(int monthOrYear, String path){     //Ввели месяц или год?

        if (monthOrYear == 1){
            String fileName = "m.20210";
            for (int i = 1; i < 4 ; i++){
                int numMonth = i-1;
                try {
                    String file = Files.readString(Path.of((path+"\\\\"+fileName+i+".csv")));
                    System.out.println(file);
                    if (i == 1){
                        splitFile(file, monthOrYear, numMonth);
                    } else if (i == 2) {
                        splitFile(file, monthOrYear, numMonth);
                    } else {
                        splitFile(file, monthOrYear, numMonth);
                    }

                } catch (IOException e) {
                    System.out.println("Невозможно прочитать файл с месячным отчётом. " +
                            "Возможно, файл отсутствует.");
                }
            }
        } else if (monthOrYear == 2){
            String fileName = "y.2021.csv";
            try {
                String file = Files.readString(Path.of(fileName));
                    splitFile(file, monthOrYear, 0);
            } catch (IOException e) {
                System.out.println("Невозможно прочитать файл с годовым отчётом. " +
                        "Возможно, файл отсутствует.");
            }
        }

    }

    void check(){   //сверяем отчеты
        int[] expensesMonthsOfMonthRep = new int[3];  //массив расходов по трем месяцам из отчетов по меясцам
        int[] incommingsMonthsOfMonthRep = new int[3];    //массив доходов по трем месяцам из отчетов по месяцам

        int[] expensesMonthsOfYearRep = new int[3]; //массив расходов по трем месяцам из годового отчета
        int[] incommingsMonthsOfYearRep = new int[3];   //массив доходов по трем месяцам из годового отчета

        //Заполнение массивов трат и доходов из файлов месячных отчетов
        for (int i = 0; i < 3; i++){ //Пройдемся по месяцам
            for (Integer str : months.get(i).keySet()){  //строка в соответствующем месяце
                if (!months.get(i).get(str).is_expense) {
                    expensesMonthsOfMonthRep[i] += months.get(i).get(str).sum_of_one;
                } else {
                    incommingsMonthsOfMonthRep[i] += months.get(i).get(str).sum_of_one;
                }
            }
        }

        //Заполняем массив доходов и трат по меясцам из годового отчета
        for (Integer monthStr : year_2021.keySet()) {
            //Если текущая строка - доход и сл. строка это расход по тому же месяцу, то:
            if (year_2021.get(monthStr).is_expense){
                expensesMonthsOfYearRep[monthStr] += year_2021.get(monthStr).amount;
            } else if (!(year_2021.get(monthStr).is_expense)) {
                incommingsMonthsOfYearRep[monthStr] += year_2021.get(monthStr).amount;
            }
        }

        //Сверяем массивы
        for (int i = 0; i < 3; i++) {

            if (!(expensesMonthsOfMonthRep[i] == expensesMonthsOfYearRep[i])){

                System.out.println("Внимание! Данные в отчетах не сходятся. Обнаружено несоответствие в расходах в " +
                        (i+1) + " месяце!");
                break;
            }

            if (!(incommingsMonthsOfMonthRep[i] == incommingsMonthsOfYearRep[i])){

                System.out.println("Внимание! Данные в отчетах не сходятся. Обнаружено несоответствие в доходах в " +
                        (i+1) + " месяце!");
                break;
            }
        }

        System.out.println("Все успешно, данные подтверждены!)");

    }


    void splitFile(String file, int monthYear, int numMonth){

        //Разделяем наш файл (в виде одной строки) на множество строк
        String[] lines = file.split(System.lineSeparator());

        for (int i = 1; i < lines.length; i++){
            String[] lineContents = lines[i].split(","); //Парсим строку на фрагменты String
            if (monthYear == 1){    //Если парсим файл месячного отчета
                //Преобразовываем значения из массива строки в нужные типы
                String name = lineContents[0];
                boolean result = Boolean.parseBoolean(lineContents[1]);
                int countItem = Integer.parseInt(lineContents[2]);
                int priceOfOne = Integer.parseInt(lineContents[3]);

                //Создаем объект - строку типа MonthlyReport
                MonthlyReport thisString = new MonthlyReport(name, result, countItem, priceOfOne);
                HashMap<Integer, MonthlyReport> month = new HashMap<>();
                month.put(i,thisString); //i - номер строки, thisString - содержимое строки

                //В зависимости от месяца добавляем значения в нужный элемент (месяц) списка "months"
                if (Objects.equals(numMonth, 0)){
                    months.add(0, month);
                } else if (Objects.equals(numMonth, 1)) {
                    months.add(1, month);
                } else if (Objects.equals(numMonth, 2)){
                    months.add(2, month);
                }

            } else if (monthYear == 2){ //Если парсим годовой отчет
                short month = Short.parseShort(lineContents[0]);
                int sumOfMonth = Integer.parseInt(lineContents[1]);
                boolean isExpense = Boolean.parseBoolean(lineContents[2]);

                YearlyReport thisStringYear = new YearlyReport(month, sumOfMonth, isExpense);
                //i - номер месяца (0, 1) -  январь - (доход, трата)),
                // thisStringYear - данные по месяцам
                year_2021.put(i, thisStringYear);

            }
        }
    }

    //Печатаем месячные отчеты
    void printMonthReport(){

        for (int i = 0; i < months.size(); i++){    //номер месяца в списке month

            if (i == 0){
                System.out.println("Январь.");
                mostOfIncomeItem(i); //Самый прибыльный товар
                mostOfExpense(i);    //Самая большая трата
            } else if (i == 1) {
                System.out.println("Февраль.");
                mostOfIncomeItem(i);
                mostOfExpense(i);
            } else if (i == 2) {
                System.out.println("Март.");
                mostOfIncomeItem(i);
                mostOfExpense(i);
            }

        }

    }

    //Печатаем годовой отчет
    void printYearReport(){
        System.out.println("Шёл 2021 год...");
        diff();
        middleExpenseAndIncome();
    }

    //Ищем самый прибыльный товар за месяц и сумму прибыли с него
    void mostOfIncomeItem (int numMonth){

        int maxSum = 0;     //Прибыль самого прибыльного товара
        String itemName = "";   //Название самого прибыльного товара

        for (Integer str : months.get(numMonth).keySet()){  //строка в соответствующем месяце
            if (!months.get(numMonth).get(str).is_expense) {
                if ((months.get(numMonth).get(str).quantity * months.get(numMonth).get(str).sum_of_one) > maxSum){
                    maxSum = months.get(numMonth).get(str).quantity * months.get(numMonth).get(str).sum_of_one;
                    itemName = months.get(numMonth).get(str).item_name;
                }
            }
        }
        System.out.println("Самый прибыльный товар в этом месяце: " + itemName);
        System.out.println("Мы получили +" + maxSum + " прибыли!");

    }

    //Ищем самую большую трату за месяц
    void mostOfExpense(int numMonth){

        int maxExpenses = 0;    //Самая большая трата
        String itemName = "";   //Название самого дорогого товара

        for (Integer str : months.get(numMonth).keySet()){  //строка в соответствующем месяце
            if (months.get(numMonth).get(str).is_expense) {
                if (months.get(numMonth).get(str).quantity > maxExpenses){
                    maxExpenses = months.get(numMonth).get(str).quantity;
                    itemName = months.get(numMonth).get(str).item_name;
                }
            }
        }
        System.out.println("Самый дорогой товар этого месяца: " + itemName);
        System.out.println("Самая большая трата в этом месяце: " + maxExpenses);

    }

    void diff(){
        int difference = 0;
        int buf;

        for (Integer monthStr : year_2021.keySet()) {
            //Если текущая строка - доход и сл. строка это расход по тому же месяцу, то:
            if (!(year_2021.get(monthStr).is_expense) &&
                    ((year_2021.get(monthStr).month == year_2021.get(monthStr + 1).month) && (year_2021.get(monthStr).is_expense))){
                buf = year_2021.get(monthStr).amount - year_2021.get(monthStr + 1).amount;
                if (buf > difference) {
                    difference = buf;
                }
                System.out.println("Прибыль в " + year_2021.get(monthStr) + " : " + difference);

                //Если текущая строка - расход и сл. строка это доход по тому же месяцу, то:
            } else if (((year_2021.get(monthStr).is_expense) &&
                    ((year_2021.get(monthStr).month == year_2021.get(monthStr + 1).month) && !(year_2021.get(monthStr).is_expense)))){
                buf = year_2021.get(monthStr + 1).amount - year_2021.get(monthStr).amount;
                if (buf > difference) {
                    difference = buf;
                }
            }
        }
    }

    //Средний расход за все месяцы в году
    void middleExpenseAndIncome(){

        double midExpenses;
        double midIncome;
        int allExpenses = 0;
        int allIncome = 0;

        for (Integer monthStr : year_2021.keySet()){
            if ((year_2021.get(monthStr).is_expense)){
                allExpenses += year_2021.get(monthStr).amount;
            }
            if (!(year_2021.get(monthStr).is_expense)){
                allIncome += year_2021.get(monthStr).amount;
            }
        }

        midExpenses = (double) allExpenses / 3;     // midExpenses = (все расходы) / (3 мес)
        System.out.println("Средний расход за все месяцы составил: " + midExpenses);

        midIncome = (double) allIncome / 3;     // midIncome = (все доходы) / (3 мес)
        System.out.println("Средний доход за все месяцы составил: " + midIncome);

    }

}
