package io.spehel.configuration;

import io.spehel.bank.domain.CategoriesRepository;
import io.spehel.bank.domain.model.CategoryModel;
import io.spehel.redis.domain.RedisCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisActuator {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private RedisCategoryRepository redisCategoryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initCache() {
        redisCategoryRepository.deleteAll();
        List<CategoryModel> categories = categoriesRepository.findAll();

        for (CategoryModel category : categories) {
            redisCategoryRepository.save(category.toDTO());
        }
    }

}
