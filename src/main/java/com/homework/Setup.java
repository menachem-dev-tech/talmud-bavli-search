package com.homework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.homework.entities.Amud;
import com.homework.entities.Daf;
import com.homework.entities.Masechet;

public class Setup {
    private ArrayList<Masechet> shas = new ArrayList<>();

    private String getFileName() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder fileName = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * chars.length());
            fileName.append(chars.charAt(index));
        }
        return fileName.toString() + ".txt";
    }

    private Masechet switchToNewMasechet(Masechet masechet, String line, int prakim, StringBuilder toFile) {
        masechet.setPrakim(Integer.toString(prakim));
        masechet = new Masechet(line.trim());
        this.shas.add(masechet);
        return masechet;
    }

    private File printAmudToFile(String currentMasechet, String currentDaf, File file, String path,
            StringBuilder toFile) {
        if (lineLength(toFile.toString().trim()) < 1)
            return null;

        String fileName = getFileName();
        toFile.insert(0, currentMasechet + " " + currentDaf + "\n");
        file = new File(path, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private Daf switchToNewDaf(String currentDaf, Daf daf, Masechet masechet, StringBuilder toFile) {
        String name = currentDaf.split("\s+")[1];
        daf = new Daf(name);
        masechet.addDapim(daf);
        return daf;
    }

    private Amud switchToNewAmud(String line, String currentDaf, Amud amud, File file, StringBuilder toFile,
            Daf daf) {
        if (lineLength(toFile.toString().trim()) < 4)
            return null;
        String path = file.getAbsolutePath();
        boolean hasMishna = (toFile.indexOf("משנה   ") != -1);
        String amudName = currentDaf.trim().split("\s+")[2];
        amud = new Amud(amudName, path, hasMishna);
        int index = 0;
        if (amud.getName().equals("א"))
            index = 0;
        else
            index = 1;
        daf.addAmud(amud, index);
        toFile.setLength(0);
        return amud;
    }

    private static int lineLength(String line) {
        return line.split("\s+").length;
    }

    private boolean skip(String line, String currentDaf, String currentMasechet) {
        return (line.trim().contains("תלמוד בבלי") || line.trim().equals(currentDaf)
                || line.trim().equals(currentMasechet));
    }

    private boolean stop(String line, String currentMasechet, String currentDaf, StringBuilder toFile) {
        return newMasechet(line, currentMasechet) || line.startsWith("דף") && lineLength(line) < 4
                || line.equals("מסכת נדה");
    }

    private boolean hasPerek(String line) {
        return (line.contains("פרק") && line.startsWith("מסכת") && lineLength(line) < 6);
    }

    private boolean newDaf(String line, String currentDaf, StringBuilder toFile) {
        return (line.trim().startsWith("דף") && lineLength(line) < 4 && line.trim().endsWith("א"));
    }

    private boolean newMasechet(String line, String currentMasechet) {
        return ((line.trim().startsWith("מסכת") && lineLength(line) < 4 && !line.trim().equals(currentMasechet)));
    }


boolean WriteAmud(File bavli, boolean skipWriting) {
    if (skipWriting)
        return false;

    String line = "", currentMasechet = "", currentDaf = "";
    int prakim = 0;
    Amud amud = new Amud("", "", false);
    Daf daf = new Daf("");
    Masechet masechet = new Masechet(" ");
    File file = new File(" ");
    StringBuilder toFile = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(bavli))) {
        while ((line = reader.readLine()) != null) {
            if (skip(line, currentDaf, currentMasechet))
                continue;
            if (hasPerek(line)) {
                prakim++;
                continue;
            }
            if (stop(line, currentMasechet, currentDaf, toFile)) {
                if (!currentMasechet.isBlank() && !currentDaf.isBlank()) {
                    if (!skipWriting) {
                        file = printAmudToFile(currentMasechet, currentDaf, file, "data/shas", toFile);
                        if (file == null || file.getPath().isBlank()) {
                            throw new Errors.TheObjectsWereNotCreatedException(
                                    " הקובץ לא נוצר כראוי עבור " + currentMasechet + " " + currentDaf);
                        }
                    }

                    amud = switchToNewAmud(line, currentDaf, amud, file, toFile, daf);
                    if (amud == null || amud.getPath() == null || amud.getPath().isBlank()) {
                        throw new Errors.TheObjectsWereNotCreatedException(
                                " העמוד לא נוצר כראוי עבור " + currentMasechet + " " + currentDaf);
                    }
                }

                if (newMasechet(line, currentMasechet)) {
                    masechet = switchToNewMasechet(masechet, line, prakim, toFile);
                    if (masechet == null) {
                        throw new Errors.TheObjectsWereNotCreatedException(
                                " המסכת לא נוצרה כראוי עבור " + currentMasechet);
                    }
                    currentMasechet = masechet.getName();
                    prakim = 0;
                    continue;
                }

                currentDaf = line.trim();
                if (newDaf(line, currentDaf, toFile)) {
                    daf = switchToNewDaf(currentDaf, daf, masechet, toFile);
                    if (daf == null) {
                        throw new Errors.TheObjectsWereNotCreatedException(" הדף לא נוצר כראוי  " + currentDaf);
                    }
                    continue;
                }

                continue;
            }

            toFile.append(line + "\n");
        }

    } catch (IOException e) {
        System.out.println("שגיאה בקריאה מהקובץ: " + e.getMessage());
    } catch (Errors.TheObjectsWereNotCreatedException e) {
        System.out.println(e.getMessage());
    }
    return true;
}

    public ArrayList<Masechet> getShas() {
        return shas;
    }
}
