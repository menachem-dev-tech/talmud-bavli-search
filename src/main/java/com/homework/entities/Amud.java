package com.homework.entities;

public class Amud {
    String name;
    private String path;
    private boolean hasMishna;

    public Amud(String name, String path, boolean hasMishna) {
        this.name = name;
        this.path = path;
        this.hasMishna = hasMishna;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean HasMishna() {
        return hasMishna;
    }

    @Override
    public String toString() {
        return String.format("      Amud: name= %s path= %s hasMishna= %b", name, path, hasMishna);

    }

}
