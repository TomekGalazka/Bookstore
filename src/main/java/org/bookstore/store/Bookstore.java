package org.bookstore.store;

import org.bookstore.items.Book;
import org.bookstore.categories.Category;
import org.bookstore.clients.Client;
import org.bookstore.service.BookstoreService;

import java.util.*;

public class Bookstore {
    private Set<Book> bookSet;
    private Set<Category> categorySet;
    private Set<Client> clientSet;
    private final BookstoreService bookstoreService;

    private Map<Long, List<Book>> reservedItems;

    private Map<Long, List<Book>> boughtItems;

    public Bookstore(BookstoreService bookstoreService) {
        this.bookSet = new HashSet<>();
        this.categorySet = new HashSet<>();
        this.clientSet = new HashSet<>();
        this.bookstoreService = bookstoreService;
        this.reservedItems = new HashMap<>();
        this.boughtItems = new HashMap<>();
    }

    public Set<Book> getBookSet() {
        return bookSet;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }

    public Set<Client> getClientSet() {
        return clientSet;
    }

    public Map<Long, List<Book>> getReservedItems() {
        return reservedItems;
    }

    public Map<Long, List<Book>> getBoughtItems() {
        return boughtItems;
    }

    public void addBookFromUserInput(Scanner scanner) {
        Book book = readBookDetails(scanner);
        try {
            setBookSet(bookstoreService.addItem(book, getBookSet()));
            System.out.println("*** Book added successfully! ***");
        }
        catch (BookstoreService.ItemAlreadyExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removeBookFromUserInput(Scanner scanner) {
        Book book = readBookDetails(scanner);
        try {
            setBookSet(bookstoreService.removeItem(book, getBookSet()));
            System.out.println("*** Book removed successfully! ***");
        } catch (BookstoreService.ItemNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void editBookFromUserInput(Scanner scanner) {
        Book oldBook = readBookDetails(scanner);
        Book newBook = readBookDetails(scanner);
        try {
            setBookSet(bookstoreService.editItem(oldBook, newBook, getBookSet()));
            System.out.println("*** Book edited successfully! ***");
        } catch (BookstoreService.ItemNotFoundException | BookstoreService.ItemAlreadyExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addCategoryFromUserInput(Scanner scanner) {
        Category category = readCategoryDetails(scanner);
        try {
            setCategorySet(bookstoreService.addItemCategory(category, getCategorySet()));
            System.out.println("*** Category added successfully! ***");
        } catch (BookstoreService.CategoryAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void reserveBookFromUserInput(Scanner scanner) {
        Book book = readBookDetails(scanner);
        Client client = readClientDetails(scanner);
        try {
            setReservedItems(bookstoreService.reserveItem(book,
                    getBookSet(),
                    client,
                    getClientSet(),
                    getReservedItems()));
            System.out.println("*** Book successfully reserved! ***");
        } catch (BookstoreService.ItemNotFoundException |
                 BookstoreService.ItemNotAvaiableException |
                 BookstoreService.ItemAlreadyExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sellBookFromUserInput(Scanner scanner) {
        Book book = readBookDetails(scanner);
        Client client = readClientDetails(scanner);
        try {
            setBoughtItems(bookstoreService.sellItem(book, getBookSet(), client, getClientSet(), getBoughtItems()));
            setBookSet(bookstoreService.adjustBookNumber(book, getBookSet()));
            System.out.println("*** Book successfully bought! ***");
        } catch (BookstoreService.ItemNotFoundException |
                 BookstoreService.ItemNotAvaiableException e) {
            System.err.println(e.getMessage());
        }
    }

    //TODO Use Logger instead of "system.out.println" and allow user to only generate report he/she is interested in.
    public void generateReport() {
        bookstoreService.generateReport(getBookSet(), getClientSet(), getReservedItems(), getBoughtItems());
    }

    public void addClientFromUserInput(Scanner scanner) {
        Client client = readClientDetails(scanner);
        try {
            setClientSet(bookstoreService.addClient(client, getClientSet()));
            System.out.println("*** New client added! ***");
        } catch (BookstoreService.ItemAlreadyExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setBookSet(Set<Book> bookSet) {
        this.bookSet = bookSet;
    }

    public void setCategorySet(Set<Category> categorySet) {
        this.categorySet = categorySet;
    }

    public void setClientSet(Set<Client> clientSet) {
        this.clientSet = clientSet;
    }

    public void setReservedItems(Map<Long, List<Book>> reservedItems) {
        this.reservedItems = reservedItems;
    }

    public void setBoughtItems(Map<Long, List<Book>> boughtItems) {
        this.boughtItems = boughtItems;
    }
    private static Book readBookDetails(Scanner scanner) {
        System.out.println("Enter book details:");

        scanner.nextLine();

        System.out.print("Title: ");
        String title = getStringInput(scanner);

        System.out.print("Author: ");
        String author = getStringInput(scanner);

        System.out.print("Year of Publication: ");
        int yearOfPublication = getIntInput(scanner);

        System.out.print("Price: ");
        double price = getDoubleInput(scanner);

        System.out.print("Copies Available: ");
        int copiesAvailable = getIntInput(scanner);

        Category category = readCategoryDetails(scanner);

        return new Book(title, author, yearOfPublication, price, copiesAvailable, category);
    }

    private static Category readCategoryDetails(Scanner scanner) {
        System.out.println("Enter category details:");
        System.out.print("Category Name: ");
        String categoryName = getStringInput(scanner);

        System.out.print("Category Description: ");
        String categoryDescription = getStringInput(scanner);

        return new Category(categoryName, categoryDescription);
    }

    private static Client readClientDetails(Scanner scanner) {
        System.out.println("Enter client details:");

        System.out.print("ID: ");
        long id = getLongInput(scanner);

        System.out.print("Name: ");
        String name = getStringInput(scanner);

        System.out.print("Surname: ");
        String surname = getStringInput(scanner);

        System.out.print("Email: ");
        String email = getStringInput(scanner);

        return new Client(id, name, surname, email);
    }
    private static String getStringInput(Scanner scanner) {
        return scanner.nextLine().trim();
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid double.");
            }
        }
    }

    private static long getLongInput(Scanner scanner) {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid long.");
            }
        }
    }

}