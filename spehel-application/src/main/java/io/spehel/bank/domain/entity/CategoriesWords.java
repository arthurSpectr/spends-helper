package io.spehel.bank.domain.entity;

import io.spehel.bank.domain.model.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class CategoriesWords {

    @Id
    public String id;

    private Category category;
    private List<String> words;

    @PersistenceConstructor
    public CategoriesWords(String id, Category category, List<String> words) {
        this.id = id;
        this.category = category;
        this.words = words;
    }

    public CategoriesWords() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
