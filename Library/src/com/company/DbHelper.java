package com.company;

import java.sql.*;

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
                System.out.println(
                        resultSet.getInt("id") + "| "
                                + resultSet.getString("title") + "| "
                                + resultSet.getString("author") + "| "
                                + resultSet.getString("genre") + "| "
                                + resultSet.getInt("year")
                );
                while (resultSet.next()) {
                    System.out.println(
                            resultSet.getInt("id") + "| "
                                + resultSet.getString("title") + "| "
                                + resultSet.getString("author") + "| "
                                + resultSet.getString("genre") + "| "
                                + resultSet.getInt("year")
                    );
                }

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
                System.out.println(
                        resultSet.getInt("id") + "| "
                                + resultSet.getString("lastname") + "| "
                                + resultSet.getString("name") + "| "
                                + resultSet.getString("surname") + "| "
                                + resultSet.getInt("number_library_card")
                );
                while (resultSet.next()) {
                    System.out.println(
                            resultSet.getInt("id") + "| "
                                    + resultSet.getString("lastname") + "| "
                                    + resultSet.getString("name") + "| "
                                    + resultSet.getString("surname") + "| "
                                    + resultSet.getInt("number_library_card")
                    );
                }

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
        try (PreparedStatement statement = this.connection.prepareStatement(command)) {
            statement.setObject(1, issue.getReaderId());
            statement.setObject(2, issue.getBookId());
            statement.setObject(3, issue.getDateOfIssue());
            statement.execute();
            isAdd = true;
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return isAdd;
    }
}
