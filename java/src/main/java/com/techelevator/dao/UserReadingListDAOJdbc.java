package com.techelevator.dao;

import com.techelevator.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserReadingListDAOJdbc implements UserReadingListDAO {
    private JdbcTemplate jdbcTemplate;

    public UserReadingListDAOJdbc (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    };

    @Override
    public List<Book> getUserReadingList( int userID ) {
        List<Book> usersReadingList = new ArrayList<>();
        String sql = "SELECT book.book_id, book.title, book.description\n" +
                ", book.published_date, book.cover_art, book.series_id\n" +
                "FROM users\n" +
                "JOIN user_reading_list\n" +
                "ON users.user_id = user_reading_list.user_id\n" +
                "JOIN reading_list\n" +
                "ON user_reading_list.list_id = reading_list.list_id\n" +
                "JOIN book\n" +
                "ON reading_list.book_id = book.book_id\n" +
                "WHERE users.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet( sql, userID );
        while( results.next() ) {
            Book book = mapRowToBook(results);
            usersReadingList.add(book);
        }
        return usersReadingList;
    }

    @Override
    public boolean addBookToUserReadingList(int userID, int bookID) {
        List<Book> userReadList = getUserReadingList( userID );
        for( Book book : userReadList ) {
            if( book.getId() == bookID ) {
                return false;
            }
        }

        String sql = "INSERT INTO reading_list ( list_id, book_id )\n" +
                "VALUES ( ( SELECT list_id FROM user_reading_list WHERE user_id = ? ), ? );";

        return jdbcTemplate.update( sql, userID, bookID ) == 1;
    }

    @Override
    public boolean deleteBookFromUserReadingList(int userID, int bookID) {
        List<Book> userReadList = getUserReadingList( userID );
        int foundBook = 0;

        for( Book book : userReadList ) {
            if( book.getId() == bookID ) {
                foundBook++;
            }
        }

        if( foundBook < 1 ) {
            return false;
        }

        String sql = "DELETE FROM reading_list \n" +
                "WHERE list_id = ( SELECT list_id FROM user_reading_list WHERE user_id = ? )\n" +
                "\tAND book_id = ?;";

        return jdbcTemplate.update( sql, userID, bookID ) == 1;
    }

    @Override
    public boolean updateReadingList(int userID, int bookID) {
        boolean isReading;

        String findRead = "SELECT is_being_read FROM reading_list WHERE book_id = ? " +
                "AND list_id = ( SELECT list_id FROM user_reading_list WHERE user_id = ?)";
        SqlRowSet findReadBoolean = jdbcTemplate.queryForRowSet( findRead, bookID, userID );
        if( findReadBoolean.next() ) {
            isReading = !findReadBoolean.getBoolean("is_being_read");
        }
        else {
            return false;
        }

        String sql = "UPDATE reading_list SET is_being_read = ? WHERE book_id = ? " +
                "AND list_id = ( SELECT list_id FROM user_reading_list WHERE user_id = ?)";
        return jdbcTemplate.update( sql, isReading, bookID, userID ) == 1;
    }

    @Override
    public List<Book> getUserCurrentlyReading(int userID) {
        List <Book> currentReadingList = new ArrayList<>();
        String sql = "SELECT book.book_id, book.title, book.description\n" +
                ", book.published_date, book.cover_art, book.series_id\n" +
                "FROM users\n" +
                "JOIN user_reading_list\n" +
                "ON users.user_id = user_reading_list.user_id\n" +
                "JOIN reading_list ON reading_list.list_id = user_reading_list.list_id\n" +
                "JOIN book ON reading_list.book_id = book.book_id\n" +
                "WHERE users.user_id = ? AND reading_list.is_being_read = true;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userID);
        while( results.next() ) {
            Book book = mapRowToBook(results);
            currentReadingList.add(book);
        }
        return currentReadingList;
    }

    @Override
    public boolean isBookInList(int bookId) {
        return false;
    }

    private List <String> listOfAuthorsByBookID( int bookID ) {
        List <String> authors = new ArrayList<>();
        String sql = "SELECT author_name FROM author "
                +"JOIN book_author ON author.author_id = book_author.author_id "
                +"WHERE book_id = ( SELECT book_id FROM book WHERE book_id = ? );";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookID);
        while(results.next()) {
            String author = results.getString("author_name");
            authors.add(author);
        }
        return authors;
    }

    private List<String> listOfGenresByBookID( int bookID ) {
        List<String> genres = new ArrayList<>();

        String sql = "SELECT genre_name FROM genre "
                + "JOIN book_genre ON genre.genre_id = book_genre.genre_id " +
                "WHERE book_id = (SELECT book_id FROM book WHERE book_id = ?)";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookID);
        while(results.next()) {
            String genre = results.getString("genre_name");
            genres.add(genre);
        }

        return genres;
    }



    private String getGenreByBookID(int bookID ) {
        String genre = "";
        String sql = "SELECT genre_name FROM genre " +
                "WHERE genre_id = (SELECT genre_id FROM book_genre WHERE book_id = ? );";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookID);
        if (results.next()) {
            genre = results.getString("genre_name");
        }
        return genre;
    }

    private String getBookSeriesBySeriesId(int seriesID){
        String series = "";
        String sql = "SELECT series_name FROM series WHERE series_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, seriesID);

        if(results.next()){
            series = results.getString("series_name");
        }
        return series;
    }

    private Book mapRowToBook( SqlRowSet rowSet ) {
        Book book = new Book();

        book.setId( rowSet.getInt("book_id") );
        book.setTitle(rowSet.getString("title"));
        book.setDescription(rowSet.getString("description"));
        book.setImgUrl(rowSet.getString("cover_art"));
        book.setInitialPublishDate(rowSet.getDate("published_date").toLocalDate());
        book.setAuthors( listOfAuthorsByBookID( rowSet.getInt("book_id") ) );
        book.setGenres( listOfGenresByBookID( rowSet.getInt("book_id") ) );
        book.setSeries( getBookSeriesBySeriesId(rowSet.getInt("series_id") ) );

        return book;
    }




}
