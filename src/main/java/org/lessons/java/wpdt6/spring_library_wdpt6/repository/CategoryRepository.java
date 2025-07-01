package org.lessons.java.wpdt6.spring_library_wdpt6.repository;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
