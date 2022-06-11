package com.techelevator.dao;

import com.techelevator.model.ForumTopic;
import com.techelevator.model.TopicPost;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class ForumDAOJdbc implements ForumDAO{
    private JdbcTemplate jdbcTemplate;

    public ForumDAOJdbc( JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ForumTopic getTopicByTopicId(int topicId) {
        ForumTopic forumTopic = null;
        String sql = "SELECT forum_topic.topic_id, forum_topic.topic_name" +
                ", users.username, forum_topic.topic_date\n" +
                "FROM forum_topic\n" +
                "JOIN users\n" +
                "ON forum_topic.user_id = users.user_id\n" +
                "WHERE forum_topic.topic_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet( sql, topicId );
        if( results.next() ) {
            forumTopic = mapRowToForumTopic( results );
        }
        return forumTopic;
    }

    @Override
    public List<ForumTopic> getAllTopicNames() {
        List<ForumTopic> forumTopics = new ArrayList<>();
        String sql = "SELECT forum_topic.topic_id, forum_topic.topic_name" +
                ", users.username, forum_topic.topic_date\n" +
                "FROM forum_topic\n" +
                "JOIN users\n" +
                "ON forum_topic.user_id = users.user_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet( sql );
        while( results.next() ) {
            ForumTopic forumTopic = mapRowToForumTopic( results );
            forumTopics.add( forumTopic );
        }
        return forumTopics;
    }

    @Override
    public List<TopicPost> getAllPostsByForumTopicId(int topicId) {
        List<TopicPost> topicPosts = new ArrayList<>();
        String sql = "SELECT forum_post.post_id, forum_post.topic_id " +
                ", forum_post.post, users.username, forum_post.post_date\n" +
                "FROM forum_post\n" +
                "JOIN forum_topic\n" +
                "ON forum_post.topic_id = forum_topic.topic_id\n" +
                "JOIN users\n" +
                "ON forum_post.user_id = users.user_id\n" +
                "WHERE forum_post.topic_id = ? " +
                "ORDER BY forum_post.post_date;";
        SqlRowSet results = jdbcTemplate.queryForRowSet( sql, topicId );
        while( results.next() ) {
            TopicPost topicPost = mapRowToTopicPost( results );
            topicPosts.add( topicPost );
        }
        return topicPosts;
    }

    @Override
    public boolean addTopicToForum( int userID, ForumTopic topic ) {
        for( ForumTopic forumTopic : getAllTopicNames() ) {
            if( forumTopic.getTopicName().equalsIgnoreCase( topic.getTopicName() ) ) {
                return false;
            }
        }

        String sql = "INSERT INTO forum_topic ( topic_name, user_id )\n" +
                "VALUES( ?, ? )";

       jdbcTemplate.update( sql, topic.getTopicName(), userID );

        TopicPost initPost = new TopicPost();
        initPost.setPost(topic.getInitialPost());

        sql = "SELECT topic_id FROM forum_topic WHERE topic_name like ? AND user_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, topic.getTopicName(), userID);

        if (result.next()) {
            initPost.setTopicId(result.getInt("topic_id"));
        }else {
            return false;
        }
        System.out.println(initPost.getTopicId());
        return addPostToTopic(userID, initPost);
    }

    @Override
    public boolean addPostToTopic(int userID, TopicPost post) {
//        if( post.getPost() == null || post.getPost().length() < 1 ) {
//            return false;
//        }

        String sql = "INSERT INTO forum_post ( topic_id, post, user_id )\n" +
                "VALUES ( ?, ?, ? );";

        return jdbcTemplate.update( sql, post.getTopicId(), post.getPost(), userID ) == 1;
    }

    @Override
    public boolean updatePost(int userID, TopicPost post) {
        String queryUsername = "SELECT username FROM users WHERE user_id = ?";
        SqlRowSet queryGetUsername = jdbcTemplate.queryForRowSet( queryUsername, userID );
        if( queryGetUsername.next() ) {
            if( !post.getPostCreatedByUsername().equalsIgnoreCase(
                    queryGetUsername.getString("username"))) {
                return false;
            }
        }

        String sql = "UPDATE forum_post SET post = ? WHERE user_id = ? AND post_id = ?";
        return jdbcTemplate.update( sql, post.getPost(), userID, post.getPostId() ) == 1;
    }

    @Override
    public TopicPost getTopicPostByPostID(int userID, int postID) {
        TopicPost post = null;
        String sql = "SELECT post_id, topic_id, post, user_id, post_date " +
                "FROM forum_post WHERE user_id = ? AND post_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet( sql, userID, postID );
        if( result.next() ) {
            post = mapRowToTopicPost( result );
        }
        return post;
    }

    @Override
    public boolean deletePostByPostID(int userID, int postID) {
        String sql = "DELETE FROM forum_post WHERE user_id = ? AND postID = ?";

        return jdbcTemplate.update( sql, userID, postID ) == 1 ;
    }

    private ForumTopic mapRowToForumTopic(SqlRowSet rowSet ) {
        ForumTopic forumTopic = new ForumTopic();

        forumTopic.setTopicId( rowSet.getInt( "topic_id" ) );
        forumTopic.setTopicName( rowSet.getString( "topic_name" ) );
        forumTopic.setCreatedByUsername( rowSet.getString( "username" ) );
        forumTopic.setTopicCreatedDate( rowSet.getDate( "topic_date" ).toLocalDate() );

        return forumTopic;
    }

    private TopicPost mapRowToTopicPost( SqlRowSet rowSet ) {
        TopicPost topicPost = new TopicPost();

        topicPost.setPostId( rowSet.getInt( "post_id" ) );
        topicPost.setTopicId( rowSet.getInt( "topic_id" ) );
        topicPost.setPost( rowSet.getString( "post" ) );
        topicPost.setPostCreatedByUsername( rowSet.getString( "username" ) );
//        topicPost.setPostCreatedDate( rowSet.getDate( "post_date" ).toLocalDate() );
        topicPost.setPostCreatedDate( rowSet.getTimestamp( "post_date" ).toLocalDateTime().toString().replace("T", " ") );

        return topicPost;
    }
}
