package library;

import DataStructures.Queue;
import DataStructures.Stack;

import java.util.ArrayList;

public class Library {
    private Stack<Book> checkIn = new Stack();
    private Stack<Book> checkOut = new Stack();
    private ArrayList<Book> books = new ArrayList<>();

    public Stack getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Stack checkIn) {
        this.checkIn = checkIn;
    }

    public Stack getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Stack checkOut) {
        this.checkOut = checkOut;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void sortByAuthor(){

    }

    public void sortByName(){

    }

    public ArrayList<Book> searchByAuthor(String author){
        return new ArrayList<>();
    }

    public Book searchBytitle(String title){
        return new Book();
    }

    public Boolean checkIn(){
        return true;
    }

    public Boolean checkOut(){
        return true;
    }

    public Queue<Book> sortByPriority(){
        return new Queue<>();
    }
}
