package com.fynes.als.model;

public class Echo {

    private String message;
    private String name;


    public Echo(String name) {
        this.name = name;
        this.message = String.format("Hello %s", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
