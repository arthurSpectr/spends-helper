package io.spehel.bank.domain;

import io.spehel.bank.domain.model.Category;
import io.spehel.bank.domain.model.SpentModelDTO;
import io.spehel.redis.domain.RedisCategoryModel;
import io.spehel.redis.domain.RedisCategoryRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Component
public class CategoryResolver {

    private static final Logger log = LoggerFactory.getLogger(CategoryResolver.class);

    @Autowired
    private RedisCategoryRepository repository;

    public List<SpentModelDTO> resolveCategories(List<SpentModelDTO> spends) {
        Iterable<RedisCategoryModel> categoryModels = repository.findAll();

        for (SpentModelDTO spend : spends) {

            String prettyDescription = spend.getDescription().replaceAll("\\s", "");
            Category category = resolve(prettyDescription, categoryModels);

            if (category != null) {
                spend.setDescription(prettyDescription + " - " + category.name());
            } else {
                spend.setDescription(prettyDescription + " - " + Category.UNRECOGNISED.name());
            }
        }

        return spends;
    }

    // TODO improve mechanism for identifying category. Add saving updated record to mongodb
    public Category resolve(String description, Iterable<RedisCategoryModel> categoryModels) {

        for (RedisCategoryModel categoryModel : categoryModels) {
            if (categoryModel.getWords() == null) continue;
            for (String word : categoryModel.getWords()) {
                if (StringUtils.containsIgnoreCase(description, word)) return categoryModel.getCategory();
            }
        }

        saveRecordsToFile(description);

        return null;
    }

    private void saveRecordsToFile(String description) {
        try {
            Files.write(Path.of("/Users/arthurtyan/Documents/artur/projects/spends-helper-service/log.txt"), (description + " - " + " Words - null\n\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

//            Files.write(Path.of("/Users/arthurtyan/Documents/artur/projects/spends-helper-service"), ("[ " + comparingWords.stream().collect(Collectors.joining(", ")) + " ]").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (Exception e) {
            log.error("error occurred - {0}", e);
        }
    }

}
