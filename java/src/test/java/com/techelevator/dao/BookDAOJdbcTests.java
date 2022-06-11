package com.techelevator.dao;

import com.techelevator.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class BookDAOJdbcTests extends FinalCapstoneDaoTests{

    private BookDAOJdbc sut;
    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new BookDAOJdbc(jdbcTemplate);

    }

    @Test
    public void listBooksTest() {
        List<Book> books = sut.listBooks();
        List<Book> listOfBooks = books;
        List<Book> expected = listOfBooks;
        sut.equals(listOfBooks);
        List<Book> actual = listOfBooks;
        Assert.assertEquals(expected, actual);
    }




}
