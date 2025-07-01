package org.lessons.java.wpdt6.spring_library_wdpt6.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Book;
import org.lessons.java.wpdt6.spring_library_wdpt6.model.Borrowing;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.BookRepository;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.BorrowingRepository;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired // * forziamo la dipendency injection
    private BookRepository bookRepository;

    @Autowired // * forziamo la dipendency injection
    private BorrowingRepository borrowingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        model.addAttribute("categories", categoryRepository.findAll());
        return "books/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model){

        //  ! Validazione dei dati
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryRepository.findAll());
            return "/books/create";
        }

        // Salva il libro nel DB
        bookRepository.save(formBook);

        return "redirect:/books";
    }

    @GetMapping("/edit/{id}") // ! ...com/books/edit/11
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book", bookRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());

        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model){

        //  ! Validazione dei dati
        if (bindingResult.hasErrors()){
            model.addAttribute("categories", categoryRepository.findAll());
            return "/books/edit";
        }

        // Salva il libro nel DB
        bookRepository.save(formBook);

        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model){

        Book bookToDelete = bookRepository.findById(id).get();

        // ! Per ogni prestito in cui ho prestato questo libro che sto per cancellare
        // * rimuovi ogni traccia di tale prestito
        for (Borrowing borrowing : bookToDelete.getBorrowings()) {
            borrowingRepository.delete(borrowing);
        }
        
        // ! e solo in seguito cancellalo
        // * cancella la risorsa dal DB
        bookRepository.delete(bookToDelete);


        return "redirect:/books";
    }


    @GetMapping("/{id}/borrow") // ! ...com/books/create
    public String borrow(@PathVariable("id") Integer id, Model model){

        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no book with id " + id + ", so you cannot borrow it!");
        }

        model.addAttribute("book", bookOptional.get());
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(bookOptional.get());
        borrowing.setBorrowingDate(LocalDate.now());
        model.addAttribute("borrowing", borrowing);

        return "borrowings/create";
    }


}
