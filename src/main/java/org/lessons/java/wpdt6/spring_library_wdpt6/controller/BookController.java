package org.lessons.java.wpdt6.spring_library_wdpt6.controller;

import java.util.List;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Book;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired // * forziamo la dipendency injection
    private BookRepository bookRepository;

    @GetMapping
    public String index(Model model){
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books/index";
    }
}
