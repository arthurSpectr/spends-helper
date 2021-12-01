package io.spehel.bank.entrypoint;

import io.spehel.bank.domain.CategoriesWordsRepository;
import io.spehel.bank.domain.entity.CategoriesWords;
import io.spehel.bank.domain.model.Category;
import org.bson.types.ObjectId;
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
    private CategoriesWordsRepository repository;

    @GetMapping
    public String getAdminPanel(Model model) {
        List<CategoriesWords> all = repository.findAll();
        model.addAttribute("words", all);
        return "admin";
    }

    @PostMapping("/add-record")
    public String addRecord(CategoriesWords categoriesWords, Model model) {
        List<String> words = categoriesWords.getWords();
        categoriesWords.setWords(words.isEmpty() ? words : List.of(words.get(0).split(" ")));
        repository.save(categoriesWords);
        List<CategoriesWords> all = repository.findAll();
        model.addAttribute("words", all);
        return "redirect:/admin";
    }

    @GetMapping("/add-record")
    public String getRecord(CategoriesWords categoriesWords, Model model) {
        model.addAttribute("categories", Category.values());
        return "add-record";
    }

    @GetMapping("/delete-record/{id}")
    public String deleteRecord(@PathVariable("id") String objectId, Model model) {
        Optional<CategoriesWords> byId = repository.findById(objectId);

        if (byId.isPresent()) {
            CategoriesWords categoriesWords = byId.get();
            repository.delete(categoriesWords);
        }

        List<CategoriesWords> all = repository.findAll();
        model.addAttribute("words", all);
        return "redirect:/admin";
    }

    @GetMapping("/edit-record/{id}")
    public String editRecord(@PathVariable("id") String objectId, Model model, CategoriesWords categoriesWords) {
        Optional<CategoriesWords> byId = repository.findById(objectId);

        if (byId.isPresent()) {
            CategoriesWords words = byId.get();
            model.addAttribute("wordsModel", words);
        }


        return "add-record";
    }

}
