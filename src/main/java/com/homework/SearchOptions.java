package com.homework;

import java.io.File;
import java.util.Scanner;

public class SearchOptions {

    static void search(File bavli, ShasIndexManager sort, Scanner scanner) {
        System.out.println("מה ברצונך לעשות?\n1. לחיפוש דף\n2. לחיפוש טקסט\n3. לחיפוש דף בצורה בינארית");
        int option = scanner.nextInt();
        scanner.nextLine();
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
                System.out.println("איזה פרק משניות ברצונך ללמוד?");
                FindMasechet.getMasechet(bavli, scanner.nextLine());
                break;

            default:
                break;
        }
    }
}
