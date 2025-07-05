package org.lessons.java.wpdt6.spring_library_wdpt6.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books") 
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title must not be null, blank or empty")
    @Size(min = 1, message = "Title must have at least one characters")
    private String title;

    @NotBlank(message = "Author must not be null, blank or empty")
    @Size(min = 2, message = "Author must have at least two characters")
    private String author;

    @Column(name = "isbn_code")
    @NotNull(message = "ISBN code must not be null, blank or empty")
    @Size( min = 13, max = 13, message = "The ISBN code must be made of 13 characters")
    private String isbn;
    
    @Lob
    @NotBlank(message = "Image URL must not be null, blank or empty")
    private String imageUrl;
    
    @NotBlank(message = "Publisher must not be null, blank or empty")
    private String publisher;

    @Lob
    @Size(max = 2000, message = "Synopsis cannot be longer thatn 2000 characters")
    private String synopsis;
    
    @NotNull(message = "Publication date cannot be null")
    @PastOrPresent(message = "Publication date must be in the present or in the past")
    private LocalDate publicationDate;
    
    @NotNull(message = "Number of copies cannot be null")
    @PositiveOrZero(message = "Number of copies must be at least zero")
    private Integer numberOfCopies;

    @Formula("(SELECT books.number_of_copies - count(b.id) "
    + "FROM books "
    + "LEFT OUTER JOIN `borrowings` b "
    + "ON books.id = b.book_id " 
    + "AND b.return_date IS NULL "
    + "WHERE books.id = id)")
    private Integer availableCopies;

    public Integer getAvailableCopies() {
        return this.availableCopies;
    }

    public boolean isAvailable(){
        return this.availableCopies > 0;
    }

    // ! tipo di relazione
    @OneToMany( mappedBy = "book")
    private List<Borrowing> borrowings;

    @ManyToMany
    @JoinTable(
      name = "book_category",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }


    public List<Borrowing> getBorrowings() {
        return this.borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }


    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDate getPublicationDate() {
        return this.publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfCopies() {
        return this.numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }


    @Override
    public String toString() {
        return this.title;
    }
}
