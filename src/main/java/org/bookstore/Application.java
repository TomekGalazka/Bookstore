package org.bookstore;


import org.bookstore.service.BookstoreService;
import org.bookstore.store.Bookstore;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        //Instantiate bookstore object and bookstoreService
        BookstoreService bookstoreService = new BookstoreService();
        Bookstore bookstore = new Bookstore(bookstoreService);

        //Instantiate scanner object
        Scanner scanner = new Scanner(System.in);
        boolean exitBookstore = false;

        //Run console program
        while (!exitBookstore) {
            System.out.println("Welcome to our Bookstore! How can we assist?");
            System.out.println("1. Add book to Bookstore");
            System.out.println("2. Remove book from Bookstore");
            System.out.println("3. Edit book in Bookstore");
            System.out.println("4. Add book category");
            System.out.println("5. Reserve a book");
            System.out.println("6. Buy book");
            System.out.println("7. Generate Bookstore report");
            System.out.println("8. Add client to Bookstore");
            System.out.println("0. Exit Bookstore");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookstore.addBookFromUserInput(scanner);
                    break;
                case 2:
                    bookstore.removeBookFromUserInput(scanner);
                    break;
                case 3:
                    bookstore.editBookFromUserInput(scanner);
                    break;
                case 4:
                    bookstore.addCategoryFromUserInput(scanner);
                    break;
                case 5:
                    bookstore.reserveBookFromUserInput(scanner);
                    break;
                case 6:
                    bookstore.sellBookFromUserInput(scanner);
                    break;
                case 7:
                    bookstore.generateReport();
                    break;
                case 8:
                    bookstore.addClientFromUserInput(scanner);
                    break;
                case 0:
                    System.out.println("Exiting Bookstore. Goodbye!");
                    exitBookstore = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}