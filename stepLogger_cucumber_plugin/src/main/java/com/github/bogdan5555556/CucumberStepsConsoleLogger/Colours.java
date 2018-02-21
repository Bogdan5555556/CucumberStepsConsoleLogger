package com.github.bogdan5555556.CucumberStepsConsoleLogger;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum Colours {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    GREY("\u001B[90m"),
    DEFAULT("\u001B[9m");

    private String value;

    Colours(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Colours byName(String name) {
        List<Colours> colours = Arrays.asList(Colours.values());
        for (Colours colour : colours) {
            if (Objects.equals(colour.name(), name)) {
                return colour;
            }
        }
        return null;
    }

    public StringBuilder appendTo(StringBuilder stringBuilder){
        stringBuilder.append(value);
        return stringBuilder;
    }
}
