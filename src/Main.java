import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Report report = new Report();
        boolean flagReadMonthReport = false;
        boolean flagReadYearReport = false;

        System.out.println("Введите путь к каталогу с отчетами. Пример: C:\\\\directoryName");
        String path = scanner.nextLine();

        while (true){

            printMenu();
            int command = scanner.nextInt();    //Считываем номер команды от пользователя

            if (command == 1) { //1. Считать все месячные отчёты
                report.read(command, path);
                flagReadMonthReport = true;
            } else if (command == 2 ) { //2. Считать годовой отчёт
                report.read(command, path);
                flagReadYearReport = true;
            } else if (command == 3 ) { //3. Сверить отчёты
                if (flagReadMonthReport && flagReadYearReport){
                    report.check();
                } else {
                    System.out.println("Внимание! Перед сверкой просканьте все отчеты!");
                }
            } else if (command == 4 ) { //4. Вывести информацию о всех месячных отчётах
                report.printMonthReport();
            } else if (command == 5) { //5. Вывести информацию о годовом отчёте
                report.printYearReport();
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }

        scanner.close();
        System.out.println("Программа завершена");

    }

    public static void printMenu(){
        System.out.println("Что вы хотите сделать?\n" +
                "1. Считать все месячные отчёты\n" +
                "2. Считать годовой отчёт\n" +
                "3. Сверить отчёты\n" +
                "4. Вывести информацию о всех месячных отчётах\n" +
                "5. Вывести информацию о годовом отчёте\n" +
                "0. Выход");
    }
}

