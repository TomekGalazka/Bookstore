package org.bookstore.books;

public record Book(String title, String author, int yearOfPublication, double price, int copiesAvailable) {
}
