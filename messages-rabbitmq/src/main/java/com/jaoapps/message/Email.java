package com.prueba.message;


import java.io.Serializable;

public class Email implements Serializable{
    private String name;

    public Email(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
