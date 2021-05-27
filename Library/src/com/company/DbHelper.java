package com.company;

import java.sql.*;
import java.util.*;

public class DbHelper {

    private Connection connection;
    public final static String tableBook = "book";
    public final static String tableIssue = "issue";
    public final static String tableReader = "reader";

    public DbHelper(String db) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + db);
//            System.out.println("Success");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Добавление книги в бд
     * */
    public synchronized boolean addBook(Book book) {
        boolean isAdd = false;
        String command = "INSERT INTO "
                + tableBook
                + "(`id`, `title`, `author`, `genre`, `year`) "
                + "VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(command)) {
            statement.setObject(1, book.getId());
            statement.setObject(2, book.getTitle());
            statement.setObject(3, book.getAuthor());
            statement.setObject(4, book.getGenre());
            statement.setObject(5, book.getYear());
            statement.execute();
            isAdd = true;
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isAdd;
    }

    /**
     * Обновление книги в бд
     * */
    public synchronized boolean updateBook(Book book) {
        boolean isUpdate = false;
        String command = "UPDATE " + tableBook + " SET"
                + " `title` = ?, "
                + " `author` = ?, "
                + " `genre` = ?, "
                + " `year` = ? "
                + "WHERE `id` = ?";
        String commandSelect = "SELECT * FROM " + tableBook + " WHERE `id` = " + book.getId();  // Запрос на существование
        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(commandSelect);
            // Если такая книга существует, удаляем
            if (resultSet.next()) {
                PreparedStatement statement = this.connection.prepareStatement(command);
                statement.setObject(1, book.getTitle());
                statement.setObject(2, book.getAuthor());
                statement.setObject(3, book.getGenre());
                statement.setObject(4, book.getYear());
                statement.setObject(5, book.getId());
                statement.execute();
                isUpdate = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isUpdate;
    }

    /**
     * Удаление книги из бд
     * */
    public synchronized boolean deleteBook(int book_id) {
        boolean isDel = false;

        String commandSelect = "SELECT * FROM " + tableBook + " WHERE `id` = " + book_id;  // Запрос на существование
        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(commandSelect);
            // Если такая книга существует, удаляем
            if (resultSet.next()) {
                String commandDel = "DELETE FROM " + tableBook + " WHERE `id` = ?";
                PreparedStatement stDel = this.connection.prepareStatement(commandDel);
                stDel.setObject(1, book_id);
                stDel.execute();

                isDel = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isDel;
    }

    /**
     * Вывод всей таблицы отвечающую за книги
     * */
    public synchronized boolean printAllBooks() {
        boolean isEmpty = false;

        String command = "SELECT * FROM " + tableBook;
        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(command);
            // Вывод всех книг
            if (resultSet.next()) {
                System.out.println();
                System.out.println("+----+-------+--------+------+-------+");
                System.out.println("| id | title | author | genre | year |");
                System.out.println("+----+-------+--------+------+-------+");
                do {
                    System.out.println( "| " +
                            resultSet.getInt("id") + " | "
                                    + resultSet.getString("title") + " | "
                                    + resultSet.getString("author") + " | "
                                    + resultSet.getString("genre") + " | "
                                    + resultSet.getInt("year") + " |"
                    );
                } while (resultSet.next());
                System.out.println("+----+-------+--------+------+-------+");

                isEmpty = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isEmpty;
    }


    /**
     * Добавление читателя в бд
     * */
    public synchronized boolean addReader(Reader reader) {
        boolean isAdd = false;
        String command = "INSERT INTO "
                + tableReader
                + "(`id`, `lastname`, `name`, `surname`, `number_library_card`) "
                + "VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(command)) {
            statement.setObject(1, reader.getId());
            statement.setObject(2, reader.getLastname());
            statement.setObject(3, reader.getName());
            statement.setObject(4, reader.getSurname());
            statement.setObject(5, reader.getNumberLibraryCard());
            statement.execute();
            isAdd = true;
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isAdd;
    }


    /**
     * Обновление читателя в бд
     * */
    public synchronized boolean updateReader(Reader reader) {
        boolean isUpdate = false;
        String command = "UPDATE " + tableReader + " SET"
                + " `lastname` = ?, "
                + " `name` = ?, "
                + " `surname` = ?, "
                + " `number_library_card` = ? "
                + "WHERE `id` = ?";
        String commandSelect = "SELECT * FROM " + tableReader + " WHERE `id` = " + reader.getId();  // Запрос на существование
        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(commandSelect);
            // Если такая книга существует, удаляем
            if (resultSet.next()) {
                PreparedStatement statement = this.connection.prepareStatement(command);
                statement.setObject(1, reader.getLastname());
                statement.setObject(2, reader.getName());
                statement.setObject(3, reader.getSurname());
                statement.setObject(4, reader.getNumberLibraryCard());
                statement.setObject(5, reader.getId());
                statement.execute();
                isUpdate = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isUpdate;
    }

    /**
     * Удаление читателя из бд
     * */
    public synchronized boolean deleteReader(int reader_id) {
        boolean isDel = false;

        String commandSelect = "SELECT * FROM " + tableReader + " WHERE `id` = " + reader_id;  // Запрос на существование
        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(commandSelect);
            // Если такая книга существует, удаляем
            if (resultSet.next()) {
                String commandDel = "DELETE FROM " + tableReader + " WHERE `id` = ?";
                PreparedStatement stDel = this.connection.prepareStatement(commandDel);
                stDel.setObject(1, reader_id);
                stDel.execute();

                isDel = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isDel;
    }

    /**
     * Вывод всей таблицы отвечающую за читателей
     * */
    public synchronized boolean printAllReaders() {
        boolean isEmpty = false;

        String command = "SELECT * FROM " + tableReader;
        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(command);
            // Вывод всех книг
            if (resultSet.next()) {
                System.out.println();
                System.out.println("+----+----------+------+---------+---------------------+");
                System.out.println("| id | lastname | name | surname | number_library_card |");
                System.out.println("+----+----------+------+---------+---------------------+");
                do {
                    System.out.println( "| " +
                            resultSet.getInt("id") + " | "
                                    + resultSet.getString("lastname") + " | "
                                    + resultSet.getString("name") + " | "
                                    + resultSet.getString("surname") + " | "
                                    + resultSet.getInt("number_library_card") + " |"
                    );
                } while (resultSet.next());
                System.out.println("+----+----------+------+---------+---------------------+");

                isEmpty = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isEmpty;
    }



    /**
     * Добавление выдачи в бд
     * */
    public synchronized boolean addIssue(Issue issue) {
        boolean isAdd = false;
        String command = "INSERT INTO "
                + tableIssue
                + "(`reader_id`, `book_id`, `date_of_issue`) "
                + "VALUES(?, ?, ?)";
        // Если такой читатель и такая книга существует
        if (this.readerExists(issue.getReaderId()) && this.bookExists(issue.getBookId())) {
            try (PreparedStatement statement = this.connection.prepareStatement(command)) {
                statement.setObject(1, issue.getReaderId());
                statement.setObject(2, issue.getBookId());
                statement.setObject(3, issue.getDateOfIssue());
                statement.execute();
                isAdd = true;
            } catch (SQLException ex) {
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            }
        } else {
            System.err.println("Читателя или книги с таким id не существует");
        }
        return isAdd;
    }

    private synchronized boolean readerExists(int reader_id) {
        boolean readerExistsVar = false;
        String command = "SELECT id FROM " + tableReader + " WHERE `id` = " + reader_id;
        try (Statement st = this.connection.createStatement()){
            ResultSet resultSet = st.executeQuery(command);
            if (resultSet.next()) {
                readerExistsVar = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return readerExistsVar;
    }

    private synchronized boolean bookExists(int book_id) {
        boolean bookExistsVar = false;
        String command = "SELECT id FROM " + tableBook + " WHERE `id` = " + book_id;
        try (Statement st = this.connection.createStatement()){
            ResultSet resultSet = st.executeQuery(command);
            if (resultSet.next()) {
                bookExistsVar = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return bookExistsVar;
    }

    /**
     * Обновление выдачи в бд
     * */
    public synchronized boolean updateIssue(Issue issueOld, Issue issueNew) {
        boolean isUpdate = false;
        // Команда на обновление
        String command = "UPDATE " + tableIssue + " SET"
                + " `reader_id` = ?, "
                + " `book_id` = ?, "
                + " `date_of_issue` = ? "
                + "WHERE `reader_id` = ? AND `book_id` = ? AND `date_of_issue` = ?";
        // Команда. Проверяет существуют ли такие данные
        String commandSelect = "SELECT * FROM " + tableIssue
                + " WHERE `reader_id` = " + issueOld.getReaderId()
                + " AND `book_id` = " + issueOld.getBookId()
                + " AND `date_of_issue` = \"" + issueOld.getDateOfIssue() + "\"";
        // Если такой читатель и такая книга существует
        if (this.readerExists(issueNew.getReaderId()) && this.bookExists(issueNew.getBookId())) {
            try (Statement stSelect = this.connection.createStatement()) {
                ResultSet resultSet = stSelect.executeQuery(commandSelect);
                // Если такая книга существует, удаляем
                if (resultSet.next()) {
                    PreparedStatement statement = this.connection.prepareStatement(command);
                    statement.setObject(1, issueNew.getReaderId());
                    statement.setObject(2, issueNew.getBookId());
                    statement.setObject(3, issueNew.getDateOfIssue());
                    statement.setObject(4, issueOld.getReaderId());
                    statement.setObject(5, issueOld.getBookId());
                    statement.setObject(6, issueOld.getDateOfIssue());
                    statement.execute();
                    isUpdate = true;
                }
            } catch (SQLException ex) {
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            }
        } else {
            System.err.println("Читателя или книги с таким id не существует");
        }
        return isUpdate;
    }

    /**
     * Удаление выдачи из бд
     * */
    public synchronized boolean deleteIssue(Issue issue) {
        boolean isDel = false;

        // Запрос на существование
        String commandSelect = "SELECT * FROM " + tableIssue + " WHERE `reader_id` = " + issue.getReaderId()
                + " AND `book_id` = " + issue.getBookId()
                + " AND `date_of_issue` = \"" + issue.getDateOfIssue() + "\"";

        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(commandSelect);
            // Если такая книга существует, удаляем
            if (resultSet.next()) {
                String commandDel = "DELETE FROM " + tableIssue + " WHERE `reader_id` = ? AND `book_id` = ?"
                        + " AND `date_of_issue` = ?";
                PreparedStatement stDel = this.connection.prepareStatement(commandDel);
                stDel.setObject(1, issue.getReaderId());
                stDel.setObject(2, issue.getBookId());
                stDel.setObject(3, issue.getDateOfIssue());
                stDel.execute();

                isDel = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isDel;
    }

    /**
     * Вывод всей таблицы отвечающую за читателей
     * */
    public synchronized boolean printAllIssue() {
        boolean isEmpty = false;

        String command = "SELECT * FROM " + tableIssue;
        try (Statement stSelect = this.connection.createStatement()) {
            ResultSet resultSet = stSelect.executeQuery(command);
            // Вывод всех книг
            if (resultSet.next()) {
                System.out.println();
                System.out.println("+-----------+---------+---------------+");
                System.out.println("| reader_id | book_id | date_of_issue |");
                System.out.println("+-----------+---------+---------------+");
                do {
                    System.out.println( "| " +
                            resultSet.getInt("reader_id") + " | "
                                    + resultSet.getInt("book_id") + " | "
                                    + resultSet.getString("date_of_issue") + " |"
                    );
                } while (resultSet.next());
                System.out.println("+-----------+---------+---------------+");

                isEmpty = true;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isEmpty;
    }

    /**
     * Вывод наиболее популярного автора
     * */
    public synchronized ArrayList<String> getMostReadAuthor() {
        ArrayList<String> popularAuthors = new ArrayList<String>();

        try (Statement stSelect = this.connection.createStatement()) {
            String command = "SELECT book_id FROM " + tableIssue;
            ResultSet resultSet = stSelect.executeQuery(command);
            // Вывод всех книг
            if (resultSet.next()) {
                System.out.println();

                HashMap<Integer, String> authors = this.getIdToAuthors();  // {id: author}

                // Составляем словарь такого вида: {author: кол_во_читателей}
                HashMap<String, Integer> rating = new HashMap<>();  // Словарь со всеми результатами
                String nameAuthor;
                do {
                    nameAuthor = authors.get(resultSet.getInt("book_id"));
                    rating.put(nameAuthor, rating.getOrDefault(nameAuthor, 0) + 1);
                } while (resultSet.next());

                // Находим наибольшее кол-во читателей
                int maxReader = 0;
                for (Map.Entry<String, Integer> entry : rating.entrySet()) {
                    if (maxReader < entry.getValue()) {
                        maxReader = entry.getValue();
                    }
                }
                // Находим чаще встречающиегося автора
                for (Map.Entry<String, Integer> entry : rating.entrySet()) {
                    if (maxReader == entry.getValue()) {
                        popularAuthors.add(entry.getKey());
                    }
                }

            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

        return popularAuthors;
    }

    /**
     * Вернет словарь вида {id: author} из таблицы tableBook
     * */
    private synchronized HashMap<Integer, String> getIdToAuthors() {
        HashMap<Integer, String> authors = new HashMap<Integer, String>();

        try (Statement st = this.connection.createStatement()) {
            String command = "SELECT id, author FROM " + tableBook;
            ResultSet resultSet = st.executeQuery(command);
            while (resultSet.next()) {
                authors.put(resultSet.getInt("id"), resultSet.getString("author"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

        return authors;
    }


    public synchronized ArrayList<HashMap<String, String>> getBooksByDateFromIssue(String date) {
        ArrayList<HashMap<String, String>> books = new ArrayList<>();

        ArrayList<Integer> books_id = getBooksIdByDate(date);
        if (!books_id.isEmpty()) {
            try (Statement st = this.connection.createStatement()) {
                String command = "SELECT * FROM " + tableBook;
                ResultSet resultSet = st.executeQuery(command);
                while (resultSet.next()) {
                    do {
                        // Если id книги подходит, до добавляем в books
                        if (books_id.contains(resultSet.getInt("id"))) {
                            HashMap<String, String> book = new HashMap<>();
                            book.put("title", resultSet.getString("title"));
                            book.put("author", resultSet.getString("author"));
                            book.put("genre", resultSet.getString("genre"));
                            book.put("year", resultSet.getInt("year") + "");
                            books.add(book);
                        }
                    } while (resultSet.next());
                }
            } catch (SQLException ex) {
                System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            }
        } else {
            System.err.println("Книг по такой дате выдачи не существует");
        }

        return books;
    }

    private synchronized ArrayList<Integer> getBooksIdByDate(String date) {
        ArrayList<Integer> books = new ArrayList<Integer>();

        try (Statement st = this.connection.createStatement()) {
            String command = "SELECT book_id FROM " + tableIssue + " WHERE date_of_issue = \"" + date + "\"";
            ResultSet resultSet = st.executeQuery(command);
            while (resultSet.next()) {
                do {
                    books.add(resultSet.getInt("book_id"));
                } while (resultSet.next());
            }
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }

        return books;
    }
}



