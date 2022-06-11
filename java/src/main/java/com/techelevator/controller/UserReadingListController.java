package com.techelevator.controller;

import com.techelevator.dao.BookDAO;
import com.techelevator.dao.UserDao;
import com.techelevator.dao.UserReadingListDAO;
import com.techelevator.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize( "isAuthenticated()" )
public class UserReadingListController {
    @Autowired
    UserDao userDao;
    @Autowired
    UserReadingListDAO userReadingListDAO;

    public UserReadingListController(UserDao userDao, UserReadingListDAO userReadingListDAO) {
        this.userDao = userDao;
        this.userReadingListDAO = userReadingListDAO;
    }

    @RequestMapping(path = "/user/list", method = RequestMethod.GET )
    public List<Book> getUserReadingList(Principal principal) {
        return userReadingListDAO.getUserReadingList(
                userDao.findIdByUsername(principal.getName())
        );
    }

    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping( path = "/user/add-book", method = RequestMethod.POST )
    public boolean addBookToUserReadingList( Principal principal, @RequestBody Book book ) {
        return userReadingListDAO.addBookToUserReadingList(
                userDao.findIdByUsername(principal.getName()), book.getId()
        );
    }

    @RequestMapping( path = "/user/delete-book", method = RequestMethod.DELETE )
    public boolean deleteBookFromUserReadingList( Principal principal, @RequestBody Book book ) {
        return userReadingListDAO.deleteBookFromUserReadingList(
                userDao.findIdByUsername(principal.getName()), book.getId()
        );
    }

    @RequestMapping(path = "/user/currently-reading", method = RequestMethod.GET)
    public List <Book> getUserCurrentlyReading(Principal principal) {
        return userReadingListDAO.getUserCurrentlyReading(
                userDao.findIdByUsername(principal.getName())
        );
    }

    @RequestMapping( path = "/user/{bookID}", method = RequestMethod.PUT )
    public boolean updateCurrentlyReadingBook( Principal principal, @PathVariable int bookID ) {
        return userReadingListDAO.updateReadingList(
                userDao.findIdByUsername(principal.getName()), bookID
        );
    }


}
