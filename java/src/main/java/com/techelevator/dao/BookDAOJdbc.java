package com.techelevator.dao;

import com.techelevator.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAOJdbc implements BookDAO {

    private JdbcTemplate jdbcTemplate;

    public BookDAOJdbc( JdbcTemplate jdbcTemplate ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Each book has:
     * String title
     * List of authors
     * String isbn
     * List of genres
     * String description
     * Date initialPublishDate
     * String imgUrl
     * String series
     * @return List books
     */
    @Override
    public List<Book> listBooks() {
        List <Book> books = new ArrayList<>();
        String sql = "SELECT book_id, title, description, published_date, cover_art, series_id, date_created FROM book;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Book book = mapRowToBook(results);
            books.add(book);
        }
        return books;
    }

    @Override
    public Book getBook(int bookID) {
        Book foundBook = null;
        String sql = "SELECT * FROM book WHERE book_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet( sql, bookID );
        if( results.next() ) {
            foundBook = mapRowToBook(results);
        }
        return foundBook;
    }


    /*
    @JsonProperty("book_id")
    private int id;

    private String title;

    @JsonProperty("author_name")
    private List<String> authors;

    private String isbn;

    @JsonProperty("genre_name")
    private List<String> genre;

    private String description;

    @JsonProperty("published_date")
    private LocalDate initialPublishDate;

    @JsonProperty("cover_art")
    private String imgUrl;

    @JsonProperty("series")
    private String series;

    @JsonProperty("date_created")
    LocalDate dateCreated;
     */
    @Override
    public void addBook(Book book){
        List<String> genreNames = getGenreTableNames();
        List<String> seriesNames = getSeriesTableNames();
        List<String> authorNames = getAuthorTableNames();

        if (!seriesNames.contains(book.getSeries().toLowerCase() )) {
            String sql1 = "INSERT INTO series(series_name)"+
                    "VALUES(?);";
            jdbcTemplate.update(sql1,book.getSeries());
        }
        int seriesId = getSeriesId(book.getSeries());

        String sql = "INSERT INTO  book (title, description, published_date, cover_art, series_id )"+
                "VALUES ( ?, ?, ?, ?, ? );";

        jdbcTemplate.update(sql, book.getTitle(), book.getDescription(), book.getInitialPublishDate(),
                book.getImgUrl(), seriesId );

        int bookId = getBookIdByTitle(book.getTitle());

        // added loop to search through genres given
        for( String genre : book.getGenres() ) {

            // original if statement, had book.getGenre().toLowerCase() instead
            if (!genreNames.contains(genre.toLowerCase() )) {
                String addGenre = "INSERT INTO genre (genre_name)"+
                        "VALUES(?);";
                jdbcTemplate.update(addGenre, StringUtils.capitalize( genre ) );
            }

            String addBookGenre = "INSERT INTO book_genre ( book_id, genre_id ) " +
                    "VALUES( ?, ? );";
            jdbcTemplate.update( addBookGenre, bookId, getGenreId( StringUtils.capitalize(genre) ) );
        }
        //int genreId = getGenreId(book.getGenre());

        for (String author : book.getAuthors()) {
            if (!authorNames.contains(author.toLowerCase())) {
                String sql1 = "INSERT INTO author(author_name)" +
                        "VALUES(?);";
                jdbcTemplate.update(sql1, author);
            }

            String sql2 = "INSERT INTO book_author (author_id, book_id) "+
                    "VALUES (?, ?);";

            jdbcTemplate.update(sql2, getAuthorId(author), bookId);
        }
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

    private List<String> listGenresByBookID(int bookID ) {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT genre_name FROM genre " +
                "JOIN book_genre " +
                "ON genre.genre_id = book_genre.genre_id " +
                "WHERE book_genre.book_id = ?;";
       SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookID);
        while (results.next()) {
            genres.add( results.getString("genre_name") );
        }
        return genres;
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

    private List<String> getGenreTableNames(){
        List<String> genreNames = new ArrayList<>();
        String sql = "SELECT genre_name FROM genre";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()){
            genreNames.add(results.getString("genre_name").toLowerCase());
        }
        return genreNames;
    }

    private List<String> getSeriesTableNames(){
        List<String> seriesNames = new ArrayList<>();
        String sql = "SELECT series_name FROM series";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()){
            seriesNames.add(results.getString("series_name").toLowerCase());
        }
        return seriesNames;
    }

    private List<String> getAuthorTableNames(){
        List<String> authorNames = new ArrayList<>();
        String sql = "SELECT author_name FROM author";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()){
            authorNames.add(results.getString("author_name").toLowerCase());
        }
        return authorNames;
    }

    private int getGenreId(String genre_name) {
        int genreId = -1;
        String sql = "SELECT genre_id FROM genre WHERE genre_name = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, genre_name);

        if(result.next()){
            genreId = result.getInt("genre_id");
        }
        return genreId;
    }

    private int getSeriesId(String series_name) {
        int seriesId = -1;
        String sql = "SELECT series_id FROM series WHERE series_name = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, series_name);

        if(result.next()){
            seriesId = result.getInt("series_id");
        }
        return seriesId;
    }

    private int getAuthorId(String author_name){
        int authorId = -1;

        String sql1 = "SELECT author_id FROM author WHERE author_name = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql1, author_name);

        if (result.next()){
            authorId = result.getInt("author_id");
        }
        return authorId;
    }

    private int getBookIdByTitle(String title){
        int bookId = -1;

        String sql = "SELECT book_id FROM book WHERE title = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, title);

        if (result.next()){
            bookId = result.getInt("book_id");
        }
        return bookId;
    }

    private List<Book> getBooksByDate(LocalDate date){
        List<Book> newBooks = new ArrayList<>();

        String sql = "SELECT * FROM book WHERE date_created > ? ORDER BY date_created;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, date );

        while (results.next()){
            Book book = mapRowToBook(results);
            newBooks.add(book);
        }
        return newBooks;
    }

    private Book mapRowToBook(SqlRowSet rowSet) {
        Book book = new Book();

        book.setId( rowSet.getInt("book_id") );
        book.setTitle(rowSet.getString("title"));
        book.setDescription(rowSet.getString("description"));
        book.setImgUrl(rowSet.getString("cover_art"));
        book.setInitialPublishDate(rowSet.getDate("published_date").toLocalDate());
        book.setAuthors( listOfAuthorsByBookID( rowSet.getInt("book_id") ) );
        book.setGenres( listGenresByBookID( rowSet.getInt("book_id") ) );
        book.setSeries(getBookSeriesBySeriesId(rowSet.getInt("series_id")));
        book.setDateCreated(rowSet.getDate("date_created").toLocalDate());

        return book;
    }
}
