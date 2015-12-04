package com.prueba.message;


import java.io.Serializable;

public class Stats implements Serializable{
    private final String name;

    public Stats(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
