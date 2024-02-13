package org.bookstore.service;

import org.bookstore.items.Book;
import org.bookstore.categories.Category;
import org.bookstore.clients.Client;

import java.util.*;

public class BookstoreService implements StoreService<Book, Long, Book, Client, Category> {


    @Override
    public Set<Book> addItem(Book item, Set<Book> itemSet) throws ItemAlreadyExistException {

        if (itemSet.contains(item)) {
            throw new ItemAlreadyExistException("Book already exist in our database " + item.toString());
        }
        itemSet.add(item);

        return itemSet;
    }

    @Override
    public Set<Book> removeItem(Book item, Set<Book> itemSet) throws ItemNotFoundException {

        if (!itemSet.contains(item)) {
            throw new ItemNotFoundException("Book does not exist in our database " + item.toString());
        }
        itemSet.remove(item);

        return itemSet;
    }

    @Override
    public Set<Book> editItem(Book oldItem, Book newItem, Set<Book> itemSet)
            throws ItemAlreadyExistException, ItemNotFoundException {

        if (!itemSet.contains(oldItem)) {
            throw new ItemNotFoundException("Old book is not present in our database: " + oldItem.toString());
        }
        if (itemSet.contains(newItem)) {
            throw new ItemAlreadyExistException("New book already exist in our database: " + newItem.toString());
        }
        itemSet.remove(oldItem);
        itemSet.add(newItem);
        return itemSet;
    }

    @Override
    public Set<Category> addItemCategory(Category itemCategory, Set<Category> itemCategorySet)
            throws CategoryAlreadyExistsException {

        if (itemCategorySet.contains(itemCategory)) {
            throw new CategoryAlreadyExistsException("Category already exist: " + itemCategory.toString());
        }
        itemCategorySet.add(itemCategory);
        return itemCategorySet;
    }

    @Override
    public Map<Long, List<Book>> reserveItem(Book item, Set<Book> itemSet,
                            Client client, Set<Client> clientSet,
                            Map<Long, List<Book>> reservedItems)
    throws ItemAlreadyExistException, ItemNotFoundException, ItemNotAvaiableException {

        //Check if book exists in our database
        if (!itemSet.contains(item)) {
            throw new ItemNotFoundException("Book does not exist in our database: " + item.toString());
        }

        //Check if client exists in our database
        if (!clientSet.contains(client)) {
            throw new ItemNotFoundException("Client does not exist in our database: " + client.toString());
        }

        //Check if item is available
        if (isNotAvailable(item, itemSet)) {
            throw new ItemNotAvaiableException("We are out of stock, book cannot be reserved: " + item.toString());
        }

        //Add book to reserved books
        long clientId = client.id();

        List<Book> clientReservation = reservedItems.computeIfAbsent(clientId, k -> new ArrayList<>());

        if (clientReservation.contains(item)) {
            throw new ItemAlreadyExistException("This client has already reserved this book: " + item.toString());
        }

        clientReservation.add(item);
        reservedItems.put(clientId, clientReservation);
        return reservedItems;
    }

    @Override
    public Map<Long, List<Book>> sellItem(Book item,
                         Set<Book> itemSet,
                         Client client,
                         Set<Client> clientSet,
                         Map<Long, List<Book>> soldItems)
            throws ItemNotFoundException, ItemNotAvaiableException {

        //Check if book exists in our database
        if (!itemSet.contains(item)) {
            throw new ItemNotFoundException("Book does not exist in our database: " + item.toString());
        }

        //Check if client exists in our database
        if (!clientSet.contains(client)) {
            throw new ItemNotFoundException("Client does not exist in our database: " + client.toString());
        }

        //Check if item is available
        if (isNotAvailable(item, itemSet)) {
            throw new ItemNotAvaiableException("We are out of stock, book cannot be reserved: " + item.toString());
        }

        //Add book to sold books
        long clientId = client.id();

        List<Book> clientShoppingList = soldItems.computeIfAbsent(clientId, k -> new ArrayList<>());
        clientShoppingList.add(item);
        soldItems.put(clientId, clientShoppingList);
        return soldItems;
    }

    boolean isNotAvailable(Book book, Set<Book> bookSet) {
        return bookSet.stream()
                .filter(b -> b.equals(book))
                .findFirst()
                .map(b -> b.copiesAvailable() <= 0)
                .orElse(false);
    }

    @Override
    public void generateReport(Set<Book> itemSet,
                               Set<Client> clientSet,
                               Map<Long, List<Book>> reservedItems,
                               Map<Long, List<Book>> soldItems) {
        System.out.println("Generating report...done.");
        System.out.println("Available books: ");
        for (Book book : itemSet ) {
            System.out.println("....Printing book data...");
            System.out.println("    -Book title: " + book.title());
            System.out.println("    -Book year of publication: " + book.yearOfPublication());
            System.out.println("    -Book price: " + book.price());
            System.out.println("    -Book copies available: " + book.copiesAvailable());
            System.out.println("    -Book category: " + book.category().categoryName());
        }
        System.out.println("Client list: ");
        for (Client client : clientSet) {
            System.out.println("....Printing client data...");
            System.out.println("    -Client name: " + client.name());
            System.out.println("    -Client surname: " + client.surname());
            System.out.println("    -Client email: " + client.email());
        }
        System.out.println("Reserved books: ");
        for (Map.Entry<Long, List<Book>> entry : reservedItems.entrySet()) {
            Optional<Client> client = clientSet.stream().filter(c -> c.id() == entry.getKey()).findFirst();
            if (client.isPresent()) {
                List<Book> books = entry.getValue();
                System.out.println("....Printing reserved books data...");
                System.out.println("    -Client ID: " + client.get().id());
                System.out.println("    -Client Name: " + client.get().name());
                System.out.println("    -Client Surame: " + client.get().surname());
                System.out.println("    -Client Email: " + client.get().email());
                System.out.println("    -Books reserved by Mr./Mrs " + client.get().surname() + ":");
                for (Book book : books) {
                    System.out.println("....Printing reserved books data...");
                    System.out.println("    -Book title: " + book.title());
                }
            }
        }
        System.out.println("Books bought: ");
        for (Map.Entry<Long, List<Book>> entry : soldItems.entrySet()) {
            Optional<Client> client = clientSet.stream().filter(c -> c.id() == entry.getKey()).findFirst();
            if (client.isPresent()) {
                List<Book> books = entry.getValue();
                System.out.println("Client ID: " + client.get().id());
                System.out.println("Client Name: " + client.get().name());
                System.out.println("Client Surame: " + client.get().surname());
                System.out.println("Client Email: " + client.get().email());
                System.out.println("Books bought by Mr./Mrs " + client.get().surname() + ":");
                for (Book book : books) {
                    System.out.println("Book title: " + book.title());
                }
            }
        }
    }

    @Override
    public Set<Client> addClient(Client client, Set<Client> clientSet) throws ItemAlreadyExistException {

        if (clientSet.contains(client)) {
            throw new ItemAlreadyExistException("Client already exist: " + client.toString());
        }
        clientSet.add(client);
        return clientSet;
    }

    public Set<Book> adjustBookNumber(Book book, Set<Book> bookSet) {
        Optional<Book> soldBookOptional = bookSet.stream()
                .filter(b -> b.equals(book))
                .findFirst();

        if (soldBookOptional.isPresent()) {
            Book soldBook = soldBookOptional.get();
            Book modifiedBook = soldBook.adjustCopiesAvailable(soldBook.copiesAvailable() - 1);

            bookSet.remove(soldBook);
            bookSet.add(modifiedBook);
        }

        return bookSet;
    }

    // Exception static classes
    public static class ItemNotFoundException extends Exception {
        public ItemNotFoundException(String message) {
            super(message);
        }
    }

    public static class ItemAlreadyExistException extends Exception {
        public ItemAlreadyExistException(String message) {
            super(message);
        }
    }

    public static class CategoryAlreadyExistsException extends Exception {
        public CategoryAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class ClientAlreadyExistsException extends Exception {
        public ClientAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class ItemNotAvaiableException extends Exception {
        public ItemNotAvaiableException(String message) {
            super(message);
        }
    }
}
