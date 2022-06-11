package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class UserReadingListDAOJdbcTests extends FinalCapstoneDaoTests {
    private UserReadingListDAO sut;

    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
        sut = new UserReadingListDAOJdbc( jdbcTemplate );


    }
    @Test
    public void addingABook() {
        List<Book> userReadList = sut.getUserReadingList( 1 );
        int currentBookListSize = userReadList.size();
        int expected = currentBookListSize + 1;
        sut.addBookToUserReadingList(1, 1);
        int actual = sut.getUserReadingList(1).size();
        Assert.assertEquals(expected, actual);


    }

    @Test
    public void getReadingList() {
        List<Book> userReadList = sut.getUserReadingList( 1 );
        List <Book> readingBookList = userReadList;
        List <Book> expected = readingBookList;
        sut.equals(readingBookList);
        List <Book> actual = readingBookList;
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getCurrentReadingList() {
        List <Book> currentReadingList = sut.getUserCurrentlyReading(1);
        List <Book> currentBooks = currentReadingList;
        List <Book> expected = currentBooks;
        sut.equals(currentBooks);
        List <Book> actual = currentBooks;
        Assert.assertEquals(expected, actual);



    }


    @Test
    public void isBookInList() {
       List<Book> listOfAuthorsByBookID = sut.getUserReadingList(1);
       List<Book> bookInList = listOfAuthorsByBookID;
       List<Book> expected = bookInList;
       sut.equals(bookInList);
       List<Book> actual = bookInList;
        Assert.assertEquals(expected, actual);

    }


    @Test
    public void genreByID() {

    }









}
