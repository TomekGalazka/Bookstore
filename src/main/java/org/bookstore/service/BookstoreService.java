package org.bookstore.service;

import org.bookstore.books.Book;
import org.bookstore.clients.Client;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookstoreService implements StoreService<Book, Long, Book, Client> {


    @Override
    public void addItem(Book item, Set<Book> itemSet) {

    }

    @Override
    public void removeItem(Book itemType, Set<Book> itemSet) {

    }

    @Override
    public void editItem(Book oldItem, Book newItem, Set<Book> itemSet) {

    }

    @Override
    public void addItemCategory(Book itemCategory, Set<Book> itemCategorySet) {

    }

    @Override
    public void reserveItem(Book item, Set<Book> itemSet, Client client, Map<Long, List<Book>> reservedItems) {

    }

    @Override
    public void sellItem(Book item, Set<Book> itemSet, Client client, Map<Long, List<Book>> soldItems) {

    }

    @Override
    public void generateReport(Set<Book> itemSet, Map<Long, Book> soldItems) {

    }

    @Override
    public void addClient(Client client, Set<Client> clientSet) {

    }
}
