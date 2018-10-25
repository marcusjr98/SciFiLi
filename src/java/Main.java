import library.Book;
import library.Library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();

    public static void main(String[] args) throws FileNotFoundException {
        createLib();

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

/*
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i).getAuthor());
        }*/

    }
}
