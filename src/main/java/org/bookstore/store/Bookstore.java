package org.bookstore.store;

import org.bookstore.books.Book;
import org.bookstore.categories.Category;
import org.bookstore.service.BookstoreService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bookstore {
    private final Set<Book> bookSet;
    private final Set<Category> categorySet;
    private final Map<Long, String> clientList;
    private final BookstoreService bookstoreService;

    public Bookstore(BookstoreService bookstoreService) {
        this.bookSet = new HashSet<>();
        this.categorySet = new HashSet<>();
        this.clientList = new HashMap<>();
        this.bookstoreService = bookstoreService;
    }

    public Set<Book> getBookList() {
        return bookSet;
    }

    public Set<Category> getCategoryList() {
        return categorySet;
    }

    public Map<Long, String> getClientList() {
        return clientList;
    }
}
