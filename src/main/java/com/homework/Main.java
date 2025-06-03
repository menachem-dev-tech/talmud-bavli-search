package com.homework;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File bavli = new File("data/bavli.txt");
        if (!bavli.exists()) {
            bavli = new File("data/bavli_sample.txt");
            System.out.println("⚠️ Warning: Using sample input file instead of full Talmud.");
        }
        System.out.println("Using input file: " + bavli.getAbsolutePath());
        
        File indexFile = new File("data/index.txt");
        File shasDir = new File("data/shas");

        if (!shasDir.exists()) {
            shasDir.mkdir();
        }
        Setup setup = new Setup();
        ShasIndexManager sort;

        if (indexFile.exists()) {
            sort = new ShasIndexManager();
            sort.loadingByIndex(indexFile);
        } else {
            boolean skipWriting = shasDir.exists() && shasDir.listFiles().length > 1;
            boolean created = setup.WriteAmud(bavli, skipWriting);
            sort = new ShasIndexManager(setup);
            if (created)
                sort.writeIndex();
        }
        SearchOptions.search(bavli, sort, scanner);
    }
}