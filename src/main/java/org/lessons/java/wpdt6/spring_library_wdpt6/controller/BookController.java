package org.lessons.java.wpdt6.spring_library_wdpt6.controller;

import java.util.List;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Book;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;


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
            books = bookRepository.findAll();
        }
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/{id}") // $ app.com/books/2
    public String show( @PathVariable("id") Integer id, Model model){
        model.addAttribute("book", bookRepository.findById(id).get());
        return "books/show";
    }

    @GetMapping("/create") // ! ...com/books/create
    public String create(Model model){
        model.addAttribute("book", new Book());
        return "books/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model){

        //  ! Validazione dei dati
        if (bindingResult.hasErrors()){
            return "/books/create";
        }

        // Salva il libro nel DB
        bookRepository.save(formBook);

        return "redirect:/books";
    }

    @GetMapping("/edit/{id}") // ! ...com/books/edit/11
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book", bookRepository.findById(id).get());
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model){

        //  ! Validazione dei dati
        if (bindingResult.hasErrors()){
            return "/books/edit";
        }

        // Salva il libro nel DB
        bookRepository.save(formBook);

        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model){

        // * cancella la risorsa dal DB
        bookRepository.deleteById(id);

        return "redirect:/books";
    }

}
