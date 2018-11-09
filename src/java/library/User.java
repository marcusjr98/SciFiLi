package library;

import library.Book;

import java.util.ArrayList;

public class User {
    private String userName;
    private String passWord;
    private boolean isAdmin;
    private ArrayList<Book> coBooks = new ArrayList<>();

    public ArrayList<Book> getCoBooks() {
        return coBooks;
    }

    public void setCoBooks(ArrayList<Book> coBooks) {
        this.coBooks = coBooks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
