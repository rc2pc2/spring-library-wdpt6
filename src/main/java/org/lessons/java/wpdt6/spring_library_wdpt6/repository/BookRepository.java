package org.lessons.java.wpdt6.spring_library_wdpt6.repository;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    
}
