package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Book {

    @JsonProperty("book_id")
    private int id;
    private String title;
    @JsonProperty("author_name")
    private List<String> authors;
    private String isbn;
    @JsonProperty("genre_name")
    private List<String> genres;
    private String description;
    @JsonProperty("published_date")
    private LocalDate initialPublishDate;
    @JsonProperty("cover_art")
    private String imgUrl;
    @JsonProperty("series")
    private String series;
    @JsonProperty("date_created")
    LocalDate dateCreated;


    public Book() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<String> getGenres() {
        return this.genres;
    }

    public void setGenres(List<String> genre) {
        this.genres = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getInitialPublishDate() {
        return initialPublishDate;
    }

    public void setInitialPublishDate(LocalDate initialPublishDate) {
        this.initialPublishDate = initialPublishDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return title;
    }
}
