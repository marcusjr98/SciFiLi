package library;

import DataStructures.PriorityQueue;
import DataStructures.Stack;
import javafx.util.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

public class Library {
    private Stack<String> checkIn = new Stack<>();
    private Stack<String> checkOut = new Stack<>();
    private ArrayList<Book> books = new ArrayList<>();

    public Stack getCheckIn() {
        return checkIn;
    }

    public Stack getCheckOut() {
        return checkOut;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public PriorityQueue<Book> sortByPriority() {
        PriorityQueue<Book> priorityQueue = new PriorityQueue<>();
        for (Book book : books) {
            if (book.getStatus() == 1) {
                priorityQueue.insert(book, book.getImportance());
            }
        }

        return priorityQueue;
    }

    public void createExitFile() throws IOException {
        File file = new File("../ClosingFile/close.txt");
        if (file.exists())
            file.delete();
        if (file.createNewFile()) {
            PrintStream out = new PrintStream(new FileOutputStream("../ClosingFile/close.txt"));
            System.setOut(out);
            System.out.println("Daily Output for " + new Date().toString());
            sortByName(true);
        }

    }

    public void sortByAuthor(boolean print) {
        insertionSort(books, false);
        if (print)
            for (Book book : books)
                printBookInfo(book, "title");
    }

    public void sortByName(boolean print) {
        insertionSort(books, true);
        if (print)
            for (Book book : books)
                printBookInfo(book, "title");
    }

    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().equalsIgnoreCase(author)) {
                booksByAuthor.add(books.get(i));
            }
        }
        return booksByAuthor;
    }

    public Book searchByTitle(String title) {
        sortByName(false);
        int num = binarySearch(title);
        if (num == -1)
            return null;
        else
            return books.get(num);
    }

    public void printBookInfo(Book book, String searchedBy) {
        if (searchedBy.equalsIgnoreCase("title"))
            System.out.println(String.format("Information on %S:", book.getName()));

        System.out.println(String.format("Title: %S", book.getName()));
        System.out.println(String.format("Author: %S", book.getAuthor()));

        if (book.getStatus() == 1)
            System.out.println("Status: Checked In!\n");
        else
            System.out.println("Status: Checked Out!\n");

    }

    public Pair<Boolean, ArrayList<Book>> checkIn() {
        int badBook = 0;
        ArrayList<Book> badBooks = new ArrayList<>();
        while (!checkIn.IsEmpty()) {
            String bookName = (String) checkIn.pop();
            bookName = bookName.replace(" ", "");
            System.out.println(String.format("Checking in: %S", bookName));
            Book book = searchByTitle(bookName);
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
            bookName = bookName.replace(" ", "");
            System.out.println(String.format("Checking out: %S", bookName));
            Book book = searchByTitle(bookName);
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

    private int binarySearch(String name) {

        //This can be changed to be done recursively
        //The following binary search came from stack overflow: https://stackoverflow.com/questions/32260445/implementing-binary-search-on-an-array-of-strings
        int low = 0;
        int high = books.size() - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            if (books.get(mid).getName().compareToIgnoreCase(name) < 0) {
                low = mid + 1;
            } else if (books.get(mid).getName().compareToIgnoreCase(name) > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        // We reach here when element is not present
        //  in array
        return -1;
    }
}
