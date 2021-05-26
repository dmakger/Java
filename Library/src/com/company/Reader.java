package com.company;


public class Reader {
    private int id;
    private String lastname;
    private String name;
    private String surname;
    private int numberLibraryCard;

    public Reader(int id, String lastname, String name, String surname, int numberLibraryCard) {
        this.id = id;
        this.lastname = lastname;
        this.name = name;
        this.surname = surname;
        this.numberLibraryCard = numberLibraryCard;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSurname() {
        return surname;
    }

    public int getNumberLibraryCard() {
        return numberLibraryCard;
    }
}
