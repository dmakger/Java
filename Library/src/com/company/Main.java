package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MainHelper helper = new MainHelper();
        DbHelper db = new DbHelper("bruh.db");

        boolean isExit = false;
        String n;

        while (!isExit) {
            helper.menu();
            System.out.print(">> ");
            n = in.nextLine();

            // 1. Table book
            if (n.equals("1")) {
                System.out.println();
                while (!isExit) {
                    helper.book.menu();
                    System.out.print(">> ");
                    n = in.nextLine();
                    // 1. Add book
                    if (n.equals("1")) {
                        Book book = helper.book.input();  // Создаем объект Book для добавления в таблицу
                        boolean isAdd = db.addBook(book);  // Добавляем объект типа Book, в таблицу. true если добавлен
                        if (isAdd) {
                            System.out.println("\nКнига успешно добавлена");
                        } else {
                            System.out.println("\nКнига не добавлена. Скорее всего введенный вами book_id уже существует");
                        }
                        System.out.println();
                    }
                    // 2. Update book
                    else if (n.equals("2")) {
                        Book book = helper.book.input();
                        boolean isUpdate = db.updateBook(book);
                        if (isUpdate) {
                            System.out.println("\nКнига успешно обновлена");
                        } else {
                            System.out.println("\nТакой книги не существует");
                        }
                        System.out.println();
                    }

                    // 3. Delete book
                    else if (n.equals("3")) {
                        int book_id = helper.book.inputId();
                        boolean isDel = db.deleteBook(book_id);
                        if (isDel) {
                            System.out.println("\nКнига успешно удалена");
                        } else {
                            System.out.println("\nТакой книги не существует");
                        }
                        System.out.println();
                    }
                    // 4. Print all table book
                    else if (n.equals("4")) {
                        boolean isTableEmpty = db.printAllBooks();
                        if (!isTableEmpty) {
                            System.out.println("\nТаблица пуста");
                        }
                        System.out.println();
                    }

                    // 0. Back
                    else if (n.equals("0")) {
                        isExit = true;
                        System.out.println();
                    }
                }

                isExit = false;  // Возвращаем переменную в прежний вид, чтобы не выйти из внешнего цикла
            }

            // 2. Table reader
            else if (n.equals("2")) {
                System.out.println();
                while (!isExit) {
                    helper.reader.menu();
                    System.out.print(">> ");
                    n = in.nextLine();
                    // 1. Add reader
                    if (n.equals("1")) {
                        Reader reader = helper.reader.input();  // Создаем объект Book для добавления в таблицу
                        boolean isAdd = db.addReader(reader);  // Добавляем объект типа Book, в таблицу. true если добавлен
                        if (isAdd) {
                            System.out.println("\nЧитатель успешно добавлен");
                        } else {
                            System.out.println("\nЧитатель не добавлен. Скорее всего введенный вами reader_id уже существует");
                        }
                        System.out.println();
                    }
                    // 2. Update reader
                    else if (n.equals("2")) {
                        Reader reader = helper.reader.input();
                        boolean isUpdate = db.updateReader(reader);
                        if (isUpdate) {
                            System.out.println("\nЧитатель успешно обновлен");
                        } else {
                            System.out.println("\nТакого читателя не существует");
                        }
                        System.out.println();
                    }

                    // 3. Delete reader
                    else if (n.equals("3")) {
                        int reader_id = helper.reader.inputId();
                        boolean isDel = db.deleteReader(reader_id);
                        if (isDel) {
                            System.out.println("\nЧитатель успешно удален");
                        } else {
                            System.out.println("\nТакого читателя не существует");
                        }
                        System.out.println();
                    }
                    // 4. Print all table reader
                    else if (n.equals("4")) {
                        boolean isTableEmpty = db.printAllReaders();
                        if (!isTableEmpty) {
                            System.out.println("\nТаблица пуста");
                        }
                        System.out.println();
                    }

                    // 0. Back
                    else if (n.equals("0")) {
                        isExit = true;
                        System.out.println();
                    }
                }

                isExit = false;  // Возвращаем переменную в прежний вид, чтобы не выйти из внешнего цикла
            }

            // 3. Table issue
            if (n.equals("3")) {
                System.out.println();
                while (!isExit) {
                    helper.issue.menu();
                    System.out.print(">> ");
                    n = in.nextLine();
                    // 1. Add book
                    if (n.equals("1")) {
                        Issue issue = helper.issue.input();  // Создаем объект Book для добавления в таблицу
                        boolean isAdd = db.addIssue(issue);  // Добавляем объект типа Book, в таблицу. true если добавлен
                        if (isAdd) {
                            System.out.println("\nВыдача успешно добавлена");
                        } else {
                            System.out.println("\nВыдача не добавлена");
                        }
                        System.out.println();
                    }
//                    // 2. Update book
//                    else if (n.equals("2")) {
//                        Issue issue = helper.issue.input();
//                        boolean isUpdate = db.updateIssue(issue);
//                        if (isUpdate) {
//                            System.out.println("\nВыдача успешно обновлена");
//                        } else {
//                            System.out.println("\nТакой выдачи не существует");
//                        }
//                        System.out.println();
//                    }
//
//                    // 3. Delete book
//                    else if (n.equals("3")) {
//                        Issue issue = helper.issue.input();
//                        boolean isDel = db.deleteIssue(issue);
//                        if (isDel) {
//                            System.out.println("\nВыдача успешно удалена");
//                        } else {
//                            System.out.println("\nТакой выдачи не существует");
//                        }
//                        System.out.println();
//                    }
//                    // 4. Print all table book
//                    else if (n.equals("4")) {
//                        boolean isTableEmpty = db.printAllIssue();
//                        if (!isTableEmpty) {
//                            System.out.println("\nТаблица пуста");
//                        }
//                        System.out.println();
//                    }

                    // 0. Back
                    else if (n.equals("0")) {
                        isExit = true;
                        System.out.println();
                    }
                }

                isExit = false;  // Возвращаем переменную в прежний вид, чтобы не выйти из внешнего цикла
            }

            // 0. Back
            else if (n.equals("0")) {
                isExit = true;
            }
        }


    }
}

class MainHelper{

    private Scanner in = new Scanner(System.in);
    public final BookHelper book = new BookHelper();
    public final ReaderHelper reader = new ReaderHelper();
    public final IssueHelper issue = new IssueHelper();

    void menu() {
        System.out.println("[0] Exit");
        System.out.println("[1] Table book");
        System.out.println("[2] Table reader");
        System.out.println("[3] Table issue");
    }

    class BookHelper {
        void menu() {
            System.out.println("[0] Back");
            System.out.println("[1] Add book");
            System.out.println("[2] Update book");
            System.out.println("[3] Delete book");
            System.out.println("[4] Print all table book");
        }

        public Book input() {
            System.out.print("book_id: ");
            int book_id = in.nextInt();
            in.nextLine();

            System.out.print("title: ");
            String title = in.nextLine();

            System.out.print("author: ");
            String author = in.nextLine();

            System.out.print("genre: ");
            String genre = in.nextLine();

            System.out.print("year: ");
            int year = in.nextInt();

            return new Book(book_id, title, author, genre, year);
        }

        public int inputId() {
            System.out.print("book_id: ");
            int book_id = in.nextInt();
            in.nextLine();
            return book_id;
        }
    }

    class ReaderHelper {
        void menu() {
            System.out.println("[0] Back");
            System.out.println("[1] Add reader");
            System.out.println("[2] Update reader");
            System.out.println("[3] Delete reader");
            System.out.println("[4] Print all table reader");
        }

        public Reader input() {
            System.out.print("reader_id: ");
            int reader_id = in.nextInt();
            in.nextLine();

            System.out.print("lastname: ");
            String lastname = in.nextLine();

            System.out.print("name: ");
            String name = in.nextLine();

            System.out.print("surname: ");
            String surname = in.nextLine();

            System.out.print("number_library_card: ");
            int number_library_card = in.nextInt();

            return new Reader(reader_id, lastname, name, surname, number_library_card);
        }

        public int inputId() {
            System.out.print("reader_id: ");
            int reader_id = in.nextInt();
            in.nextLine();
            return reader_id;
        }
    }

    class IssueHelper {
        void menu() {
            System.out.println("[0] Back");
            System.out.println("[1] Add issue");
            System.out.println("[2] Update issue");
            System.out.println("[3] Delete issue");
            System.out.println("[4] Print all table issue");
        }

        public Issue input() {
            System.out.print("reader_id: ");
            int reader_id = in.nextInt();
            in.nextLine();

            System.out.print("book_id: ");
            int book_id = in.nextInt();
            in.nextLine();

            System.out.print("date_of_issue: ");
            String date_of_issue = in.nextLine();

            return new Issue(reader_id, book_id, date_of_issue);
        }
    }
}
