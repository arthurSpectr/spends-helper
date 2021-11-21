package io.spehel.bank.entrypoint;

import io.spehel.bank.domain.CategoriesWordsRepository;
import io.spehel.bank.domain.model.CategoriesWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoriesWordsRepository repository;

    @GetMapping
    public String getAdminPanel(Model model) {
        List<CategoriesWords> all = repository.findAll();
        model.addAttribute("words", all);
        return "admin";
    }

    @PostMapping
    public String addRecord(CategoriesWords categoriesWords, Model model) {
        repository.save(categoriesWords);
        List<CategoriesWords> all = repository.findAll();
        model.addAttribute("words", all);
        return "admin";
    }

}
