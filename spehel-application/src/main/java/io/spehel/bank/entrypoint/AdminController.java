package io.spehel.bank.entrypoint;

import io.spehel.bank.domain.CategoriesRepository;
import io.spehel.bank.domain.model.Category;
import io.spehel.bank.domain.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        CategoryModel existingModel = repository.findByCategory(categoriesWords.getCategory());

        if(existingModel != null) {
            List<String> newWords = Stream.concat(categoriesWords.getWords().stream(), existingModel.getWords().stream()).distinct().collect(Collectors.toList());
            existingModel.setWords(newWords);

            repository.save(existingModel);
        } else {
            List<String> words = categoriesWords.getWords();
            categoriesWords.setWords(words);

            repository.save(categoriesWords);
        }

        List<CategoryModel> all = repository.findAll();
        model.addAttribute("words", all);

        return "redirect:/admin";
    }

    @GetMapping("/add-record")
    public String getRecord(Model model) {
        CategoryModel categoriesWords = new CategoryModel();
        model.addAttribute("wordsModel", categoriesWords);
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
        Optional<CategoryModel> categoryModel = repository.findById(objectId);

        if (categoryModel.isPresent()) {
            CategoryModel words = categoryModel.get();
            model.addAttribute("wordsModel", words);
        }

        return "edit-record";
    }

    @PostMapping("/edit-record/{id}")
    public String saveEditRecord(@PathVariable("id") String objectId, Model model, CategoryModel categoriesWords) {
        Optional<CategoryModel> wordsModel = repository.findById(objectId);

        if (wordsModel.isPresent()) {
            CategoryModel words = wordsModel.get();
            words.setWords(categoriesWords.getWords());
            repository.save(words);
        }

        List<CategoryModel> all = repository.findAll();
        model.addAttribute("words", all);

        return "redirect:/admin";
    }

}
