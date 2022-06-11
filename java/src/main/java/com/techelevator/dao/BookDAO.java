package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDAO {

    List<Book> listBooks();

    Book getBook( int bookID );

    void addBook(Book book);

}
