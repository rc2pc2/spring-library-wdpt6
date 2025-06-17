package org.lessons.java.wpdt6.spring_library_wdpt6.controller;

import java.util.List;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Book;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired // * forziamo la dipendency injection
    private BookRepository bookRepository;

    @GetMapping
    public String index(Model model, @RequestParam( name = "keyword", required = false) String keyword){

        // & Se qualcuno ha cercato, allora cerchiamo
        List<Book> books;
        if ( keyword != null && !keyword.isEmpty()){
            // % implementiamo la ricerca
            books = bookRepository.findByTitleContainingIgnoreCase(keyword);
        } else {
            books = bookRepository.findAll(Sort.by("publicationDate"));
        }
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/{id}") // $ app.com/books/2
    public String show( @PathVariable("id") Integer id, Model model){
        model.addAttribute("book", bookRepository.findById(id).get());
        return "books/show";
    }
}
