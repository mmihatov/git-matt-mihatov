package com.techelevator.dao;

import com.techelevator.model.ForumTopic;
import com.techelevator.model.TopicPost;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ForumDAOJdbcTests extends FinalCapstoneDaoTests {

    private ForumDAOJdbc sut;


    @Before
    public void setup() {
        DataSource dataSource = this.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new ForumDAOJdbc(jdbcTemplate);

    }


    @Test
    public void getTopicByTopicIdTest() {
        ForumTopic topicID = sut.getTopicByTopicId(1);
        Assert.assertEquals(1, topicID);

    }

    @Test
    public void getAllPostsByForumTopicIdTest() {
        List<TopicPost> topicPosts = sut.getAllPostsByForumTopicId(1);
        List<TopicPost> topicIDSearch = topicPosts;
        List<TopicPost> expected = topicIDSearch;
        sut.equals(topicIDSearch);
        List<TopicPost> actual = topicIDSearch;
        Assert.assertEquals(expected, actual);


    }

//    @Test
//    public void forumTopicAdd() {
//        List<ForumTopic> currentForumTopics = sut.getAllTopicNames();
//        int allForumPosts = currentForumTopics.size();
//        int expected = allForumPosts + 1;
//        sut.getAllTopicNames();
//        String topicTitle = "Better: Tolkien or Martin?";
//        boolean actual = sut.addTopicToForum(1, );
//
//
//    }

    @Test
    public void getTopicPostByIDTest() {
        TopicPost post = null;
        sut.equals(null);
        TopicPost expected = null;
        TopicPost actual = sut.getTopicPostByPostID(1, 2);
        Assert.assertEquals(null, actual);


    }





}
