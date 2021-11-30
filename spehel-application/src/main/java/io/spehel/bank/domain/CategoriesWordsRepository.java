package io.spehel.bank.domain;

import io.spehel.bank.domain.entity.CategoriesWords;
import io.spehel.bank.domain.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesWordsRepository extends MongoRepository<CategoriesWords, String> {
    List<CategoriesWords> findAllByCategory(Category category);

    CategoriesWords findByWords(List<String> words);
}
