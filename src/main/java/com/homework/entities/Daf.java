package com.homework.entities;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Daf {
    private String name;
    private Amud[] amudim = new Amud[2];

    public Daf(String name) {
        this.name = name;
    }

    public void addAmud(Amud amud, int index) {
        if (index >= 0 && index < 2) {
            amudim[index] = amud;
        }
    }

    public String getName() {
        return name;
    }

    public Amud getAmudA() {
        return amudim[0];
    }

    public Amud getAmudB() {
        return amudim[1];
    }

    public Amud[] getAmudim() {
        return amudim;
    }

    @Override
    public String toString() {
        return String.format("   Daf: name= %s\n%s",
                name,
                Arrays.stream(amudim)
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .collect(Collectors.joining("\n")));
    }
}