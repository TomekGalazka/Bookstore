package org.bookstore.store;

import org.bookstore.books.Book;
import org.bookstore.categories.Category;
import org.bookstore.clients.Client;
import org.bookstore.service.BookstoreService;

import java.util.*;

public class Bookstore {
    private Set<Book> bookSet;
    private Set<Category> categorySet;
    private Set<Client> clientSet;
    private BookstoreService bookstoreService;

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

    public BookstoreService getBookstoreService() {
        return bookstoreService;
    }

    public Map<Long, List<Book>> getReservedItems() {
        return reservedItems;
    }

    public Map<Long, List<Book>> getBoughtItems() {
        return boughtItems;
    }

}
