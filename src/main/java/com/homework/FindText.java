package com.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FindText {

    private static String removeFirstWord(String line) {
        if (line.trim().startsWith("גמרא") || line.trim().startsWith("משנה"))
            return line.substring(line.indexOf(" ") + 1).trim();
        return line;
    }

    public static String getLastXWords(String line, String text) {
        if (!line.trim().startsWith("גמרא") && !line.trim().startsWith("משנה"))
            return "";

        String[] words = line.split("\\s+");
        int wordCount = text.split("\\s+").length - 1;
        StringBuilder result = new StringBuilder();
        int start = Math.max(0, words.length - wordCount);
        for (int i = start; i < words.length; i++) {
            result.append(words[i]).append(" ");
        }
        return result.toString().trim();
    }

    static public void getText(File bavli, String text) {
        try (BufferedReader reader = new BufferedReader(new FileReader("bavli.txt"))) {
            String line = "";
            String masechet = "";
            String daf = "";
            String endOfLine = "";
            int find = -1;

            while ((line = reader.readLine()) != null) {
                if (line.contains("מסכת") && line.length() < 20) {
                    masechet = line;
                }
                if (line.contains("דף") && line.length() < 20) {
                    daf = line;
                }
                if ((endOfLine.trim() + " " + removeFirstWord(line).trim()).contains(text.trim())) {
                    System.out.println(masechet + " " + daf);
                    find = 0;
                }
                if (line.trim().startsWith("גמרא") || line.trim().startsWith("משנה"))
                    endOfLine = getLastXWords(line, text);
            }

            if (find == -1) {
                throw new Errors.TheTextNotFoundException(text);
            }

        } catch (Errors.TheTextNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("הקובץ לא נמצא: " + bavli.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("שגיאה בקריאת הקובץ");
            e.printStackTrace();
        }
    }
}
