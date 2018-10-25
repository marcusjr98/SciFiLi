package library;

import DataStructures.Queue;
import DataStructures.Stack;
import javafx.util.Pair;

import java.util.ArrayList;

public class Library {
    private Stack<String> checkIn = new Stack<>();
    private Stack<String> checkOut = new Stack<>();
    private ArrayList<Book> books = new ArrayList<>();

    public Stack getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Stack<String> checkIn) {
        this.checkIn = checkIn;
    }

    public Stack getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Stack<String> checkOut) {
        this.checkOut = checkOut;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public Queue<Book> sortByPriority() {
        return new Queue<>();
    }

    public void createExitFile(){
        
    }

    public void sortByAuthor() {
        insertionSort(books, false);
        for (Book book : books)
            printBookInfo(book, "title");
    }

    public void sortByName() {
        insertionSort(books, true);
        for (Book book : books)
            printBookInfo(book, "title");
    }

    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> booksbyAuthor = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().equals(author)) {
                booksbyAuthor.add(books.get(i));
            }
        }
        return booksbyAuthor;
    }

    public Book searchBytitle(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equals(title)) {
                return books.get(i);
            }
        }
        return null;
    }

    public void printBookInfo(Book book, String searchedBy) {
        if (searchedBy.equalsIgnoreCase("title"))
            System.out.println(String.format("Information on %S:", book.getName()));
        else
            System.out.println(String.format("Information on books by %S:", book.getAuthor()));

        System.out.println(String.format("Title %S", book.getName()));
        System.out.println(String.format("Author %S", book.getAuthor()));

        if (book.getStatus() == 1)
            System.out.println("Status: Checked In!\n");
        else
            System.out.println("Status: Checked Out!\n");

        //Might make dynamic search with statement below
        String method_name = Thread.currentThread().getStackTrace()[1].getMethodName();

    }

    public Pair<Boolean, ArrayList<Book>> checkIn() {
        int badBook = 0;
        ArrayList<Book> badBooks = new ArrayList<>();
        while (!checkIn.IsEmpty()) {
            String bookName = (String) checkIn.pop();
            System.out.println(String.format("Checking in: %S", bookName));
            Book book = this.searchBytitle(bookName);
            if (book != null) {
                if (books.get(books.indexOf(book)).getStatus() == 0)
                    books.get(books.indexOf(book)).setStatus(1);
                else {
                    badBooks.add(books.get(books.indexOf(book)));
                    badBook++;
                }
            } else {
                Book newBook = new Book();
                newBook.setName(bookName);
                badBook++;
                badBooks.add(newBook);
            }
        }
        return new Pair<>((badBook == 0), badBooks);
    }


    public Pair<Boolean, ArrayList<Book>> checkOut() {
        int badBook = 0;
        ArrayList<Book> badBooks = new ArrayList<>();
        while (!checkOut.IsEmpty()) {
            String bookName = (String) checkOut.pop();
            Book book = this.searchBytitle(bookName);
            if (book != null) {
                if (books.get(books.indexOf(book)).getStatus() == 1)
                    books.get(books.indexOf(book)).setStatus(0);
                else {
                    badBooks.add(books.get(books.indexOf(book)));
                    badBook++;
                }
            } else {
                Book newBook = new Book();
                newBook.setName(bookName);
                newBook.setStatus(1);
                badBook++;
                badBooks.add(newBook);
            }
        }
        return new Pair<>((badBook == 0), badBooks);
    }


    private void insertionSort(ArrayList<Book> books, boolean sortByTitle) {
        //The following insertion sort came from stack overflow: https://stackoverflow.com/questions/17432738/insertion-sort-using-string-compareto
        Book key;

        if (sortByTitle) {
            for (int j = 1; j < books.size(); j++) { //the condition has changed
                key = books.get(j);
                int i = j - 1;
                while (i >= 0) {
                    if (key.getName().compareTo(books.get(i).getName()) > 0) {//here too
                        break;
                    }
                    books.set(i + 1, books.get(i));
                    i--;
                }
                books.set(i + 1, key);
            }
        } else {
            for (int j = 1; j < books.size(); j++) { //the condition has changed
                key = books.get(j);
                int i = j - 1;
                while (i >= 0) {
                    if (key.getAuthor().compareTo(books.get(i).getAuthor()) > 0) {//here too
                        break;
                    }
                    books.set(i + 1, books.get(i));
                    i--;
                }
                books.set(i + 1, key);
            }
        }

        this.books = books;
    }
}
