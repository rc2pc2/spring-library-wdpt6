package org.lessons.java.wpdt6.spring_library_wdpt6.controller;

import org.lessons.java.wpdt6.spring_library_wdpt6.model.Borrowing;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
    
    @Autowired
    private BorrowingRepository borrowingRepository;


    @GetMapping
    public String index(Model model){
        model.addAttribute("borrowings", borrowingRepository.findAll());
        return "borrowings/index";
    }
    // index
    // show

    @PostMapping
    public String store( @Valid @ModelAttribute("borrowing") Borrowing borrowingForm, BindingResult bindingResult, Model model){

        // se ci sono errori
        if (bindingResult.hasErrors()){
            model.addAttribute("borrowing", borrowingForm);
            return "borrowings/create";
        }

        // * se non ci sono errori allora creo il prestito
        borrowingRepository.save(borrowingForm);

        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        borrowingRepository.deleteById(id);
        return "redirect:/books";
    }
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("borrowing", borrowingRepository.findById(id).get());
        return "borrowings/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,  @Valid @ModelAttribute("borrowing") Borrowing borrowingForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("borrowing", borrowingForm);
            return "borrowings/edit";
        }

        borrowingRepository.save(borrowingForm);
        return "redirect:/books";
    }

}
