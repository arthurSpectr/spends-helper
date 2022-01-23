package io.spehel.bank.domain.model;

import io.spehel.redis.domain.RedisCategoryModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "categorymodel")
public class CategoryModel {

    @Id
    public String id;

    private Category category;

    @TextIndexed
    private List<String> words;

    @PersistenceConstructor
    public CategoryModel(String id, Category category, List<String> words) {
        this.id = id;
        this.category = category;
        this.words = words;
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

    public String getPrettyWords() {
        return String.join(" ", words);
    }

    public RedisCategoryModel toDTO() {
        return new RedisCategoryModel(id, category, words);
    }
}
