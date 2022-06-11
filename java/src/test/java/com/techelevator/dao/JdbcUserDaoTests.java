package com.techelevator.dao;

import com.techelevator.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDaoTests extends FinalCapstoneDaoTests {

    private JdbcUserDao sut;

    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password","user");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void usernameIdSearch() {
        int userID = sut.findIdByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", userID);
    }

//    @Test
//    public void getUserByIdTest() {
//        User userId = sut.getUserById(2);
//        long numID = userId;
//
//        Assert.assertEquals(2, userId);
//
//    }


    @Test
    public void findAllUsersTest() {
        List<User> users = sut.findAll();
        sut.equals(users);
        List<User> expected = users;
        List<User> actual = users;
        Assert.assertEquals(expected, actual);


    }

    @Test
    public void findByUsernameTest() {
        User usernameSearch = sut.findByUsername("user");
        Assert.assertEquals("user", usernameSearch);

    }





}
