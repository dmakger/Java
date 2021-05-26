package com.company;

public class Issue {

    private int readerId;
    private int bookId;
    private String dateOfIssue;

    public Issue(int readerId, int bookId, String dateOfIssue) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.dateOfIssue = dateOfIssue;
    }

    public int getReaderId() {
        return readerId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }
}
