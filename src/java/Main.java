/******************************************
 * SciFiLi
 * Created by Marcus Castille Jr.
 * and Devon Knudsen
 * CSC-220 Fall_2018 Dr. Jacques
 ****************************************/

import javafx.util.Pair;
import library.Book;
import library.Library;
import library.User;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, User> hashMap = new HashMap<>();
    private static Boolean loggedIn = false;

    public static void main(String[] args) throws IOException {

        File readme = new File("../README.txt");
        Scanner readmeFile = new Scanner(readme);
        while (readmeFile.hasNextLine()) {
            System.out.println(readmeFile.nextLine());
        }
        playSound();

        createLib();
        boolean closed = false;
        String choice;
        User admin = new User();
        admin.setAdmin(true);
        admin.setUserName("admin");
        admin.setPassWord("admin123");
        hashMap.put("admin", admin);
        while (!closed) {
            if (Library.currentUser != null) {
                hashMap.put(Library.currentUser.getUserName(), Library.currentUser);
            }
            login();
            printInstructions();
            while (!closed && loggedIn) {
                library.sortByAuthor(false);
                System.out.println(String.format("\nLogged in as %s", Library.currentUser.getUserName()));
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

                    // Log out of the system
                    case "5":
                        loggedIn = false;
                        System.out.println("You have been logged out\n");
                        break;

                    case "6":
                        addUser();
                        break;

                    // Prints out all of the books that the current user has checked out
                    case "7":
                        if (!Library.currentUser.getCoBooks().isEmpty()) {
                            System.out.println("You have the following books checked out:");
                            for (Book book : Library.currentUser.getCoBooks()) {
                                System.out.println(String.format("%s by %s", book.getName(),
                                        book.getAuthor()));
                            }
                        } else System.out.println("You have no books checked out.");
                        break;

                    case "8":
                        System.out.println("The following users exits:");
                        System.out.println("***************************");
                        for (String key : hashMap.keySet())
                            System.out.println(key);
                        break;

                    // close the library
                    case "q":
                        if (Library.currentUser.isAdmin()) {
                            closed = true;
                            break;
                        } else
                            System.out.println("Only admins can close the library");
                        break;

                    // print the instructions if the user needs help
                    case "help":
                        printInstructions();
                        break;

                    default:
                        System.out.println("Please enter a stated command. \n");
                        break;
                }
            }
        }

        // create the exit file after closing the library
        System.out.println("***The library has closed for the day***");
        library.createExitFile();
    }


    private static void login() {
        System.out.println("Welcome to the MD Library! Please login");
        System.out.print("Please enter your user name: ");
        String userName = scanner.next();
        User user = hashMap.get(userName);
        Console console = System.console();

        if (user == null) {
            System.out.println("That user does not exist");
            login();
            return;
        }

        if (user.isAdmin()) {
            char[] chars = console.readPassword("Please enter your password:");
            String passWord = new String(chars);
            String actualPassword = user.getPassWord();
            if (actualPassword != null && actualPassword.equals(passWord)) {
                loggedIn = true;
                Library.currentUser = user;
                System.out.println(String.format("Welcome %s", userName));
            } else {
                System.out.println("***Incorrect Password***");
                login();
            }
        } else {
            Library.currentUser = user;
            System.out.println(String.format("Welcome %s", userName));
            loggedIn = true;
        }

    }

    private static void addUser() {
        if (Library.currentUser.isAdmin()) {
            String newUserName = "";
            while (newUserName.trim().equals("")) {
                System.out.print("Please enter the new user's username: ");
                newUserName = scanner.next();
            }
            if (hashMap.containsKey(newUserName)) {
                System.out.println("That user already exists! Please choose a different user name");
                addUser();
                return;
            }

            String choice = "";
            while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
                System.out.println("Do you want this user to be an admin? Y or N");
                choice = scanner.next();
            }

            User newUser = new User();
            newUser.setUserName(newUserName);
            if (choice.equalsIgnoreCase("y")) {
                System.out.print("Please enter the new user's password: ");
                String password = scanner.next();
                newUser.setPassWord(password);
                newUser.setAdmin(true);
            }
            hashMap.put(newUserName, newUser);
            System.out.println(String.format("New user %s created", newUserName));
        } else {
            System.out.println("Only admins can add users");
        }
    }

    // method used to print the books in the order of their priority
    private static void priorityOrder() {
        DataStructures.PriorityQueue<Book> priorityQueue = library.sortByPriority();
        System.out.println("Books By Their Importance: ");
        System.out.println("******************************");
        while (!priorityQueue.IsEmpty()) {
            Book book = (Book) priorityQueue.removeMax();
            System.out.println(book.getName());
        }
    }

    // method used to check in books
    private static void checkIn() {
        System.out.println("What books are you checking in? (Enter titles only separated by ';' or enter '*' to check in all books)");
        String booksToCI = scanner.nextLine();
        if (booksToCI.trim().equals("")) {
            checkIn();
            return;
        }
        String[] books = booksToCI.split(";");
        if (!booksToCI.trim().equals("*")) {
            // push each book entered into the library check in stack
            for (String book : books) {
                if (!book.trim().isEmpty())
                    library.getCheckIn().push(book);
            }
        } else {
            for (Book book : Library.currentUser.getCoBooks()) {
                if (!book.getName().trim().isEmpty())
                    library.getCheckIn().push(book.getName());
            }
        }

        Pair<Boolean, ArrayList<Book>> pair = library.checkIn();

        // if the book is able to be checked in
        if (pair.getKey()) {
            if (booksToCI.trim().equals("*")) {
                System.out.println("All books checked in");
                return;
            }
            if (!pair.getValue().isEmpty())
                for (String bookName : books) {
                    System.out.println(String.format("%S has been checked in", bookName.trim()));
                }

            // if the book is not able to be checked in
        } else {
            System.out.println("The following books were not checked in. Please see the reasons below. All others were checked in");
            for (Book book : pair.getValue())

                // if the book is already checked in
                if (book.getStatus() == 1)
                    System.out.println(book.getName().trim() + " is already checked in ");

                    // if the book entered isn't valid (aka doesn't exist)
                else if (!Library.currentUser.getCoBooks().contains(book)) {
                    System.out.println(String.format("You do not currently have the book %s checked out", book.getName()));
                } else
                    System.out.println(book.getName().trim() + " does not exist.");

        }
    }

    // method used to check out books
    private static void checkOut() {
        System.out.println("What books are you checking Out? (Enter titles only separated by ';')");
        String booksToCO = scanner.nextLine();
        if (booksToCO.trim().equals("")) {
            checkOut();
            return;
        }
        String[] books = booksToCO.split(";");

        // push each book entered into the library check out stack
        for (String book : books) {
            if (!book.trim().isEmpty())
                library.getCheckOut().push(book);
        }

        Pair<Boolean, ArrayList<Book>> pair = library.checkOut();

        // if the book is in the library and able to be checked out
        if (pair.getKey()) {
            for (String book : books)
                if (!book.trim().isEmpty())
                    System.out.println(String.format("%S has been checked out", book.trim()));

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

    //The following method came from a youtube tutorial located at https://www.youtube.com/watch?v=QVrxiJyLTqU
    public static synchronized void playSound() {
        File file = new File("../greetings_exalted.wav");
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // method that prints explanations for all of the choices the user can make
    private static void printInstructions() {
        System.out.println("\n Press ");
        System.out.println("\t 0 - To search for an author or book");
        System.out.println("\t 1 - To check in books");
        System.out.println("\t 2 - To check out books");
        System.out.println("\t 3 - To sort the books");
        System.out.println("\t 4 - To print the books in order of importance");
        System.out.println("\t 5 - To log out of the program");
        System.out.println("\t 6 - To add a new user(only admins can do this)");
        System.out.println("\t 7 - To print out all books you currently have checked out");
        System.out.println("\t 8 - To print out all the users in the system.");
        System.out.println("\t help - ***To print the instructions again***");
        System.out.println("\t q - To close the library(only admins can do this) \n");
    }
}
