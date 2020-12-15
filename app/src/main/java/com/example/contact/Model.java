package com.example.contact;

public class Model {
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    String organization, name, number;

    Model(String organization, String name, String number) {
        this.organization = organization;
        this.name = name;
        this.number = number;
    }

}
