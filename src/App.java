import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Animals.Animals;
import Animals.AnimalsIsNotCreateException;
import Animals.*;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {
    public static List<Animals> animal_list;
    public static void main(String[] args) throws Exception {
        animal_list = new ArrayList<>();
        Scanner iScanner = new Scanner(System.in);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        animal_list.add(new Cat("Хельга", LocalDate.parse("04-03-2023",formatter)));
        animal_list.add(new Dog("Дружок", LocalDate.parse("20-05-2021",formatter)));
        animal_list.add(new Hamster("Глюк", LocalDate.parse("29-07-2022",formatter)));
        animal_list.add(new Horse("Рысак", LocalDate.parse("20-09-2019",formatter)));
        animal_list.add(new Camel("Ланцелот", LocalDate.parse("24-11-2018",formatter)));
        animal_list.add(new Donkey("Иа", LocalDate.parse("15-10-2021",formatter)));

        try {
            head_menu(iScanner);
        } catch (AnimalsIsNotCreateException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getInfo());
        } finally {
            iScanner.close();
        }
    }

    public static LocalDate inputDate(Scanner iScanner) {
        System.out.println();
        System.out.printf("День рождения животного\r\n");
        Boolean check = false;
        LocalDate now = LocalDate.now();
        int year = 1980;
        int month = 1;
        int day = 1;
        LocalDate date = LocalDate.now();
        while (!check){
            while (!check){
                year = inputData("Введите год: ", iScanner);
                if (year < now.getYear()) {check = true;}
            }
            check = false;
            while (!check){
                month = inputData("Введите месяц: ", iScanner);
                if (month >= 1 && month <= 12) {check = true;}
            }
            check = false;
            while (!check){
                day = inputData("Введите день: ", iScanner);
                if (day >=1 && day <= 31) {check = true;}
            }
            check = false;
            if (isDateValid(year, month, day)) {
                date = LocalDate.of(year, month, day);
                if(date.isBefore(now) || date.isEqual(now) ) {check = true;}
            }
        }
        return date;
    }

    public static boolean isDateValid(int year, int month, int day) {
        boolean dateIsValid = true;
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            dateIsValid = false;
        }
        return dateIsValid;
    }
    
    public static void createAnimal(Scanner iScanner) {
        int sel_menu = -1;
        System.out.println();
        System.out.println("Выберите животное:");
        System.out.println("1. Кошка");
        System.out.println("2. Собака");
        System.out.println("3. Хомяк");
        System.out.println("4. Лошадь");
        System.out.println("5. Верблюд");
        System.out.println("6. Осел");
        sel_menu = inputData("Выберите пункт меню: ", iScanner);
        System.out.printf("Введите имя животного: ");
        iScanner.nextLine(); // Не понял баги, почему то считывал пустую строку, поэтому одно пустое чтение
        String name = iScanner.nextLine();
        LocalDate date = inputDate(iScanner);
        try {
            switch (sel_menu) {
                case 1:
                    animal_list.add(new Cat(name, date));
                    break;
                case 2:
                    animal_list.add(new Dog(name, date));
                    break;
                case 3:
                    animal_list.add(new Hamster(name, date));
                    break;
                case 4:
                    animal_list.add(new Horse(name, date));
                    break;
                case 5:
                    animal_list.add(new Camel(name, date));
                    break;
                case 6:
                    animal_list.add(new Donkey(name, date));
                    break;
            }
            if (animal_list.size() > 0) {
                System.out.println(animal_list.get(0));
            } else {
                System.out.println("Питомник пуст");
            }
            
        } catch (AnimalsIsNotCreateException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getInfo());
        }
    }

    public static int inputData(String text, Scanner iScanner) {
        System.out.printf(text);
        int num = 0;
        if (iScanner.hasNextInt()) {
            num = iScanner.nextInt();
        } else {
            num = -1;
        }
        return num;
    }

    public static void selAnimal(Scanner iScanner) throws AnimalsIsNotCreateException{
        printAnimals();
        int id = -1;
        System.out.println();
        while ((animal_list.size() > 0) &&((id < 1) || id > animal_list.size())) {
            id = inputData("Выберите животное: ", iScanner);
        }
        if (id > 0) {
            Animals anim = animal_list.get(id - 1);
            System.out.printf("%sИзученные команды: %s\r\n", anim, anim.getCommands());
            int sel_menu = -1;
            System.out.println("1. Обучить животное");
            System.out.println("2. Назад");
            sel_menu = inputData("Выберите пункт меню: ", iScanner);
            switch (sel_menu) {
                case 1:
                    train_animal(anim, iScanner);
                    break;
                case 2:
                    head_menu(iScanner);
                    break;
                default:
                    selAnimal(iScanner);
                    break;
            }
        } else {
            System.out.println("Питомник пуст");
            head_menu(iScanner);
        }
    }

    public static void train_animal(Animals animal, Scanner iScanner) throws AnimalsIsNotCreateException {
        int sel_menu = -1;
        System.out.println("Какой команде обучить:");
        int i = 1;
        for (Command com : Command.values()) {
            System.out.printf("%d. %s\r\n", i++, com);
        }
        System.out.printf("%d. Назад\r\n", i);
        sel_menu = inputData("Выберите команду: ", iScanner);
        if (sel_menu > 0 && sel_menu < i) {
                animal.setCommands(Command.values()[sel_menu - 1]);
        } else {
                selAnimal(iScanner);
        }
    }
    public static void printAnimals() {
        for (int i = 0; i < animal_list.size(); i++) {
            System.out.printf("%d. %s", i + 1, animal_list.get(i));
        }
        if (animal_list.size() == 0) {
            System.out.println("Питомник пуст");
        }
    }
    
    public static void head_menu(Scanner iScanner) throws AnimalsIsNotCreateException {
        int sel_menu = -1;
        System.out.println();
        System.out.println("Питомник");
        System.out.println("1. Добавить животное");
        System.out.println("2. Выбрать животное");
        System.out.println("3. Вывести всех животных");
        System.out.println("4. Выход");
        sel_menu = inputData("Выберите пункт меню: ", iScanner);
        switch (sel_menu) {
            case 1:
                createAnimal(iScanner);
                head_menu(iScanner);
                break;
            case 2:
                selAnimal(iScanner);
                head_menu(iScanner);
                break;
            case 3:
                printAnimals();
                head_menu(iScanner);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                head_menu(iScanner);
                break;
        }
    }
    
}
