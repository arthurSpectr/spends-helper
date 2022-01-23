package io.spehel.bank.domain;

import io.spehel.bank.domain.model.Category;
import io.spehel.redis.domain.RedisCategoryModel;
import io.spehel.redis.domain.RedisCategoryRepository;
import io.spehel.spends.domain.SpentModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryResolver {

    @Autowired
    private RedisCategoryRepository repository;

    List<SpentModel> resolveCategories(List<SpentModel> spends) {

        for (SpentModel spend : spends) {
            Category category = resolve(spend.getDescription());

            if (category != null) {
                spend.setDescription(spend.getDescription() + " - " + category.name());
            } else {
                spend.setDescription(spend.getDescription() + " - " + "UNRECOGNISED");
            }
        }

        return spends;
    }

    // TODO improve mechanism for identifying category. Add saving updated record to mongodb
    public Category resolve(String description) {
        Iterable<RedisCategoryModel> categoryModels = repository.findAll();

        for (RedisCategoryModel categoryModel : categoryModels) {
            if (categoryModel.getWords() == null) continue;
            for (String word : categoryModel.getWords()) {
                if (StringUtils.containsIgnoreCase(word, description)) return categoryModel.getCategory();
            }
        }

        return null;
    }

}
