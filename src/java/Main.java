import javafx.util.Pair;
import library.Book;
import library.Library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        createLib();
        boolean closed = false;

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
                    break;


                case "q":
                    closed = true;
                    break;

                case "help":
                    break;

                default:
                    System.out.println("Please enter a stated command. ");
                    break;
            }
        }
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
                    System.out.println(book.getName() + " does not exist.");
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
                    System.out.println(book.getName() + " does not exist.");
        }
    }

    private static void search() {
        System.out.println("Do you want to search by author or title?");
        System.out.print("1 - Author\n2 - Title \n");
        String searchBy = scanner.next();
        Book book;
        ArrayList<Book> booksByAuthor;
        if (searchBy.equalsIgnoreCase("1")) {
            System.out.println("What author would you like to search for?");
            String author = scanner.next();
            booksByAuthor = library.searchByAuthor(author);
            if (booksByAuthor.size() == 0)
                System.out.println("No books were found by that author.\n");
            for (Book book1 : booksByAuthor) {
                library.printBookInfo(book1, "author");
            }
        } else if (searchBy.equalsIgnoreCase("2")) {
            System.out.println("What title would you like to search for?");
            String title = scanner.next();
            book = library.searchBytitle(title);
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
/*        The path to the file has to be exact when testing in Intellij but for the final
        version it can be relative like below*/
        //File bookList = new File("../BookList.txt");
        File bookList = new File("C:\\Users\\marcu\\Desktop\\CSC220Data\\SciFiLi\\src\\BookList.txt");
        Scanner sc = new Scanner(bookList);

        while (sc.hasNextLine()) {
            Book book = new Book();
            String bookInfo = sc.nextLine();
            List<String> bookInfoList = Arrays.asList(bookInfo.split(", "));

            if (bookInfoList.size() == 5) {
                book.setName(bookInfoList.get(0) + bookInfoList.get(1));
                book.setAuthor(bookInfoList.get(2));
                book.setStatus(Integer.parseInt(bookInfoList.get(3)));
                book.setImportance(Integer.parseInt(bookInfoList.get(4)));
                books.add(book);
            } else {
                book.setName(bookInfoList.get(0));
                book.setAuthor(bookInfoList.get(1));
                book.setStatus(Integer.parseInt(bookInfoList.get(2)));
                book.setImportance(Integer.parseInt(bookInfoList.get(3)));
                books.add(book);
            }
        }
        library.setBooks(books);

//        for (int i = 0; i < books.size(); i++) {
//            System.out.println(books.get(i).getName() + books.get(i).getStatus());
//        }

    }
}
