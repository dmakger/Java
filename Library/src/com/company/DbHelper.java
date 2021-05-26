package com.company;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            System.out.println("Success");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

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
            System.err.println(ex.getClass() + ": " + ex.getMessage());
        }
        return isAdd;
    }

}
