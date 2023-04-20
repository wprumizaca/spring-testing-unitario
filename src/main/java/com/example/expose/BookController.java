package com.example.expose;


import com.example.services.contract.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {

    private final IBookService iBookService;

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("books", iBookService.getAll());
        return "employee-list";
    }
//    public List<Book> findAll(){
//        return iBookService.getAll();
//    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        model.addAttribute("books", iBookService.findById(id));
        return "employee-list";
    }

}
