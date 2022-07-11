import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Report {

    /**
     * allStringsOfFileMonth - список, содержащий все строки из одного файла меясчного отчета,
     * allStringsOfFileYear - Список ХэшТаблиц, содержащий все строки из годового отчета
     */
    ArrayList<MonthlyReport> allStringsOfFileMonth = new ArrayList<>(); //список распарсенных строк одного меясчного отчета
    ArrayList<YearlyReport> allStringsOfFileYear = new ArrayList<>(); //список распарсенных строк ежегодного отчета
    ArrayList<Month> listOfMonths = new ArrayList<>();  //список распарсенных месячных отчетов
    ArrayList<Year_to_Month> listOfMonthsInYear = new ArrayList<>();  //список распарсенных месячных отчетов в годовом отчете


    Month jan = new Month(allStringsOfFileMonth); //объект месячного отчета, содержащий все строки файла
    Month feb = new Month(allStringsOfFileMonth);
    Month mar = new Month(allStringsOfFileMonth);

    Year_to_Month jan_2021 = new Year_to_Month(allStringsOfFileYear); //объект месяца в годовом отчете
    Year_to_Month feb_2021 = new Year_to_Month(allStringsOfFileYear);
    Year_to_Month mar_2021 = new Year_to_Month(allStringsOfFileYear);

    //Заполняем списки с отчетами пустыми значениями
    void pullReportList(int monthOrYear){
        if (monthOrYear == 1){
            //наполняем список меясчных отчетов
            listOfMonths.add(jan);
            listOfMonths.add(feb);
            listOfMonths.add(mar);
        } else {
            //Заполняем список месяцев в годовом отчете
            listOfMonthsInYear.add(jan_2021);
            listOfMonthsInYear.add(feb_2021);
            listOfMonthsInYear.add(mar_2021);
        }
    }


    void read(int monthOrYear, String path) {     //Ввели месяц или год?

        if (monthOrYear == 1) {
            String fileName = "m.20210";
            for (int i = 1; i < 4; i++) {
                int numMonth = i - 1;
                try {
                    String file = Files.readString(Path.of((path + "\\\\" + fileName + i + ".csv")));

                    if (i == 1) {
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
        } else if (monthOrYear == 2) {
            String fileName = "y.2021.csv";

            try {
                String file = Files.readString(Path.of(path + "\\\\" + fileName));
                splitFile(file, monthOrYear, 0);
            } catch (IOException e) {
                System.out.println("Невозможно прочитать файл с годовым отчётом. " +
                        "Возможно, файл отсутствует.");
            }
        }

    }

    void splitFile(String file, int monthYear, int numMonth) {

        //Разделяем наш файл (в виде одной строки) на множество строк
        String[] lines = file.split(System.lineSeparator());


        for (int i = 1; i < lines.length; i++) {
            String[] lineContents = lines[i].split(","); //Парсим строку на фрагменты String
            if (monthYear == 1) {    //Если парсим файл месячного отчета
                //Преобразовываем значения из массива строки в нужные типы
                String name = lineContents[0];
                boolean result = Boolean.parseBoolean(lineContents[1]);
                int countItem = Integer.parseInt(lineContents[2]);
                int priceOfOne = Integer.parseInt(lineContents[3]);

                //Создаем объект - строку типа MonthlyReport
                MonthlyReport thisString = new MonthlyReport(name, result, countItem, priceOfOne);

                //В зависимости от месяца добавляем значения в нужный элемент (месяц) списка "allStringsOfFileMonth"
                if (numMonth == 0) { //Январь
                    jan.month.add(thisString); //добавляем в месячный отчет по январю новую строку
                } else if (numMonth == 1) { //Февраль
                    feb.month.add(thisString);
                } else if (numMonth == 2) {  //Март
                    mar.month.add(thisString);
                }

            } else if (monthYear == 2) { //Если парсим годовой отчет
                short month = Short.parseShort(lineContents[0]);
                int sumOfMonth = Integer.parseInt(lineContents[1]);
                boolean isExpense = Boolean.parseBoolean(lineContents[2]);

                YearlyReport thisStringYear = new YearlyReport(month, sumOfMonth, isExpense);

                if (thisStringYear.month == 1) {
                    jan_2021.yearToMonth.add(thisStringYear);
                } else if (thisStringYear.month == 2) {
                    feb_2021.yearToMonth.add(thisStringYear);
                } else if (thisStringYear.month == 3) {
                    mar_2021.yearToMonth.add(thisStringYear);
                }

            }
        }
    }

    void check() {   //сверяем отчеты
        int[] expensesMonthsOfMonthRep = new int[3];  //массив расходов по трем месяцам из отчетов по меясцам
        int[] incommingsMonthsOfMonthRep = new int[3];    //массив доходов по трем месяцам из отчетов по месяцам

        int[] expensesMonthsOfYearRep = new int[3]; //массив расходов по трем месяцам из годового отчета
        int[] incommingsMonthsOfYearRep = new int[3];   //массив доходов по трем месяцам из годового отчета

        //Заполнение массивов трат и доходов из файлов месячных отчетов

        for (int i = 0; i < listOfMonths.size(); i++){
            int bufSumExp = 0;
            int bufSumInc = 0;
            for (int j = 0; j < listOfMonths.get(j).month.size(); j++){
                if (listOfMonths.get(i).month.get(j).is_expense){
                    bufSumExp += listOfMonths.get(i).month.get(j).sum_of_one * listOfMonths.get(i).month.get(j).quantity;
                    bufSumInc += listOfMonths.get(i).month.get(j+1).sum_of_one * listOfMonths.get(i).month.get(j+1).quantity;
                    break;
                } else {
                    bufSumInc += listOfMonths.get(i).month.get(j).sum_of_one * listOfMonths.get(i).month.get(j).quantity;
                    bufSumExp += listOfMonths.get(i).month.get(j+1).sum_of_one * listOfMonths.get(i).month.get(j+1).quantity;
                }
            }
            expensesMonthsOfMonthRep[i]=bufSumExp;
            incommingsMonthsOfMonthRep[i]=bufSumInc;
        }


        //Заполняем массив доходов и трат по меясцам из годового отчета
        for (int i = 0; i < listOfMonthsInYear.size(); i++){
            int bufSumExp = 0;
            int bufSumInc = 0;
            for(int j = 0; j<listOfMonthsInYear.get(i).yearToMonth.size(); j++){
                if (listOfMonthsInYear.get(i).yearToMonth.get(j).is_expense){   //если строка в месяце годового отчета == расход
                    bufSumExp += listOfMonthsInYear.get(i).yearToMonth.get(j).amount;
                } else {
                    bufSumInc += listOfMonthsInYear.get(i).yearToMonth.get(j).amount;
                }
            }
            expensesMonthsOfYearRep[i] = bufSumExp;
            incommingsMonthsOfYearRep[i] = bufSumInc;
        }

        //Сверяем массивы
        for (int i = 0; i < 3; i++) {

            if (!(expensesMonthsOfMonthRep[i] == expensesMonthsOfYearRep[i])){

                System.out.println("Внимание! Данные в отчетах не сходятся. Обнаружено несоответствие в расходах в " +
                        (i+1) + " месяце!");
            } else if (!(incommingsMonthsOfMonthRep[i] == incommingsMonthsOfYearRep[i])){

                System.out.println("Внимание! Данные в отчетах не сходятся. Обнаружено несоответствие в доходах в " +
                        (i+1) + " месяце!");
            } else {
                System.out.println("\nВсе успешно, данные подтверждены!)");
            }
        }

    }


    //Печатаем месячные отчеты
    void printMonthReport() {

        for (int i = 0; i < listOfMonths.size(); i++) {    //номер месяца в списке month

            if (i == 0) {
                System.out.println("\nЯнварь.");
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


    //Ищем самый прибыльный товар за месяц и сумму прибыли с него
    void mostOfIncomeItem(int numMonth) {

        int maxSum = 0;     //Прибыль c самого прибыльного товара
        String itemName = "";   //Название самого прибыльного товара

        for (int i = 0; i < listOfMonths.get(numMonth).month.size(); i++) {  //строка в соответствующем месяце
            if (!listOfMonths.get(numMonth).month.get(i).is_expense) { //false - доход
                if ((listOfMonths.get(numMonth).month.get(i).quantity *
                        listOfMonths.get(numMonth).month.get(i).sum_of_one) > maxSum) {
                    maxSum = listOfMonths.get(numMonth).month.get(i).quantity *
                            listOfMonths.get(numMonth).month.get(i).sum_of_one;
                    itemName = listOfMonths.get(numMonth).month.get(i).item_name;
                }
            }
        }
        System.out.println("Самый прибыльный товар: " + itemName + "; Внесли в казну +"+ maxSum + ".");

    }

    //Ищем самую большую трату за месяц
    void mostOfExpense(int numMonth) {

        int maxExpenses = 0;    //Самая большая трата
        String itemName = "";   //Название самого дорогого товара

        for (int i = 0; i < listOfMonths.get(numMonth).month.size(); i++) {  //строка в соответствующем месяце
            if (listOfMonths.get(numMonth).month.get(i).is_expense) {
                if ((listOfMonths.get(numMonth).month.get(i).quantity *
                        listOfMonths.get(numMonth).month.get(i).sum_of_one) > maxExpenses) {
                    maxExpenses = listOfMonths.get(numMonth).month.get(i).quantity *
                            listOfMonths.get(numMonth).month.get(i).sum_of_one;
                    itemName = listOfMonths.get(numMonth).month.get(i).item_name;
                }
            }
        }
        System.out.println("Самый дорогой товар: " + itemName + "; Потратили -" + maxExpenses + ".\n");
    }

    //Печатаем годовой отчет
    void printYearReport() {

        System.out.println("\nШёл 2021 год...");
        profit();
        middleExpenseAndIncome();
    }

    void profit() {

        int count = 0; //счетчик месяцев, нужен для печати
        int inCome = 0; //итоговая сумма прибыли за месяц
        int flag = 0; //флаг того, что мы уже посчитали доход/расход в этом месяце

        for (Year_to_Month toMonth : listOfMonthsInYear){
            count++;
            for (int i = 0; i < listOfMonthsInYear.get(i).yearToMonth.size(); i++){
                if (toMonth.yearToMonth.get(i).is_expense && (i==0) && (flag == 0)){  //если строка = доход, то ..
                    inCome = toMonth.yearToMonth.get(i+1).amount - toMonth.yearToMonth.get(i).amount;
                    flag = 1;
                } else if (flag == 0){
                    inCome = toMonth.yearToMonth.get(i).amount - toMonth.yearToMonth.get(i+1).amount;
                    flag = 1;
                }
            }

            System.out.println("Прибыль в " + count + " месяце: " + inCome);
            inCome = 0;
            flag = 0;

        }
    }


    //Средний расход за все месяцы в году
    void middleExpenseAndIncome() {

        double midExpenses;
        double midIncome;
        int allExpenses = 0;
        int allIncome = 0;

        //Делаем обход по всем месяцам в годовом отчете и по всем строкам в этих месяцах
        for (Year_to_Month year_to_month : listOfMonthsInYear) {    // проход по месяцам

            for (int i = 0; i < year_to_month.yearToMonth.size(); i++){ //проход по строкам в месяце
                if (year_to_month.yearToMonth.get(i).is_expense){  //если строка = доход, то ..
                    allExpenses += year_to_month.yearToMonth.get(i).amount;
                } else {
                    allIncome += year_to_month.yearToMonth.get(i).amount;
                }

            }
        }

        midExpenses = (double) allExpenses / 3;     // midExpenses = (все расходы) / (3 мес)
        System.out.println("Средний расход за все месяцы составил: " + midExpenses);

        midIncome = (double) allIncome / 3;     // midIncome = (все доходы) / (3 мес)
        System.out.println("Средний доход за все месяцы составил: " + midIncome);

    }

}

