package com.homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;

public class FindMasechet {
    static List<String> masechtot = List.of("ברכות", "שבת", "עירובין", "פסחים",
            "שקלים", "ראש השנה", "יומא", "סוכה", "ביצה", "תענית", "מגילה", "מועד קטן", "חגיגה", "יבמות", "כתובות",
            "נדרים", "נזיר", "סוטה", "גיטין", "קידושין", "בבא קמא", "בבא מציעא", "בבא בתרא", "סנהדרין", "מכות", "שבועות",
            "עבודה זרה", "הוריות", "זבחים", "מנחות", "חולין", "בכורות", "ערכין", "תמורה", "כריתות", "מעילה", "תמיד",
            "נדה");

    static private boolean findMasechet(String search) {
        int index = search.indexOf(" ");
        if (!masechtot.contains(search.substring(index + 1)))
            return false;
        return true;

    }

    static String compareSearchForMasecht(String search) {
        String[] searchArr = search.split(" ");
        if (searchArr[0].equals("מסכת")) {
            if (searchArr[1].equals("בבא") || searchArr[1].equals("ראש") || searchArr[1].equals("מועד")
                    || searchArr[1].equals("עבודה")) {
                return searchArr[0] + " " + searchArr[1] + " " + searchArr[2];
            } else
                return searchArr[0] + " " + searchArr[1];
        } else {
            if (searchArr[0].equals("בבא") || searchArr[0].equals("ראש") || searchArr[0].equals("מועד")
                    || searchArr[0].equals("עבודה")) {
                return "מסכת " + searchArr[0] + " " + searchArr[1];
            } else
                return "מסכת " + searchArr[0];
        }

    }

    static public void getMasechet(File bavli, String search) {
        String masechet = compareSearchForMasecht(search);
        try (BufferedReader reader = new BufferedReader(new FileReader("bavli.txt"))) {
            boolean findMasechet = findMasechet(masechet);
            if (!findMasechet)
                throw new Errors.MasechetNotFoundException(masechet);
            String line = "";
            while ((line = reader.readLine()) != null && !line.contains(masechet)) {
            }
            if (line == null) {
                throw new Errors.MasechetNotFoundException(masechet);
            }

            FindDaf.getDaf(bavli, reader, search);

        } catch (FileNotFoundException e) {
            System.err.println("הקובץ לא נמצא: " + bavli.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("שגיאה בקריאת הקובץ");
            e.printStackTrace();
        } catch (Errors.MasechetNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}
