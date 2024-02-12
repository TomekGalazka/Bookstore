package org.bookstore.items;


import org.bookstore.categories.Category;

public record Book(String title,
                   String author,
                   int yearOfPublication,
                   double price,
                   int copiesAvailable,
                   Category category) {

    // Additional constructor to create a modified copy of the record
    public Book adjustCopiesAvailable(int newCopiesAvailable) {
        return new Book(title, author, yearOfPublication, price, newCopiesAvailable, category);
    }
}
