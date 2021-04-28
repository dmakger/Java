package com.company;

import java.util.ArrayList;

public class DotCom {
    private ArrayList<String> locationCells;
    private String name;

    public void setLocationCells(ArrayList<String> _locationCells) {
        locationCells = _locationCells;
    }

    public void setName(String _name){
        name = _name;
    }

    public String getName(){
        return name;
    }

    public String checkYourself(String userInput){
        String result = "Мимо";
        int index = locationCells.indexOf(userInput);
        if (index > -1){
            locationCells.remove(index);
            if (locationCells.isEmpty()){
                result = "Потопил";
                System.out.println("Вы покончили с сайтом " + name + ". Помянем");
            } else {
                result = "Попал";
            }
        }
        return result;
    }
}
