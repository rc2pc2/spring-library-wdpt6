package org.lessons.java.wpdt6.spring_library_wdpt6.repository;

import java.util.List;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    
    public List<Book> findByTitleContainingIgnoreCase(String title);

    public List<Book> findByTitle(String title);

    public List<Book> findByAuthor(String author);
}
