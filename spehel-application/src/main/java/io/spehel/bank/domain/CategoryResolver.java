package io.spehel.bank.domain;

import io.spehel.bank.domain.model.Category;
import io.spehel.bank.domain.model.SpentModelDTO;
import io.spehel.redis.domain.RedisCategoryRepository;
import io.spehel.redis.domain.model.RedisCategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryResolver {

    @Autowired
    private RedisCategoryRepository repository;

    List<SpentModelDTO> resolveCategories(List<SpentModelDTO> spends) {

        for (SpentModelDTO spend : spends) {
            Category category = resolve(spend.getDescription());

            if(category != null) {
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
        String[] split = description.split("[./\\n\\s]");

        for (RedisCategoryModel categoryModel : categoryModels) {
            for (String s : split) {

                if(categoryModel.getWords() == null) continue;
                for (String word : categoryModel.getWords()) {
                    if(word.contains(s)) return categoryModel.getCategory();
                }
            }
        }

        return null;
    }

}
