package com.techelevator.controller;

import com.techelevator.dao.ForumDAO;
import com.techelevator.dao.UserDao;
import com.techelevator.model.ForumTopic;
import com.techelevator.model.TopicPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ForumController {
    @Autowired
    ForumDAO forumDAO;
    @Autowired
    UserDao userDao;

    public ForumController( ForumDAO forumDAO, UserDao userDao ) {
        this.forumDAO = forumDAO;
        this.userDao = userDao;
    }

    @RequestMapping( path = "/forum", method = RequestMethod.GET )
    public List<ForumTopic> getAllForumTopicNames() {
        return forumDAO.getAllTopicNames();
    }

    @RequestMapping( path = "/forum/{topicID}", method = RequestMethod.GET )
    public List<TopicPost> getAllForumPostByTopic(@PathVariable int topicID ) {
        return forumDAO.getAllPostsByForumTopicId( topicID );
    }

    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping( path = "/forum/add-forum-topic", method = RequestMethod.POST )
    public boolean addForumTopic(Principal principal, @Valid @RequestBody ForumTopic forumTopic ) {
        return forumDAO.addTopicToForum(
                userDao.findIdByUsername( principal.getName() )
                , forumTopic
        );
    }

    @ResponseStatus( HttpStatus.CREATED )
    @RequestMapping( path = "/forum/topic/add-post", method = RequestMethod.POST )
    public boolean addTopicPost( Principal principal, @Valid @RequestBody TopicPost post ) {
        return forumDAO.addPostToTopic(
                userDao.findIdByUsername( principal.getName() )
                , post
        );
    }

    @RequestMapping( path = "/forum/topic/update-post", method = RequestMethod.PUT )
    public boolean updateTopicPostByTopicID( Principal principal, @RequestBody TopicPost topicPost ) {
        return forumDAO.updatePost( userDao.findIdByUsername(principal.getName()), topicPost );
    }

    @RequestMapping( path = "/forum/topic/{postID}", method = RequestMethod.GET )
    public TopicPost getTopicPost( Principal principal, @PathVariable int postID ) {
        return forumDAO.getTopicPostByPostID( userDao.findIdByUsername(principal.getName()), postID );
    }

    @RequestMapping( path = "/forum/topic/{postID}", method = RequestMethod.DELETE )
    public boolean deleteTopicPost( Principal principal, @PathVariable int postID ) {
        return forumDAO.deletePostByPostID( userDao.findIdByUsername(principal.getName()), postID );
    }

    @RequestMapping( path = "/forum/topic-name/{topicID}", method = RequestMethod.GET )
    public ForumTopic getForumTopicByTopicID( @PathVariable int topicID ) {
        return forumDAO.getTopicByTopicId( topicID );
    }

}
