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

        printInstructions();
        String choice;
        while (!closed) {
            library.sortByAuthor(false);
            System.out.println("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "0":
                    search();
                    break;

                case "1":
                    checkIn();
                    break;

                case "2":
                    checkOut();
                    break;

                case "3":
                    sort();
                    break;

                case "4":
                    DataStructures.PriorityQueue<Book> priorityQueue = library.sortByPriority();
                    while (!priorityQueue.IsEmpty()) {
                        Book book1 = (Book) priorityQueue.removeMax();
                        System.out.println(book1.getName());
                    }
                    break;


                case "q":
                    closed = true;
                    break;

                case "help":
                    printInstructions();
                    break;

                default:
                    System.out.println("Please enter a stated command. ");
                    break;
            }
        }
        library.createExitFile();
    }

    private static void checkIn() {
        System.out.println("What books are you checking in? (Enter titles only separated by ';')");
        String booksToCI = scanner.nextLine();
        String[] books = booksToCI.split(";");
        for (String book : books) {
            library.getCheckIn().push(book);
        }
        Pair<Boolean, ArrayList<Book>> pair = library.checkIn();
        if (pair.getKey()) {
            for (String bookName : books)
                System.out.println(String.format("%S has been checked in", bookName));
        } else {
            System.out.println("The following books were not checked in. Please see the reasons below. All others were checked in");
            for (Book book : pair.getValue())
                if (book.getStatus() == 1)
                    System.out.println(book.getName() + " is already checked in ");
                else
                    System.out.println(book.getName().toUpperCase() + " does not exist.");
        }
    }

    private static void checkOut() {
        System.out.println("What books are you checking Out? (Enter titles only separated by ';')");
        String booksToCO = scanner.nextLine();
        String[] books = booksToCO.split(";");
        for (String book : books) {
            library.getCheckOut().push(book);
        }
        Pair<Boolean, ArrayList<Book>> pair = library.checkOut();
        if (pair.getKey()) {
            for (String bookName : books)
                System.out.println(String.format("%S has been checked out", bookName));
        } else {
            System.out.println("The following books were not checked out. Please see the reasons below. All others were checked out");
            for (Book book : pair.getValue())
                if (book.getStatus() == 0)
                    System.out.println(book.getName() + " is already checked out ");
                else
                    System.out.println(book.getName().toUpperCase() + " does not exist.");
        }
    }

    private static void search() {
        System.out.println("Do you want to search by author or title?");
        System.out.print("1 - Author\n2 - Title \n");
        String searchBy = scanner.nextLine();
        Book book;
        ArrayList<Book> booksByAuthor;
        if (searchBy.equalsIgnoreCase("1")) {
            System.out.println("What author would you like to search for?");
            String author = scanner.nextLine();
            booksByAuthor = library.searchByAuthor(author);
            if (booksByAuthor.size() == 0)
                System.out.println("No books were found by that author.\n");
            else {
                System.out.println(String.format("Information on books by %S:", author));
            }
            for (Book book1 : booksByAuthor) {
                library.printBookInfo(book1, "author");
            }
        } else if (searchBy.equalsIgnoreCase("2")) {
            System.out.println("What title would you like to search for?");
            String title = scanner.nextLine();


            book = library.searchByTitle(title);
            if (book != null)
                library.printBookInfo(book, "title");
            else
                System.out.println(String.format("The book %S does not exist. Please try searching again.", title));
        } else {
            System.out.println("Please choose what to search by");
            search();
        }

    }

    private static void sort() {
        System.out.println("Do you want to sort by author or title?");
        System.out.print("1 - Author\n2 - Title \n");
        String searchBy = scanner.next();
        if (searchBy.equalsIgnoreCase("1"))
            library.sortByAuthor(true);
        else if (searchBy.equalsIgnoreCase("2"))
            library.sortByName(true);
        else {
            System.out.println("Please choose what to sort by");
            sort();
        }
    }


    private static void createLib() throws FileNotFoundException {
        ArrayList<Book> books = new ArrayList<>();
        File bookList = new File("../BookList.txt");
        Scanner sc = new Scanner(bookList);

        while (sc.hasNextLine()) {
            Book book = new Book();
            String bookInfo = sc.nextLine();
            List<String> bookInfoList = Arrays.asList(bookInfo.split(", "));

            if (bookInfoList.size() == 4) {
                book.setName(bookInfoList.get(0));
                book.setAuthor(bookInfoList.get(1));
                book.setStatus(Integer.parseInt(bookInfoList.get(2)));
                book.setImportance(Integer.parseInt(bookInfoList.get(3)));
                books.add(book);
            } else {
                book.setName(bookInfoList.get(0) + ", " + bookInfoList.get(1));
                book.setAuthor(bookInfoList.get(2));
                book.setStatus(Integer.parseInt(bookInfoList.get(3)));
                book.setImportance(Integer.parseInt(bookInfoList.get(4)));
                books.add(book);
            }
        }
        library.setBooks(books);


    }

    private static void printInstructions() {
        System.out.println("\n Press ");
        System.out.println("\t 0 - To search for an author or book");
        System.out.println("\t 1 - To check in books");
        System.out.println("\t 2 - To check out books");
        System.out.println("\t 3 - To sort the books");
        System.out.println("\t help - To print the instructions again.");
        System.out.println("\t q - To quit the application. ");
    }
}
