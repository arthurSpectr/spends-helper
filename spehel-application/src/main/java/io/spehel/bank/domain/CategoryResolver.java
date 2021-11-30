package io.spehel.bank.domain;

import io.spehel.bank.domain.entity.CategoriesWords;
import io.spehel.bank.domain.model.SpentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryResolver {

    @Autowired
    private CategoriesWordsRepository repository;


    // TODO optimize process with caching or elastic search or something
    SpentModel[] resolveCategories(SpentModel[] spends) {

        for (SpentModel spend : spends) {
            CategoriesWords byWords = repository.findByWords(List.of(spend.getDescription()));
            spend.setDescription(byWords.getCategory().toString());
        }

        return spends;
    }

}
