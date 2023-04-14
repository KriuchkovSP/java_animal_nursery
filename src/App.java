import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import Animals.Animals;
import Animals.AnimalsIsNotCreateException;
import Animals.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

public class App {
    public static List<Animals> animal_list;
    public static void main(String[] args) throws Exception {
        animal_list = new ArrayList<>();
        Scanner iScanner = new Scanner(System.in);
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
        System.out.printf("День рождения животного");
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
            date = LocalDate.of(year, month, day);
            if(date.isBefore(now) || date.isEqual(now)) {check = true;}
        }
        return date;
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
            System.out.println(animal_list.get(0));
            
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

    public static selAnimal(Scanner iScanner) {
        
    }

    public static void head_menu(Scanner iScanner) throws AnimalsIsNotCreateException {
        int sel_menu = -1;
        System.out.println();
        System.out.println("Зверинец");
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
                getAnimals();
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
