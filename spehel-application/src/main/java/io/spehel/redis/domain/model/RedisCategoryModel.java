package io.spehel.redis.domain.model;

import io.spehel.bank.domain.model.Category;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("Category")
public class RedisCategoryModel {
    public String id;

    private Category category;

    private List<String> words;

    public RedisCategoryModel(String id, Category category, List<String> words) {
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
}
