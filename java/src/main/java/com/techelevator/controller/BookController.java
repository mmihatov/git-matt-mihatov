package com.techelevator.controller;

import com.techelevator.dao.BookDAO;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
//@PreAuthorize( "isAuthenticated()" ) //works, uncomment when ready
public class BookController {
    @Autowired
    UserDao userDao;
    @Autowired
    BookDAO bookDAO;

    public BookController( UserDao userDao, BookDAO bookDAO ) {
        this.userDao = userDao;
        this.bookDAO = bookDAO;
    }

    @PreAuthorize( "isAuthenticated()" )
    @RequestMapping( path = "/books/{id}", method = RequestMethod.GET )
    public Book getBookByID(@PathVariable int id ) {
        return bookDAO.getBook( id );
    }

    @RequestMapping( path= "/list", method = RequestMethod.GET )
    public List<Book> getListOfAllBooks() {
        return bookDAO.listBooks();
    }

   @PreAuthorize("hasRole('ADMIN')") //restricts to admin, remove comment when ready
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping( path= "/add-book", method = RequestMethod.POST )
    public void addBook(@RequestBody Book book) {
        this.bookDAO.addBook(book);
    }
}