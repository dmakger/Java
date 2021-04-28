package com.company;

import java.lang.Math;
import java.util.*;

public class DotComBust {

    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
    private int numOfGuesses = 0;

    private void setUpGame() {
        // Let's create several "site", and assign them names
        DotCom one = new DotCom();
        one.setName("ya.ru");
        DotCom two = new DotCom();
        two.setName("google.com");
        DotCom three = new DotCom();
        three.setName("github.com");
        dotComsList.add(one);
        dotComsList.add(two);
        dotComsList.add(three);

        System.out.println("Ваша цель - потопить три сайта:");
        System.out.println(one.getName() + ", " + two.getName() + ", " + three.getName());
        System.out.println("Попытайтесь потопить их за минимальное количество времени");

        for (DotCom dotComToSet : dotComsList){
            ArrayList<String> newLocation = helper.placeDotCom(3);
            dotComToSet.setLocationCells(newLocation);
        }
    }

    private void startPlaying(){
        while (!dotComsList.isEmpty()){
            String userGuess = helper.getUserInput("Сделайте ход:");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess){
        ++numOfGuesses;
        String result = "Мимо";

        for (DotCom dotComToTest : dotComsList){
            result = dotComToTest.checkYourself(userGuess);
            if (result.equals("Попал")){
                break;
            }
            if (result.equals("Потопил")){
                dotComsList.remove(dotComToTest);
                break;
            }
        }
        System.out.println(result);
    }

    private void finishGame(){
        System.out.println("Все \"сайты\" пали");
        System.out.println("Количество попыток: " + numOfGuesses);
    }

    public static void main(String[] args){
        DotComBust game = new DotComBust();
        game.setUpGame();
        game.startPlaying();
    }

}
