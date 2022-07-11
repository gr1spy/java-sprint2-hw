import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Report report = new Report();
        boolean flagReadMonthReport = false;
        boolean flagReadYearReport = false;

        System.out.println("Введите путь к каталогу с отчетами. Пример: C:\\\\Java_prog\\\\java-sprint2-hw\\\\otchet");
        String path = scanner.nextLine();

        report.pullReportList(1);
        report.pullReportList(2);

        while (true){

            printMenu();
            int command = scanner.nextInt();    //Считываем номер команды от пользователя

            if (command == 1) { //1. Считать все месячные отчёты

                if (!flagReadMonthReport){
                    report.read(command, path);
                } else {
                    System.out.println("Повторное считывание отчета невозможно!\nПерезапустите программу.");
                }
                flagReadMonthReport = true;

            } else if (command == 2 ) { //2. Считать годовой отчёт

                if (!flagReadYearReport){
                    report.read(command, path);
                } else {
                    System.out.println("Повторное считывание отчета невозможно!\nПерезапустите программу.");
                }

                flagReadYearReport = true;

            } else if (command == 3 ) { //3. Сверить отчёты
                if (flagReadMonthReport && flagReadYearReport){
                    report.check();
                } else {
                    System.out.println("\nВнимание! Перед сверкой просканьте все отчеты!");
                }
            } else if (command == 4 ) { //4. Вывести информацию о всех месячных отчётах
                if(flagReadMonthReport) {
                    report.printMonthReport();
                } else {
                    System.out.println("\nВы не передали данные месячных отчетов!\n + " +
                            "Перейдите в пункт меню #1");
                }
            } else if (command == 5) { //5. Вывести информацию о годовом отчёте
                if(flagReadYearReport) {
                    report.printYearReport();
                } else {
                    System.out.println("\nВы не передали данные годового отчета!\n + " +
                            "Перейдите в пункт меню #2");
                }
            } else if (command == 0) {
                System.out.println("Выход");
                break;
            } else {
                System.out.println("\nИзвините, такой команды пока нет.");
            }
        }

        scanner.close();
        System.out.println("Программа завершена");

    }

    public static void printMenu(){
        System.out.println("\nЧто вы хотите сделать?\n" +
                "1. Считать все месячные отчёты\n" +
                "2. Считать годовой отчёт\n" +
                "3. Сверить отчёты\n" +
                "4. Вывести информацию о всех месячных отчётах\n" +
                "5. Вывести информацию о годовом отчёте\n" +
                "0. Выход");
    }
}

