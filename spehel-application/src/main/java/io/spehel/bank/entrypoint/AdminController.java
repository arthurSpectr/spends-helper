package io.spehel.bank.entrypoint;

import io.spehel.bank.domain.CategoriesRepository;
import io.spehel.bank.domain.model.CategoryModel;
import io.spehel.bank.domain.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoriesRepository repository;

    @GetMapping
    public String getAdminPanel(Model model) {
        List<CategoryModel> all = repository.findAll();
        model.addAttribute("words", all);
        return "admin";
    }

    @PostMapping("/add-record")
    public String addRecord(CategoryModel categoriesWords, Model model) {
        List<String> words = categoriesWords.getWords();
        categoriesWords.setWords(words.isEmpty() ? words : List.of(words.get(0).split(" ")));
        repository.save(categoriesWords);
        List<CategoryModel> all = repository.findAll();
        model.addAttribute("words", all);
        return "redirect:/admin";
    }

    @GetMapping("/add-record")
    public String getRecord(Model model) {
        model.addAttribute("categories", Category.values());
        return "add-record";
    }

    @GetMapping("/delete-record/{id}")
    public String deleteRecord(@PathVariable("id") String objectId, Model model) {
        Optional<CategoryModel> byId = repository.findById(objectId);

        if (byId.isPresent()) {
            CategoryModel categoriesWords = byId.get();
            repository.delete(categoriesWords);
        }

        List<CategoryModel> all = repository.findAll();
        model.addAttribute("words", all);
        return "redirect:/admin";
    }

    @GetMapping("/edit-record/{id}")
    public String editRecord(@PathVariable("id") String objectId, Model model) {
        Optional<CategoryModel> byId = repository.findById(objectId);

        if (byId.isPresent()) {
            CategoryModel words = byId.get();
            model.addAttribute("wordsModel", words);
        }


        return "add-record";
    }

    @PostMapping("/edit-record/{id}")
    public String saveEditRecord(@PathVariable("id") String objectId, Model model, CategoryModel categoriesWords) {
        Optional<CategoryModel> byId = repository.findById(objectId);

        if (byId.isPresent()) {
            CategoryModel words = byId.get();
            words.setWords(categoriesWords.getWords());
            repository.save(words);
        }

        List<CategoryModel> all = repository.findAll();
        model.addAttribute("words", all);

        return "redirect:/admin";
    }

}
