package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Main program = new Main();
        program.go();
    }

    public void go() {
        DbHelper helper = new DbHelper("bruh.db");

        boolean isExit = false;
        int n;

        while (!isExit) {
            menu();
            System.out.print(">> ");
            n = in.nextInt();
            if (n == 1) {
                Book book = inputBook();
                boolean isAdd = helper.addBook(book);
                if (!isAdd) {
                    System.out.println("\nКнига не добавлена. Скорее всего введенный вами book_id уже существует");
                } else {
                    System.out.println("\nКнига успешно добавлена");
                }
                System.out.println();
            }
            else if (n == 0) {
                isExit = true;
            }
        }
    }

    void menu() {
        System.out.println("[0] Exit");
        System.out.println("[1] Add book");
    }

    public Book inputBook() {
        System.out.print("book_id: ");
        int id = in.nextInt();
        in.nextLine();

        System.out.print("title: ");
        String title = in.nextLine();

        System.out.print("author: ");
        String author = in.nextLine();

        System.out.print("genre: ");
        String genre = in.nextLine();

        System.out.print("year: ");
        int year = in.nextInt();

        return new Book(id, title, author, genre, year);
    }
}
