package com.example.springBootCRUD.book;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name="book")
@Table(name="book", uniqueConstraints = { @UniqueConstraint(
        name="UK_isbn", columnNames = "isbn")
})
@NoArgsConstructor
public class Book {
    @SequenceGenerator(
            name="book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    @Id
    private Long bookId;
    private String isbn;
    private String name;
    private String author;
    private Integer publicationYear;
    private Double priceGbp;

    /**
     * The constructor to create the instance of a book object.
     * @param isbn The ISBN of the book.
     * @param name The name of the book.
     * @param author The author of the book.
     * @param publicationYear The publication year of the book.
     * @param priceGbp The price of the book in pounds.
     */
    public Book(String isbn, String name, String author, Integer publicationYear, Double priceGbp) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.publicationYear = publicationYear;
        this.priceGbp = priceGbp;
    }

    /**
     * Get ID of the book.
     * @return The ID of the book.
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * Set the ID of the book.
     * @param bookId ID of the book.
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * Get the ISBN of the book.
     * @return ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Set the ISBN of the book.
     * @param isbn ISBN of the book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Get the name of the book.
     * @return The name of the book.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the book.
     * @param name The name of the book.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the author of the book.
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the author of the book.
     * @param author The author of the book.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the publication year of the book.
     * @return The publication year of the book.
     */
    public Integer getPublicationYear() {
        return publicationYear;
    }

    /**
     * Set the publication year of the book.
     * @param publicationYear The publication year of the book.
     */
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Get the price of the book in pounds.
     * @return The price of the book in pounds.
     */
    public Double getPriceGbp() {
        return priceGbp;
    }

    /**
     * Set the price of the book in pounds.
     * @param priceGbp The price of the book in pounds.
     */
    public void setPriceGbp(double priceGbp) {
        this.priceGbp = priceGbp;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", priceGbp=" + priceGbp +
                '}';
    }
}
