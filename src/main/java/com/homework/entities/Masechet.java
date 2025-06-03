package com.homework.entities;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Masechet {
    private String name;
    private String prakim;
    private ArrayList<Daf> dapim = new ArrayList<>();

    public Masechet(String name) {
        this.name = name;
    }

    public void addDapim(Daf daf) {
        this.dapim.add(daf);
    }

    public void setPrakim(String prakim) {
        this.prakim = prakim;
    }

    public String getName() {
        return name;
    }

    public String getPrakim() {
        return prakim;
    }

    public ArrayList<Daf> getDapim() {
        return dapim;
    }



    @Override
    public String toString() {
        return String.format("Masechet: name =\n%s\nprakim = %s\n%s",
                name,
                prakim,
                dapim.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")));
    }
}
