package org.bookstore.books;


import org.bookstore.categories.Category;

public record Book(String title,
                   String author,
                   int yearOfPublication,
                   double price,
                   int copiesAvailable,
                   Category category) {
}
