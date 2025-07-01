package org.lessons.java.wpdt6.spring_library_wdpt6.controller;


import org.lessons.java.wpdt6.spring_library_wdpt6.model.Book;
import org.lessons.java.wpdt6.spring_library_wdpt6.model.Category;
import org.lessons.java.wpdt6.spring_library_wdpt6.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/index";
    }

    @GetMapping("{id}")
    public String index(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "categories/show";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("category", new Category());
        return "categories/create";
    }

    @PostMapping()
    public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            return "categories/create";
        }

        // ! creare la nostra categoria su db
        categoryRepository.save(formCategory);

        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "categories/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()){
            return "categories/edit";
        }

        // ! creare la nostra categoria su db
        categoryRepository.save(formCategory);

        return "redirect:/categories";
    }
    

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        Category categoryToDelete = categoryRepository.findById(id).get();

        for (Book linkedBook : categoryToDelete.getBooks()) {
            linkedBook.getCategories().remove(categoryToDelete);
        }

        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }

}
