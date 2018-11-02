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

	// getter for checkIn stack
    public Stack getCheckIn() {
        return checkIn;
    }
	
	// getter for checkOut stack
    public Stack getCheckOut() {
        return checkOut;
    }

	// getter and setter for books arraylist
    public ArrayList<Book> getBooks() {
        return books;
    }
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

	// method used to create a priority queue made of all the books that are checked in
    public PriorityQueue<Book> sortByPriority() {
        PriorityQueue<Book> priorityQueue = new PriorityQueue<>();
        for (Book book : books) {
            if (book.getStatus() == 1) {
                priorityQueue.insert(book, book.getImportance());
            }
        }

        return priorityQueue;
    }

	// method used to create the exit file after the libarian closes the library
    public void createExitFile() throws IOException {
        File file = new File("../ClosingFile/close.txt");
		
		// if the file already exists, delete it
        if (file.exists())
            file.delete();
		
		// if it doesn't exist, create a new output file sorted by book title
        if (file.createNewFile()) {
            PrintStream out = new PrintStream(new FileOutputStream("../ClosingFile/close.txt"));
            System.setOut(out);
            System.out.println("Daily Output for " + new Date().toString());
            sortByName(true);
        }

    }
	
	// method used to sort all the books by author and then print them
    public void sortByAuthor(boolean print) {
        insertionSort(books, false);
        if (print) {
            String author = null;
            for (Book book : books) {
                if (!book.getAuthor().equals(author)) {
                    System.out.println(String.format("*****%S*****", book.getAuthor()));
                    author = book.getAuthor();
                }
                printBookInfo(book, "title");
            }
        }
    }

	// method used to sort all the books by title and then print them
    public void sortByName(boolean print) {
        insertionSort(books, true);
        if (print) {
            char letter = '\u0000';
            for (Book book : books) {
                if (book.getName().charAt(0) != letter) {
                    System.out.println(String.format("*****%S*****", book.getName().charAt(0)));
                    letter = book.getName().charAt(0);
                }
                printBookInfo(book, "title");
            }
        }
    }

	// method usd to search for all books written by the same author
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> booksByAuthor = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
			
			// if the author of the current book is the same as the author the user is searching for
            if (books.get(i).getAuthor().equalsIgnoreCase(author)) {
                booksByAuthor.add(books.get(i));
            }
        }
        return booksByAuthor;
    }

	// method used to search for a book based the user entered title
    public Book searchByTitle(String title) {
        sortByName(false);
        int num = binarySearch(title);
		
		// if the book wasn't found
        if (num == -1)
            return null;
		
		// if the book was found
        else
            return books.get(num);
    }

	// method used to print the information of the book
    public void printBookInfo(Book book, String searchedBy) {
		
		// if the user is searching by title print the following
        if (searchedBy.equalsIgnoreCase("title"))
            System.out.println(String.format("Information on %S:", book.getName()));
		
        System.out.println(String.format("Title: %S", book.getName()));
        System.out.println(String.format("Author: %S", book.getAuthor()));
		
		// if the book isn;t checked out
        if (book.getStatus() == 1)
            System.out.println("Status: Checked In!\n");
		
		// if the book isn't checked out
        else
            System.out.println("Status: Checked Out!\n");

    }
	
	// method used to check in books
    public Pair<Boolean, ArrayList<Book>> checkIn() {
        int badBook = 0;
        ArrayList<Book> badBooks = new ArrayList<>();
		
		// while there are books in the check in stack
        while (!checkIn.IsEmpty()) {
            String bookName = (String) checkIn.pop();
            System.out.println(String.format("Checking in: %S", bookName.trim()));
            Book book = searchByTitle(bookName.trim());
			
			// if the book exists within the books arraylist
            if (book != null) {
				
				// if the book status is checked out, change the status to being checked in
                if (books.get(books.indexOf(book)).getStatus() == 0)
                    books.get(books.indexOf(book)).setStatus(1);
				
				// if the book status is already checked in, add it to an arraylist of books that cannot validly
				// be checked in
                else {
                    badBooks.add(books.get(books.indexOf(book)));
                    badBook++;
                }
				
			// if the book doesn't exist within the book's arraylist, create a new "bad book" and
			// add it to the arraylist of books that cannot be validly checked in
            } else {
                Book newBook = new Book();
                newBook.setName(bookName);
                badBook++;
                badBooks.add(newBook);
            }
        }
        return new Pair<>((badBook == 0), badBooks);
    }
	
	// method used to check out books
    public Pair<Boolean, ArrayList<Book>> checkOut() {
        int badBook = 0;
        ArrayList<Book> badBooks = new ArrayList<>();
		
		// while there are books in the check out stack
        while (!checkOut.IsEmpty()) {
            String bookName = (String) checkOut.pop();
            System.out.println(String.format("Checking out: %S", bookName.trim()));
            Book book = searchByTitle(bookName.trim());
			
			// if the book exists within the books arraylist
            if (book != null) {
				
				// if the book status is checked in, change the status to being checked out
                if (books.get(books.indexOf(book)).getStatus() == 1)
                    books.get(books.indexOf(book)).setStatus(0);
				
				// if the book status is already checked out, add it to an arraylist of books that cannot validly
				// be checked out
                else {
                    badBooks.add(books.get(books.indexOf(book)));
                    badBook++;
                }
				
			// if the book doesn't exist within the book's arraylist, create a new "bad book" and
			// add it to the arraylist of books that cannot be validly checked in
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

	// insertion sort method
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
	
	// binary search method
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
