package org.lessons.java.wpdt6.spring_library_wdpt6.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "borrowings")
public class Borrowing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "You must specify a borrowing date to borrow a book")
    @PastOrPresent( message = "You cannot borrow a book in the future")
    private LocalDate borrowingDate;

    // * se e' null allora ancora non e' stato ritornato
    @PastOrPresent(message = "You cannot return a book in the future")
    private LocalDate returnDate;

    @Lob
    private String notes;

    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private Book book;	


    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getBorrowingDate() {
        return this.borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
