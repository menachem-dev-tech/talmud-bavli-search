package com.homework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.homework.entities.Amud;
import com.homework.entities.Daf;
import com.homework.entities.Masechet;

public class ShasIndexManager {
    private ArrayList<Masechet> sortedShas = new ArrayList<>();

    ShasIndexManager(Setup setup) {
        this.sortedShas = sortedShas(setup.getShas());
    }

    ShasIndexManager() {
        this.sortedShas = new ArrayList<>();
    }

    public void writeIndex() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/index.txt"))) {
            for (Masechet masechet : sortedShas) {
                writer.println(masechet);
            }
        } catch (Exception e) {
            System.out.println("שגיאה בהדפסת הקובץ " + e.getMessage());
        }

    }

    public void SearchMasechetBinary(String search) {
        try {
            String masechet = FindMasechet.compareSearchForMasecht(search);
            int left = 0, right = sortedShas.size() - 1;
            while (left <= right) {
                int mid = (left + right) / 2;

                int compare = clean(masechet).compareTo(clean(sortedShas.get(mid).getName()));
                if (compare == 0) {
                        printAmudFromSearch(search, sortedShas.get(mid));
                    return;
                } else if (compare > 0) {
                    left = mid + 1;
                } else
                    right = mid - 1;
            }
            throw new Errors.MasechetNotFoundException(masechet);

        } catch (Errors.MasechetNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printAmudFromSearch(String search, Masechet masechet) {
        try {
            String[] parts = search.split("\\s+");
            String daf = parts[parts.length - 2];
            int amudIndex = parts[parts.length - 1].equals("א") ? 0 : 1;
            ArrayList<Daf> dapim = masechet.getDapim();
            int left = 0, right = dapim.size() - 1;
            while (left <= right) {
                int mid = (left + right) / 2;
                int compare = clean(daf).compareTo(clean(dapim.get(mid).getName()));
                if (compare == 0) {
                    Amud amud = dapim.get(mid).getAmudim()[amudIndex];
                    try {
                        String content = Files.readString(Paths.get(amud.getPath()));
                        System.out.println(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                } else if (compare > 0) {
                    left = mid + 1;
                } else
                    right = mid - 1;
            }
            throw new Errors.DafNotFoundException(daf);

        } catch (Errors.DafNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ArrayList<Masechet> sortedShas(ArrayList<Masechet> shas) {
        ArrayList<Masechet> sortedShas = new ArrayList<>();

        for (Masechet m : shas) {
            ArrayList<Daf> sortedDapim = sortedDapim(new ArrayList<>(m.getDapim()));
            Masechet copy = new Masechet(m.getName());
            copy.setPrakim(m.getPrakim());

            for (Daf daf : sortedDapim)
                copy.addDapim(daf);

            sortedShas.add(copy);
        }

        for (int i = 0; i < sortedShas.size() - 1; i++) {
            for (int j = 0; j < sortedShas.size() - 1 - i; j++) {
                if (sortedShas.get(j).getName().compareTo(sortedShas.get(j + 1).getName()) > 0) {
                    Masechet temp = sortedShas.get(j);
                    sortedShas.set(j, sortedShas.get(j + 1));
                    sortedShas.set(j + 1, temp);
                }
            }
        }

        return sortedShas;
    }

    public static ArrayList<Daf> sortedDapim(ArrayList<Daf> dapim) {
        for (int i = 0; i < dapim.size() - 1; i++) {
            for (int j = 0; j < dapim.size() - 1 - i; j++) {
                if (dapim.get(j).getName().compareTo(dapim.get(j + 1).getName()) > 0) {
                    Daf temp = dapim.get(j);
                    dapim.set(j, dapim.get(j + 1));
                    dapim.set(j + 1, temp);
                }
            }
        }

        return dapim;
    }

    void loadingByIndex(File indexFile) {
        Amud amud = new Amud("", "", false);
        Daf daf = new Daf("");
        Masechet masechet = new Masechet(" ");
        try (BufferedReader reader = new BufferedReader(new FileReader(indexFile))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("מסכת")) {
                    masechet = new Masechet(clean(line));
                    this.sortedShas.add(masechet);
                } else if (line.contains("Daf")) {
                    daf = new Daf(clean(line).split(" ")[2]);
                    masechet.addDapim(daf);
                } else if (line.contains("Amud")) {
                    String[] parts = clean(line).split("\\s+");
                    String name = parts[2];
                    String path = parts[4];
                    boolean hasMishna = Boolean.parseBoolean(parts[6]);
                    amud = new Amud(name, path, hasMishna);
                    int index = name.equals("א") ? 0 : 1;
                    daf.addAmud(amud, index);

                }
            }

        } catch (Exception e) {
            System.out.println("שגיאה בקריאת האינדקס: " + e.getMessage());
        }
    }

    static int getAmud(String search) {
        if (search.split("\\s+")[2].equals("א"))
            return 0;
        else
            return 1;

    }

    public List<Masechet> sortedShas() {
        return sortedShas;
    }

    private static String clean(String s) {
        if (s == null)
            return "";
        return s.replaceAll("[\\p{C}]", "") // מסיר תווים בלתי נראים כולל תווי כיווניות
                .replaceAll("\\s+", " ") // מאחד רווחים מרובים לרווח אחד
                .trim(); // מסיר רווחים מההתחלה ומהסוף
    }
}
