package org.bookstore.store;

import org.bookstore.categories.Category;
import org.bookstore.clients.Client;
import org.bookstore.items.Book;
import org.bookstore.service.BookstoreService;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BookstoreTest {

    @Test
    void addBookFromUserInput_WhenValidInput_AddsBookToBookSet() {
        // Arrange
        BookstoreService bookstoreService = new BookstoreService();
        Bookstore bookstore = new Bookstore(bookstoreService);
        Scanner scanner = new Scanner("Title\nAuthor\n2022\n19.99\n5\nCategoryName\nCategoryDescription\n");

        // Act
        bookstore.addBookFromUserInput(scanner);

        // Assert
        Set<Book> bookSet = bookstore.getBookSet();
        assertEquals(1, bookSet.size());
    }

    @Test
    void removeBookFromUserInput_WhenValidInput_RemovesBookFromBookSet() {
        // Arrange
        BookstoreService bookstoreService = new BookstoreService();
        Bookstore bookstore = new Bookstore(bookstoreService);
        Book bookToRemove = new Book("Title", "Author", 2022, 19.99, 5, new Category("CategoryName", "CategoryDescription"));
        bookstore.setBookSet(new HashSet<>(Collections.singletonList(bookToRemove)));
        Scanner scanner = new Scanner("Title\nAuthor\n2022\n19.99\n5\nCategoryName\nCategoryDescription\n");

        // Act
        bookstore.removeBookFromUserInput(scanner);

        // Assert
        Set<Book> bookSet = bookstore.getBookSet();
        assertTrue(bookSet.isEmpty());
    }

    @Test
    void editBookFromUserInput_WhenValidInput_EditsBookInBookSet() {
        // Arrange
        BookstoreService bookstoreService = new BookstoreService();
        Bookstore bookstore = new Bookstore(bookstoreService);
        Book oldBook = new Book("OldTitle", "OldAuthor", 2022, 19.99, 5, new Category("OldCategory", "OldDescription"));
        bookstore.setBookSet(new HashSet<>(Collections.singletonList(oldBook)));
        Scanner scanner = new Scanner("OldTitle\nOldAuthor\n2022\n19.99\n5\nOldCategory\nOldDescription\n"
                + "NewTitle\nNewAuthor\n2023\n29.99\n10\nNewCategory\nNewDescription\n");

        // Act
        bookstore.editBookFromUserInput(scanner);

        // Assert
        Set<Book> bookSet = bookstore.getBookSet();
        Book editedBook = new Book("NewTitle", "NewAuthor", 2023, 29.99, 10, new Category("NewCategory", "NewDescription"));
        assertTrue(bookSet.contains(editedBook));
        assertFalse(bookSet.contains(oldBook));
    }

    @Test
    void reserveBookFromUserInput_WhenValidInput_ReservesBookAndUpdateReservedItems() {
        // Arrange
        BookstoreService bookstoreService = new BookstoreService();
        Bookstore bookstore = new Bookstore(bookstoreService);
        Book book = new Book("ReservedTitle", "ReservedAuthor", 2022, 19.99, 5, new Category("ReservedCategory", "ReservedDescription"));
        bookstore.setBookSet(new HashSet<>(Collections.singletonList(book)));
        Client client = new Client(1, "John", "Doe", "john@example.com");
        bookstore.setClientSet(new HashSet<>(Collections.singletonList(client)));
        Scanner scanner = new Scanner("ReservedTitle\nReservedAuthor\n2022\n19.99\n5\nReservedCategory\nReservedDescription\n");

        // Act
        bookstore.reserveBookFromUserInput(scanner);

        // Assert
        Map<Long, List<Book>> reservedItems = bookstore.getReservedItems();
        assertEquals(1, reservedItems.size());
        assertTrue(reservedItems.containsKey(client.id()));
        List<Book> clientReservation = reservedItems.get(client.id());
        assertTrue(clientReservation.contains(book));
    }

    @Test
    void sellBookFromUserInput_WhenValidInput_SellsBookAndUpdateBoughtItemsAndBookSet() {
        // Arrange
        BookstoreService bookstoreService = new BookstoreService();
        Bookstore bookstore = new Bookstore(bookstoreService);
        Book book = new Book("SoldTitle", "SoldAuthor", 2022, 19.99, 5, new Category("SoldCategory", "SoldDescription"));
        bookstore.setBookSet(new HashSet<>(Collections.singletonList(book)));
        Client client = new Client(1, "Jane", "Doe", "jane@example.com");
        bookstore.setClientSet(new HashSet<>(Collections.singletonList(client)));
        Scanner scanner = new Scanner("SoldTitle\nSoldAuthor\n2022\n19.99\n5\nSoldCategory\nSoldDescription\n");

        // Act
        bookstore.sellBookFromUserInput(scanner);

        // Assert
        Map<Long, List<Book>> boughtItems = bookstore.getBoughtItems();
        assertEquals(1, boughtItems.size());
        assertTrue(boughtItems.containsKey(client.id()));
        List<Book> clientShoppingList = boughtItems.get(client.id());
        assertTrue(clientShoppingList.contains(book));

        Set<Book> bookSet = bookstore.getBookSet();
        assertEquals(4, bookSet.iterator().next().copiesAvailable());  // 5 - 1 = 4
    }
    @Test
    void adjustBookNumber_WhenBookIsSold_DecreasesCopiesAvailableInBookSet() {
        // Arrange
        BookstoreService bookstoreService = new BookstoreService();
        Bookstore bookstore = new Bookstore(bookstoreService);
        Book book = new Book("AdjustTitle", "AdjustAuthor", 2022, 19.99, 5, new Category("AdjustCategory", "AdjustDescription"));
        bookstore.setBookSet(new HashSet<>(Collections.singletonList(book)));

        // Act
        bookstoreService.adjustBookNumber(book, bookstore.getBookSet());

        // Assert
        assertEquals(4, bookstore.getBookSet().iterator().next().copiesAvailable());  // 5 - 1 = 4
    }
}