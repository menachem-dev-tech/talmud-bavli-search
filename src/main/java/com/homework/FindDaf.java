package com.homework;

import java.io.File;
import java.io.IOException;

import com.homework.Errors.InvalidSearchFormatException;

import java.io.BufferedReader;

public class FindDaf {

    static public String compareSearchForDaf(String search) throws InvalidSearchFormatException {
        String[] searchArr = search.split(" ");
        if (searchArr.length < 3) {
        throw new Errors.InvalidSearchFormatException(search);
    }
        return "דף " + searchArr[searchArr.length - 2];
    }
    
    static private String getAmud(String search) {
        String[] searchArr = search.split(" ");
        return " " + searchArr[searchArr.length - 1];
    }

    static public void getDaf(File bavli, BufferedReader reader, String search) {
        String daf = "";
        try {
            daf = compareSearchForDaf(search);
        } catch (InvalidSearchFormatException e) {
            e.printStackTrace();
        }
        String amud = getAmud(search);
        String line = "";
        boolean dafFound = false;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.trim().contains(daf + amud)) {
                    dafFound = true;
                    break;
                }
            }
            if (!dafFound) {
                throw new Errors.DafNotFoundException(daf + amud);
            }

            String currentDaf = line;
            System.out.println(line);
            line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("דף") && !line.equals(currentDaf) && line.length() < 12) {
                    break;
                }
                if (line.equals(currentDaf))
                    continue;
                
                System.out.println(line);

            }
        } catch (IOException e) {
            System.out.println("שגיאה בקריאת הדף");
            e.printStackTrace();
        } catch (Errors.DafNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
