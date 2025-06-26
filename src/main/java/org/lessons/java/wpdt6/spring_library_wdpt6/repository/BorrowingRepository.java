package org.lessons.java.wpdt6.spring_library_wdpt6.repository;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {
}
