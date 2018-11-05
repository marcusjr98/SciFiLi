import javafx.util.Pair;
import library.Book;
import library.Library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        createLib();
        boolean closed = false;
        String choice;
        printInstructions();
        while (!closed) {
            library.sortByAuthor(false);
            System.out.println("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
				
				// search by author or title
                case "0":
                    search();
                    break;
				
				// check in books
                case "1":
                    checkIn();
                    break;
				
				// check out books
                case "2":
                    checkOut();
                    break;
				
				// sort by either title or author
                case "3":
                    sort();
                    break;
				
				// print out the books in order of priority
                case "4":
                    priorityOrder();
                    break;
				
				// close the library
                case "q":
                    closed = true;
                    break;
				
				// print the instructions if the user needs help
                case "help":
                    printInstructions();
                    break;
				
                default:
                    System.out.println("Please enter a stated command. ");
                    break;
            }
        }
		
		// create the exit file after closing the library
        System.out.println("***The library has closed for the day***");
        library.createExitFile();
    }
	
	// method used to print the books in the order of their priority
    private static void priorityOrder() {
        DataStructures.PriorityQueue<Book> priorityQueue = library.sortByPriority();
        System.out.println("Books By Their Importance: ");
        while (!priorityQueue.IsEmpty()) {
            Book book = (Book) priorityQueue.removeMax();
            System.out.println(book.getName());
        }
    }

	// method used to check in books
    private static void checkIn() {
        System.out.println("What books are you checking in? (Enter titles only separated by ';')");
        String booksToCI = scanner.nextLine();
        String[] books = booksToCI.split(";");
		
		// push each book entered into the library check in stack
        for (String book : books) {
            library.getCheckIn().push(book);
        }
		
        Pair<Boolean, ArrayList<Book>> pair = library.checkIn();
		
		// if the book is able to be checked in
        if (pair.getKey()) {
            for (String bookName : books)
                System.out.println(String.format("%S has been checked in", bookName.trim()));

		// if the book is not able to be checked in
        } else {
            System.out.println("The following books were not checked in. Please see the reasons below. All others were checked in");
            for (Book book : pair.getValue())
				
				// if the book is already checked in
                if (book.getStatus() == 1)
                    System.out.println(book.getName().trim() + " is already checked in ");

				// if the book entered isn't valid (aka doesn't exist)
                else
                    System.out.println(book.getName().trim() + " does not exist.");
        }
    }

	// method used to check out books
    private static void checkOut() {
        System.out.println("What books are you checking Out? (Enter titles only separated by ';')");
        String booksToCO = scanner.nextLine();
        String[] books = booksToCO.split(";");
		
		// push each book entered into the library check out stack
        for (String book : books) {
            library.getCheckOut().push(book);
        }
		
        Pair<Boolean, ArrayList<Book>> pair = library.checkOut();
		
		// if the book is in the library and able to be checked out
        if (pair.getKey()) {
            for (String bookName : books)
                System.out.println(String.format("%S has been checked out", bookName.trim()));
		
		// if the book is not able to be checked out
        } else {
            System.out.println("The following books were not checked out. Please see the reasons below. All others were checked out");
            for (Book book : pair.getValue())
				
				// if the book is already checked out
                if (book.getStatus() == 0)
                    System.out.println(book.getName().trim() + " is already checked out ");

				// if the book isn't valid (aka doesn't exist)
                else
                    System.out.println(book.getName().trim() + " does not exist.");
        }
    }

	// method used to search for books based on the author or title
    private static void search() {
        System.out.println("Do you want to search by author or title?");
        System.out.print("1 - Author\n2 - Title \n");
        String searchBy = scanner.nextLine();
        Book book;
        ArrayList<Book> booksByAuthor;
		
		// if the user wants to search by author
        if (searchBy.equalsIgnoreCase("1")) {
            System.out.println("What author would you like to search for?");
            String author = scanner.nextLine();
            booksByAuthor = library.searchByAuthor(author);
			
			// if no books by that author are found
            if (booksByAuthor.size() == 0)
                System.out.println(String.format("No books were found by the author: %s\n", author));

            
			// if atleast one book by that author is found
			else {
                System.out.println(String.format("Information on books by %S:", author));
            }
			
			// print all books for that particular author
            for (Book book1 : booksByAuthor) {
                library.printBookInfo(book1, "author");
            }
			
		// if the user wants to search by title
        } else if (searchBy.equalsIgnoreCase("2")) {
            System.out.println("What title would you like to search for?");
            String title = scanner.nextLine();
            book = library.searchByTitle(title);
			
			// if the book is found, print the info
            if (book != null)
                library.printBookInfo(book, "title");
			
			// if the book isn't found
            else
                System.out.println(String.format("The book %S does not exist. Please try searching again.", title));
        
		// an error catch if the user doesn't enter a valid input
		} else {
            System.out.println("Please choose what to search by");
            search();
        }

    }

	// method used to sort the books based on either author or title
    private static void sort() {
        System.out.println("Do you want to sort by author or title?");
        System.out.print("1 - Author\n2 - Title \n");
        String searchBy = scanner.next();
		
		// if the user chooses to sort by author
        if (searchBy.equalsIgnoreCase("1"))
            library.sortByAuthor(true);
		
		// if the user chooses to sort by title 
        else if (searchBy.equalsIgnoreCase("2"))
            library.sortByName(true);
		
		// an error catch if the user doesn't enter a valid input
        else {
            System.out.println("Please choose what to sort by");
            sort();
        }
    }

	// method used to create the library object and fill it with book objects
    private static void createLib() throws FileNotFoundException {
        ArrayList<Book> books = new ArrayList<>();
        File bookList = new File("../BookList.txt");
        Scanner sc = new Scanner(bookList);
		
		// while there is another line (another book) in the file
        while (sc.hasNextLine()) {
            Book book = new Book();
            String bookInfo = sc.nextLine();
            List<String> bookInfoList = Arrays.asList(bookInfo.split(", "));
			
			// if there is not a comma within the middle of the book title
            if (bookInfoList.size() == 4) {
                book.setName(bookInfoList.get(0));
                book.setAuthor(bookInfoList.get(1));
                book.setStatus(Integer.parseInt(bookInfoList.get(2)));
                book.setImportance(Integer.parseInt(bookInfoList.get(3)));
                books.add(book);
				
			// if there is a comma within the middle of the book title
            } else {
                book.setName(bookInfoList.get(0) + ", " + bookInfoList.get(1));
                book.setAuthor(bookInfoList.get(2));
                book.setStatus(Integer.parseInt(bookInfoList.get(3)));
                book.setImportance(Integer.parseInt(bookInfoList.get(4)));
                books.add(book);
            }
        }
		
		// set the library classes array of book objects equal to the array of book data just created
        library.setBooks(books);
    }
	
	// method that prints explanations for all of the choices the user can make
    private static void printInstructions() {
        System.out.println("\n Press ");
        System.out.println("\t 0 - To search for an author or book");
        System.out.println("\t 1 - To check in books");
        System.out.println("\t 2 - To check out books");
        System.out.println("\t 3 - To sort the books");
        System.out.println("\t 4 - To print the books in order of importance");
        System.out.println("\t help - ***To print the instructions again***");
        System.out.println("\t q - To quit the application. ");
    }
}
