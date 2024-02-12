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

    public void addBook(Book book) {

        try {
            setBookSet(bookstoreService.addItem(book, getBookSet()));
        }
        catch (BookstoreService.ItemAlreadyExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removeBook(Book book) {

        try {
            setBookSet(bookstoreService.removeItem(book, getBookSet()));
        } catch (BookstoreService.ItemNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void editBook(Book oldBook, Book newBook) {
        try {
            setBookSet(bookstoreService.editItem(oldBook, newBook, getBookSet()));
        } catch (BookstoreService.ItemNotFoundException | BookstoreService.ItemAlreadyExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addCategory(Category category) {

        try {
            setCategorySet(bookstoreService.addItemCategory(category, getCategorySet()));
        } catch (BookstoreService.CategoryAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void reserveBook(Book book, Client client) {

        try {
            setReservedItems(bookstoreService.reserveItem(book,
                    getBookSet(),
                    client,
                    getClientSet(),
                    getReservedItems()));
        } catch (BookstoreService.ItemNotFoundException |
                 BookstoreService.ItemNotAvaiableException |
                 BookstoreService.ItemAlreadyExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sellBook(Book book, Client client) {

        try {
            setBoughtItems(bookstoreService.sellItem(book, getBookSet(), client, getClientSet(), getBoughtItems()));
            setBookSet(bookstoreService.adjustBookNumber(book, getBookSet()));
        } catch (BookstoreService.ItemNotFoundException |
                 BookstoreService.ItemNotAvaiableException e) {
            System.err.println(e.getMessage());
        }
    }

    //TODO Use Logger instead of "system.out.println" and allow user to only generate report he/she is interested in.
    public void generateReport() {
        bookstoreService.generateReport(getBookSet(), getClientSet(), getReservedItems(), getBoughtItems());
    }

    public void addClient(Client client) {

        try {
            setClientSet(bookstoreService.addClient(client, getClientSet()));
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
}
