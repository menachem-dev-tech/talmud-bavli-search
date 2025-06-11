package com.homework;

import java.io.File;
import java.util.Scanner;

public class SearchOptions {

    static void search(File bavli, ShasIndexManager sort, Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\nמה ברצונך לעשות?\n" +
                    "1. חיפוש דף\n" +
                    "2. חיפוש טקסט\n" +
                    "3. חיפוש דף בצורה בינארית\n" +
                    "4. חיפוש פרק משניות\n" +
                    "0. יציאה");
            System.out.print("בחר אפשרות: ");
            String input = scanner.nextLine();
            int option;
            try {
                option = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ קלט לא תקין. נסה שוב.");
                continue;
            }
            switch (option) {
                case 1:
                    System.out.println("איזה דף ברצונך ללמוד?");
                    FindMasechet.getMasechet(bavli, scanner.nextLine());
                    break;
                case 2:
                    System.out.println("הזן טקסט לחיפוש");
                    FindText.getText(bavli, scanner.nextLine());
                    break;
                case 3:
                    System.out.println("איזה דף ברצונך ללמוד? (חיפוש בינארי)");
                    sort.SearchMasechetBinary(scanner.nextLine());
                    break;
                case 4:
                    System.out.println("אפשרות זו טרם מומשה. מקווים להוסיף אותה בקרוב.");
                    break;
                case 0:
                    System.out.println("להתראות!");
                    running = false;
                    break;
                default:
                    System.out.println("⚠️ אפשרות לא קיימת. נסה שוב.");
                    break;
            }
        }
    }
}
